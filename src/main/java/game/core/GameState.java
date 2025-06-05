package game.core;

import game.board.Board;
import player.accounts.Player;

/**
 * Represents the current state of a game in progress.
 * Tracks the board, players, current turn, and game status.
 */
public class GameState {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private final TurnManager turnManager;
    private GameStatus status;
    private Player winner;
    
    public GameState(Player player1, Player player2) {
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.turnManager = new TurnManager(player1, player2);
        this.status = GameStatus.IN_PROGRESS;
        this.winner = null;
    }
    
    // Getters
    public Board getBoard() { return board; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public TurnManager getTurnManager() { return turnManager; }
    public GameStatus getStatus() { return status; }
    public Player getWinner() { return winner; }
    public Player getCurrentPlayer() { return turnManager.getCurrentPlayer(); }
    
    // Game state management
    public void setStatus(GameStatus status) { this.status = status; }
    public void setWinner(Player winner) { 
        this.winner = winner;
        this.status = GameStatus.FINISHED;
    }
    
    public boolean isGameOver() {
        return status == GameStatus.FINISHED;
    }
    
    public boolean isInProgress() {
        return status == GameStatus.IN_PROGRESS;
    }
}
