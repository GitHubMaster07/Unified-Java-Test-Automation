package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Integrity Engine.
 * Provides a low-level access layer to the database to perform state validation 
 * and verify that API/UI actions have been correctly persisted in the system of record.
 */
public class DataValidationUtility {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    /**
     * Initializes the database connection profile.
     * Connection details are typically injected from environment-specific configurations 
     * to support seamless testing across the deployment pipeline.
     */
    public DataValidationUtility(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    /**
     * Executes read-only queries to perform deep state assertions.
     * This method is used to compare the UI/API output with the actual backend data 
     * to ensure no data corruption occurred during the transaction.
     */
    public void executeSelectQuery(String query) {
        // Utilizing try-with-resources to guarantee connection closure and prevent memory leaks
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            int columnCount = rs.getMetaData().getColumnCount();

            // Mapping result set to standard output for traceability in test logs/reports
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            // Log full stack trace to simplify root cause analysis (RCA) during test failures
            e.printStackTrace();
        }
    }

    /**
     * Facilitates 'Test Data Cleanup' or 'State Injection' by executing DML commands.
     * Essential for maintaining test isolation by resetting the database state 
     * before or after specific execution blocks.
     * * @return count of affected records to verify the success of the data manipulation.
     */
    public int executeUpdateQuery(String query) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(query)) {

            return ps.executeUpdate();

        } catch (SQLException e) {
            // Capturing persistence errors to identify potential DB constraint violations
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Entry point for manual debugging and connectivity smoke tests.
     */
    public static void main(String[] args) {
        // Standard template for local verification of DB access policies
        DataValidationUtility dbUtil = new DataValidationUtility(
                "jdbc:mysql://localhost:3306/your_db_name",
                "your_username",
                "your_password"
        );

        dbUtil.executeSelectQuery("SELECT * FROM users");

        int rowsUpdated = dbUtil.executeUpdateQuery("UPDATE users SET status='active' WHERE id=1");
        System.out.println("Operational check - rows affected: " + rowsUpdated);
    }
}
