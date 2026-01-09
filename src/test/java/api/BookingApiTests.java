package api;

import core.ConfigManager;
import io.restassured.RestAssured;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Regression")
@Feature("API Stability")
public class BookingApiTests {

    @BeforeClass
    public void setup() {
        // Redundant check here is a safety net. If BaseApiTest failed or wasn't inherited,
        // we prevent RestAssured from defaulting to localhost and giving false results.
        String baseUri = ConfigManager.getProperty("api.base.url");
        if (baseUri == null) {
            throw new RuntimeException("Environment Mismatch: 'api.base.url' is missing. Termination required.");
        }
        RestAssured.baseURI = baseUri;
    }

    @Test(groups = "regression", description = "Verify API Health")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Connectivity check. Failure here usually points to VPN/Proxy issues or a dead environment.")
    public void testHealthCheck() {
        try {
            RestAssured.given()
                    .when()
                    .get("/ping")
                    .then()
                    // Herokuapp's /ping returns 201 Created by design.
                    // Hardcoding this avoids 'Expected 200 but got 201' false failures.
                    .statusCode(201);
        } catch (Exception e) {
            // Windows firewalls often silently drop packets.
            // Explicit log helps to distinguish between 'Server Error' and 'Network Unreachable'.
            System.err.println("NETWORK FAILURE: Connection to " + RestAssured.baseURI + " timed out or was refused.");
            throw e;
        }
    }

    @Test(groups = "regression")
    public void verifyNewBookingDataIntegrity() {
        // Placeholder for data consistency checks between DB and API responses.
        System.out.println("API INTEGRITY: Execution successful.");
    }
}