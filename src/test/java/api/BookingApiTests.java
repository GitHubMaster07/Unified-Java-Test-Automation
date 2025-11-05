package api;

import builders.BookingDataBuilder;
import data.DataValidationUtility; // Import the utility from Step 7
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class BookingApiTests {

    @BeforeClass
    public void setup() {
        // Set the base URI for the Restful Booker API (or your target API)
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test(groups = {"api", "regression"})
    public void verifyNewBookingDataIntegrity() {
        String tempPatientId = "P9876"; // Use this ID for both API and DB checks

        // Step 1: BUILD the request payload using the builder (from Step 6)
        Map<String, Object> payload = new BookingDataBuilder()
                .setFirstname("Integrity")
                .setLastname("Checker")
                .setTotalPrice(200)
                .build();

        // Simulate adding the patient ID to the API request body
        payload.put("patientId", tempPatientId);

        // Step 2: SEND the API request and extract the status
        String apiStatus = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().path("booking.eligibilityStatus");
        // NOTE: Assuming the API response contains eligibilityStatus



        // Step 3: DATABASE/ETL VALIDATION (The Senior SDET differentiator)
        // Assert that the API response status matches the authoritative DB source
        boolean dbActive = DataValidationUtility.isPatientStatusActive(tempPatientId); // <-- UNCOMMENT THIS LINE

        Assert.assertTrue(dbActive,
                "CRITICAL FAILURE: Database status is NOT Active for patient: " + tempPatientId);
    }
}

