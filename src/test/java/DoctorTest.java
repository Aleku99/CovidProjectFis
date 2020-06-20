import junit.framework.TestCase;

import static org.junit.Assert.*;

public class DoctorTest extends TestCase {
    public void testNameValidation()
    {
        assertEquals(false,Doctor.nameValidation(""));
        assertEquals(false,Doctor.nameValidation("Alex1"));
        assertEquals(true,Doctor.nameValidation("Alex"));
    }
    public void testUserValidation()
    {
        assertEquals(false,Doctor.userValidation(""));
        assertEquals(true,Doctor.userValidation("maimuta12"));
    }

}