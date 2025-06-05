/**
 * Interactive test to showcase the enhanced console UI for Xou Dou Qi.
 * This demonstrates all the visual improvements and enhanced input parsing.
 */
import console.ui.ConsoleOutputView;
import console.ui.ConsoleInputHandler;
import player.accounts.Player;
import game.board.Board;
import game.core.GameManager;
import game.core.GameState;

public class InteractiveUIShowcase {
    public static void main(String[] args) {
        ConsoleOutputView view = new ConsoleOutputView();
        ConsoleInputHandler input = new ConsoleInputHandler();
        
        try {
            // Show welcome screen
            view.displayWelcome();
            System.out.println(view.CYAN + "Press Enter to continue through the UI showcase..." + view.RESET);
            input.readLine();
            
            // Demo 1: Enhanced Main Menu
            view.displayMainMenu();
            System.out.println("\n" + view.YELLOW + "📋 Main menu with colors and emoji!" + view.RESET);
            input.readLine();
            
            // Demo 2: Game Setup
            Player alice = new Player(1, "Alice");
            Player bob = new Player(2, "Bob");
            view.displayGameStart(alice, bob);
            
            // Demo 3: Enhanced Board Display
            Board board = new Board();
            view.displayBoard(board);
            System.out.println(view.YELLOW + "🎯 Notice the colorized board with symbols and grid!" + view.RESET);
            input.readLine();
            
            // Demo 4: Turn Display
            view.displayCurrentTurn(alice, 1);
            System.out.println();
            System.out.println(view.YELLOW + "📝 Enhanced turn display with better input prompts!" + view.RESET);
            input.readLine();
            
            // Demo 5: Input Parsing Showcase
            System.out.println("\n" + view.PURPLE + view.BOLD + "=== INPUT PARSING DEMO ===" + view.RESET);
            System.out.println(view.CYAN + "Testing various input formats..." + view.RESET);
            
            String[] inputs = {"A1 B1", "0,0 1,0", "a1-b1", "invalid move", "help", "quit"};
            for (String testInput : inputs) {
                System.out.println("\n" + view.BLUE + "Input: '" + testInput + "'" + view.RESET);
                ConsoleInputHandler.PositionParseResult result = input.parsePositionWithFeedback(testInput);
                
                if (result.isValid) {
                    view.displaySuccess("✓ Parsed: " + result.suggestion);
                } else {
                    view.displayError(result.message);
                    System.out.println(view.YELLOW + "💡 " + result.suggestion + view.RESET);
                }
            }
            
            input.readLine();
            
            // Demo 6: Message Types
            System.out.println("\n" + view.PURPLE + view.BOLD + "=== MESSAGE TYPES DEMO ===" + view.RESET);
            view.displaySuccess("Move executed successfully!");
            view.displayError("Invalid move: Piece cannot move there");
            view.displayMessage("Game continues...");
            view.displayMoveAttempt("A1", "B1", true);
            view.displayCapture("Lion", "Dog");
            view.displaySpecialMove("river swimming", "Rat");
            input.readLine();
            
            // Demo 7: Game Status
            view.displayGameStatus(alice, 8, 7, 6);
            input.readLine();
            
            // Demo 8: Help Screen
            view.displayHelp();
            
            // Demo 9: Victory Screen
            view.displayGameEnd(alice);
            view.displayGameStats(15, 240000); // 4 minutes
            
            // Demo 10: Tips
            view.displayGameTips();
            input.readLine();
            
            view.displayGoodbye();
            
            System.out.println("\n" + view.GREEN + view.BOLD + "🎉 UI Showcase Complete!" + view.RESET);
            System.out.println(view.CYAN + "Enhanced features demonstrated:" + view.RESET);
            System.out.println(view.WHITE + "• Colorized board with symbols and grid borders" + view.RESET);
            System.out.println(view.WHITE + "• Enhanced input parsing with detailed feedback" + view.RESET);
            System.out.println(view.WHITE + "• Improved message formatting and visual feedback" + view.RESET);
            System.out.println(view.WHITE + "• Better game state displays and status updates" + view.RESET);
            System.out.println(view.WHITE + "• Professional-looking help and menu systems" + view.RESET);
            
        } catch (Exception e) {
            System.err.println("Showcase error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            input.close();
        }
    }
}
