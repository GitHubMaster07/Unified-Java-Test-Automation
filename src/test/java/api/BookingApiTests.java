package api;

import builders.BookingDataBuilder;
import core.ConfigManager; // <-- NEW IMPORT
import data.DataValidationUtility;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig; // <-- Required for timeouts
import io.restassured.http.ContentType;
import org.apache.http.params.CoreConnectionPNames; // <-- Required for timeouts
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class BookingApiTests {

    @BeforeClass
    public void setup() {
        // Set the base URI using the ConfigManager
        RestAssured.baseURI = ConfigManager.getProperty("api.base.url");

        // Implement Connection Timeout using value from ConfigManager
        int timeout = ConfigManager.getIntProperty("api.timeout.seconds") * 1000;

        // Note: We are setting timeouts to prevent infinite hangs due to server being down
        RestAssured.config = RestAssured.config().httpClient(
                HttpClientConfig.httpClientConfig().setParam(
                        CoreConnectionPNames.CONNECTION_TIMEOUT, timeout
                ).setParam(
                        CoreConnectionPNames.SO_TIMEOUT, timeout
                )
        );
    }

    // ... rest of the class (verifyNewBookingDataIntegrity method remains the same)

}