package db;

import core.DBManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Suite for Data Integrity and ETL validation.
 * Verifies that the persistence layer correctly reflects the business state 
 * initiated by UI or API operations, ensuring end-to-end data consistency.
 */
public class BookingDBTests {

    /**
     * Leveraging the centralized DBManager for session handling. 
     * This ensures consistent connection pooling and credential management.
     */
    private final DBManager dbManager = new DBManager();

    /**
     * Infrastructure Health Check.
     * Validates connectivity to the persistence layer before executing complex data assertions.
     * Essential for early detection of environment-related failures in the CI/CD pipeline.
     */
    @Test(groups = {"db"})
    public void testDatabaseConnection() {
        Assert.assertNotNull(dbManager.getConnection(), 
                "Critical Failure: Unable to establish a secure handshake with the database.");
    }

    /**
     * Validation of Post-Transaction State (ETL/Integrity Check).
     * This test acts as a 'Truth Verification' step, ensuring that data injected via 
     * upstream services (API/UI) is correctly stored with the expected status and attributes.
     */
    @Test(groups = {"db"})
    public void testRetrieveExpectedUserRecord() {
        // Business identifier for the target record validation
        String expectedUser = "john.doe@example.com";
        
        /**
         * Querying the 'System of Record' for specific business constraints.
         * We verify not just existence, but the 'Active' status to ensure correct lifecycle state.
         */
        String query = "SELECT * FROM users WHERE email = '" + expectedUser + "' AND status = 'active'";

        List<Map<String, Object>> result = dbManager.executeQuery(query);

        /**
         * Constraint 1: Uniqueness. 
         * Ensures the database maintains integrity and doesn't contain duplicate records for a single entity.
         */
        Assert.assertEquals(result.size(), 1,
                "Integrity Violation: Expected a unique record for user: " + expectedUser);

        // Extracting record for deep attribute verification
        Map<String, Object> userRecord = result.get(0);

        /**
         * Constraint 2: Schema Integrity.
         * Validates that the returned data types match the expected domain model (Primary Key integrity).
         */
        Assert.assertTrue(userRecord.get("id") instanceof Integer, "Type Mismatch: User ID must adhere to Integer schema.");

        /**
         * Constraint 3: Data Accuracy.
         * Final verification that the persisted email matches the source input exactly.
         */
        Assert.assertEquals(userRecord.get("email"), expectedUser,
                "Data Corruption: Persisted email value deviates from the source transaction.");
    }
}
