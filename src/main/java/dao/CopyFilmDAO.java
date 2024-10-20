package dao;

import model.CopyFilm;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code CopyFilmDAO} class provides CRUD operations for the {@link CopyFilm} entity.
 * This class implements the {@link DAO} interface and interacts with a MySQL database to perform
 * operations like fetching, adding, updating, and deleting {@link CopyFilm} records.
 *
 * This DAO handles the operations related to the {@code CopyFilm} entity,
 * including retrieving all copies, getting a copy by its ID, adding, updating, and deleting a copy.
 */
public class CopyFilmDAO implements DAO<CopyFilm> {

    /**
     * Database connection object used to execute SQL queries.
     */
    private static Connection con;

    /**
     * Constructor for CopyFilmDAO that initializes the database connection.
     *
     * @param c the Connection object used for the database interaction
     */
    public CopyFilmDAO(Connection c) {
        con = c;
    }

    /**
     * Retrieves all CopyFilm records from the database.
     *
     * @return a List of CopyFilm objects representing all the copies stored in the database
     */
    @Override
    public List<CopyFilm> getAll() {
        var copies = new ArrayList<CopyFilm>();
        try (var st = con.createStatement()) {
            var rs = st.executeQuery("select * from copy");
            while (rs.next()) {
                var film = new CopyFilm();
                film.setId(rs.getInt("id"));
                film.setCondition(rs.getString("condition"));
                film.setSupport(rs.getString("support"));
                film.setFilmId(rs.getInt("film_id"));
                film.setUserId(rs.getInt("user_id"));
                copies.add(film);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return copies;
    }

    /**
     * Retrieves a CopyFilm by its ID from the database.
     *
     * @param id the ID of the CopyFilm to retrieve
     * @return the CopyFilm object with the specified ID
     */
    @Override
    public CopyFilm getById(int id) {
        var film = new CopyFilm();
        try (var ps = con.prepareStatement("select * from copy where id=?")) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                film.setId(rs.getInt("id"));
                film.setCondition(rs.getString("condition"));
                film.setSupport(rs.getString("support"));
                film.setFilmId(rs.getInt("film_id"));
                film.setUserId(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    /**
     * Adds a new CopyFilm record to the database.
     *
     * @param copyFilm the CopyFilm object to be added to the database
     */
    @Override
    public void add(CopyFilm copyFilm) {
        try (var ps = con.prepareStatement("insert into copy(condition, support, film_id, user_id) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, copyFilm.getCondition());
            ps.setString(2, copyFilm.getSupport());
            ps.setInt(3, copyFilm.getFilmId());
            ps.setInt(4, copyFilm.getUserId());
            ps.executeUpdate();
            if (ps.executeUpdate() > 0) {
                var rs = ps.getGeneratedKeys();
                rs.next();
                copyFilm.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing CopyFilm record in the database.
     *
     * @param copyFilm the CopyFilm object containing the updated data
     */
    @Override
    public void update(CopyFilm copyFilm) {
        try (var ps = con.prepareStatement("update copy set condition=?, support=?, film_id=?, user_id=? where id=?")) {
            ps.setString(1, copyFilm.getCondition());
            ps.setString(2, copyFilm.getSupport());
            ps.setInt(3, copyFilm.getFilmId());
            ps.setInt(4, copyFilm.getUserId());
            ps.setInt(5, copyFilm.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a CopyFilm record from the database.
     *
     * @param copyFilm the CopyFilm object to be deleted
     */
    @Override
    public void delete(CopyFilm copyFilm) {
        try (var ps = con.prepareStatement("delete from copy where id=?")) {
            ps.setInt(1, copyFilm.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all CopyFilm records associated with a specific User.
     *
     * @param user the User whose CopyFilm records are to be retrieved
     * @return a List of CopyFilm objects associated with the specified User
     */
    public List<CopyFilm> getByUser(User user) {
        int id = user.getId();
        var copies = new ArrayList<CopyFilm>();

        try (var ps = con.prepareStatement("select * from copy where user_id=?")) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            while (rs.next()) {
                var copy = new CopyFilm();
                copy.setId(rs.getInt("id"));
                copy.setCondition(rs.getString("condition"));
                copy.setSupport(rs.getString("support"));
                copy.setFilmId(rs.getInt("film_id"));
                copy.setUserId(rs.getInt("user_id"));
                copies.add(copy);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return copies;
    }
}