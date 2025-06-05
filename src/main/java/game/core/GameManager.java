package game.core;

import game.board.Board;
import player.accounts.AccountService;
import player.accounts.Player;
import console.ui.ConsoleOutputView;
import console.ui.ConsoleInputHandler;

/**
 * Central game manager that coordinates the overall game flow.
 * Handles game initialization, player management, and main game loop.
 */
public class GameManager {
    private final AccountService accountService;
    private final ConsoleOutputView outputView;
    private final ConsoleInputHandler inputHandler;
    private GameState currentGame;
    private boolean running;
    
    public GameManager(AccountService accountService, ConsoleOutputView outputView, ConsoleInputHandler inputHandler) {
        this.accountService = accountService;
        this.outputView = outputView;
        this.inputHandler = inputHandler;
        this.running = false;
    }
    
    /**
     * Start the main application loop.
     */
    public void start() {
        running = true;
        outputView.displayWelcome();
        
        while (running) {
            try {
                showMainMenu();
                // TODO: Handle user input for menu selection
                
                // For now, create a demo game to show the board
                startDemoGame();
                break; // Exit after demo for now
                
            } catch (Exception e) {
                outputView.displayError("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        outputView.displayGoodbye();
    }
    
    private void showMainMenu() {
        outputView.displayMainMenu();
    }
      /**
     * Start a new game between two players.
     */
    public void startNewGame(Player player1, Player player2) {
        currentGame = new GameState(player1, player2);
        outputView.displayGameStart(player1, player2);
        outputView.displayBoard(currentGame.getBoard());
        
        // Start the game loop
        playGame();
    }
    
    /**
     * Main game loop - handles turn-by-turn gameplay.
     */
    private void playGame() {
        while (currentGame.isInProgress() && running) {
            try {
                Player currentPlayer = currentGame.getCurrentPlayer();
                outputView.displayCurrentTurn(currentPlayer, currentGame.getTurnManager().getTurnCount());
                
                String input = inputHandler.readLine();
                
                // Handle special commands
                if (inputHandler.isCommand(input)) {
                    if (handleCommand(input)) {
                        continue; // Continue the game loop
                    } else {
                        break; // Exit command was entered
                    }
                }
                  // Parse move command with enhanced feedback
                ConsoleInputHandler.PositionParseResult parseResult = inputHandler.parsePositionWithFeedback(input);
                if (!parseResult.isValid) {
                    outputView.displayError(parseResult.message + ": " + parseResult.suggestion);
                    continue;
                }
                
                int[] move = parseResult.coordinates;
                
                int fromCol = move[0];
                int fromRow = move[1];
                int toCol = move[2];
                int toRow = move[3];                // Show move attempt feedback
                String fromPos = inputHandler.convertToAlgebraic(fromCol, fromRow);
                String toPos = inputHandler.convertToAlgebraic(toCol, toRow);
                
                // Attempt to make the move
                Board board = currentGame.getBoard();
                int playerNumber = (currentPlayer == currentGame.getPlayer1()) ? 1 : 2;
                
                if (board.makeMove(fromCol, fromRow, toCol, toRow, playerNumber)) {
                    outputView.displayMoveAttempt(fromPos, toPos, true);
                    outputView.displaySuccess("Move successful!");
                    outputView.displayBoard(board);
                    
                    // Check for win condition
                    if (board.checkWinCondition()) {
                        Integer winner = board.getWinner();
                        Player winningPlayer = (winner == 1) ? currentGame.getPlayer1() : currentGame.getPlayer2();
                        currentGame.setWinner(winningPlayer);
                        outputView.displayGameEnd(winningPlayer);
                        break;
                    }
                    
                    // Switch to next player's turn
                    currentGame.getTurnManager().nextTurn();
                } else {
                    String reason = board.getInvalidMoveReason(fromCol, fromRow, toCol, toRow, playerNumber);
                    outputView.displayError("Invalid move: " + reason);
                }
                
            } catch (Exception e) {
                outputView.displayError("Error processing move: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Handle special commands during gameplay.
     * Returns true to continue game, false to exit.
     */
    private boolean handleCommand(String command) {
        String cmd = command.toLowerCase().trim();
        
        switch (cmd) {
            case "help":
                outputView.displayHelp();
                return true;
            case "quit":
            case "exit":
                outputView.displayMessage("Game abandoned.");
                currentGame.setStatus(GameStatus.ABANDONED);
                return false;
            default:
                outputView.displayError("Unknown command: " + command);
                return true;
        }
    }
    
    /**
     * Temporary demo method to show the initial board setup.
     */
    private void startDemoGame() {
        // Create demo players
        Player demoPlayer1 = new Player(1, "DemoPlayer1");
        Player demoPlayer2 = new Player(2, "DemoPlayer2");
        
        outputView.displayMessage("Starting demo game to show initial board setup...");
        startNewGame(demoPlayer1, demoPlayer2);
    }
    
    public void stop() {
        running = false;
    }
    
    public GameState getCurrentGame() {
        return currentGame;
    }
}
