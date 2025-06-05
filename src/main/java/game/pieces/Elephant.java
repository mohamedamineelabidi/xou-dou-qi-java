package game.pieces;

/**
 * Elephant piece - Rank 8 (highest).
 * Special rule: Cannot capture Rat, but can be captured by Rat.
 */
public class Elephant extends Piece {
    public Elephant(int owner) {
        super(PieceType.ELEPHANT, 8, owner);
    }
    
    @Override
    public boolean canCapture(Piece other) {
        if (other == null || other.owner == this.owner) {
            return false;
        }
        
        // Special rule: Elephant cannot capture Rat
        if (other.getType() == PieceType.RAT) {
            return false;
        }
        
        return super.canCapture(other);
    }
}
