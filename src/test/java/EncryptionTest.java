import junit.framework.TestCase;

import static org.junit.Assert.*;

public class EncryptionTest extends TestCase {

    public void testEncrypt()
    {
        assertEquals("2446", Encryption.encrypt_password("1234"));
        assertEquals("bddf", Encryption.encrypt_password("abcd"));
    }
    public void testDecrypt()
    {
        assertEquals("1234", Encryption.decrypt_password("2446"));
        assertEquals("abcd",Encryption.decrypt_password("bddf"));
    }
}