/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.email;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailHandlerTest {

    @Mock
    Logger logMock;

    @Mock
    EmailPropertiesFile emailPropertiesMock;

    @Mock
    ValidatorFactory validatorFactoryMock;
    
    @Mock
    Validator validatorMock;

    EmailHandler handler;

    @Before
    public void before() {
        // mocks
        Mockito.when(validatorFactoryMock.getValidator()).thenReturn(validatorMock);
        
        // handler
        handler = Mockito.spy(new EmailHandler());
        handler.log = logMock;
        handler.emailProperties = emailPropertiesMock;
        handler.validatorFactory = validatorFactoryMock;
    }

    @Test
    public void test_getting_email_account() {
        // S E T U P
        // The TwitterProperties mock object returns a Properties object.
        Properties props = new Properties();
        {
            props.setProperty("host", "smtp.somewhere.org");
            props.setProperty("port", "44");
            props.setProperty("sslEnabled", "true");
            props.setProperty("username", "someone");
            props.setProperty("password", "password");
            props.setProperty("emailAddress", "someone@somewhere.org");
            Mockito.when(emailPropertiesMock.toProperties()).thenReturn(props);
        }

        // action
        EmailAccount acct
            = handler.getEmailAccount();
        
        // assert
        Assert.assertNotNull(acct);
        Assert.assertEquals("someone@somewhere.org", acct.getEmailAddress());
        Assert.assertEquals("smtp.somewhere.org", acct.getHost());
        Assert.assertEquals("password", acct.getPassword());
        Assert.assertEquals(new Integer(44), acct.getPort());
        Assert.assertEquals("someone", acct.getUsername());
        Assert.assertEquals(Boolean.TRUE, acct.isSslEnabled());
    }
    
    @Test
    public void test_validate() {
        // S E T U P
        // EmailAccount object
        EmailAccount acct = Mockito.mock(EmailAccount.class);
        // Create Set of ConstraintViolations to return
        Set<ConstraintViolation<EmailAccount>> expected 
            = new HashSet<ConstraintViolation<EmailAccount>>();
        {    
            // Add a violation to the set.
            expected.add(Mockito.mock(ConstraintViolation.class));
            
            // Validator retuns set with 1 violation on validate 
            Mockito.when(validatorMock.validate(acct)).thenReturn(expected);
        }
        
        // A C T I O N
        Set<ConstraintViolation<EmailAccount>> actual = handler.validate(acct);
        
        // A S S E R T
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void test_email_data_source() {
        // setup
        Mockito.when(emailPropertiesMock.getAbsolutePath()).thenReturn("/junit/mock/shrubbery.dat");
        Mockito.when(emailPropertiesMock.exists()).thenReturn(Boolean.TRUE);
        
        // action
        EmailDataSource ds = handler.getEmailAccountDataSource();
        
        // assert
        Assert.assertEquals("/junit/mock/shrubbery.dat", ds.getDescription());
        Assert.assertTrue(ds.exists());
    }
    
}
