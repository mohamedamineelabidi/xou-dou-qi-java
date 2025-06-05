package game.pieces;

/**
 * Dog piece - Rank 3.
 * Standard movement and capture rules.
 */
public class Dog extends Piece {
    public Dog(int owner) {
        super(PieceType.DOG, 3, owner);
    }
}
