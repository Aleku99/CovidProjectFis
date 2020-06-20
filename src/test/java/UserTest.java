import junit.framework.TestCase;

import static org.junit.Assert.*;

public class UserTest extends TestCase {

    public void testValidateDocRegistration()
    {
        assertEquals(false,User.validateDocRegistration("afgagfd","","alex","1234567890","jud","gav"));
        assertEquals(false,User.validateDocRegistration("aloghin99","bv","alex","1234567890","jud","gav"));
        assertEquals(false,User.validateDocRegistration("rmiculit99","bv","alex12","1234567890","jud","gav"));
        assertEquals(false,User.validateDocRegistration("rmiculit99","bv","alex","123456789","jud","gav"));
        assertEquals(true,User.validateDocRegistration("rmiculit99","bv","alex","1234567890","jud","gav"));

    }
    public void testValidatePacientRegistration()
    {
        assertEquals(false,User.validatePacientRegistration("rmiculit99","123","razvan","1234567890","Soarelu"));
        assertEquals(false,User.validatePacientRegistration("rmiculit","","razvan","1234567890","Soarelu"));
        assertEquals(false,User.validatePacientRegistration("rmiculit","123","razvan1","1234567890","Soarelu"));
        assertEquals(false,User.validatePacientRegistration("rmiculit9","123","razvan","123456789","Soarelu"));
        assertEquals(false,User.validatePacientRegistration("rmiculit9","123","razvan","1234567890",""));
        assertEquals(true,User.validatePacientRegistration("rmiculit9","123","razvan","1234567890","Soarelu"));
    }

}