package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserDAO} class provides CRUD operations for the {@link User} entity.
 * This class implements the {@link DAO} interface to interact with a MySQL database for
 * performing operations such as retrieving, adding, updating, and deleting {@link User} records.
 * It also includes a method for validating user credentials.
 */
public class UserDAO implements DAO<User> {

    /**
     * Database connection object used to execute SQL queries.
     */
    private static Connection con = null;

    /**
     * Constructor for UserDAO that initializes the database connection.
     *
     * @param c the Connection object used for database interaction
     */
    public UserDAO(Connection c) {
        con = c;
    }

    /**
     * Retrieves all User records from the database.
     *
     * @return a List of User objects representing all users stored in the database
     */
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            var rs = st.executeQuery("select * from user");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * Retrieves a User by its ID from the database.
     *
     * @param id the ID of the User to retrieve
     * @return the User object with the specified ID
     */
    @Override
    public User getById(int id) {
        var user = new User();
        try (PreparedStatement ps = con.prepareStatement("select * from user where id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("userName"));
                    user.setPassword(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * Adds a new User record to the database.
     *
     * @param user the User object to be added to the database
     */
    @Override
    public void add(User user) {
        try (var ps = con.prepareStatement("insert into user(userName,password) values(?,?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            if (ps.executeUpdate() > 0) {
                var rs = ps.getGeneratedKeys();
                rs.next();
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing User record in the database.
     *
     * @param user the User object containing updated data
     */
    @Override
    public void update(User user) {
        try (var ps = con.prepareStatement("update user set userName=?, password=? where id=?")) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a User record from the database.
     *
     * @param user the User object to be deleted
     */
    @Override
    public void delete(User user) {
        try (var ps = con.prepareStatement("delete from user where id=?")) {
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates a user's credentials by checking the database for a matching username and password.
     *
     * @param user the username to validate
     * @param userPassword the password to validate
     * @return the User object if the credentials are valid, otherwise returns an empty User object
     */
    public User validateUser(String user, char[] userPassword) {
        var userData = new User();
        try (var ps = con.prepareStatement("select * from user where userName=? and password=?")) {
            var password = new String(userPassword);
            ps.setString(1, user);
            ps.setString(2, password);
            var rs = ps.executeQuery();
            if (rs.next()) {
                userData.setId(rs.getInt("id"));
                userData.setUserName(rs.getString("userName"));
                userData.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userData;
    }
}

