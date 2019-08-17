package org.ferris.tweial.console.twitter;

import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TwitterAccountAccessTokenTest extends TwitterAccountConstructorTest {

    @Test
    public void test_accessToken_NotNull() throws Exception {
        // setup
        Properties props = new Properties();
        TwitterAccount invalid = new TwitterAccount(props);

        // action
        Set<ConstraintViolation<TwitterAccount>> violations 
            = validator.validateProperty(invalid, "accessToken");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<TwitterAccount> violation = violations.iterator().next();
        assertEquals("Twitter open-authentication access token is required", violation.getMessage());
    }

    @Test
    public void test_accessToken_Size() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("oAuthAccessToken", "");
        TwitterAccount invalid = new TwitterAccount(props);

        // action
        Set<ConstraintViolation<TwitterAccount>> violations 
            = validator.validateProperty(invalid, "accessToken");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<TwitterAccount> violation = violations.iterator().next();
        assertEquals("Twitter open-authentication access token must have a value", violation.getMessage());
    }
}
