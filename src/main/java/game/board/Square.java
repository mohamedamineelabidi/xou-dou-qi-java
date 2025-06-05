package game.board;

import game.pieces.Piece;

/**
 * Represents a single square on the game board.
 * Each square has a type (normal, river, trap, sanctuary) and may contain a piece.
 */
public class Square {
    private final int column;
    private final int row;
    private final ZoneType zoneType;
    private final int owner; // 0 = neutral, 1 = player 1, 2 = player 2 (for traps/sanctuaries)
    private Piece piece;
    
    public Square(int column, int row, ZoneType zoneType, int owner) {
        this.column = column;
        this.row = row;
        this.zoneType = zoneType;
        this.owner = owner;
        this.piece = null;
    }
    
    public Square(int column, int row, ZoneType zoneType) {
        this(column, row, zoneType, 0); // neutral by default
    }
    
    // Getters
    public int getColumn() { return column; }
    public int getRow() { return row; }
    public ZoneType getZoneType() { return zoneType; }
    public int getOwner() { return owner; }
    public Piece getPiece() { return piece; }
    
    // Piece management
    public void setPiece(Piece piece) { this.piece = piece; }
    public void removePiece() { this.piece = null; }
    public boolean hasPiece() { return piece != null; }
    public boolean isEmpty() { return piece == null; }
    
    // Zone type checks
    public boolean isRiver() { return zoneType == ZoneType.RIVER; }
    public boolean isTrap() { return zoneType == ZoneType.TRAP; }
    public boolean isSanctuary() { return zoneType == ZoneType.SANCTUARY; }
    public boolean isNormal() { return zoneType == ZoneType.NORMAL; }
    
    // Owner checks for special zones
    public boolean isPlayer1Zone() { return owner == 1; }
    public boolean isPlayer2Zone() { return owner == 2; }
    public boolean isNeutralZone() { return owner == 0; }
    
    @Override
    public String toString() {
        return String.format("Square[%d,%d](%s%s)", 
            column, row, zoneType, 
            owner > 0 ? "-P" + owner : "");
    }
}
