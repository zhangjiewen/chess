package net.marvk.chess.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ColoredPiece {
    WHITE_KING(Piece.KING, Color.WHITE, 'K', '♔'),
    WHITE_QUEEN(Piece.QUEEN, Color.WHITE, 'Q', '♕'),
    WHITE_ROOK(Piece.ROOK, Color.WHITE, 'R', '♖'),
    WHITE_BISHOP(Piece.BISHOP, Color.WHITE, 'B', '♗'),
    WHITE_KNIGHT(Piece.KNIGHT, Color.WHITE, 'N', '♘'),
    WHITE_PAWN(Piece.PAWN, Color.WHITE, 'P', '♙'),
    BLACK_KING(Piece.KING, Color.BLACK, 'k', '♚'),
    BLACK_QUEEN(Piece.QUEEN, Color.BLACK, 'q', '♛'),
    BLACK_ROOK(Piece.ROOK, Color.BLACK, 'r', '♜'),
    BLACK_BISHOP(Piece.BISHOP, Color.BLACK, 'b', '♝'),
    BLACK_KNIGHT(Piece.KNIGHT, Color.BLACK, 'n', '♞'),
    BLACK_PAWN(Piece.PAWN, Color.BLACK, 'p', '♟');

    private final Piece piece;
    private final Color color;
    private final char san;
    private final char utf8Piece;

    private static final Map<Character, ColoredPiece> SAN_PIECE_MAP =
            Arrays.stream(ColoredPiece.values())
                  .collect(Collectors.toMap(ColoredPiece::getSan, Function.identity()));

    ColoredPiece(final Piece piece, final Color color, final char san, final char utf8Piece) {
        this.piece = piece;
        this.color = color;
        this.san = san;
        this.utf8Piece = utf8Piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public Color getColor() {
        return color;
    }

    public char getSan() {
        return san;
    }

    public char getUtf8Char() {
        return utf8Piece;
    }

    public static ColoredPiece getPieceFromSan(final char san) {
        return SAN_PIECE_MAP.get(san);
    }

    public static ColoredPiece getPiece(final Color color, final Piece piece) {
        return piece.ofColor(color);
    }
}
