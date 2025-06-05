package game.pieces;

/**
 * Enum representing the different types of animal pieces in Xou Dou Qi.
 * Each piece type has a symbol for board display.
 */
public enum PieceType {
    ELEPHANT("E"),   // Rank 8 - Highest
    LION("L"),       // Rank 7
    TIGER("T"),      // Rank 6
    PANTHER("P"),    // Rank 5
    WOLF("W"),       // Rank 4
    DOG("D"),        // Rank 3
    CAT("C"),        // Rank 2
    RAT("R");        // Rank 1 - Lowest (but can capture Elephant)
    
    private final String symbol;
    
    PieceType(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() {
        return symbol;
    }
}
