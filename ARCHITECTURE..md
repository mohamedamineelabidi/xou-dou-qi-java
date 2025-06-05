# Xou Dou Qi - Application Architecture 🏗️

This document outlines the proposed software architecture for the **Xou Dou Qi** Java console application. The architecture emphasizes modularity, maintainability, and clear separation of concerns.

## Modules / Packages

The application will be divided into the following primary modules/packages:

1.  **`game.core` (or `game.logic`)**:
    * Responsibilities: Manages the overall game state, enforces game rules, tracks turns[cite: 4], and determines win/loss conditions[cite: 3].
    * Key Classes: `GameManager`, `GameState`, `TurnManager`.

2.  **`game.board`**:
    * Responsibilities: Represents the game board, including individual squares, special zones (Rivers, Traps, Sanctuaries)[cite: 8, 9]. Manages piece positions.
    * Key Classes: `Board`, `Square`, `ZoneType (Enum: RIVER, TRAP, SANCTUARY, NORMAL)`.

3.  **`game.pieces`**:
    * Responsibilities: Defines the animal pieces, their hierarchy[cite: 11], specific movement rules (e.g., Rat in water[cite: 13], Lion/Tiger jump [cite: 12]), and capture logic.
    * Key Classes: `AbstractPiece` (or `Piece` interface), `Elephant`, `Lion`, `Tiger`, `Panther`, `Dog`, `Wolf`, `Cat`, `Rat`. Each piece class will encapsulate its specific behaviors.

4.  **`game.rules`**:
    * Responsibilities: Encapsulates specific rule checks, such as valid moves, capture conditions[cite: 11], trap effects[cite: 16], and river interactions[cite: 12, 13]. This module might contain rule engines or strategy objects.
    * Key Classes: `MoveValidator`, `CaptureRule`, `TrapRule`, `RiverRule`.

5.  **`player.accounts`**:
    * Responsibilities: Manages player data, including creation, authentication (login), and potentially scores or stats[cite: 2].
    * Key Classes: `Player`, `AccountService`.

6.  **`data.storage`**:
    * Responsibilities: Handles all database interactions, including saving and retrieving player accounts [cite: 2] and game history[cite: 5]. Abstracts the underlying database technology.
    * Key Classes: `DatabaseManager`, `PlayerDAO` (Data Access Object), `GameHistoryDAO`.

7.  **`console.ui`**:
    * Responsibilities: Manages all interaction with the user via the console. This includes parsing text commands[cite: 3], displaying the game board, providing feedback, and showing game history[cite: 6].
    * Key Classes: `ConsoleInputHandler`, `ConsoleOutputView`.

## Module Interactions

* The **`console.ui`** captures player input and passes commands to the **`game.core`** (e.g., `GameManager`).
* The **`GameManager`** uses **`game.rules`** to validate actions and **`game.board`** and **`game.pieces`** to update the game state.
* **`player.accounts`** is used by **`GameManager`** (or a higher-level application orchestrator) to manage player sessions.
* Both **`player.accounts`** and **`game.core`** (for game history) interact with **`data.storage`** to persist and retrieve data.
* The **`console.ui`** queries the **`game.board`**, **`game.pieces`**, and potentially **`player.accounts`** or **`data.storage`** (for history) to display information.

```mermaid
graph TD
    A[Console UI] --> B{Game Core/Logic};
    B --> C[Game Rules];
    B --> D[Game Board];
    B --> E[Game Pieces];
    B --> F[Player Accounts];
    F --> G[Data Storage];
    B -- Game History --> G;
    G <--> H[(Embedded DB: SQLite/H2)];