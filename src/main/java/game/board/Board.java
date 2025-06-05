package game.board;

import game.pieces.*;
import game.rules.MoveValidator;
import game.rules.MoveValidator;

/**
 * Represents the 9x7 Xou Dou Qi game board with all squares and special zones.
 * Coordinates system: (0,0) is bottom-left, (6,8) is top-right.
 * Player 1 starts at bottom (rows 0-2), Player 2 starts at top (rows 4-6).
 */
public class Board {
    public static final int BOARD_WIDTH = 7;  // columns 0-6
    public static final int BOARD_HEIGHT = 9; // rows 0-8
    
    private final Square[][] squares;
    
    public Board() {
        squares = new Square[BOARD_WIDTH][BOARD_HEIGHT];
        initializeBoard();
        setupInitialPieces();
    }
    
    /**
     * Initialize all squares with their appropriate zone types and owners.
     */
    private void initializeBoard() {
        // Initialize all squares as normal first
        for (int col = 0; col < BOARD_WIDTH; col++) {
            for (int row = 0; row < BOARD_HEIGHT; row++) {
                squares[col][row] = new Square(col, row, ZoneType.NORMAL);
            }
        }
        
        // Set up Player 1 zones (bottom)
        setupPlayer1Zones();
        
        // Set up Player 2 zones (top)
        setupPlayer2Zones();
        
        // Set up river zones (center)
        setupRiverZones();
    }
    
    private void setupPlayer1Zones() {
        // Player 1 sanctuary at (3,0)
        squares[3][0] = new Square(3, 0, ZoneType.SANCTUARY, 1);
        
        // Player 1 traps at (2,0), (4,0), (3,1)
        squares[2][0] = new Square(2, 0, ZoneType.TRAP, 1);
        squares[4][0] = new Square(4, 0, ZoneType.TRAP, 1);
        squares[3][1] = new Square(3, 1, ZoneType.TRAP, 1);
    }
    
    private void setupPlayer2Zones() {
        // Player 2 sanctuary at (3,8)
        squares[3][8] = new Square(3, 8, ZoneType.SANCTUARY, 2);
        
        // Player 2 traps at (2,8), (4,8), (3,7)
        squares[2][8] = new Square(2, 8, ZoneType.TRAP, 2);
        squares[4][8] = new Square(4, 8, ZoneType.TRAP, 2);
        squares[3][7] = new Square(3, 7, ZoneType.TRAP, 2);
    }
    
    private void setupRiverZones() {
        // Left river lake (columns 1-2, rows 3-5)
        for (int col = 1; col <= 2; col++) {
            for (int row = 3; row <= 5; row++) {
                squares[col][row] = new Square(col, row, ZoneType.RIVER);
            }
        }
        
        // Right river lake (columns 4-5, rows 3-5)
        for (int col = 4; col <= 5; col++) {
            for (int row = 3; row <= 5; row++) {
                squares[col][row] = new Square(col, row, ZoneType.RIVER);
            }
        }
    }
    
    /**
     * Set up initial piece placement according to standard Xou Dou Qi layout.
     */
    private void setupInitialPieces() {
        // Player 1 pieces (bottom rows)
        placePiece(new Tiger(1), 0, 0);     // Tiger at (0,0)
        placePiece(new Lion(1), 6, 0);      // Lion at (6,0)
        placePiece(new Dog(1), 1, 1);       // Dog at (1,1)
        placePiece(new Cat(1), 5, 1);       // Cat at (5,1)
        placePiece(new Elephant(1), 0, 2);  // Elephant at (0,2)
        placePiece(new Wolf(1), 2, 2);      // Wolf at (2,2)
        placePiece(new Panther(1), 4, 2);   // Panther at (4,2)
        placePiece(new Rat(1), 6, 2);       // Rat at (6,2)
        
        // Player 2 pieces (top rows, mirrored)
        placePiece(new Lion(2), 0, 8);      // Lion at (0,8)
        placePiece(new Tiger(2), 6, 8);     // Tiger at (6,8)
        placePiece(new Cat(2), 1, 7);       // Cat at (1,7)
        placePiece(new Dog(2), 5, 7);       // Dog at (5,7)
        placePiece(new Rat(2), 0, 6);       // Rat at (0,6)
        placePiece(new Panther(2), 2, 6);   // Panther at (2,6)
        placePiece(new Wolf(2), 4, 6);      // Wolf at (4,6)
        placePiece(new Elephant(2), 6, 6);  // Elephant at (6,6)
    }
    
    // Board access methods
    public Square getSquare(int column, int row) {
        if (isValidPosition(column, row)) {
            return squares[column][row];
        }
        return null;
    }
    
    public boolean isValidPosition(int column, int row) {
        return column >= 0 && column < BOARD_WIDTH && row >= 0 && row < BOARD_HEIGHT;
    }
    
    // Piece management
    public void placePiece(Piece piece, int column, int row) {
        if (isValidPosition(column, row)) {
            Square square = squares[column][row];
            square.setPiece(piece);
            piece.setPosition(column, row);
        }
    }
    
    public void removePiece(int column, int row) {
        if (isValidPosition(column, row)) {
            squares[column][row].removePiece();
        }
    }
    
    public Piece getPiece(int column, int row) {
        if (isValidPosition(column, row)) {
            return squares[column][row].getPiece();
        }
        return null;
    }
    
    public boolean hasPiece(int column, int row) {
        if (isValidPosition(column, row)) {
            return squares[column][row].hasPiece();
        }
        return false;
    }
    
    // Board state queries
    public boolean isEmpty(int column, int row) {
        if (isValidPosition(column, row)) {
            return squares[column][row].isEmpty();
        }
        return false;
    }
    
    /**
     * Move a piece from one position to another.
     * This is a low-level method that doesn't validate rules.
     */
    public boolean movePiece(int fromCol, int fromRow, int toCol, int toRow) {
        if (!isValidPosition(fromCol, fromRow) || !isValidPosition(toCol, toRow)) {
            return false;
        }
        
        Piece piece = getPiece(fromCol, fromRow);
        if (piece == null) {
            return false;
        }
        
        // Remove piece from old position
        removePiece(fromCol, fromRow);
        
        // Place piece at new position
        placePiece(piece, toCol, toRow);
        
        return true;
    }
    
    /**
     * Attempt to move a piece from one position to another with full rule validation.
     * Returns true if the move was successful, false otherwise.
     */
    public boolean makeMove(int fromCol, int fromRow, int toCol, int toRow, int currentPlayer) {
        if (!MoveValidator.isValidMove(this, fromCol, fromRow, toCol, toRow, currentPlayer)) {
            return false;
        }
        
        // Get the piece and target square
        Piece piece = getPiece(fromCol, fromRow);
        Square targetSquare = getSquare(toCol, toRow);
        
        // Handle capture if there's a piece at the destination
        Piece capturedPiece = null;
        if (targetSquare.hasPiece()) {
            capturedPiece = targetSquare.getPiece();
            removePiece(toCol, toRow);
        }
        
        // Move the piece
        removePiece(fromCol, fromRow);
        placePiece(piece, toCol, toRow);
        
        return true;
    }
    
    /**
     * Check if a move is valid without executing it.
     */
    public boolean isValidMove(int fromCol, int fromRow, int toCol, int toRow, int currentPlayer) {
        return MoveValidator.isValidMove(this, fromCol, fromRow, toCol, toRow, currentPlayer);
    }
    
    /**
     * Get the reason why a move is invalid.
     */
    public String getInvalidMoveReason(int fromCol, int fromRow, int toCol, int toRow, int currentPlayer) {
        return MoveValidator.getInvalidMoveReason(this, fromCol, fromRow, toCol, toRow, currentPlayer);
    }
    
    /**
     * Check if the game has ended (a piece reached the opponent's sanctuary).
     */
    public boolean checkWinCondition() {
        // Check if any piece has reached the opponent's sanctuary
        Square player1Sanctuary = getSquare(3, 0);  // Player 1's sanctuary
        Square player2Sanctuary = getSquare(3, 8);  // Player 2's sanctuary
        
        // Check if Player 2 has won (piece in Player 1's sanctuary)
        if (player1Sanctuary.hasPiece() && player1Sanctuary.getPiece().getOwner() == 2) {
            return true;
        }
        
        // Check if Player 1 has won (piece in Player 2's sanctuary)
        if (player2Sanctuary.hasPiece() && player2Sanctuary.getPiece().getOwner() == 1) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Get the winner if the game has ended, null otherwise.
     */
    public Integer getWinner() {
        Square player1Sanctuary = getSquare(3, 0);
        Square player2Sanctuary = getSquare(3, 8);
        
        if (player1Sanctuary.hasPiece() && player1Sanctuary.getPiece().getOwner() == 2) {
            return 2; // Player 2 wins
        }
        
        if (player2Sanctuary.hasPiece() && player2Sanctuary.getPiece().getOwner() == 1) {
            return 1; // Player 1 wins
        }
        
        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Xou Dou Qi Board (9x7):\n");
        
        // Print column headers
        sb.append("   ");
        for (int col = 0; col < BOARD_WIDTH; col++) {
            sb.append(String.format("%3d", col));
        }
        sb.append("\n");
        
        // Print rows from top to bottom (row 8 to 0)
        for (int row = BOARD_HEIGHT - 1; row >= 0; row--) {
            sb.append(String.format("%2d ", row));
            for (int col = 0; col < BOARD_WIDTH; col++) {
                Square square = squares[col][row];
                if (square.hasPiece()) {
                    sb.append(String.format("%3s", square.getPiece().getSymbol()));
                } else {
                    // Show zone type
                    switch (square.getZoneType()) {
                        case RIVER:
                            sb.append(" ~ ");
                            break;
                        case TRAP:
                            sb.append(square.getOwner() == 1 ? " T1" : " T2");
                            break;
                        case SANCTUARY:
                            sb.append(square.getOwner() == 1 ? " S1" : " S2");
                            break;
                        default:
                            sb.append(" . ");
                            break;
                    }
                }
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}
