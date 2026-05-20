import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        User user = new User("12345", "Ahmed Ali", "Pass123!", "0551234567");
        assertEquals("12345", user.getId());
        assertEquals("Ahmed Ali", user.getName());
        assertEquals("0551234567", user.getPhone());
    }

    @Test
    void testPasswordCheckCorrect() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        assertTrue(user.checkPassword("Pass123!"));
    }

    @Test
    void testPasswordCheckWrong() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        assertFalse(user.checkPassword("wrongpassword"));
    }

    @Test
    void testInitialDriverStatusIsNone() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        assertEquals(DriverStatus.NONE, user.getDriverStatus());
    }

    @Test
    void testDriverDocSubmissionSetsPending() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        user.submitDriverDocs("123456", "1234567890", "01/01/1995", "Toyota Camry");
        assertEquals(DriverStatus.PENDING, user.getDriverStatus());
    }

    @Test
    void testDriverApproval() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        user.submitDriverDocs("123456", "1234567890", "01/01/1995", "Toyota Camry");
        user.approveDriver();
        assertEquals(DriverStatus.APPROVED, user.getDriverStatus());
    }

    @Test
    void testDriverRejection() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        user.submitDriverDocs("123456", "1234567890", "01/01/1995", "Toyota Camry");
        user.rejectDriver();
        assertEquals(DriverStatus.REJECTED, user.getDriverStatus());
    }

    @Test
    void testInitialAverageRatingIsZero() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        assertEquals(0.0, user.getAverageRating(), 0.001);
    }

    @Test
    void testAverageRatingCalculation() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        user.addRating(4);
        user.addRating(5);
        user.addRating(3);
        assertEquals(4.0, user.getAverageRating(), 0.001);
    }

    @Test
    void testSingleRatingAverage() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        user.addRating(5);
        assertEquals(5.0, user.getAverageRating(), 0.001);
    }

    @Test
    void testSetNameUpdatesName() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        user.setName("Khalid");
        assertEquals("Khalid", user.getName());
    }

    @Test
    void testSetPhoneUpdatesPhone() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        user.setPhone("0559876543");
        assertEquals("0559876543", user.getPhone());
    }

    @Test
    void testSetPasswordAllowsNewLogin() {
        User user = new User("12345", "Ahmed", "Pass123!", "0551234567");
        user.setPassword("NewPass456@");
        assertTrue(user.checkPassword("NewPass456@"));
        assertFalse(user.checkPassword("Pass123!"));
    }

    @Test
    void testDriverDetailsContainsName() {
        User user = new User("12345", "Khalid", "Pass123!", "0551234567");
        user.submitDriverDocs("111", "222", "01/01/1995", "Camry");
        String details = user.getDriverDetails();
        assertTrue(details.contains("Khalid"));
    }
}
