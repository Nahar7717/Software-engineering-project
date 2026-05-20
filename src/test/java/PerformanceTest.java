import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Non-functional tests — verify that core operations complete within
 * acceptable time bounds under bulk load.
 */
class PerformanceTest {

    private static final int BULK = 1000;
    private static final long USER_CREATION_LIMIT_MS   = 500;
    private static final long RIDE_CREATION_LIMIT_MS   = 500;
    private static final long RIDE_SEARCH_LIMIT_MS     = 200;
    private static final long ENCRYPTION_LIMIT_MS      = 1000;
    private static final long STATUS_WORKFLOW_LIMIT_MS = 500;

    @Test
    void testBulkUserCreationIsWithinTimeLimit() {
        long start = System.currentTimeMillis();
        List<User> users = new ArrayList<>(BULK);
        for (int i = 0; i < BULK; i++) {
            users.add(new User("ID" + i, "User" + i, "Pass" + i + "!A", "055" + String.format("%07d", i)));
        }
        long elapsed = System.currentTimeMillis() - start;

        assertEquals(BULK, users.size());
        assertTrue(elapsed < USER_CREATION_LIMIT_MS,
            "Creating " + BULK + " users took " + elapsed + "ms — expected < " + USER_CREATION_LIMIT_MS + "ms");
    }

    @Test
    void testBulkRideCreationIsWithinTimeLimit() {
        long start = System.currentTimeMillis();
        List<Ride> rides = new ArrayList<>(BULK);
        for (int i = 0; i < BULK; i++) {
            rides.add(new Ride("Driver" + i, "055" + i, "From" + i, "To" + i, 20.0 + i, RideType.INSTANT, "NOW"));
        }
        long elapsed = System.currentTimeMillis() - start;

        assertEquals(BULK, rides.size());
        assertTrue(elapsed < RIDE_CREATION_LIMIT_MS,
            "Creating " + BULK + " rides took " + elapsed + "ms — expected < " + RIDE_CREATION_LIMIT_MS + "ms");
    }

    @Test
    void testRideSearchPerformanceWithPartialBookings() {
        List<Ride> rides = new ArrayList<>(BULK);
        User passenger = new User("P001", "Passenger", "Pass123!", "0559999999");

        for (int i = 0; i < BULK; i++) {
            rides.add(new Ride("Driver" + i, "055" + i, "From" + i, "To" + i, 20.0, RideType.INSTANT, "NOW"));
        }
        // Book every 10th ride
        for (int i = 0; i < BULK; i += 10) {
            rides.get(i).bookRide(passenger);
        }

        long start = System.currentTimeMillis();
        long openCount = rides.stream().filter(r -> r.status == RideStatus.OPEN).count();
        long elapsed = System.currentTimeMillis() - start;

        assertEquals(BULK - BULK / 10, openCount);
        assertTrue(elapsed < RIDE_SEARCH_LIMIT_MS,
            "Filtering " + BULK + " rides took " + elapsed + "ms — expected < " + RIDE_SEARCH_LIMIT_MS + "ms");
    }

    @Test
    void testEncryptionOf100ItemsIsWithinTimeLimit() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            SecurityProvider.encryptAsset("LicenseNumber" + i);
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < ENCRYPTION_LIMIT_MS,
            "100 encryptions took " + elapsed + "ms — expected < " + ENCRYPTION_LIMIT_MS + "ms");
    }

    @Test
    void testDriverStatusWorkflowPerformance() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < BULK; i++) {
            User u = new User("D" + i, "Driver" + i, "Pass123!", "055" + i);
            u.submitDriverDocs("LIC" + i, "IQ" + i, "01/01/1995", "Toyota");
            u.approveDriver();
            u.addRating(4);
            u.addRating(5);
            u.getAverageRating();
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < STATUS_WORKFLOW_LIMIT_MS,
            "Driver workflow for " + BULK + " users took " + elapsed + "ms — expected < " + STATUS_WORKFLOW_LIMIT_MS + "ms");
    }

    @Test
    void testRideLifecyclePerformance() {
        User passenger = new User("P001", "Passenger", "Pass123!", "0559999999");

        long start = System.currentTimeMillis();
        for (int i = 0; i < BULK; i++) {
            Ride r = new Ride("Driver", "055", "A", "B", 25.0, RideType.INSTANT, "NOW");
            r.bookRide(passenger);
            r.finishRide();
        }
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < RIDE_CREATION_LIMIT_MS,
            "Full lifecycle for " + BULK + " rides took " + elapsed + "ms — expected < " + RIDE_CREATION_LIMIT_MS + "ms");
    }
}
