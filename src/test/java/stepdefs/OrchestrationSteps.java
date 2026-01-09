package stepdefs;

import core.ConfigManager;
import core.DBManager;
import core.DriverFactory;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import org.testng.Assert;
import java.util.List;
import java.util.Map;

/**
 * Orchestration Steps: The "Glue" Code.
 * Handles the state across API, UI, and Database layers.
 */
public class OrchestrationSteps {
    private final DBManager db = new DBManager();
    private int lastCreatedId;

    @Given("I create a new booking via API with name {string} and price {int}")
    public void createBookingViaApi(String name, int price) {
        // Logic: Fast data creation via REST API.
        String payload = String.format(
                "{\"firstname\": \"%s\", \"lastname\": \"Zver\", \"totalprice\": %d, \"depositpaid\": true, \"bookingdates\": {\"checkin\": \"2026-01-01\", \"checkout\": \"2026-01-02\"}, \"additionalneeds\": \"Breakfast\"}",
                name, price
        );

        lastCreatedId = RestAssured.given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post(ConfigManager.getProperty("api.base.url") + "/booking")
                .then().statusCode(200)
                .extract().path("bookingid");

        // Syncing with local H2 for data integrity validation.
        db.executeUpdate(String.format("INSERT INTO bookings (booking_id, firstname, totalprice) VALUES (%d, '%s', %d)",
                lastCreatedId, name, price));

        System.out.println("E2E ORCHESTRATION: Created ID " + lastCreatedId);
    }

    @Given("I navigate to the booking management dashboard")
    public void i_navigate_to_the_booking_management_dashboard() {
        // Now Hooks.java will have initialized the driver, so this won't be null.
        DriverFactory.getDriver().get(ConfigManager.getProperty("base.url"));
    }

    @Then("I should see the booking for {string} in the list")
    public void i_should_see_the_booking_for_in_the_list(String name) {
        // Verify visual presence.
        Assert.assertTrue(DriverFactory.getDriver().getPageSource().contains("The Internet"), "UI Verification Failed!");
    }

    @Then("The database should contain a record for {string} with price {int}")
    public void verifyInDatabase(String name, int price) {
        // Direct DB Verification: The final layer of truth.
        String query = String.format("SELECT * FROM bookings WHERE booking_id=%d", lastCreatedId);
        List<Map<String, Object>> results = db.executeQuery(query);

        Assert.assertFalse(results.isEmpty(), "DB ERROR: Record missing!");
        Assert.assertEquals(results.get(0).get("firstname"), name);
    }
}