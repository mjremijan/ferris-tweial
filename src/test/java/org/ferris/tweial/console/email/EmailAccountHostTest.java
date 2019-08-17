package org.ferris.tweial.console.email;

import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EmailAccountHostTest extends EmailAccountConstructorTest {

    @Test
    public void test_host_NotNull() throws Exception {
        // setup
        Properties props = new Properties();
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "host");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("SMTP host is required", violation.getMessage());
    }

    @Test
    public void test_host_Size_min() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("host", "");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "host");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("SMTP host value \"\" must be between 4 and 54 characters long", violation.getMessage());
    }

    @Test
    public void test_host_Size_max() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("host", "1234567890123456789012345678901234567890123456789012345");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "host");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("SMTP host value \"1234567890123456789012345678901234567890123456789012345\" must be between 4 and 54 characters long", violation.getMessage());
    }

}
