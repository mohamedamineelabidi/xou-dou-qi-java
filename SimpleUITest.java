/**
 * Simple UI Test to verify our enhanced console features work.
 */
import console.ui.ConsoleOutputView;
import console.ui.ConsoleInputHandler;
import player.accounts.Player;
import game.board.Board;

public class SimpleUITest {
    public static void main(String[] args) {
        try {
            ConsoleOutputView view = new ConsoleOutputView();
            ConsoleInputHandler input = new ConsoleInputHandler();
            
            // Test welcome display
            view.displayWelcome();
            
            // Test enhanced board display
            Player alice = new Player(1, "Alice");
            Player bob = new Player(2, "Bob");
            Board board = new Board();
            
            view.displayGameStart(alice, bob);
            view.displayBoard(board);
            
            // Test input parsing
            System.out.println("\n" + ConsoleOutputView.YELLOW + "=== Testing Input Parsing ===" + ConsoleOutputView.RESET);
            
            String[] testInputs = {"A1 B1", "help", "invalid"};
            for (String testInput : testInputs) {
                System.out.println("Testing: '" + testInput + "'");
                ConsoleInputHandler.PositionParseResult result = input.parsePositionWithFeedback(testInput);
                if (result.isValid) {
                    view.displaySuccess("Valid: " + result.suggestion);
                } else {
                    view.displayError(result.message);
                }
            }
            
            // Test different message types
            view.displaySuccess("Move completed successfully!");
            view.displayError("Invalid move attempted");
            view.displayMessage("Game is in progress...");
            
            view.displayGoodbye();
            input.close();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
