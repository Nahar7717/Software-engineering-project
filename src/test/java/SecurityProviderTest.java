import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SecurityProviderTest {

    @Test
    void testEncryptionReturnsNonNull() {
        String result = SecurityProvider.encryptAsset("TestLicense123");
        assertNotNull(result);
    }

    @Test
    void testEncryptionReturnsNonEmptyString() {
        String result = SecurityProvider.encryptAsset("TestLicense123");
        assertFalse(result.isEmpty());
    }

    @Test
    void testEncryptedOutputDiffersFromInput() {
        String input = "LicenseNumber999";
        String encrypted = SecurityProvider.encryptAsset(input);
        assertNotEquals(input, encrypted);
    }

    @Test
    void testEncryptionIsConsistentForSameInput() {
        String data = "IqamaNumber12345";
        String first = SecurityProvider.encryptAsset(data);
        String second = SecurityProvider.encryptAsset(data);
        assertEquals(first, second);
    }

    @Test
    void testDifferentInputsProduceDifferentOutputs() {
        String enc1 = SecurityProvider.encryptAsset("License001");
        String enc2 = SecurityProvider.encryptAsset("License002");
        assertNotEquals(enc1, enc2);
    }

    @Test
    void testEncryptedOutputIsBase64() {
        String encrypted = SecurityProvider.encryptAsset("TestData9876");
        assertTrue(encrypted.matches("[A-Za-z0-9+/=]+"),
            "Expected Base64-encoded output but got: " + encrypted);
    }

    @Test
    void testEncryptionOfEmptyString() {
        String result = SecurityProvider.encryptAsset("");
        assertNotNull(result);
    }

    @Test
    void testEncryptionOfNumericString() {
        String result = SecurityProvider.encryptAsset("1234567890");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
