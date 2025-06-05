# Xou Dou Qi: The Java Jungle Showdown 🐘🐅🐀

**Tagline:** Command your animal champions and conquer the digital jungle!

## Purpose

**Xou Dou Qi: The Java Jungle Showdown** is a two-player, turn-based strategy board game implemented as a Java console application. It aims to provide an interactive and engaging digital version of the traditional Chinese board game Xou Dou Qi (also known as Jungle or Dou Shou Qi), allowing players to compete on the same machine[cite: 1].

This project solves the need for an accessible, console-based rendition of the game, focusing on core gameplay, player management, and game history tracking.

## Core Technology

* **Language:** Java
* **User Interface:** Console-based (Text commands) [cite: 3]
* **Database:** Embedded Database (e.g., SQLite, H2) for player accounts and game history [cite: 2, 5]

## High-Level Architecture

The application will be structured with a modular design:

1.  **Core Game Logic:** Manages game state, rules, piece movements, captures, and win conditions[cite: 3].
2.  **Player Management:** Handles player account creation, login, and data persistence[cite: 2].
3.  **Data Storage:** Interfaces with the embedded database for storing player and game history data[cite: 2, 5].
4.  **Console UI Layer:** Manages user input via text commands and displays game output to the console[cite: 3].

## Features ✨

* **Player Account Management:** Create, log in, and register player accounts stored in an embedded database[cite: 2].
* **Full Game Logic Implementation:** Accurately implements all rules of Xou Dou Qi, including:
    * Hierarchical piece movements and captures (e.g., Rat captures Elephant, Elephant cannot capture Rat unless in a trap)[cite: 11].
    * Special animal abilities (e.g., Lion and Tiger jumping rivers[cite: 12], Rat swimming [cite: 13]).
    * Effects of special board zones (Rivers, Trap Squares[cite: 16], Sanctuaries).
    * Clear victory conditions (occupying the opponent's sanctuary [cite: 10, 17]).
* **Simple Console Interface:**
    * Intuitive text-based command system for gameplay[cite: 3].
    * Automatic turn management between the two players[cite: 4].
* **Game History:**
    * Saves results of past games (wins, losses, draws, scores) to the database[cite: 5].
    * Allows players to view their game history on demand[cite: 6].

## Installation

**(Placeholder)** Instructions on how to compile and set up the project will go here. This will likely involve:
* Cloning the repository.
* Ensuring Java Development Kit (JDK) is installed.
* Instructions for any build tools (e.g., Maven, Gradle) if used.
* Setting up the embedded database if manual steps are needed.

## Usage

**(Placeholder)** Instructions on how to run the game and basic commands will go here. For example:
* How to start a new game.
* Command syntax for moving pieces (e.g., `move A1 B1`).
* Commands for viewing game history or managing accounts.