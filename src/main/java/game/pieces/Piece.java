package game.pieces;

/**
 * Abstract base class for all game pieces in Xou Dou Qi.
 * Each piece has a type, rank (for capture hierarchy), owner, and position.
 */
public abstract class Piece {
    protected final PieceType type;
    protected final int rank;      // 1-8, higher ranks can capture lower ranks
    protected final int owner;     // 1 = Player 1, 2 = Player 2
    protected int column;
    protected int row;
    
    protected Piece(PieceType type, int rank, int owner) {
        this.type = type;
        this.rank = rank;
        this.owner = owner;
        this.column = -1;  // Not placed initially
        this.row = -1;
    }
    
    // Getters
    public PieceType getType() { return type; }
    public int getRank() { return rank; }
    public int getOwner() { return owner; }
    public int getColumn() { return column; }
    public int getRow() { return row; }
    
    // Position management
    public void setPosition(int column, int row) {
        this.column = column;
        this.row = row;
    }
    
    /**
     * Returns the display symbol for this piece on the board.
     * Format: Letter for piece type + number for player (e.g., "E1" for Player 1's Elephant)
     */
    public String getSymbol() {
        return type.getSymbol() + owner;
    }
    
    /**
     * Check if this piece can capture another piece based on standard rules.
     * Special cases (like Rat capturing Elephant) are handled in subclasses.
     */
    public boolean canCapture(Piece other) {
        if (other == null || other.owner == this.owner) {
            return false;
        }
        
        // Standard rule: higher or equal rank captures lower rank
        return this.rank >= other.rank;
    }
    
    /**
     * Check if this piece can move to the specified position.
     * Base implementation checks for basic adjacency (1 square horizontal/vertical).
     * Subclasses override for special movement rules.
     */
    public boolean canMoveTo(int targetCol, int targetRow) {
        // Basic movement: one square horizontally or vertically
        int colDiff = Math.abs(targetCol - this.column);
        int rowDiff = Math.abs(targetRow - this.row);
        
        return (colDiff == 1 && rowDiff == 0) || (colDiff == 0 && rowDiff == 1);
    }
    
    @Override
    public String toString() {
        return String.format("%s (Player %d, Rank %d) at (%d,%d)", 
            type, owner, rank, column, row);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Piece piece = (Piece) obj;
        return rank == piece.rank && 
               owner == piece.owner && 
               type == piece.type;
    }
    
    @Override
    public int hashCode() {
        return type.hashCode() * 31 + owner * 7 + rank;
    }
}
