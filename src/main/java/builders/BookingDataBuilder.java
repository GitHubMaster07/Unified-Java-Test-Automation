package builders;

import java.util.HashMap;
import java.util.Map;

/**
 * Data Factory for API Payload Construction.
 * Implements the Builder pattern to create flexible and immutable-like data structures 
 * for Booking entities, ensuring test data consistency across various API scenarios.
 */
public class BookingDataBuilder {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid = true;
    private Map<String, String> bookingdates = new HashMap<>();

    /**
     * Initializes the builder with 'Safe Defaults'.
     * Pre-configuring standard check-in/out dates allows for minimal scenario setup 
     * while still providing full customization for edge cases.
     */
    public BookingDataBuilder() {
        bookingdates.put("checkin", "2025-01-01");
        bookingdates.put("checkout", "2025-01-05");
    }

    /**
     * Fluent API methods for entity attribute customization.
     * Returns the current instance to allow method chaining in test scripts.
     */
    public BookingDataBuilder setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public BookingDataBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public BookingDataBuilder setTotalPrice(int totalprice) {
        this.totalprice = totalprice;
        return this;
    }

    /**
     * Orchestrates the final JSON-compatible payload assembly.
     * Encapsulates the logic for mandatory fields and default 'additionalneeds' 
     * to keep API test methods clean and readable.
     * @return Map representing the structured booking object.
     */
    public Map<String, Object> build() {
        Map<String, Object> booking = new HashMap<>();
        booking.put("firstname", this.firstname);
        booking.put("lastname", this.lastname);
        booking.put("totalprice", this.totalprice);
        booking.put("depositpaid", this.depositpaid);
        booking.put("bookingdates", this.bookingdates);
        booking.put("additionalneeds", "Breakfast");
        return booking;
    }
}
