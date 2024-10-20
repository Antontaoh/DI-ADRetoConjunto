package view;

import dao.CopyFilmDAO;
import util.JdbcUtil;
import util.SessionManager;

import javax.swing.*;

/**
 * The {@code Detail} class is a JDialog that displays the details of a selected film.
 * It allows the user to view information about the selected film, such as the title, genre, director,
 * release year, and description. The dialog also provides buttons to return to the main view or delete
 * a copy of the film.
 * <p>
 * This dialog uses the {@link CopyFilmDAO} for interacting with the database and uses {@link SessionManager}
 * to manage session data such as the currently selected film or user.
 * </p>
 */
public class Detail extends javax.swing.JDialog {
    private JPanel detailPanel;
    private JButton returnButton;
    private JButton deleteButton;
    private JTextField titleField;
    private JTextField genreField;
    private JTextField directorField;
    private JTextField yearField;
    private JTextArea descriptionField;

    /**
     * The DAO object used to interact with the {@code copy} table in the database.
     */
    CopyFilmDAO copyDao = new CopyFilmDAO(JdbcUtil.getConnection());

    /**
     * Constructs a new {@code Detail} dialog, displaying information about the selected film.
     * Initializes the form fields with the details of the selected film and sets up action listeners
     * for the buttons to handle returning to the main view and deleting a copy of the film.
     */
    public Detail() {
        setContentPane(detailPanel);
        setModal(true);
        setTitle(SessionManager.selectedFilm.getTitle());
        setLocationRelativeTo(null);
        setResizable(false);

        // Fill in the form fields with the details of the selected film
        titleField.setText(SessionManager.selectedFilm.getTitle());
        genreField.setText(SessionManager.selectedFilm.getGenre());
        directorField.setText(SessionManager.selectedFilm.getDirector());
        yearField.setText(String.valueOf(SessionManager.selectedFilm.getYear()));
        descriptionField.setText(SessionManager.selectedFilm.getDescription());
        pack();

        // Set action listeners for the buttons
        returnButton.addActionListener(e -> {
            returnView();
        });

        deleteButton.addActionListener(e -> {
            delete();
        });
    }

    /**
     * Closes the current dialog and returns to the main view.
     */
    public void returnView() {
        SessionManager.selectedFilm = null;  // Clear the selected film in the session
        var mainView = new MainView();       // Create a new instance of the main view
        mainView.setVisible(true);           // Show the main view
        dispose();                           // Close the detail view
    }

    /**
     * Prompts the user to confirm if they want to delete the selected film copy.
     * If the user confirms, the film copy is removed from the session and the database.
     */
    public void delete() {
        // Show a confirmation dialog
        var isDeletable = JOptionPane.showConfirmDialog(this, "Do you want to delete the copy?");
        if (isDeletable == JOptionPane.YES_OPTION) {
            // Remove the selected copy from the session and database
            SessionManager.userCopies.remove(SessionManager.selectedCopy);
            copyDao.delete(SessionManager.selectedCopy);
            SessionManager.selectedFilm = null;  // Clear the selected film after deletion
        }
        dispose();  // Close the current dialog
        var mainView = new MainView();  // Return to the main view
        mainView.setVisible(true);  // Show the main view
    }
}

