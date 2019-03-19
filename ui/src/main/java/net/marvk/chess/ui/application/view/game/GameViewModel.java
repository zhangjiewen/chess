package net.marvk.chess.ui.application.view.game;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;
import net.marvk.chess.core.bitboards.Bitboard;
import net.marvk.chess.core.board.*;
import net.marvk.chess.ui.application.view.board.BoardStateViewModel;
import net.marvk.chess.ui.model.AsyncPlayer;
import net.marvk.chess.ui.model.Game;
import net.marvk.chess.ui.model.Player;
import net.marvk.chess.ui.model.PlayerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Log4j2
public class GameViewModel implements ViewModel {
    private final SimpleObjectProperty<BoardStateViewModel> boardState = new SimpleObjectProperty<>();
    private final SimpleBooleanProperty auto = new SimpleBooleanProperty();
    private final SimpleObjectProperty<PlayerFactory> whiteFactory = new SimpleObjectProperty<>(AsyncPlayer::new);
    private final SimpleObjectProperty<PlayerFactory> blackFactory = new SimpleObjectProperty<>(AsyncPlayer::new);
    private final SimpleBooleanProperty gameInProgress = new SimpleBooleanProperty(false);

    private final SimpleBooleanProperty abortGame = new SimpleBooleanProperty(false);

    private Game game;
    private final ExecutorService gameExecutor;

    private CountDownLatch countDownLatch;

    public GameViewModel() {
        this.gameExecutor = Executors.newSingleThreadExecutor();

        auto.addListener((observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                next();
            }
        });
    }

    public SimpleBooleanProperty autoProperty() {
        return auto;
    }

    public void setMove(final Move move) {
        final Player white = game.getPlayer(Color.WHITE);

        if (white instanceof AsyncPlayer) {
            ((AsyncPlayer) white).setMove(move);
        }

        final Player black = game.getPlayer(Color.BLACK);

        if (black instanceof AsyncPlayer) {
            ((AsyncPlayer) black).setMove(move);
        }
    }

    public void start() {
        gameExecutor.execute(() -> {
            Platform.runLater(() -> gameInProgress.set(true));

            game = new Game(
                    whiteFactory.get(),
                    blackFactory.get()
            );

            Platform.runLater(this::updateBoard);

            log.info("Start game loop");

            while (!game.isGameOver() && !abortGame.get()) {
                final Bitboard lastBoard = game.getBoard();

                final Optional<MoveResult> moveResult = game.nextMove();
                Platform.runLater(() -> boardState.set(new BoardStateViewModel(
                        lastBoard,
                        game.getLastMove().getMove(),
                        lastEvaluation(),
                        0.
                )));

                if (!auto.get()) {
                    countDownLatch = new CountDownLatch(1);
                    try {
                        countDownLatch.await();
                    } catch (final InterruptedException e) {
                        log.error("", e);
                        Thread.currentThread().interrupt();
                    }
                }

                moveResult.ifPresent(result -> Platform.runLater(() -> boardState.set(new BoardStateViewModel(
                        game.getBoard(),
                        game.getLastMove().getMove(),
                        lastEvaluation(),
                        0.
                ))));
            }

            Platform.runLater(() -> {
                abortGame.set(false);
                gameInProgress.set(false);
            });
        });
    }

    public void updateBoard() {
        boardState.set(new BoardStateViewModel(game.getBoard(), game.getLastMove().getMove(), lastEvaluation(), 0.));
    }

    private Map<Move, Double> lastEvaluation() {

        final Player player = game.getPlayer(game.getTurn().opposite());

//        if (player instanceof LastEvaluationGettable) {
//            final Map<Move, Double> map = ((LastEvaluationGettable) player).getLastEvaluation();
//
//            return map == null ? Collections.emptyMap() : map;
//        } else {
//            return Collections.emptyMap();
//        }

        return Collections.emptyMap();
    }

    public void next() {
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public void move(final Move move) {
        tryMove(game.getPlayer(Color.WHITE), move);
        tryMove(game.getPlayer(Color.BLACK), move);
    }

    private static void tryMove(final Player player, final Move move) {
        if (player instanceof AsyncPlayer) {
            ((AsyncPlayer) player).setMove(move);
        }
    }

    public BoardStateViewModel getBoardState() {
        return boardState.get();
    }

    public SimpleObjectProperty<BoardStateViewModel> boardStateProperty() {
        return boardState;
    }

    static ObservableList<PlayerFactoryChoiceBoxViewModel> playerFactoryChoiceBoxViewModels() {
        return FXCollections.observableArrayList(
                new PlayerFactoryChoiceBoxViewModel(AsyncPlayer::new, "Human")
//                ,
//                new PlayerFactoryChoiceBoxViewModel(color -> new AlphaBetaPlayerExplicit(color, new SimpleHeuristic(), 1), "AlphaBetaPlayerExplicit(1)"),
//                new PlayerFactoryChoiceBoxViewModel(color -> new AlphaBetaPlayerExplicit(color, new SimpleHeuristic(), 2), "AlphaBetaPlayerExplicit(2)"),
//                new PlayerFactoryChoiceBoxViewModel(color -> new AlphaBetaPlayerExplicit(color, new SimpleHeuristic(), 3), "AlphaBetaPlayerExplicit(3)"),
//                new PlayerFactoryChoiceBoxViewModel(color -> new AlphaBetaPlayerExplicit(color, new SimpleHeuristic(), 4), "AlphaBetaPlayerExplicit(4)"),
//                new PlayerFactoryChoiceBoxViewModel(color -> new AlphaBetaPlayerExplicit(color, new SimpleHeuristic(), 5), "AlphaBetaPlayerExplicit(5)"),
//                new PlayerFactoryChoiceBoxViewModel(color -> new AlphaBetaPlayerExplicit(color, new SimpleHeuristic(), 6), "AlphaBetaPlayerExplicit(6)"),
//                new PlayerFactoryChoiceBoxViewModel(color -> new AlphaBetaPlayerExplicit(color, new SimpleHeuristic(), 7), "AlphaBetaPlayerExplicit(7)"),
//                new PlayerFactoryChoiceBoxViewModel(color -> new AlphaBetaPlayerExplicit(color, new SimpleHeuristic(), 8), "AlphaBetaPlayerExplicit(8)"),
//                new PlayerFactoryChoiceBoxViewModel(color -> new AlphaBetaPlayerExplicit(color, new SimpleHeuristic(), 9), "AlphaBetaPlayerExplicit(9)")
        );
    }

    public PlayerFactory getWhiteFactory() {
        return whiteFactory.get();
    }

    public SimpleObjectProperty<PlayerFactory> whiteFactoryProperty() {
        return whiteFactory;
    }

    public void setWhiteFactory(final PlayerFactory whiteFactory) {
        this.whiteFactory.set(whiteFactory);
    }

    public PlayerFactory getBlackFactory() {
        return blackFactory.get();
    }

    public SimpleObjectProperty<PlayerFactory> blackFactoryProperty() {
        return blackFactory;
    }

    public void setBlackFactory(final PlayerFactory blackFactory) {
        this.blackFactory.set(blackFactory);
    }

    public boolean isGameInProgress() {
        return gameInProgress.get();
    }

    public SimpleBooleanProperty gameInProgressProperty() {
        return gameInProgress;
    }

    public void abort() {
        if (gameInProgress.get()) {
            abortGame.set(true);
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    public boolean isAbortGame() {
        return abortGame.get();
    }

    public SimpleBooleanProperty abortGameProperty() {
        return abortGame;
    }
}
