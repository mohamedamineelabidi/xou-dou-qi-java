package game.core;

/**
 * Enum representing the possible states of a game.
 */
public enum GameStatus {
    IN_PROGRESS,    // Game is currently being played
    FINISHED,       // Game has ended with a winner
    PAUSED,         // Game is temporarily paused
    ABANDONED       // Game was abandoned/quit
}
