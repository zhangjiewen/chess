package net.marvk.chess.board;

import java.util.Arrays;

public final class Boards {
    private Boards() {
        throw new AssertionError("No instances of utility class " + Boards.class);
    }

    public static final Fen STARTING_POSITION = Fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

    public static Board startingPosition() {
        return new SimpleBoard(STARTING_POSITION);
    }

    public static void parsePiecePlacement(final String piecePlacement, final ColoredPiece[][] target) {
        final String[] ranks = piecePlacement.split("/");

        for (int rank = 0; rank < target.length; rank++) {
            final String rankRecord = ranks[ranks.length - rank - 1];

            int file = 0;

            for (final char c : rankRecord.toCharArray()) {
                if (c >= '0' && c <= '9') {
                    file += c - '0';
                } else {
                    final ColoredPiece piece = ColoredPiece.getPieceFromSan(c);

                    target[rank][file] = piece;

                    file += 1;
                }
            }
        }
    }
}
