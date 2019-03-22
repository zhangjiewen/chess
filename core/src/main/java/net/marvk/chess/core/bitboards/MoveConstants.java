package net.marvk.chess.core.bitboards;

import net.marvk.chess.core.board.Piece;

/*
 * MSB . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . LSB
 *
 * xxxxxxxxxxxxxxx xxxxxx xxxxxx xxxxxxxxxxxx x  xxxxxx xxxxxx x x x x x x xxx xxx
 *                    |       |         |     |     |      |   | | | | | |  |   |
 *                    |       |         |     |     |      |   | | | | | |  |   |
 * _____UNUSED____    |       |         |     |     |      |   | | | | | |  |    --> Piece moved
 *                    |       |         |     |     |      |   | | | | | |  |
 *                    |       |         |     |     |      |   | | | | | |   ------> Piece attacked
 *                    |       |         |     |     |      |   | | | | | |
 *                    |       |         |     |     |      |   | | | | |  ---------> Self lost king side castle
 *                    |       |         |     |     |      |   | | | | |
 *                    |       |         |     |     |      |   | | | |  -----------> Self lost queen side castle
 *                    |       |         |     |     |      |   | | | |
 *                    |       |         |     |     |      |   | | |  -------------> Opponent lost king side castle
 *                    |       |         |     |     |      |   | | |
 *                    |       |         |     |     |      |   | |  ---------------> Opponent lost queen side castle
 *                    |       |         |     |     |      |   | |
 *                    |       |         |     |     |      |   |  -----------------> Is castle move
 *                    |       |         |     |     |      |   |
 *                    |       |         |     |     |      |    -------------------> Is en passant attack
 *                    |       |         |     |     |      |
 *                    |       |         |     |     |       -----------------------> Source square
 *                    |       |         |     |     |
 *                    |       |         |     |      ------------------------------> Target square
 *                    |       |         |     |
 *                    |       |         |      ------------------------------------> Halfmove reset
 *                    |       |         |
 *                    |       |          ------------------------------------------> Previous halfmove
 *                    |       |
 *                    |         ---------------------------------------------------> Previous en passant square } Technically you can use files
 *                    |
 *                     ------------------------------------------------------------> Next en passant square     } and get that information from the pawn move
 */
public final class MoveConstants {
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public static final long NO_SQUARE = 0L;

    public static final int NO_PIECE = 0;
    public static final int PAWN     = 0b001;
    public static final int KNIGHT   = 0b010;
    public static final int BISHOP   = 0b011;
    public static final int ROOK     = 0b100;
    public static final int QUEEN    = 0b101;
    public static final int KING     = 0b110;

    public static final Piece[] PIECES = {
            null,
            Piece.PAWN,
            Piece.KNIGHT,
            Piece.BISHOP,
            Piece.ROOK,
            Piece.QUEEN,
            Piece.KING
    };

    public static final long PIECE_MOVED_MASK                      = 0x7L;
    public static final long PIECE_ATTACKED_MASK                   = 0x38L;
    public static final long SELF_LOST_KING_SIDE_CASTLE_MASK       = 0x40L;
    public static final long SELF_LOST_QUEEN_SIDE_CASTLE_MASK      = 0x80L;
    public static final long OPPONENT_LOST_KING_SIDE_CASTLE_MASK   = 0x100L;
    public static final long OPPONENT_LOST_QUEEN_SIDE_CASTLE_MASK  = 0x200L;
    public static final long CASTLE_MOVE_MASK                      = 0x400L;
    public static final long EN_PASSANT_ATTACK_MASK                = 0x800L;
    public static final long SOURCE_SQUARE_INDEX_MASK              = 0x3f000L;
    public static final long TARGET_SQUARE_INDEX_MASK              = 0xfc0000L;
    public static final long HALFMOVE_RESET_MASK                   = 0x1000000L;
    public static final long PREVIOUS_HALFMOVE_MASK                = 0x1ffe000000L;
    public static final long PREVIOUS_EN_PASSANT_SQUARE_INDEX_MASK = 0x7e000000000L;
    public static final long NEXT_EN_PASSANT_SQUARE_INDEX_MASK     = 0x1f80000000000L;
    public static final long PROMOTION_PIECE_MASK                  = 0xe000000000000L;
    public static final long NOT_USED_MASK                         = 0xfff0000000000000L;

    public static final int PIECE_MOVED_SHIFT                      = Long.numberOfTrailingZeros(PIECE_MOVED_MASK);
    public static final int PIECE_ATTACKED_SHIFT                   = Long.numberOfTrailingZeros(PIECE_ATTACKED_MASK);
    public static final int SELF_LOST_KING_SIDE_CASTLE_SHIFT       = Long.numberOfTrailingZeros(SELF_LOST_KING_SIDE_CASTLE_MASK);
    public static final int SELF_LOST_QUEEN_SIDE_CASTLE_SHIFT      = Long.numberOfTrailingZeros(SELF_LOST_QUEEN_SIDE_CASTLE_MASK);
    public static final int OPPONENT_LOST_KING_SIDE_CASTLE_SHIFT   = Long.numberOfTrailingZeros(OPPONENT_LOST_KING_SIDE_CASTLE_MASK);
    public static final int OPPONENT_LOST_QUEEN_SIDE_CASTLE_SHIFT  = Long.numberOfTrailingZeros(OPPONENT_LOST_QUEEN_SIDE_CASTLE_MASK);
    public static final int CASTLE_MOVE_SHIFT                      = Long.numberOfTrailingZeros(CASTLE_MOVE_MASK);
    public static final int EN_PASSANT_ATTACK_SHIFT                = Long.numberOfTrailingZeros(EN_PASSANT_ATTACK_MASK);
    public static final int SOURCE_SQUARE_INDEX_SHIFT              = Long.numberOfTrailingZeros(SOURCE_SQUARE_INDEX_MASK);
    public static final int TARGET_SQUARE_INDEX_SHIFT              = Long.numberOfTrailingZeros(TARGET_SQUARE_INDEX_MASK);
    public static final int HALFMOVE_RESET_SHIFT                   = Long.numberOfTrailingZeros(HALFMOVE_RESET_MASK);
    public static final int PREVIOUS_HALFMOVE_SHIFT                = Long.numberOfTrailingZeros(PREVIOUS_HALFMOVE_MASK);
    public static final int PREVIOUS_EN_PASSANT_SQUARE_INDEX_SHIFT = Long.numberOfTrailingZeros(PREVIOUS_EN_PASSANT_SQUARE_INDEX_MASK);
    public static final int NEXT_EN_PASSANT_SQUARE_INDEX_SHIFT     = Long.numberOfTrailingZeros(NEXT_EN_PASSANT_SQUARE_INDEX_MASK);
    public static final int PROMOTION_PIECE_SHIFT                  = Long.numberOfTrailingZeros(PROMOTION_PIECE_MASK);
    public static final int NOT_USED_SHIFT                         = Long.numberOfTrailingZeros(NOT_USED_MASK);

    public static final int A1 = 0;
    public static final int A2 = 1;
    public static final int A3 = 2;
    public static final int A4 = 3;
    public static final int A5 = 4;
    public static final int A6 = 5;
    public static final int A7 = 6;
    public static final int A8 = 7;
    public static final int B1 = 8;
    public static final int B2 = 9;
    public static final int B3 = 10;
    public static final int B4 = 11;
    public static final int B5 = 12;
    public static final int B6 = 13;
    public static final int B7 = 14;
    public static final int B8 = 15;
    public static final int C1 = 16;
    public static final int C2 = 17;
    public static final int C3 = 18;
    public static final int C4 = 19;
    public static final int C5 = 20;
    public static final int C6 = 21;
    public static final int C7 = 22;
    public static final int C8 = 23;
    public static final int D1 = 24;
    public static final int D2 = 25;
    public static final int D3 = 26;
    public static final int D4 = 27;
    public static final int D5 = 28;
    public static final int D6 = 29;
    public static final int D7 = 30;
    public static final int D8 = 31;
    public static final int E1 = 32;
    public static final int E2 = 33;
    public static final int E3 = 34;
    public static final int E4 = 35;
    public static final int E5 = 36;
    public static final int E6 = 37;
    public static final int E7 = 38;
    public static final int E8 = 39;
    public static final int F1 = 40;
    public static final int F2 = 41;
    public static final int F3 = 42;
    public static final int F4 = 43;
    public static final int F5 = 44;
    public static final int F6 = 45;
    public static final int F7 = 46;
    public static final int F8 = 47;
    public static final int G1 = 48;
    public static final int G2 = 49;
    public static final int G3 = 50;
    public static final int G4 = 51;
    public static final int G5 = 52;
    public static final int G6 = 53;
    public static final int G7 = 54;
    public static final int G8 = 55;
    public static final int H1 = 56;
    public static final int H2 = 57;
    public static final int H3 = 58;
    public static final int H4 = 59;
    public static final int H5 = 60;
    public static final int H6 = 61;
    public static final int H7 = 62;
    public static final int H8 = 63;

    private MoveConstants() {
        throw new AssertionError("No instances of utility class " + MoveConstants.class);
    }
}
