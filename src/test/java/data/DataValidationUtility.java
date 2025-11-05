package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class for database validation and queries.
 */
public class DataValidationUtility {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    /**
     * Constructor to initialize DB credentials.
     * @param dbUrl JDBC URL, e.g., "jdbc:mysql://localhost:3306/dbname"
     * @param dbUser Database username
     * @param dbPassword Database password
     */
    public DataValidationUtility(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    /**
     * Executes a SELECT query and prints the results.
     * @param query SQL query string
     */
    public void executeSelectQuery(String query) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes an UPDATE/INSERT/DELETE query.
     * @param query SQL query string
     * @return number of rows affected
     */
    public int executeUpdateQuery(String query) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(query)) {

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Example usage
    public static void main(String[] args) {
        DataValidationUtility dbUtil = new DataValidationUtility(
                "jdbc:mysql://localhost:3306/your_db_name",
                "your_username",
                "your_password"
        );

        // Example SELECT
        dbUtil.executeSelectQuery("SELECT * FROM users");

        // Example UPDATE
        int rowsUpdated = dbUtil.executeUpdateQuery("UPDATE users SET status='active' WHERE id=1");
        System.out.println("Rows updated: " + rowsUpdated);
    }
}
