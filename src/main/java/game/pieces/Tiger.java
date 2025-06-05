package game.pieces;

/**
 * Tiger piece - Rank 6.
 * Special ability: Can jump over river if no rat is in the path.
 */
public class Tiger extends Piece {
    public Tiger(int owner) {
        super(PieceType.TIGER, 6, owner);
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
