package player.accounts;

/**
 * Represents a player in the game system.
 * Stores player information including ID, username, and game statistics.
 */
public class Player {
    private final int playerId;
    private final String username;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    
    public Player(int playerId, String username) {
        this.playerId = playerId;
        this.username = username;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
    }
    
    public Player(int playerId, String username, int gamesPlayed, int gamesWon, int gamesLost) {
        this.playerId = playerId;
        this.username = username;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
    }
    
    // Getters
    public int getPlayerId() { return playerId; }
    public String getUsername() { return username; }
    public int getGamesPlayed() { return gamesPlayed; }
    public int getGamesWon() { return gamesWon; }
    public int getGamesLost() { return gamesLost; }
    
    // Game statistics management
    public void recordWin() {
        gamesWon++;
        gamesPlayed++;
    }
    
    public void recordLoss() {
        gamesLost++;
        gamesPlayed++;
    }
    
    public double getWinRate() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        return (double) gamesWon / gamesPlayed;
    }
    
    @Override
    public String toString() {
        return String.format("Player[%d]: %s (W:%d L:%d Total:%d)", 
            playerId, username, gamesWon, gamesLost, gamesPlayed);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Player player = (Player) obj;
        return playerId == player.playerId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(playerId);
    }
}
