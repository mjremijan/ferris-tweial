package org.ferris.tweial.console.twitter;

import org.junit.Test;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import twitter4j.Twitter;
import twitter4j.conf.Configuration;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class TwitterBuilderTest {

    @Mock
    Logger logMock;

    @Test
    public void test_setAccount() {
        // setup
        TwitterBuilder builder = new TwitterBuilder();
        TwitterAccount account = Mockito.mock(TwitterAccount.class);
     
        // action
        TwitterBuilder returned = builder.setAccount(account);
        
        // assert
        // - account should be stored properly
        Assert.assertEquals(account, builder.account);
        // - the builder instance should be returned
        Assert.assertEquals(returned, builder);
    }
    
    
    @Test
    public void test_build() {
        // setup
        TwitterBuilder builder = new TwitterBuilder();
        builder.account = Mockito.mock(TwitterAccount.class); {
            Mockito.when(builder.account.getConsumerKey()).thenReturn(("ck"));
            Mockito.when(builder.account.getConsumerSecret()).thenReturn(("cs"));
            Mockito.when(builder.account.getAccessToken()).thenReturn(("at"));
            Mockito.when(builder.account.getAccessTokenSecret()).thenReturn(("ats"));
        }
     
        // action
        Twitter expected = builder.build();
        
        // assert
        // - expected object should not be null
        Assert.assertNotNull(expected);  
        // - get the Configuration object and make sure
        //   the values I pass in via TwitterAccount make
        //   it into the Twitter object that's built.
        Configuration c = expected.getConfiguration();
        Assert.assertEquals("at", c.getOAuthAccessToken());
        Assert.assertEquals("ats", c.getOAuthAccessTokenSecret());
        Assert.assertEquals("ck", c.getOAuthConsumerKey());
        Assert.assertEquals("cs", c.getOAuthConsumerSecret());
    }

}