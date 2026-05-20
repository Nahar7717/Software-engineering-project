import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RideTest {

    private User passenger;

    @BeforeEach
    void setUp() {
        passenger = new User("S001", "Ahmed", "Pass123!", "0559998888");
    }

    @Test
    void testRideCreationSetsFields() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        assertEquals("Mall", ride.from);
        assertEquals("University", ride.to);
        assertEquals(25.0, ride.cost, 0.001);
        assertEquals(RideType.INSTANT, ride.type);
        assertEquals("NOW", ride.dateTime);
        assertEquals("Khalid", ride.driverName);
    }

    @Test
    void testNewRideStatusIsOpen() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        assertEquals(RideStatus.OPEN, ride.status);
    }

    @Test
    void testBookRideSetsStatusBooked() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        ride.bookRide(passenger);
        assertEquals(RideStatus.BOOKED, ride.status);
    }

    @Test
    void testBookRideAssignsPassenger() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        ride.bookRide(passenger);
        assertEquals(passenger, ride.passenger);
    }

    @Test
    void testCancelBookingRestoresOpenStatus() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        ride.bookRide(passenger);
        ride.cancelBooking();
        assertEquals(RideStatus.OPEN, ride.status);
    }

    @Test
    void testCancelBookingClearsPassenger() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        ride.bookRide(passenger);
        ride.cancelBooking();
        assertNull(ride.passenger);
    }

    @Test
    void testFinishRideSetsCompleted() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        ride.bookRide(passenger);
        ride.finishRide();
        assertEquals(RideStatus.COMPLETED, ride.status);
    }

    @Test
    void testScheduledRideType() {
        Ride ride = new Ride("Khalid", "0551112222", "Airport", "IMAMU", 40.0, RideType.SCHEDULED, "15-12-2025 09:00 AM");
        assertEquals(RideType.SCHEDULED, ride.type);
        assertEquals("15-12-2025 09:00 AM", ride.dateTime);
    }

    @Test
    void testInitialRatingAndReviewAreEmpty() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        assertEquals(0, ride.rating);
        assertEquals("", ride.review);
    }

    @Test
    void testToStringContainsFromAndTo() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        String str = ride.toString();
        assertTrue(str.contains("Mall"));
        assertTrue(str.contains("University"));
    }

    @Test
    void testToStringContainsNowTag() {
        Ride ride = new Ride("Khalid", "0551112222", "Mall", "University", 25.0, RideType.INSTANT, "NOW");
        assertTrue(ride.toString().contains("NOW"));
    }

    @Test
    void testToStringContainsLaterTag() {
        Ride ride = new Ride("Khalid", "0551112222", "A", "B", 10.0, RideType.SCHEDULED, "01-01-2026 08:00 AM");
        assertTrue(ride.toString().contains("LATER"));
    }

    @Test
    void testRideRequestCreation() {
        RideRequest req = new RideRequest("Home", "University", "NOW", passenger);
        assertEquals("Home", req.from);
        assertEquals("University", req.to);
        assertEquals("NOW", req.time);
        assertEquals(passenger, req.student);
    }

    @Test
    void testRideRequestToStringContainsPassengerName() {
        RideRequest req = new RideRequest("Home", "University", "NOW", passenger);
        assertTrue(req.toString().contains("Ahmed"));
    }
}
