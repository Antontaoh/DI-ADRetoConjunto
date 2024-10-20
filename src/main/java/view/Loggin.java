package view;

import dao.CopyFilmDAO;
import dao.UserDAO;
import model.CopyFilm;
import util.SessionManager;
import util.JdbcUtil;

import javax.swing.*;
import java.util.List;

/**
 * The {@code Loggin} class is a JFrame that represents the login screen of the application.
 * It allows users to input their username and password, and verifies the credentials against
 * the database. If the credentials are correct, the user is logged in and redirected to the main view.
 * If the credentials are incorrect, an error message is displayed.
 * <p>
 * The login functionality uses the {@link UserDAO} to validate the user's credentials and
 * {@link CopyFilmDAO} to retrieve the films associated with the logged-in user. The current user
 * and their films are stored in the {@link SessionManager}.
 * </p>
 */
public class Loggin extends JFrame {
    private JPanel mainLoggin;
    private JPasswordField passwordField1;
    private JTextField userTextField;
    private JButton logginButton;
    private JButton closeButton;

    /**
     * Constructs a new {@code Loggin} frame that displays the login form.
     * Sets up the window properties, including size, title, and layout.
     * Initializes action listeners for the buttons to handle user login and closing the application.
     */
    public Loggin() {
        setContentPane(mainLoggin);
        setTitle("loggin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,1000);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add action listener to handle login
        logginButton.addActionListener((e) -> {
            logginUser();
        });

        // Add action listener to close the application
        closeButton.addActionListener((e) -> {
            SessionManager.resetSession();
            dispose();
        });
    }

    /**
     * Authenticates the user by validating the username and password entered in the login form.
     * If the credentials are correct, the user's details and associated films are stored in the session.
     * The main view is then displayed. If the credentials are incorrect, an error message is shown.
     */
    private void logginUser() {
        var userDao = new UserDAO(JdbcUtil.getConnection());
        var copyDao = new CopyFilmDAO(JdbcUtil.getConnection());
        var user = userDao.validateUser(userTextField.getText(), passwordField1.getPassword());

        if (user != null) {
            // Retrieve the user's films from the database
            List<CopyFilm> userCopy = copyDao.getByUser(user);
            user.setFilms(userCopy);

            // Store the user and their films in the session
            SessionManager.currentUser = user;
            SessionManager.userCopies = userCopy;

            // Redirect to the main view
            var mainView = new MainView();
            mainView.setVisible(true);
            dispose();
        } else {
            // Show error message if the credentials are incorrect
            JOptionPane.showMessageDialog(this, "Incorrect Username or Password");
            userTextField.setText("");
            passwordField1.setText("");
        }
    }
}

