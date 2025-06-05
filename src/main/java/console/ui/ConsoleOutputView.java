package console.ui;

import game.board.Board;
import game.board.Square;
import game.pieces.Piece;
import player.accounts.Player;

/**
 * Handles all console output for the Xou Dou Qi application.
 * Manages display of the game board, messages, menus, and game information.
 */
public class ConsoleOutputView {
      // ANSI Color codes for better visual experience
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    
    // Box drawing characters for better borders
    private static final String TOP_LEFT = "╔";
    private static final String TOP_RIGHT = "╗";
    private static final String BOTTOM_LEFT = "╚";
    private static final String BOTTOM_RIGHT = "╝";
    private static final String HORIZONTAL = "═";
    private static final String VERTICAL = "║";
    private static final String T_DOWN = "╦";
    private static final String T_UP = "╩";
    private static final String T_RIGHT = "╠";
    private static final String T_LEFT = "╣";
    private static final String CROSS = "╬";
    
    public void displayWelcome() {
        clearScreen();
        System.out.println();
        printBoxed("🐘🐅🐀 XOU DOU QI: JAVA JUNGLE SHOWDOWN 🐀🐅🐘", CYAN + BOLD);
        System.out.println(YELLOW + "        Command your animal champions and conquer" + RESET);
        System.out.println(YELLOW + "                  the digital jungle!" + RESET);
        System.out.println();
        System.out.println(GREEN + "Welcome to the ultimate strategy battle!" + RESET);
        System.out.println();
    }
      public void displayMainMenu() {
        System.out.println(BLUE + BOLD + "═══════════════ MAIN MENU ═══════════════" + RESET);
        System.out.println();
        System.out.println(GREEN + " 1. " + WHITE + "🎮 Start New Game" + RESET);
        System.out.println(GREEN + " 2. " + WHITE + "👤 Login Existing Player" + RESET);
        System.out.println(GREEN + " 3. " + WHITE + "📝 Create New Account" + RESET);
        System.out.println(GREEN + " 4. " + WHITE + "📊 View Game History" + RESET);
        System.out.println(GREEN + " 5. " + WHITE + "🚪 Exit Game" + RESET);
        System.out.println();
        System.out.println(CYAN + "You can also type: 'new', 'login', 'create', 'history', or 'exit'" + RESET);
        System.out.println(BLUE + "═══════════════════════════════════════" + RESET);
        System.out.print(YELLOW + "Please select an option (1-5): " + RESET);
    }
      // Helper methods for better formatting
    public void clearScreen() {
        // Clear screen for Windows/Unix
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
            }
        } catch (Exception e) {
            // If clear fails, just add some space
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    public void printBoxed(String text, String color) {
        int width = text.length() + 4;
        String border = HORIZONTAL.repeat(width - 2);
        
        System.out.println(color + TOP_LEFT + border + TOP_RIGHT + RESET);
        System.out.println(color + VERTICAL + " " + text + " " + VERTICAL + RESET);
        System.out.println(color + BOTTOM_LEFT + border + BOTTOM_RIGHT + RESET);
    }
    
    private void printSeparator(String title) {
        System.out.println();
        System.out.println(BLUE + "═══════════════ " + title.toUpperCase() + " ═══════════════" + RESET);
    }
      public void displayBoard(Board board) {
        clearScreen();
        System.out.println();
        printBoxed("🎯 JUNGLE BATTLEFIELD 🎯", GREEN + BOLD);
        System.out.println();
        displayEnhancedBoard(board);
        System.out.println();
        displayColoredLegend();
    }
    
    /**
     * Display the board with enhanced visual formatting and colors.
     */
    private void displayEnhancedBoard(Board board) {
        // Print column headers (A-G)
        System.out.print(BLUE + "     ");
        for (int col = 0; col < 7; col++) {
            System.out.print(BOLD + CYAN + String.format("%4c", 'A' + col) + RESET);
        }
        System.out.println(RESET);
        
        // Print top border
        System.out.print(BLUE + "   " + TOP_LEFT);
        for (int col = 0; col < 7; col++) {
            System.out.print(HORIZONTAL.repeat(3));
            if (col < 6) System.out.print(T_DOWN);
        }
        System.out.println(TOP_RIGHT + RESET);
        
        // Print rows from top to bottom (row 8 to 0)
        for (int row = 8; row >= 0; row--) {
            // Row number
            System.out.print(BLUE + String.format("%2d ", row) + VERTICAL + RESET);
            
            for (int col = 0; col < 7; col++) {
                String cellContent = formatSquareContent(board, col, row);
                System.out.print(cellContent);
                
                if (col < 6) {
                    System.out.print(BLUE + VERTICAL + RESET);
                }
            }
            System.out.println(BLUE + VERTICAL + RESET);
            
            // Print row separator (except for last row)
            if (row > 0) {
                System.out.print(BLUE + "   " + T_RIGHT);
                for (int col = 0; col < 7; col++) {
                    System.out.print(HORIZONTAL.repeat(3));
                    if (col < 6) System.out.print(CROSS);
                }
                System.out.println(T_LEFT + RESET);
            }
        }
        
        // Print bottom border
        System.out.print(BLUE + "   " + BOTTOM_LEFT);
        for (int col = 0; col < 7; col++) {
            System.out.print(HORIZONTAL.repeat(3));
            if (col < 6) System.out.print(T_UP);
        }
        System.out.println(BOTTOM_RIGHT + RESET);
    }
    
    /**
     * Format the content of a square with appropriate colors and symbols.
     */
    private String formatSquareContent(Board board, int col, int row) {
        Square square = board.getSquare(col, row);
        if (square.hasPiece()) {
            Piece piece = square.getPiece();
            String pieceSymbol = piece.getSymbol();
            
            // Color pieces by player
            if (piece.getOwner() == 1) {
                return GREEN + BOLD + String.format("%3s", pieceSymbol) + RESET;
            } else {
                return RED + BOLD + String.format("%3s", pieceSymbol) + RESET;
            }
        } else {
            // Show zone type with colors
            switch (square.getZoneType()) {
                case RIVER:
                    return CYAN + BOLD + " ~ " + RESET;
                case TRAP:
                    String trapSymbol = square.getOwner() == 1 ? "T1" : "T2";
                    if (square.getOwner() == 1) {
                        return GREEN + String.format("%3s", trapSymbol) + RESET;
                    } else {
                        return RED + String.format("%3s", trapSymbol) + RESET;
                    }
                case SANCTUARY:
                    String sanctSymbol = square.getOwner() == 1 ? "S1" : "S2";
                    if (square.getOwner() == 1) {
                        return GREEN + UNDERLINE + String.format("%3s", sanctSymbol) + RESET;
                    } else {
                        return RED + UNDERLINE + String.format("%3s", sanctSymbol) + RESET;
                    }
                default:
                    return String.format("%3s", " · ");
            }
        }
    }
      /**
     * Display a colorized and enhanced legend.
     */
    private void displayColoredLegend() {
        printSeparator("LEGEND");
        System.out.println();
        
        // Piece legend with colors
        System.out.println(BOLD + WHITE + "🐾 ANIMAL PIECES:" + RESET);
        System.out.println(GREEN + "  Player 1: " + RESET + formatLegendPieces(1));
        System.out.println(RED + "  Player 2: " + RESET + formatLegendPieces(2));
        System.out.println();
        
        // Zone legend with colors
        System.out.println(BOLD + WHITE + "🗺️  SPECIAL ZONES:" + RESET);
        System.out.println(CYAN + BOLD + "  ~  " + RESET + "= River (only Rats can swim)");
        System.out.println(GREEN + "  T1 " + RESET + "= Player 1 Trap  " + RED + "T2 " + RESET + "= Player 2 Trap");
        System.out.println(GREEN + UNDERLINE + "  S1 " + RESET + "= Player 1 Sanctuary  " + RED + UNDERLINE + "S2 " + RESET + "= Player 2 Sanctuary");
        System.out.println("   ·  = Empty square");
        System.out.println();
        
        // Movement help
        System.out.println(BOLD + WHITE + "🎮 MOVEMENT:" + RESET);
        System.out.println(YELLOW + "  • Move format: " + WHITE + "'A1 B1'" + YELLOW + " or " + WHITE + "'0,0 1,0'" + RESET);
        System.out.println(YELLOW + "  • Type " + WHITE + "'help'" + YELLOW + " for detailed rules" + RESET);
        System.out.println(YELLOW + "  • Type " + WHITE + "'quit'" + YELLOW + " to exit game" + RESET);
    }
    
    /**
     * Format piece legend for a specific player.
     */
    private String formatLegendPieces(int player) {
        String color = (player == 1) ? GREEN : RED;
        return color + "E" + player + RESET + "(🐘Elephant-8) " +
               color + "L" + player + RESET + "(🦁Lion-7) " +
               color + "T" + player + RESET + "(🐅Tiger-6) " +
               color + "P" + player + RESET + "(🐆Panther-5) " +
               color + "W" + player + RESET + "(🐺Wolf-4) " +
               color + "D" + player + RESET + "(🐕Dog-3) " +
               color + "C" + player + RESET + "(🐱Cat-2) " +
               color + "R" + player + RESET + "(🐭Rat-1)";
    }
    
    private void displayLegend() {
        displayColoredLegend();
    }
      public void displayGameStart(Player player1, Player player2) {
        clearScreen();
        System.out.println();
        printBoxed("🎯 JUNGLE BATTLE BEGINS! 🎯", PURPLE + BOLD);
        System.out.println();
        
        // Player intro with colors
        System.out.println(GREEN + BOLD + "🟢 Player 1: " + WHITE + player1.getUsername() + RESET);
        System.out.println(GREEN + "   Commands the bottom pieces (Green)" + RESET);
        System.out.println();
        System.out.println(RED + BOLD + "🔴 Player 2: " + WHITE + player2.getUsername() + RESET);
        System.out.println(RED + "   Commands the top pieces (Red)" + RESET);
        System.out.println();
        
        printBoxed("⚡ OBJECTIVE: Move a piece to opponent's sanctuary to win! ⚡", YELLOW);
        System.out.println();
        
        System.out.println(CYAN + "Press Enter to see the battlefield..." + RESET);
        try {
            System.in.read();
        } catch (Exception e) {
            // Continue if input fails
        }
    }
    
    public void displayCurrentTurn(Player currentPlayer, int turnNumber) {
        System.out.println();
        String playerColor = currentPlayer.getUsername().equals("DemoPlayer1") || 
                           currentPlayer.getPlayerId() == 1 ? GREEN : RED;
        String playerSymbol = currentPlayer.getUsername().equals("DemoPlayer1") || 
                            currentPlayer.getPlayerId() == 1 ? "🟢" : "🔴";
        
        printSeparator("TURN " + turnNumber);
        System.out.println();
        System.out.println(playerColor + BOLD + playerSymbol + " " + currentPlayer.getUsername() + "'s Turn" + RESET);
        System.out.println();
        
        // Enhanced input prompt
        System.out.print(YELLOW + "🎯 Enter your move: " + RESET);
        System.out.print(WHITE + BOLD + "[" + RESET);
        System.out.print(CYAN + "A1 B1" + RESET);
        System.out.print(WHITE + " | " + RESET);
        System.out.print(CYAN + "help" + RESET);
        System.out.print(WHITE + " | " + RESET);
        System.out.print(CYAN + "quit" + RESET);
        System.out.print(WHITE + BOLD + "] " + RESET);
    }
      public void displayMessage(String message) {
        System.out.println(BLUE + "💬 " + message + RESET);
    }
    
    public void displayError(String error) {
        System.out.println();
        System.out.println(RED + BOLD + "❌ ERROR: " + WHITE + error + RESET);
        System.out.println(YELLOW + "💡 Try again or type 'help' for assistance" + RESET);
    }
    
    public void displaySuccess(String message) {
        System.out.println();
        System.out.println(GREEN + BOLD + "✅ SUCCESS: " + WHITE + message + RESET);
    }
    
    public void displayGameEnd(Player winner) {
        clearScreen();
        System.out.println();
        
        // Victory banner
        String winnerColor = winner.getUsername().equals("DemoPlayer1") || 
                           winner.getPlayerId() == 1 ? GREEN : RED;
        String winnerSymbol = winner.getUsername().equals("DemoPlayer1") || 
                            winner.getPlayerId() == 1 ? "🟢" : "🔴";
        
        printBoxed("🏆 VICTORY ACHIEVED! 🏆", YELLOW + BOLD);
        System.out.println();
        
        System.out.println(winnerColor + BOLD + "🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉" + RESET);
        System.out.println(winnerColor + BOLD + "                CHAMPION OF THE JUNGLE!" + RESET);
        System.out.println(winnerColor + BOLD + "              " + winnerSymbol + " " + winner.getUsername() + " WINS! " + winnerSymbol + RESET);
        System.out.println(winnerColor + BOLD + "🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉" + RESET);
        System.out.println();
        
        System.out.println(PURPLE + "⚡ The sanctuary has been conquered!" + RESET);
        System.out.println(CYAN + "🐘 The jungle remembers this victory!" + RESET);
        System.out.println();
    }
      public void displayHelp() {
        clearScreen();
        System.out.println();
        printBoxed("🎓 XOU DOU QI GAME GUIDE 🎓", PURPLE + BOLD);
        System.out.println();
        
        // Basic Rules
        System.out.println(YELLOW + BOLD + "🎯 OBJECTIVE:" + RESET);
        System.out.println(WHITE + "   Move one of your pieces to the opponent's sanctuary to win!" + RESET);
        System.out.println();
        
        // Movement Rules
        System.out.println(YELLOW + BOLD + "🚶 MOVEMENT RULES:" + RESET);
        System.out.println(WHITE + "   • Move pieces one square horizontally or vertically" + RESET);
        System.out.println(WHITE + "   • Higher rank pieces capture lower rank pieces" + RESET);
        System.out.println(WHITE + "   • Cannot move to squares occupied by your own pieces" + RESET);
        System.out.println();
        
        // Special Rules
        System.out.println(YELLOW + BOLD + "⚡ SPECIAL ABILITIES:" + RESET);
        System.out.println(GREEN + "   🐭 Rat (R): " + WHITE + "Can capture Elephant despite lower rank" + RESET);
        System.out.println(GREEN + "   🐭 Rat (R): " + WHITE + "Can swim in rivers (only animal that can)" + RESET);
        System.out.println(GREEN + "   🦁 Lion (L): " + WHITE + "Can jump over rivers (if no Rat blocks)" + RESET);
        System.out.println(GREEN + "   🐅 Tiger (T): " + WHITE + "Can jump over rivers (if no Rat blocks)" + RESET);
        System.out.println();
        
        // Zone Effects
        System.out.println(YELLOW + BOLD + "🗺️  ZONE EFFECTS:" + RESET);
        System.out.println(CYAN + "   ~ River: " + WHITE + "Only Rats can enter, Lions/Tigers can jump over" + RESET);
        System.out.println(RED + "   T1/T2 Traps: " + WHITE + "Enemy pieces lose rank, can be captured by any piece" + RESET);
        System.out.println(PURPLE + "   S1/S2 Sanctuary: " + WHITE + "Cannot enter your own, winning goal" + RESET);
        System.out.println();
        
        // Commands
        System.out.println(YELLOW + BOLD + "🎮 COMMANDS:" + RESET);
        System.out.println(CYAN + "   'A1 B1'" + WHITE + " - Move piece from A1 to B1" + RESET);
        System.out.println(CYAN + "   '0,0 1,0'" + WHITE + " - Move using numeric coordinates" + RESET);
        System.out.println(CYAN + "   'help'" + WHITE + " - Show this help screen" + RESET);
        System.out.println(CYAN + "   'quit'" + WHITE + " - Exit the current game" + RESET);
        System.out.println();
        
        System.out.println(GREEN + "Press Enter to return to the game..." + RESET);
        try {
            System.in.read();
        } catch (Exception e) {
            // Continue if input fails
        }
    }
    
    public void displayGoodbye() {
        System.out.println();
        System.out.println("Thank you for playing Xou Dou Qi!");
        System.out.println("May the jungle spirits guide your future battles! 🐘🐅🐀");
        System.out.println();
    }
    
    /**
     * Display animated loading effect.
     */
    public void displayLoadingEffect(String message) {
        System.out.print(CYAN + message + " ");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(300);
                System.out.print(".");
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println(" ✓" + RESET);
    }
    
    /**
     * Display a pause prompt and wait for user input.
     */
    public void displayPause() {
        System.out.println();
        System.out.println(YELLOW + "Press Enter to continue..." + RESET);
        try {
            System.in.read();
        } catch (Exception e) {
            // Continue if input fails
        }
    }
    
    /**
     * Display game tips for new players.
     */
    public void displayGameTips() {
        System.out.println();
        printSeparator("QUICK TIPS");
        System.out.println();
        System.out.println(GREEN + "💡 " + WHITE + "Start by moving pieces toward the center" + RESET);
        System.out.println(GREEN + "💡 " + WHITE + "Use your Rat to explore river areas" + RESET);
        System.out.println(GREEN + "💡 " + WHITE + "Protect your sanctuary from enemy pieces" + RESET);
        System.out.println(GREEN + "💡 " + WHITE + "Higher rank pieces capture lower rank pieces" + RESET);
        System.out.println();
    }
    
    /**
     * Display move attempt with feedback.
     */
    public void displayMoveAttempt(String from, String to, boolean success) {
        if (success) {
            System.out.println(GREEN + "🚀 Move: " + WHITE + from + " → " + to + GREEN + " ✓" + RESET);
        } else {
            System.out.println(RED + "❌ Move: " + WHITE + from + " → " + to + RED + " (Failed)" + RESET);
        }
    }
    
    /**
     * Display capture event.
     */
    public void displayCapture(String attacker, String victim) {
        System.out.println(YELLOW + "⚔️  " + WHITE + attacker + YELLOW + " captures " + WHITE + victim + YELLOW + "!" + RESET);
    }
    
    /**
     * Display special move event.
     */
    public void displaySpecialMove(String moveType, String piece) {
        System.out.println(PURPLE + "✨ Special move: " + WHITE + piece + PURPLE + " performs " + moveType + "!" + RESET);
    }
    
    /**
     * Display current game status.
     */
    public void displayGameStatus(Player currentPlayer, int turnNumber, int player1Pieces, int player2Pieces) {
        System.out.println();
        printSeparator("GAME STATUS");
        System.out.println();
        
        String playerColor = currentPlayer.getPlayerId() == 1 ? GREEN : RED;
        System.out.println(playerColor + "Current Player: " + WHITE + currentPlayer.getUsername() + RESET);
        System.out.println(BLUE + "Turn: " + WHITE + turnNumber + RESET);
        System.out.println(GREEN + "Player 1 Pieces: " + WHITE + player1Pieces + RESET);
        System.out.println(RED + "Player 2 Pieces: " + WHITE + player2Pieces + RESET);
        System.out.println();
    }
    
    /**
     * Display final game statistics.
     */
    public void displayGameStats(int totalTurns, long durationMs) {
        System.out.println();
        printSeparator("GAME STATISTICS");
        System.out.println();
        
        long minutes = durationMs / 60000;
        long seconds = (durationMs % 60000) / 1000;
        
        System.out.println(CYAN + "📊 Total Turns: " + WHITE + totalTurns + RESET);
        System.out.println(CYAN + "⏱️  Duration: " + WHITE + minutes + "m " + seconds + "s" + RESET);
        System.out.println(CYAN + "🎯 Average Turn Time: " + WHITE + (durationMs / totalTurns / 1000) + "s" + RESET);
        System.out.println();
    }
}
