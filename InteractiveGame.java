/**
 * Interactive Xou Dou Qi Game with full player interaction, name input, and enhanced UI.
 */
import console.ui.ConsoleOutputView;
import console.ui.ConsoleInputHandler;
import player.accounts.Player;
import game.board.Board;
import game.core.GameState;
import game.core.GameStatus;
import game.core.TurnManager;

public class InteractiveGame {
    private final ConsoleOutputView view;
    private final ConsoleInputHandler input;
    
    public InteractiveGame() {
        this.view = new ConsoleOutputView();
        this.input = new ConsoleInputHandler();
    }
    
    public static void main(String[] args) {
        InteractiveGame game = new InteractiveGame();
        game.start();
    }
    
    public void start() {
        try {
            // Welcome and setup
            view.displayWelcome();
            view.displayPause();
            
            // Get player names
            Player[] players = setupPlayers();
            Player player1 = players[0];
            Player player2 = players[1];
            
            // Show game rules if needed
            if (askForRules()) {
                view.displayHelp();
            }
            
            // Start the game
            playGame(player1, player2);
            
        } catch (Exception e) {
            view.displayError("Game error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            view.displayGoodbye();
            input.close();
        }
    }
    
    /**
     * Setup players by getting their names
     */
    private Player[] setupPlayers() {
        view.clearScreen();
        view.printBoxed("🎮 PLAYER SETUP 🎮", ConsoleOutputView.CYAN + ConsoleOutputView.BOLD);
        System.out.println();
        
        // Get Player 1 name
        System.out.println(ConsoleOutputView.GREEN + ConsoleOutputView.BOLD + "🟢 Player 1 Setup:" + ConsoleOutputView.RESET);
        String player1Name = input.readString(ConsoleOutputView.WHITE + "Enter Player 1's name: " + ConsoleOutputView.RESET);
        while (player1Name.trim().isEmpty()) {
            view.displayError("Name cannot be empty!");
            player1Name = input.readString(ConsoleOutputView.WHITE + "Enter Player 1's name: " + ConsoleOutputView.RESET);
        }
        
        System.out.println();
        
        // Get Player 2 name
        System.out.println(ConsoleOutputView.RED + ConsoleOutputView.BOLD + "🔴 Player 2 Setup:" + ConsoleOutputView.RESET);
        String player2Name = input.readString(ConsoleOutputView.WHITE + "Enter Player 2's name: " + ConsoleOutputView.RESET);
        while (player2Name.trim().isEmpty() || player2Name.trim().equalsIgnoreCase(player1Name.trim())) {
            if (player2Name.trim().isEmpty()) {
                view.displayError("Name cannot be empty!");
            } else {
                view.displayError("Player 2's name must be different from Player 1's!");
            }
            player2Name = input.readString(ConsoleOutputView.WHITE + "Enter Player 2's name: " + ConsoleOutputView.RESET);
        }
        
        // Create players
        Player player1 = new Player(1, player1Name.trim());
        Player player2 = new Player(2, player2Name.trim());
        
        // Confirmation
        System.out.println();
        view.displaySuccess("Players created successfully!");
        System.out.println(ConsoleOutputView.GREEN + "Player 1: " + ConsoleOutputView.WHITE + player1.getUsername() + 
                          ConsoleOutputView.GREEN + " (Green pieces, bottom of board)" + ConsoleOutputView.RESET);
        System.out.println(ConsoleOutputView.RED + "Player 2: " + ConsoleOutputView.WHITE + player2.getUsername() + 
                          ConsoleOutputView.RED + " (Red pieces, top of board)" + ConsoleOutputView.RESET);
        
        view.displayPause();
        return new Player[]{player1, player2};
    }
    
    /**
     * Ask if players want to see the rules
     */
    private boolean askForRules() {
        System.out.println();
        System.out.println(ConsoleOutputView.YELLOW + "Would you like to see the game rules and controls?" + ConsoleOutputView.RESET);
        System.out.print(ConsoleOutputView.WHITE + "Enter 'yes' or 'no': " + ConsoleOutputView.RESET);
        
        String response = input.readLine().toLowerCase().trim();
        return response.equals("yes") || response.equals("y") || response.equals("1");
    }
    
    /**
     * Main game loop
     */
    private void playGame(Player player1, Player player2) {
        GameState gameState = new GameState(player1, player2);
        Board board = gameState.getBoard();
        TurnManager turnManager = gameState.getTurnManager();
        
        // Game start display
        view.displayGameStart(player1, player2);
        
        // Display initial board
        view.displayBoard(board);
        view.displayGameTips();
        view.displayPause();
        
        long gameStartTime = System.currentTimeMillis();
        
        // Main game loop
        while (gameState.isInProgress()) {
            try {
                Player currentPlayer = turnManager.getCurrentPlayer();
                
                // Display current turn
                view.displayBoard(board);
                view.displayCurrentTurn(currentPlayer, turnManager.getTurnCount());
                
                // Get player input
                String userInput = input.readLine();
                
                // Handle input
                if (handleUserInput(userInput, gameState)) {
                    // Valid move was made, check win condition
                    if (board.checkWinCondition()) {
                        Integer winnerId = board.getWinner();
                        Player winner = (winnerId == 1) ? player1 : player2;
                        gameState.setWinner(winner);
                        
                        // Display victory
                        view.displayBoard(board);
                        view.displayGameEnd(winner);
                        
                        // Update player stats
                        winner.recordWin();
                        Player loser = (winner == player1) ? player2 : player1;
                        loser.recordLoss();
                        
                        // Display game statistics
                        long gameTime = System.currentTimeMillis() - gameStartTime;
                        view.displayGameStats(turnManager.getTurnCount(), gameTime);
                        
                        break;
                    }
                    
                    // Move to next turn
                    turnManager.nextTurn();
                } else if (gameState.getStatus() == GameStatus.ABANDONED) {
                    // Player quit
                    view.displayMessage("Game ended by player choice.");
                    break;
                }
                
            } catch (Exception e) {
                view.displayError("Error processing turn: " + e.getMessage());
            }
        }
        
        // Ask if they want to play again
        askForNewGame();
    }
    
    /**
     * Handle user input during gameplay
     */
    private boolean handleUserInput(String input, GameState gameState) {
        if (input == null || input.trim().isEmpty()) {
            view.displayError("Please enter a command or move.");
            return false;
        }
        
        String trimmedInput = input.trim().toLowerCase();
        
        // Handle special commands
        if (trimmedInput.equals("help") || trimmedInput.equals("h")) {
            view.displayHelp();
            return false;
        }
        
        if (trimmedInput.equals("quit") || trimmedInput.equals("exit") || trimmedInput.equals("q")) {
            if (confirmQuit()) {
                gameState.setStatus(GameStatus.ABANDONED);
                return false;
            }
            return false;
        }
        
        if (trimmedInput.equals("board") || trimmedInput.equals("show")) {
            view.displayBoard(gameState.getBoard());
            return false;
        }
        
        if (trimmedInput.equals("status")) {
            displayGameStatus(gameState);
            return false;
        }
        
        if (trimmedInput.equals("tips")) {
            view.displayGameTips();
            return false;
        }
        
        // Try to parse as move
        ConsoleInputHandler.PositionParseResult parseResult = input.parsePositionWithFeedback(input);
        
        if (!parseResult.isValid) {
            view.displayError(parseResult.message);
            System.out.println(ConsoleOutputView.YELLOW + "💡 " + parseResult.suggestion + ConsoleOutputView.RESET);
            return false;
        }
        
        // Attempt to make the move
        int[] coordinates = parseResult.coordinates;
        int fromCol = coordinates[0];
        int fromRow = coordinates[1];
        int toCol = coordinates[2];
        int toRow = coordinates[3];
        
        Board board = gameState.getBoard();
        Player currentPlayer = gameState.getCurrentPlayer();
        int playerNumber = currentPlayer.getPlayerId();
        
        // Show move attempt
        String fromPos = input.convertToAlgebraic(fromCol, fromRow);
        String toPos = input.convertToAlgebraic(toCol, toRow);
        
        if (board.makeMove(fromCol, fromRow, toCol, toRow, playerNumber)) {
            view.displayMoveAttempt(fromPos, toPos, true);
            
            // Check if it was a capture
            // Note: This is a simplified check - in a full implementation, 
            // we'd track this during the move
            if (board.getPiece(toCol, toRow) != null) {
                view.displaySuccess("Move successful!");
            }
            
            return true;
        } else {
            String reason = board.getInvalidMoveReason(fromCol, fromRow, toCol, toRow, playerNumber);
            view.displayMoveAttempt(fromPos, toPos, false);
            view.displayError("Invalid move: " + reason);
            return false;
        }
    }
    
    /**
     * Confirm if player really wants to quit
     */
    private boolean confirmQuit() {
        System.out.println();
        System.out.println(ConsoleOutputView.YELLOW + "Are you sure you want to quit? Progress will be lost." + ConsoleOutputView.RESET);
        System.out.print(ConsoleOutputView.WHITE + "Enter 'yes' to quit, anything else to continue: " + ConsoleOutputView.RESET);
        
        String response = input.readLine().toLowerCase().trim();
        return response.equals("yes") || response.equals("y");
    }
    
    /**
     * Display current game status
     */
    private void displayGameStatus(GameState gameState) {
        Board board = gameState.getBoard();
        TurnManager turnManager = gameState.getTurnManager();
        
        // Count pieces for each player
        int player1Pieces = 0;
        int player2Pieces = 0;
        
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 9; row++) {
                if (board.hasPiece(col, row)) {
                    int owner = board.getPiece(col, row).getOwner();
                    if (owner == 1) player1Pieces++;
                    else if (owner == 2) player2Pieces++;
                }
            }
        }
        
        view.displayGameStatus(turnManager.getCurrentPlayer(), turnManager.getTurnCount(), 
                              player1Pieces, player2Pieces);
    }
    
    /**
     * Ask if players want to play again
     */
    private void askForNewGame() {
        System.out.println();
        System.out.println(ConsoleOutputView.CYAN + "Would you like to play another game?" + ConsoleOutputView.RESET);
        System.out.print(ConsoleOutputView.WHITE + "Enter 'yes' to play again, anything else to exit: " + ConsoleOutputView.RESET);
        
        String response = input.readLine().toLowerCase().trim();
        if (response.equals("yes") || response.equals("y")) {
            start(); // Restart the game
        }
    }
}
