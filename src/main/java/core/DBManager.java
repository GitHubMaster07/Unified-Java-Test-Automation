package core;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManager {

    /**
     * Establishes a connection to the H2 In-Memory database.
     * The 'DB_CLOSE_DELAY=-1' flag is vital: it prevents the database from vanishing
     * when the last connection is closed, keeping data alive throughout the JVM lifecycle.
     */
    public static Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

            // Self-healing schema: Automatically re-creates the table if it's a new memory instance.
            // This prevents "Table NOT FOUND" errors during parallel or clean test runs.
            ensureSchemaExists(conn);

            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("DB Connection failed. Check H2 driver dependency or JDBC URL.", e);
        }
    }

    /**
     * Synchronizes the database structure with the requirements of the entire test suite.
     * We include all columns (email, lastname, etc.) to satisfy both Orchestrated E2E and Unit tests.
     */
    private static void ensureSchemaExists(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS bookings (" +
                "booking_id INT PRIMARY KEY, " +
                "firstname VARCHAR(255), " +
                "lastname VARCHAR(255), " +
                "email VARCHAR(255), " +
                "totalprice INT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("CRITICAL SCHEMA ERROR: Failed to sync table structure: " + e.getMessage());
        }
    }

    /**
     * Executes SELECT queries and maps the ResultSet to a List of Maps.
     * Key Feature: Normalizes column names to lower case to ensure assertions like .get("email")
     * work regardless of whether the DB engine returns keys in UPPER or mixed case.
     */
    public List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> rows = new ArrayList<>();
        // Using try-with-resources to prevent memory leaks and 'too many open files' on Windows agents.
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    // .toLowerCase() is a safeguard for cross-database compatibility (H2/Oracle/Postgres).
                    row.put(metaData.getColumnName(i).toLowerCase(), rs.getObject(i));
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            System.err.println("QUERY EXECUTION ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return rows;
    }

    /**
     * Handles data modification (INSERT, UPDATE, DELETE).
     * This is the backbone for data seeding and state-based orchestration between API and UI layers.
     */
    public void executeUpdate(String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("UPDATE EXECUTION ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}