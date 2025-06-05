package game.pieces;

/**
 * Lion piece - Rank 7.
 * Special ability: Can jump over river if no rat is in the path.
 */
public class Lion extends Piece {
    public Lion(int owner) {
        super(PieceType.LION, 7, owner);
    }
    
    @Override
    public boolean canMoveTo(int targetCol, int targetRow) {
        // Standard adjacent movement
        if (super.canMoveTo(targetCol, targetRow)) {
            return true;
        }
        
        // TODO: Implement river jumping logic
        // For now, only basic movement
        return false;
    }
}
