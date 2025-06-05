package game.pieces;

/**
 * Cat piece - Rank 2.
 * Standard movement and capture rules.
 */
public class Cat extends Piece {
    public Cat(int owner) {
        super(PieceType.CAT, 2, owner);
    }
}
