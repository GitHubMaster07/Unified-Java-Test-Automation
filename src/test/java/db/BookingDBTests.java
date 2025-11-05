package db;

import core.DBManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Example test class demonstrating database validation using the DBManager.
 * NOTE: This test will fail if a PostgreSQL database is not running locally
 * with the correct connection details (from environment.properties).
 */
public class BookingDBTests {

    private final DBManager dbManager = new DBManager();

    @Test(groups = {"db"})
    public void testDatabaseConnection() {
        // Assert that we can successfully establish a connection
        Assert.assertNotNull(dbManager.getConnection(), "Database connection should not be null.");
    }

    @Test(groups = {"db"})
    public void testRetrieveExpectedUserRecord() {
        // Example ETL check: Verify a user added by an API test exists in the DB.
        // Replace this query with your actual ETL check.
        String expectedUser = "john.doe@example.com";
        String query = "SELECT * FROM users WHERE email = '" + expectedUser + "' AND status = 'active'";

        List<Map<String, Object>> result = dbManager.executeQuery(query);

        // 1. Assert that exactly one record was found
        Assert.assertEquals(result.size(), 1,
                "Expected to find exactly one active user record for " + expectedUser);

        // 2. Assert specific column values for data integrity
        Map<String, Object> userRecord = result.get(0);

        // Ensure the retrieved ID is a non-null integer (assuming 'id' is a primary key)
        Assert.assertTrue(userRecord.get("id") instanceof Integer, "User ID should be an Integer.");

        // Assert the username/email matches
        Assert.assertEquals(userRecord.get("email"), expectedUser,
                "Retrieved email does not match expected value.");
    }
}