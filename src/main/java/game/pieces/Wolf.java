package game.pieces;

/**
 * Wolf piece - Rank 4.
 * Standard movement and capture rules.
 */
public class Wolf extends Piece {
    public Wolf(int owner) {
        super(PieceType.WOLF, 4, owner);
    }
}
