package view;

import dao.FilmDAO;
import model.CopyFilm;
import util.JdbcUtil;
import util.SessionManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * The {@code MainView} class represents the main user interface of the application.
 * It displays a list of films owned by the current user, along with details like
 * film title, condition, and support type. The user can also select a film copy
 * to view its details or log out of the application.
 * <p>
 * This view allows users to interact with their film collection, including viewing
 * details about a selected copy and returning to the login screen or logging out.
 * </p>
 */
public class MainView extends javax.swing.JFrame {
    private JPanel mainContent;
    private JTable filmsList;
    private DefaultTableModel model;
    private JButton outButton;
    private JButton returnButton;

    /**
     * Constructs the {@code MainView} frame that displays the user's film collection.
     * It initializes the film list table, sets up the table model, and assigns action listeners
     * to the buttons for logging out or returning to the login screen.
     */
    public MainView() {
        // Set up the table model with column names
        String[] fields = {"Title", "Condition", "Support"};
        model = new DefaultTableModel(fields, 0);
        filmsList = new JTable(model);
        var fieldsTable = filmsList.getColumnModel().getColumn(0);
        fieldsTable.setPreferredWidth(300);

        // Show the films list for the current user
        showFilmsList();

        // Set up window properties
        setContentPane(mainContent);
        setTitle("Films List - " + SessionManager.currentUser.getUserName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 1000);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add event listener to handle film copy selection
        filmsList.getSelectionModel().addListSelectionListener(this::detailCopy);

        // Action listener for logout button
        outButton.addActionListener(e -> {
            SessionManager.resetSession();
            dispose();
        });

        // Action listener for returning to login screen
        returnButton.addActionListener(e -> {
            returnLoggin();
        });
    }

    /**
     * Populates the films list table with the user's film copies, including
     * the title, condition, and support type for each film.
     */
    public void showFilmsList() {
        for (CopyFilm c : SessionManager.userCopies) {
            var filmDao = new FilmDAO(JdbcUtil.getConnection());
            var film = filmDao.getById(c.getFilmId());
            c.setFilm(film);
            Object[] row = {film.getTitle(), c.getCondition(), c.getSupport()};
            model.addRow(row);
        }
    }

    /**
     * Handles returning to the login screen, resetting the current session and
     * disposing of the current window, then displaying the login screen again.
     */
    public void returnLoggin() {
        SessionManager.resetSession();
        dispose();
        var returnLoggin = new Loggin();
        returnLoggin.setVisible(true);
    }

    /**
     * Handles selecting a film copy from the list. When a row is selected,
     * the corresponding film details are retrieved, and the {@link Detail} view
     * is displayed for the selected film.
     *
     * @param e the event triggered when a selection is made in the films list table
     */
    public void detailCopy(ListSelectionEvent e) {
        var filmDao = new FilmDAO(JdbcUtil.getConnection());
        if (e.getValueIsAdjusting()) return;

        // Get the selected row index and the corresponding film copy
        int select = filmsList.getSelectedRow();
        SessionManager.selectedCopy = SessionManager.userCopies.get(select);
        var idFilm = SessionManager.userCopies.get(select).getFilmId();
        SessionManager.selectedFilm = filmDao.getById(idFilm);

        // Open the detail view for the selected film
        var detailView = new Detail();
        detailView.setVisible(true);
        dispose();
    }
}
