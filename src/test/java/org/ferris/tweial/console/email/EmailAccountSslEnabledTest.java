package org.ferris.tweial.console.email;

import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.junit.Assert;
import org.junit.Test;

public class EmailAccountSslEnabledTest extends EmailAccountConstructorTest {

    @Test
    public void test_sslEnabled_NotNull_not_given() throws Exception {
        // setup
        Properties props = new Properties();
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "sslEnabled");

        // assert
        Assert.assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        Assert.assertEquals("SMTP SSL Enabled is required", violation.getMessage());
    }
    
    @Test
    public void test_sslEnabled_NotNull_not_true_or_false() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("sslEnabled", "foo");
        EmailAccount invalid = new EmailAccount(props);

        // action
        Set<ConstraintViolation<EmailAccount>> violations 
            = validator.validateProperty(invalid, "sslEnabled");

        // assert
        Assert.assertEquals(1, violations.size());
        ConstraintViolation<EmailAccount> violation = violations.iterator().next();
        Assert.assertEquals("SMTP SSL Enabled is required", violation.getMessage());
    }
    
    @Test
    public void test_sslEnabled_true() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("sslEnabled", "true");

        // action
        EmailAccount invalid = new EmailAccount(props);

        // assert
        Assert.assertEquals(true, invalid.isSslEnabled());
    }
    
    @Test
    public void test_sslEnabled_false() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("sslEnabled", "false");

        // action
        EmailAccount invalid = new EmailAccount(props);

        // assert
        Assert.assertEquals(false, invalid.isSslEnabled());
    }
}
