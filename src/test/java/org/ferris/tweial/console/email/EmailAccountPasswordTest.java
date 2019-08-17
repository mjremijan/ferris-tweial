package org.ferris.tweial.console.email;

import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EmailAccountPasswordTest extends EmailAccountConstructorTest {

    @Test
    public void test_accountPassword_NotNull() throws Exception {
        // setup
        Properties props = new Properties();
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "password");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("Account password is required", violation.getMessage());
    }

    @Test
    public void test_accountPassword_Size_min() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("password", "1234");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "password");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("Account password must be between 5 and 35 characters long", violation.getMessage());
    }

    @Test
    public void test_accountPassword_Size_max() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("password", "123456789012345678901234567890123456");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "password");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("Account password must be between 5 and 35 characters long", violation.getMessage());
    }

}
