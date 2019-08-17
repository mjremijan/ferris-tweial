package org.ferris.tweial.console.email;

import java.util.Properties;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailAccountConstructorTest {

    protected Validator validator;

    
    
    @Before
    public void before() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void test_constructor_sets_all_values() {
        // setup
        Properties props = new Properties();
        props.setProperty("host", "smtp.mine.com");
        props.setProperty("port", "44");
        props.setProperty("sslEnabled", "true");
        props.setProperty("username", "mjremijan");
        props.setProperty("password", "fjfuthdfadf");
        props.setProperty("emailAddress", "someone@somewhere.org");
        
        EmailAccount valid = new EmailAccount(props);
        
        // assert
        Assert.assertEquals("smtp.mine.com", valid.getHost());
        Assert.assertEquals(new Integer(44), valid.getPort());
        Assert.assertEquals("someone@somewhere.org", valid.getEmailAddress());
        Assert.assertEquals("fjfuthdfadf", valid.getPassword());
        Assert.assertEquals("mjremijan", valid.getUsername());
        Assert.assertEquals(Boolean.TRUE, valid.isSslEnabled());
    }  
}
