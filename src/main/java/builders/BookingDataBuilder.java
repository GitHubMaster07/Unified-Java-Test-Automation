package builders;

import java.util.HashMap;
import java.util.Map;

public class BookingDataBuilder {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid = true;
    private Map<String, String> bookingdates = new HashMap<>();

    public BookingDataBuilder() {
        // Safe defaults prevent API '400 Bad Request' errors for tests that don't care about dates.
        // Using static dates here, but for long-term CI runs, dynamic dates (LocalDate.now()) are preferred.
        bookingdates.put("checkin", "2026-01-01");
        bookingdates.put("checkout", "2026-01-05");
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

    /**
     * Final assembly of the payload.
     * If the API schema changes (e.g., 'additionalneeds' becomes mandatory),
     * we fix it here in one place instead of updating 50+ test methods.
     */
    public Map<String, Object> build() {
        Map<String, Object> booking = new HashMap<>();
        // Note: Missing 'firstname' or 'lastname' will cause 500 errors on some backends.
        // We assume the test provides these, or we could add more defensive defaults here.
        booking.put("firstname", this.firstname);
        booking.put("lastname", this.lastname);
        booking.put("totalprice", this.totalprice);
        booking.put("depositpaid", this.depositpaid);
        booking.put("bookingdates", this.bookingdates);
        booking.put("additionalneeds", "Breakfast");
        return booking;
    }
}