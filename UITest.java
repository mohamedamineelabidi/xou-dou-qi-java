/**
 * Simple UI test to showcase the enhanced console interface.
 */
import console.ui.ConsoleOutputView;
import console.ui.ConsoleInputHandler;
import player.accounts.Player;

public class UITest {
    public static void main(String[] args) {
        ConsoleOutputView view = new ConsoleOutputView();
        ConsoleInputHandler input = new ConsoleInputHandler();
        
        // Test welcome screen
        view.displayWelcome();
        
        // Test main menu
        view.displayMainMenu();
        System.out.println("\n[Press Enter to see game start demo...]");
        input.readLine();
        
        // Test game start
        Player p1 = new Player(1, "Alice");
        Player p2 = new Player(2, "Bob");
        view.displayGameStart(p1, p2);
        
        // Test help screen
        view.displayHelp();
        
        // Test board display (minimal)
        System.out.println("\n[Press Enter to see board demo...]");
        input.readLine();
        
        // Test different message types
        view.displaySuccess("Great move!");
        view.displayError("Invalid move: Cannot capture your own piece");
        view.displayMessage("Game is progressing well...");
        
        // Test current turn display
        view.displayCurrentTurn(p1, 5);
        
        // Test game end
        System.out.println("\n[Press Enter to see victory screen...]");
        input.readLine();
        view.displayGameEnd(p1);
        
        view.displayGoodbye();
        input.close();
    }
}
