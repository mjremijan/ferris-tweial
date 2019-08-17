package org.ferris.tweial.console.email;

import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EmailAccountEmailAddressTest extends EmailAccountConstructorTest {

    @Test
    public void test_emailAddress_NotNull() throws Exception {
        // setup
        Properties props = new Properties();
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "emailAddress");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("Email address is required", violation.getMessage());
    }

    @Test
    public void test_emailAddress_Pattern() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("emailAddress", "invalid.pattern");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "emailAddress");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("Email address value \"invalid.pattern\" must have a pattern like someone@somewhere.org", violation.getMessage());
    }
    
    @Test
    public void test_emailAddress_OK() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("emailAddress", "someone@somewhere.org");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "emailAddress");

        // assert
        assertEquals(0, violations.size());
    }

}
