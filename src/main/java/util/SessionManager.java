package util;

import lombok.Data;
import model.CopyFilm;
import model.Film;
import model.User;

import java.util.List;

/**
 * SessionManager class to store information related to the current session
 * This class manages static attributes for user, copies, and movies.
 */
public class SessionManager {
    /** Static attribute for the logged-in user */
    public static User currentUser = null;
    /** Static attribute for a selected copy, later added to the user's copy list */
    public static CopyFilm selectedCopy = null;
    /** Static list to store all copies associated with the user */
    public static List<CopyFilm> userCopies = null;
    /** Static attribute to store the selected movie's data */
    public static Film selectedFilm = null;

    /**
     * Resets all session-related attributes to null
     */
    public static void resetSession() {
        currentUser = null;
        selectedCopy = null;
        userCopies = null;
        selectedFilm = null;
    }
}
