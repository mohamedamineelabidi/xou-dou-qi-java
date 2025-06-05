package game.core;

import player.accounts.Player;

/**
 * Manages turn sequence and tracks whose turn it is to play.
 */
public class TurnManager {
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private int turnCount;
    
    public TurnManager(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1; // Player 1 starts
        this.turnCount = 1;
    }
    
    /**
     * Switch to the next player's turn.
     */
    public void nextTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        turnCount++;
    }
    
    /**
     * Get the player whose turn it currently is.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Get the other player (not current player).
     */
    public Player getOtherPlayer() {
        return (currentPlayer == player1) ? player2 : player1;
    }
    
    /**
     * Check if it's player 1's turn.
     */
    public boolean isPlayer1Turn() {
        return currentPlayer == player1;
    }
    
    /**
     * Check if it's player 2's turn.
     */
    public boolean isPlayer2Turn() {
        return currentPlayer == player2;
    }
    
    /**
     * Get the current turn number.
     */
    public int getTurnCount() {
        return turnCount;
    }
    
    /**
     * Reset turn manager to initial state.
     */
    public void reset() {
        currentPlayer = player1;
        turnCount = 1;
    }
}
