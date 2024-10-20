import view.Loggin;

/**
 * The {@code Main} class is the entry point of the application.
 * It initializes and displays the login screen when the application is launched.
 * <p>
 * This class contains the main method, which is the starting point for the Java program.
 * It creates an instance of the {@link Loggin} class and makes the login window visible.
 * </p>
 */
public class Main {

    /**
     * The main method serves as the entry point of the application.
     * It creates an instance of the {@link Loggin} view and displays it.
     *
     * @param args command-line arguments (unused in this application)
     */
    public static void main(String[] args) {
        // Create and show the login screen
        var loggin = new Loggin();
        loggin.setVisible(true);
    }
}
