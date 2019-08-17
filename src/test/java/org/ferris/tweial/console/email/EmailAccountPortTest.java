package org.ferris.tweial.console.email;

import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EmailAccountPortTest extends EmailAccountConstructorTest {

    @Test
    public void test_port_NotNull_when_property_is_missing() throws Exception {
        // setup
        Properties props = new Properties();
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "port");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("SMTP port is required", violation.getMessage());
    }
    
    @Test
    public void test_port_NotNull_when_NAN() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("port", "nan");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "port");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("SMTP port is required", violation.getMessage());
    }
    
    
    @Test
    public void test_port_Min() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("port", "0");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "port");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("SMTP port value \"0\" must be greater or equal to 1", violation.getMessage());
    }
    
    
    @Test
    public void test_port_Max() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("port", "65536");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "port");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        assertEquals("SMTP port value \"65536\" must be less than or equal to 65535", violation.getMessage());
    }
}
