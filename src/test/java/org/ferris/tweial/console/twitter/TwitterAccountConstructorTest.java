package org.ferris.tweial.console.twitter;

import java.util.Properties;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwitterAccountConstructorTest {

    protected Validator validator;

    @Before
    public void before() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void test_constructor_sets_all_values() {
        Properties props = new Properties();
        props.setProperty("oAuthConsumerKey", "a");
        props.setProperty("oAuthConsumerSecret", "b");
        props.setProperty("oAuthAccessToken", "c");
        props.setProperty("oAuthAccessTokenSecret", "d");
        
        TwitterAccount auth
            = new TwitterAccount(props);
        
        Assert.assertEquals("a", auth.getConsumerKey());
        Assert.assertEquals("b", auth.getConsumerSecret());
        Assert.assertEquals("c", auth.getAccessToken());
        Assert.assertEquals("d", auth.getAccessTokenSecret());
    }  
}
