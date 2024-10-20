package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The JdbcUtil class is a utility class that manages the JDBC connection to the database.
 * It provides a single static method {@link #getConnection()} to access the established connection.
 * The connection is initialized when the class is loaded using a static block.
 * This class is responsible for establishing and providing the connection to the database.
 * The connection is created using the MySQL JDBC driver and the credentials specified in the static block.
 */
public class JdbcUtil {
    /**
     * The static {@code Connection} object that is used to interact with the database.
     * It is initialized in the static block and reused for all database operations.
     */
    private static Connection con;

    /**
     * Static block that initializes the {@code Connection} object when the class is loaded.
     * It establishes a connection to the MySQL database using the provided URL, user, and password.
     *
     * @throws RuntimeException if a {@link SQLException} occurs during connection initialization.
     */
    static {
        String url="jdbc:mysql://localhost:3306/mydb";    // Database URL
        String user="root";                               // Database username
        String password="my-secret-pw";                   // Database password

        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);  // Rethrow the exception as a RuntimeException
        }
    }

    /**
     * Retrieves the established database connection.
     *
     * @return the {@code Connection} object used to interact with the database.
     */
    public static Connection getConnection() {
        return con;
    }
}

