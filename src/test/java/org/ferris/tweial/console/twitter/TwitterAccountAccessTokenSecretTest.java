package org.ferris.tweial.console.twitter;

import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TwitterAccountAccessTokenSecretTest extends TwitterAccountConstructorTest {

    @Test
    public void test_accessTokenSecret_NotNull() throws Exception {
        // setup
        Properties props = new Properties();
        TwitterAccount invalid = new TwitterAccount(props);

        // action
        Set<ConstraintViolation<TwitterAccount>> violations 
            = validator.validateProperty(invalid, "accessTokenSecret");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<TwitterAccount> violation = violations.iterator().next();
        assertEquals("Twitter open-authentication access token secret is required", violation.getMessage());
    }

    @Test
    public void test_accessTokenSecret_Size() throws Exception {
        // setup
        Properties props = new Properties();
        props.setProperty("oAuthAccessTokenSecret", "");
        TwitterAccount invalid = new TwitterAccount(props);

        // action
        Set<ConstraintViolation<TwitterAccount>> violations 
            = validator.validateProperty(invalid, "accessTokenSecret");

        // assert
        assertEquals(1, violations.size());
        ConstraintViolation<TwitterAccount> violation = violations.iterator().next();
        assertEquals("Twitter open-authentication access token secret must have a value", violation.getMessage());
    }
}
