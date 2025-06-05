# Xou Dou Qi - TODO List 📝

This file tracks high-level tasks, known issues, and future considerations for the project beyond the immediate scope of initial board setup.

## Core Gameplay Implementation

* [ ] Implement piece movement logic (horizontal/vertical, one square)[cite: 14].
* [ ] Implement standard capture logic (higher/equal rank captures lower)[cite: 11].
* [ ] **Special Piece Rules:**
    * [ ] Implement Rat movement into/within River squares[cite: 13].
    * [ ] Implement Rat's inability to capture when *exiting* the river[cite: 17].
    * [ ] Implement Rat's ability to capture Elephant[cite: 11].
    * [ ] Implement Lion and Tiger jump over River (and restriction if Rat is in path)[cite: 12].
* [ ] **Special Zone Rules:**
    * [ ] Implement Trap square effects (enemy piece loses rank, can be captured by any piece, cannot capture)[cite: 16].
    * [ ] Prevent pieces from entering their own Sanctuary[cite: 14].
* [ ] Implement win condition detection (piece reaches opponent's Sanctuary)[cite: 10, 17].
* [ ] Implement turn management system[cite: 4].

## Player Account Management

* [ ] Design database schema for player accounts.
* [ ] Implement player account creation (registration) functionality[cite: 2].
* [ ] Implement player login functionality[cite: 2].
* [ ] Implement secure password storage (hashing & salting).
* [ ] Connect account management to the `data.storage` module.

## Game History

* [ ] Design database schema for storing game results[cite: 5].
* [ ] Implement functionality to save game outcomes (players, winner, date) after a game concludes[cite: 5].
* [ ] Implement functionality to retrieve and display game history for players[cite: 6].
* [ ] Connect game history to the `data.storage` module.

## Console UI Enhancements

* [ ] Develop robust text command parsing for all game actions[cite: 3].
* [ ] Improve console display of the game board, pieces, and game state.
* [ ] Implement clear prompts for player input and feedback messages.
* [ ] Add a main menu system (New Game, Load Player, View History, Exit).

## Known Issues / Future Considerations

* [ ] Consider edge cases for Lion/Tiger jump (e.g., board edges, multiple rats).
* [ ] Plan for error handling and user input validation.
* [ ] (Future) Explore if a simple AI opponent could be a stretch goal.
* [ ] (Future) Consider logging game moves for replay or analysis.
* [ ] Refine coordinate system and user input for moves (e.g., "A1 to B1" vs. numeric coordinates).