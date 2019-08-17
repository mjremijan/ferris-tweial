package org.ferris.tweial.console.twitter;

import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TwitterAccountConsumerKeyTest extends TwitterAccountConstructorTest {

    @Test
    public void test_consumerKey_NotNull() throws Exception {
        // setup
        Properties props = new Properties();
        TwitterAccount invalid = new TwitterAccount(props);

        // action
        Set<ConstraintViolation<TwitterAccount>> violations 
            = validator.validateProperty(invalid, "consumerKey");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<TwitterAccount> violation = violations.iterator().next();
        assertEquals("Twitter open-authentication consumer key is required", violation.getMessage());
    }

    @Test
    public void test_consumerKey_Size() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("oAuthConsumerKey", "");
        TwitterAccount invalid = new TwitterAccount(props);

        // action
        Set<ConstraintViolation<TwitterAccount>> violations 
            = validator.validateProperty(invalid, "consumerKey");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<TwitterAccount> violation = violations.iterator().next();
        assertEquals("Twitter open-authentication consumer key must have a value", violation.getMessage());
    }
}
