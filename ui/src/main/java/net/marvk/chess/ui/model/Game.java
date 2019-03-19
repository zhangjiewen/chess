package net.marvk.chess.ui.model;

import lombok.extern.log4j.Log4j2;
import net.marvk.chess.core.bitboards.Bitboard;
import net.marvk.chess.core.board.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
public class Game {
    private final Player whitePlayer;
    private final Player blackPlayer;

    private Bitboard board;
    private MoveResult lastMove;

    //    private final List<MoveResult> history;
    private List<MoveResult> validMoves;

    private boolean gameOver = false;

    public Game(final PlayerFactory white, final PlayerFactory black) {
        this.whitePlayer = white.create(Color.WHITE);
        this.blackPlayer = black.create(Color.BLACK);

        this.board = new Bitboard(Fen.STARTING_POSITION);
//        this.history = new ArrayList<>();

        this.lastMove = new MoveResult(board, Move.NULL_MOVE);
        this.validMoves = board.getValidMoves();

//        this.history.add(lastMove);
    }

    public synchronized Optional<MoveResult> nextMove() {
//        if (gameOver) {
//            throw new IllegalStateException("Game is over, no more moves");
//        }
//
//        final Move play = getPlayer(getTurn()).play(lastMove);
//
//        final Optional<MoveResult> maybeValidMove =
//                validMoves.stream()
//                          .filter(moveResult -> Objects.equals(moveResult.getMove(), play))
//                          .findFirst();
//
//        if (maybeValidMove.isPresent()) {
//            log.info(getTurn() + " played " + play);
//            lastMove = maybeValidMove.get();
//            board = lastMove.getBoard();
//
////            history.add(lastMove);
//        } else {
//            log.info(getTurn() + " tried to play invalid move " + play);
//            return Optional.empty();
//        }
//
//        board.getValidMoves();
//
//        final Optional<GameResult> gameResult = board.findGameResult();
//
//        if (gameResult.isPresent()) {
//            log.info(gameResult);
//
//            gameOver = true;
//        } else {
//            validMoves = board.getValidMoves();
//        }
//
//        log.info(board.getHalfmoveClock());
//
//        return Optional.of(lastMove);

        return Optional.empty();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getPlayer(final Color color) {
        Objects.requireNonNull(color);

        if (color == Color.WHITE) {
            return whitePlayer;
        }

        if (color == Color.BLACK) {
            return blackPlayer;
        }

        throw new AssertionError();
    }

    public Bitboard getBoard() {
        return board;
    }

    public MoveResult getLastMove() {
        return lastMove;
    }

    public Color getTurn() {
        return board.getActivePlayer();
    }

    public List<MoveResult> getHistory() {
        throw new UnsupportedOperationException();

//        return Collections.unmodifiableList(history);
    }

    public Player getActivePlayer() {
        return getPlayer(getTurn());
    }
}