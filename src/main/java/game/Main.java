package game;

import game.core.GameManager;
import console.ui.ConsoleInputHandler;
import console.ui.ConsoleOutputView;
import player.accounts.AccountService;
import data.storage.DatabaseManager;

/**
 * Main entry point for the Xou Dou Qi Java console application.
 * Initializes all core components and starts the game loop.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("🐘🐅🐀 Welcome to Xou Dou Qi: The Java Jungle Showdown! 🐀🐅🐘");
        System.out.println("Initializing game components...");
        
        try {
            // Initialize database
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.initialize();
            
            // Initialize services
            AccountService accountService = new AccountService(dbManager);
            ConsoleOutputView outputView = new ConsoleOutputView();
            ConsoleInputHandler inputHandler = new ConsoleInputHandler();
              // Initialize game manager
            GameManager gameManager = new GameManager(accountService, outputView, inputHandler);
            
            // Start the application
            gameManager.start();
            
        } catch (Exception e) {
            System.err.println("Failed to initialize the game: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
