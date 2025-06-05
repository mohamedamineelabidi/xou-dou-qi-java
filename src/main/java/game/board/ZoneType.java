package game.board;

/**
 * Enum representing the different types of zones on the game board.
 */
public enum ZoneType {
    NORMAL,     // Regular land square
    RIVER,      // Water square (2 lakes, each 2x3 squares)
    TRAP,       // Trap square (3 per player, near sanctuary)
    SANCTUARY   // Goal square (1 per player)
}
