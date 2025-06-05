package game.pieces;

/**
 * Rat piece - Rank 1 (lowest).
 * Special abilities:
 * - Can move into and within river squares
 * - Can capture Elephant (despite lower rank)
 * - Cannot capture when exiting river
 */
public class Rat extends Piece {
    public Rat(int owner) {
        super(PieceType.RAT, 1, owner);
    }
    
    @Override
    public boolean canCapture(Piece other) {
        if (other == null || other.owner == this.owner) {
            return false;
        }
        
        // Special rule: Rat can capture Elephant
        if (other.getType() == PieceType.ELEPHANT) {
            return true;
        }
        
        // TODO: Implement river exit restriction
        // For now, use standard rules
        return super.canCapture(other);
    }
    
    // TODO: Override canMoveTo to allow river movement
}
