package console.ui;

import java.util.Scanner;

/**
 * Handles console input for the Xou Dou Qi application.
 * Manages user input parsing and validation.
 */
public class ConsoleInputHandler {
    private final Scanner scanner;
    
    public ConsoleInputHandler() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Read a line of input from the user.
     */
    public String readLine() {
        return scanner.nextLine().trim();
    }
    
    /**
     * Read an integer from the user with prompt.
     */
    public int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.nextLine(); // consume invalid input
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume the newline
        return value;
    }
    
    /**
     * Read a string from the user with prompt.
     */
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
      /**
     * Parse a move command in format "A1 B1", "a1 b1", "0,0 1,0", or "0 0 1 0".
     * Returns an array with [fromCol, fromRow, toCol, toRow] or null if invalid.
     */
    public int[] parseMove(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        
        input = input.toUpperCase().trim();
        
        // Try to parse format like "A1 B1" or "A1-B1" or "A1 TO B1"
        if (input.matches("[A-G][0-8][\\s\\-]*(TO\\s*)?[A-G][0-8]")) {
            input = input.replace("-", " ").replace("TO", " ");
            String[] parts = input.split("\\s+");
            if (parts.length >= 2) {
                int[] from = parsePosition(parts[0]);
                int[] to = parsePosition(parts[parts.length-1]);
                if (from != null && to != null) {
                    return new int[]{from[0], from[1], to[0], to[1]};
                }
            }
        }
        
        // Try to parse format like "0,0 1,0" or "0,0-1,0"
        if (input.matches("\\d,\\d[\\s\\-]*\\d,\\d")) {
            input = input.replace("-", " ");
            String[] parts = input.split("\\s+");
            if (parts.length >= 2) {
                int[] from = parseNumericPosition(parts[0]);
                int[] to = parseNumericPosition(parts[parts.length-1]);
                if (from != null && to != null) {
                    return new int[]{from[0], from[1], to[0], to[1]};
                }
            }
        }
        
        // Try to parse format like "0 0 1 0" (space separated)
        if (input.matches("\\d\\s+\\d\\s+\\d\\s+\\d")) {
            String[] parts = input.split("\\s+");
            if (parts.length == 4) {
                try {
                    int fromCol = Integer.parseInt(parts[0]);
                    int fromRow = Integer.parseInt(parts[1]);
                    int toCol = Integer.parseInt(parts[2]);
                    int toRow = Integer.parseInt(parts[3]);
                    
                    if (isValidCoordinate(fromCol, fromRow) && isValidCoordinate(toCol, toRow)) {
                        return new int[]{fromCol, fromRow, toCol, toRow};
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Parse a position like "A1" to [column, row].
     */
    private int[] parsePosition(String pos) {
        if (pos.length() != 2) {
            return null;
        }
        
        char colChar = pos.charAt(0);
        char rowChar = pos.charAt(1);
        
        if (colChar < 'A' || colChar > 'G') {
            return null;
        }
        
        if (rowChar < '0' || rowChar > '8') {
            return null;
        }
        
        int col = colChar - 'A';  // A=0, B=1, ..., G=6
        int row = rowChar - '0';  // 0=0, 1=1, ..., 8=8
        
        return new int[]{col, row};
    }
    
    /**
     * Parse a numeric position like "0,1" to [column, row].
     */
    private int[] parseNumericPosition(String pos) {
        try {
            String[] parts = pos.split(",");
            if (parts.length != 2) {
                return null;
            }
            
            int col = Integer.parseInt(parts[0]);
            int row = Integer.parseInt(parts[1]);
            
            if (col < 0 || col > 6 || row < 0 || row > 8) {
                return null;
            }
            
            return new int[]{col, row};
        } catch (NumberFormatException e) {
            return null;
        }
    }
      /**
     * Check if coordinates are valid for the game board.
     */
    private boolean isValidCoordinate(int col, int row) {
        return col >= 0 && col <= 6 && row >= 0 && row <= 8;
    }
    
    /**
     * Check if the input is a special command.
     */
    public boolean isCommand(String input) {
        if (input == null) return false;
        
        String cmd = input.toLowerCase().trim();
        return cmd.equals("help") || cmd.equals("quit") || cmd.equals("exit") || 
               cmd.equals("surrender") || cmd.equals("board") || cmd.equals("moves") ||
               cmd.equals("status") || cmd.equals("save") || cmd.equals("load");
    }
    
    /**
     * Parse menu selection input.
     */
    public int parseMenuChoice(String input, int maxChoice) {
        if (input == null || input.trim().isEmpty()) {
            return -1;
        }
        
        try {
            int choice = Integer.parseInt(input.trim());
            if (choice >= 1 && choice <= maxChoice) {
                return choice;
            }
        } catch (NumberFormatException e) {
            // Try to parse text commands
            String cmd = input.toLowerCase().trim();
            switch (cmd) {
                case "new":
                case "start":
                case "game":
                    return 1;
                case "login":
                case "existing":
                    return 2;
                case "create":
                case "register":
                case "account":
                    return 3;
                case "history":
                case "stats":
                    return 4;
                case "exit":
                case "quit":
                    return 5;
                default:
                    return -1;
            }
        }
        
        return -1;
    }
    
    /**
     * Get helpful suggestions for invalid input.
     */
    public String getInputSuggestion(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Please enter a command. Type 'help' for assistance.";
        }
        
        String cmd = input.toLowerCase().trim();
        
        // Check for common typos and provide suggestions
        if (cmd.contains("mov") || cmd.contains("go")) {
            return "To move a piece, use format: 'A1 B1' or '0,0 1,0'";
        }
        
        if (cmd.contains("hel") || cmd.contains("?")) {
            return "Type 'help' for game instructions and commands.";
        }
        
        if (cmd.contains("qui") || cmd.contains("exi")) {
            return "Type 'quit' to exit the game.";
        }
        
        return "Invalid input. Use move format like 'A1 B1' or type 'help' for commands.";
    }
    
    /**
     * Result class for position parsing with feedback.
     */
    public static class PositionParseResult {
        public final boolean isValid;
        public final String message;
        public final String suggestion;
        public final int[] coordinates; // [fromCol, fromRow, toCol, toRow] if valid
        
        public PositionParseResult(boolean isValid, String message, String suggestion, int[] coordinates) {
            this.isValid = isValid;
            this.message = message;
            this.suggestion = suggestion;
            this.coordinates = coordinates;
        }
        
        public static PositionParseResult valid(String suggestion, int[] coordinates) {
            return new PositionParseResult(true, "Valid move", suggestion, coordinates);
        }
        
        public static PositionParseResult invalid(String message, String suggestion) {
            return new PositionParseResult(false, message, suggestion, null);
        }
    }
    
    /**
     * Parse position input with detailed feedback for the user.
     */
    public PositionParseResult parsePositionWithFeedback(String input) {
        if (input == null || input.trim().isEmpty()) {
            return PositionParseResult.invalid("Empty input", "Try: 'A1 B1' or '0,0 1,0'");
        }
        
        String trimmed = input.trim().toLowerCase();
        
        // Check for commands first
        if (isCommand(trimmed)) {
            if (trimmed.equals("help")) {
                return PositionParseResult.invalid("Help command", "Type 'help' to see game rules");
            } else if (trimmed.equals("quit") || trimmed.equals("exit")) {
                return PositionParseResult.invalid("Quit command", "Type 'quit' to exit game");
            } else {
                return PositionParseResult.invalid("Command detected", "Use '" + trimmed + "' command");
            }
        }
        
        // Try to parse as move
        int[] move = parseMove(input);
        if (move != null) {
            String algebraic = convertToAlgebraic(move[0], move[1]) + " " + convertToAlgebraic(move[2], move[3]);
            return PositionParseResult.valid("Move from " + algebraic, move);
        }
        
        // Provide helpful suggestions for invalid input
        if (input.matches(".*[a-gA-G].*[0-8].*")) {
            return PositionParseResult.invalid("Invalid format", "Try: 'A1 B1' (letter + number + space + letter + number)");
        } else if (input.matches(".*\\d.*")) {
            return PositionParseResult.invalid("Invalid coordinates", "Try: '0,0 1,0' or 'A1 B1'");
        } else {
            return PositionParseResult.invalid("Unrecognized input", "Examples: 'A1 B1', '0,0 1,0', 'help', 'quit'");
        }
    }
    
    /**
     * Convert column and row to algebraic notation (e.g., 0,0 -> A0).
     */
    public String convertToAlgebraic(int col, int row) {
        if (col < 0 || col > 6 || row < 0 || row > 8) {
            return "??";
        }
        return String.valueOf((char)('A' + col)) + row;
    }
    
    /**
     * Close the input handler and release resources.
     */
    public void close() {
        scanner.close();
    }
}
