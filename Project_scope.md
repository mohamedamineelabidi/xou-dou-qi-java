```markdown
# Project Scope: Initial Game Setup ♟️

This document outlines the scope for the first major feature: **Setting up the Game Board and Initial Piece Placement**.

## Feature: Game Board and Initial Piece Placement

The goal is to implement the foundational elements of the Xou Dou Qi game board, including its structure, special zones, and the correct initial positioning of all animal pieces for both players.

### Sub-Tasks / Implementation Steps:

1.  **Define Board Dimensions and Structure:**
    * Implement a data structure to represent the 9x7 game board[cite: 8].
    * Each cell/square on the board should be uniquely identifiable (e.g., using a coordinate system).

2.  **Represent Squares and Special Zones:**
    * Define properties for each square, including whether it's a:
        * Normal land square.
        * **River Square** (2 lakes, each typically 2x3 squares in the center)[cite: 8].
        * **Trap Square** (3 per player, near their sanctuary)[cite: 8, 9].
        * **Sanctuary Square** (1 per player, the goal square)[cite: 8, 9].
    * Store the owner/side for Trap and Sanctuary squares.

3.  **Define Piece Representation:**
    * Create classes or structures for each of the 8 animal types (Elephant, Lion, Tiger, Panther, Dog, Wolf, Cat, Rat)[cite: 11, 18].
    * Each piece instance should store its type, owner (Player 1 or Player 2), and its rank/hierarchy value[cite: 11].

4.  **Implement Initial Piece Placement:**
    * Develop a mechanism to place all 16 pieces (8 per player) onto the board in their correct starting positions as per the standard Xou Dou Qi layout[cite: 9, 18].
    * This involves mapping specific pieces to specific coordinates for both Player 1 (e.g., bottom rows) and Player 2 (e.g., top rows). Refer to the provided game board diagram (Page 2 of `ID1 V2.pdf`) for precise locations:
        * **Player 1 (e.g., Bottom):**
            * (0,0) Tigre, (6,0) Lion
            * (1,1) Chien, (5,1) Chat
            * (0,2) Elephant, (2,2) Loup, (4,2) Panthère, (6,2) Rat
            * Sanctuary at (3,0)
            * Traps at (2,0), (4,0), (3,1)
        * **Player 2 (e.g., Top, mirrored):**
            * (0,6) Lion, (6,6) Tigre
            * (1,5) Chat, (5,5) Chien
            * (0,4) Rat, (2,4) Panthère, (4,4) Loup, (6,4) Elephant
            * Sanctuary at (3,6)
            * Traps at (2,6), (4,6), (3,5)
        * *(Note: Coordinate system (column, row) assuming (0,0) is bottom-left as suggested by "Case (0,0)" on diagram. Player 1's pieces are typically at the bottom rows, Player 2's at the top. The exact coordinate assignment needs to be consistent.)*

5.  **Develop Board Display Mechanism (Basic):**
    * Create a simple console function to print the current state of the board, showing piece locations and special zones. This is crucial for verifying the setup.

### Specific Considerations:

* **Coordinate System:** A consistent coordinate system is vital. (e.g., `(0,0)` at the bottom-left [from diagram on page 2], or top-left). The choice must be documented and used consistently. The diagram indicates `Case (0,0)` as the bottom-left square for the player starting at the bottom.
* **Piece Immutability (Initial State):** Piece properties like type and rank are fixed. Owner is fixed. Position changes.
* **Zone Identification:** Squares must clearly identify if they are River, Trap, or Sanctuary, as these affect game rules[cite: 12, 13, 16, 17].
* **Player Association:** Clearly associate pieces, traps, and sanctuaries with Player 1 or Player 2.
