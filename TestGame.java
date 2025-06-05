import java.io.ByteArrayInputStream;

public class TestGame {
    public static void main(String[] args) {
        // Simulate input for testing
        String input = "1,1 1,2\nquit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Run the game
        game.Main.main(args);
    }
}
