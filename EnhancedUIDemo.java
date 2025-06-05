/**
 * Enhanced UI Demo to showcase all the improved console features.
 */
import console.ui.ConsoleOutputView;
import console.ui.ConsoleInputHandler;
import player.accounts.Player;
import game.board.Board;

public class EnhancedUIDemo {
    public static void main(String[] args) {
        ConsoleOutputView view = new ConsoleOutputView();
        ConsoleInputHandler input = new ConsoleInputHandler();
        
        try {
            // Welcome sequence
            view.displayWelcome();
            view.displayPause();
            
            // Main menu demo
            view.displayMainMenu();
            view.displayPause();
            
            // Loading effect demo
            view.displayLoadingEffect("Initializing jungle battlefield");
            view.displayLoadingEffect("Placing animal champions");
            view.displayLoadingEffect("Setting up special zones");
            
            // Game start demo
            Player alice = new Player(1, "Alice");
            Player bob = new Player(2, "Bob");
            view.displayGameStart(alice, bob);
            
            // Create and display board
            Board board = new Board();
            view.displayBoard(board);
            view.displayPause();
            
            // Game tips
            view.displayGameTips();
            view.displayPause();
            
            // Turn demo
            view.displayCurrentTurn(alice, 1);
            System.out.println(); // Simulate user input space
            
            // Input parsing demo
            System.out.println("\n" + ConsoleOutputView.YELLOW + "=== INPUT PARSING DEMO ===" + ConsoleOutputView.RESET);
            String[] testInputs = {"A1 B1", "invalid", "help", "0,0 1,0", "quit"};
            
            for (String testInput : testInputs) {
                System.out.println("\nTesting input: '" + testInput + "'");
                ConsoleInputHandler.PositionParseResult result = input.parsePositionWithFeedback(testInput);
                if (result.isValid) {
                    view.displaySuccess("Valid input: " + result.suggestion);
                } else {
                    view.displayError(result.message + " - " + result.suggestion);
                }
            }
            
            // Message types demo
            System.out.println("\n" + ConsoleOutputView.YELLOW + "=== MESSAGE TYPES DEMO ===" + ConsoleOutputView.RESET);
            view.displaySuccess("Piece moved successfully!");
            view.displayError("Cannot capture your own piece");
            view.displayMessage("Game is progressing smoothly...");
            view.displayMoveAttempt("A1", "B1", true);
            view.displayMoveAttempt("C1", "C1", false);
            view.displayCapture("Tiger", "Wolf");
            view.displaySpecialMove("river jumping", "Lion");
            
            // Game status demo
            view.displayGameStatus(alice, 5, 8, 7);
            view.displayPause();
            
            // Help screen demo
            view.displayHelp();
            
            // Victory demo
            view.displayGameEnd(alice);
            view.displayGameStats(12, 180000); // 3 minutes
            
            view.displayGoodbye();
            
        } catch (Exception e) {
            System.err.println("Demo error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            input.close();
        }
    }
}
