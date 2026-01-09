package builders;

import java.util.HashMap;
import java.util.Map;


//Builds the JSON payload for creating a booking.
 public class BookingDataBuilder {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid = true;
    private Map<String, String> bookingdates = new HashMap<>();

    // Constructor/Initializer
    public BookingDataBuilder() {
        // Set default dates for a quick test
        bookingdates.put("checkin", "2025-01-01");
        bookingdates.put("checkout", "2025-01-05");
    }

    
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

    // Method to assemble and return the final payload object
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
