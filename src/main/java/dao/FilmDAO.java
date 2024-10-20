package dao;

import model.Film;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FilmDAO} class provides CRUD operations for the {@link Film} entity.
 * This class implements the {@link DAO} interface to interact with a MySQL database for
 * performing operations such as fetching, adding, updating, and deleting {@link Film} records.
 *
 * This DAO handles the operations related to the {@code Film} entity,
 * including retrieving all films, getting a film by its ID, adding, updating, and deleting a film.
 */
public class FilmDAO implements DAO<Film> {

    /**
     * Database connection object used to execute SQL queries.
     */
    public static Connection con = null;

    /**
     * Constructor for FilmDAO that initializes the database connection.
     *
     * @param c the Connection object used for database interaction
     */
    public FilmDAO(Connection c) {
        con = c;
    }

    /**
     * Retrieves all Film records from the database.
     *
     * @return a List of Film objects representing all films stored in the database
     */
    @Override
    public List<Film> getAll() {
        var films = new ArrayList<Film>();
        try (var st = con.createStatement()) {
            var rs = st.executeQuery("select * from film");
            while (rs.next()) {
                var film = new Film();
                film.setId(rs.getInt("id"));
                film.setTitle(rs.getString("title"));
                film.setGenre(rs.getString("genre"));
                film.setYear(rs.getInt("year"));
                film.setDescription(rs.getString("description"));
                film.setDirector(rs.getString("director"));
                films.add(film);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return films;
    }

    /**
     * Retrieves a Film by its ID from the database.
     *
     * @param id the ID of the Film to retrieve
     * @return the Film object with the specified ID
     */
    @Override
    public Film getById(int id) {
        var film = new Film();
        try (var ps = con.prepareStatement("select * from film where id=?")) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                film.setId(rs.getInt("id"));
                film.setTitle(rs.getString("title"));
                film.setGenre(rs.getString("genre"));
                film.setYear(rs.getInt("year"));
                film.setDescription(rs.getString("description"));
                film.setDirector(rs.getString("director"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    /**
     * Adds a new Film record to the database.
     *
     * @param film the Film object to be added to the database
     */
    @Override
    public void add(Film film) {
        try (var ps = con.prepareStatement("insert into film(title, genre, year, description, director) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, film.getTitle());
            ps.setString(2, film.getGenre());
            ps.setInt(3, film.getYear());
            ps.setString(4, film.getDescription());
            ps.setString(5, film.getDirector());
            if (ps.executeUpdate() > 0) {
                var rs = ps.getGeneratedKeys();
                rs.next();
                film.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing Film record in the database.
     *
     * @param film the Film object containing the updated data
     */
    @Override
    public void update(Film film) {
        try (var ps = con.prepareStatement("update film set title=?, genre=?, year=?, description=?, director=? where id=?")) {
            ps.setString(1, film.getTitle());
            ps.setString(2, film.getGenre());
            ps.setInt(3, film.getYear());
            ps.setString(4, film.getDescription());
            ps.setString(5, film.getDirector());
            ps.setInt(6, film.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a Film record from the database.
     *
     * @param film the Film object to be deleted
     */
    @Override
    public void delete(Film film) {
        try (var ps = con.prepareStatement("delete from film where id=?")) {
            ps.setInt(1, film.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

