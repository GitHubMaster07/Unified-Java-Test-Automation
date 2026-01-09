package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages database connectivity (JDBC) and query execution for data validation.
 */
public class DBManager {

    private static final Logger logger = LogManager.getLogger(DBManager.class);
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public DBManager() {
        this.dbUrl = ConfigManager.getProperty("db.url");
        this.dbUser = ConfigManager.getProperty("db.user");
        this.dbPassword = ConfigManager.getProperty("db.password");
    }

    /**
     * Establishes a connection to the database.
     * @return A valid Connection object, or null if connection fails.
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            logger.info("Attempting to connect to database: {}", dbUrl);
            // Load the PostgreSQL driver explicitly (though often automatic since JDBC 4.0)
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            logger.info("Successfully connected to the database.");
        } catch (SQLException e) {
            logger.error("Database connection failed for URL: {}. SQL State: {}. Error: {}", dbUrl, e.getSQLState(), e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.fatal("PostgreSQL JDBC Driver not found in classpath. Check pom.xml.", e);
        }
        return connection;
    }

    /**
     * Executes a SELECT query and returns the results as a list of Maps.
     * Each Map represents one row.
     * @param query The SQL SELECT query string.
     * @return A List of Maps containing the query results.
     */
    public List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        logger.info("Executing database query: {}", query);

        // Use try-with-resources to ensure all resources are closed automatically
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (conn == null) {
                logger.error("Cannot execute query: Database connection is null.");
                return results;
            }

            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>(columns);
                for (int i = 1; i <= columns; ++i) {
                    // Use getObject() to handle various SQL types
                    row.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));
                }
                results.add(row);
            }
            logger.info("Query execution successful. Retrieved {} rows.", results.size());

        } catch (SQLException e) {
            logger.error("Error executing query: {}. Details: {}", query, e.getMessage());
        }
        return results;
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE query.
     * @param query The SQL DML query string.
     * @return The number of rows affected.
     */
    public int executeUpdate(String query) {
        int rowsAffected = 0;
        logger.info("Executing database update: {}", query);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            if (conn == null) {
                logger.error("Cannot execute update: Database connection is null.");
                return 0;
            }
            rowsAffected = stmt.executeUpdate(query);
            logger.info("Update successful. Rows affected: {}", rowsAffected);
        } catch (SQLException e) {
            logger.error("Error executing update: {}. Details: {}", query, e.getMessage());
        }
        return rowsAffected;
    }
}
