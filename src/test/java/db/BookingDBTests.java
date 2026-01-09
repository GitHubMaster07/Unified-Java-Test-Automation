package db;

import core.DBManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

public class BookingDBTests {
    private DBManager dbManager;

    @BeforeMethod
    public void setup() {
        dbManager = new DBManager();
        // Step 1: Purge existing data to ensure strict test isolation (no pollution from previous runs).
        dbManager.executeUpdate("DELETE FROM bookings");

        // Step 2: Seed fresh data. We explicitly populate ALL columns to prevent 'null' pointer assertions.
        String seedData = "INSERT INTO bookings (booking_id, firstname, lastname, email, totalprice) " +
                "VALUES (101, 'John', 'Doe', 'john.doe@example.com', 200)";
        dbManager.executeUpdate(seedData);
    }

    /**
     * Validates that the persistence layer correctly stores and retrieves user-specific data.
     * This test checks for data corruption, specifically focusing on the 'email' field.
     */
    @Test
    public void testRetrieveExpectedUserRecord() {
        String query = "SELECT * FROM bookings WHERE booking_id = 101";
        List<Map<String, Object>> results = dbManager.executeQuery(query);

        // Verification logic
        Assert.assertFalse(results.isEmpty(), "DB FAILURE: Targeted record (ID 101) was not found in the 'bookings' table.");

        Map<String, Object> record = results.get(0);

        // Asserting the exact value to catch mapping issues (case-sensitivity or null returns).
        String actualEmail = (String) record.get("email");
        Assert.assertEquals(actualEmail, "john.doe@example.com",
                "DATA INTEGRITY ERROR: The email retrieved from DB does not match the seeded value.");

        Assert.assertEquals(record.get("firstname"), "John", "Field mismatch: firstname.");
    }
}