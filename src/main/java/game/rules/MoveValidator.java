package game.rules;

import game.board.Board;
import game.board.Square;
import game.board.ZoneType;
import game.pieces.Piece;
import game.pieces.PieceType;

/**
 * Validates piece movements according to Xou Dou Qi rules.
 * Handles basic movement validation, special zone rules, and piece-specific movement rules.
 */
public class MoveValidator {
    
    /**
     * Check if a move is valid according to all game rules.
     */
    public static boolean isValidMove(Board board, int fromCol, int fromRow, int toCol, int toRow, int currentPlayer) {
        // Basic validation
        if (!board.isValidPosition(fromCol, fromRow) || !board.isValidPosition(toCol, toRow)) {
            return false;
        }
        
        // Check if there's a piece to move
        Piece piece = board.getPiece(fromCol, fromRow);
        if (piece == null) {
            return false;
        }
        
        // Check if the piece belongs to the current player
        if (piece.getOwner() != currentPlayer) {
            return false;
        }
        
        // Check if the destination is the same as the source
        if (fromCol == toCol && fromRow == toRow) {
            return false;
        }
        
        // Check if the piece can move to the target position (basic movement rules)
        if (!piece.canMoveTo(toCol, toRow)) {
            return false;
        }
        
        // Check destination square rules
        Square targetSquare = board.getSquare(toCol, toRow);
        
        // Check if destination has a piece
        if (targetSquare.hasPiece()) {
            Piece targetPiece = targetSquare.getPiece();
            
            // Cannot capture own pieces
            if (targetPiece.getOwner() == currentPlayer) {
                return false;
            }
            
            // Check if the piece can capture the target
            if (!canCapture(piece, targetPiece, board.getSquare(fromCol, fromRow), targetSquare)) {
                return false;
            }
        }
        
        // Check special zone rules
        if (!checkZoneRules(piece, board.getSquare(fromCol, fromRow), targetSquare)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Check if a piece can capture another piece, considering special rules and zone effects.
     */
    private static boolean canCapture(Piece attacker, Piece defender, Square attackerSquare, Square defenderSquare) {
        // Check basic capture rules (handled by piece classes)
        if (!attacker.canCapture(defender)) {
            return false;
        }
        
        // Check trap effects - pieces in enemy traps lose their rank
        if (defenderSquare.isTrap() && defenderSquare.getOwner() != defender.getOwner()) {
            // Defender is in enemy trap - any piece can capture it
            return true;
        }
        
        // Standard capture rules apply
        return true;
    }
    
    /**
     * Check special zone movement rules.
     */
    private static boolean checkZoneRules(Piece piece, Square fromSquare, Square toSquare) {
        // Rule: Pieces cannot enter their own sanctuary
        if (toSquare.isSanctuary() && toSquare.getOwner() == piece.getOwner()) {
            return false;
        }
        
        // Rule: Only rats can enter/move within river squares (except lions and tigers jumping)
        if (toSquare.isRiver() && piece.getType() != PieceType.RAT) {
            // Check if this is a lion or tiger jumping over river
            if (piece.getType() == PieceType.LION || piece.getType() == PieceType.TIGER) {
                // TODO: Implement river jumping logic
                return false; // For now, prevent lions/tigers from entering river
            }
            return false;
        }
        
        return true;
    }
    
    /**
     * Get a human-readable reason why a move is invalid.
     */
    public static String getInvalidMoveReason(Board board, int fromCol, int fromRow, int toCol, int toRow, int currentPlayer) {
        if (!board.isValidPosition(fromCol, fromRow) || !board.isValidPosition(toCol, toRow)) {
            return "Invalid board position";
        }
        
        Piece piece = board.getPiece(fromCol, fromRow);
        if (piece == null) {
            return "No piece at the source position";
        }
        
        if (piece.getOwner() != currentPlayer) {
            return "That piece doesn't belong to you";
        }
        
        if (fromCol == toCol && fromRow == toRow) {
            return "Cannot move to the same position";
        }
        
        if (!piece.canMoveTo(toCol, toRow)) {
            return "Piece cannot move to that position (check movement rules)";
        }
        
        Square targetSquare = board.getSquare(toCol, toRow);
        if (targetSquare.hasPiece()) {
            Piece targetPiece = targetSquare.getPiece();
            if (targetPiece.getOwner() == currentPlayer) {
                return "Cannot capture your own piece";
            }
            if (!canCapture(piece, targetPiece, board.getSquare(fromCol, fromRow), targetSquare)) {
                return "Cannot capture that piece (check rank hierarchy)";
            }
        }
        
        if (targetSquare.isSanctuary() && targetSquare.getOwner() == piece.getOwner()) {
            return "Cannot enter your own sanctuary";
        }
        
        if (targetSquare.isRiver() && piece.getType() != PieceType.RAT) {
            return "Only rats can enter river squares";
        }
        
        return "Move is invalid";
    }
}
