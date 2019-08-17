package org.ferris.tweial.console.twitter;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.Status;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class TweetRunEventTest {
    
    @Mock
    Logger logMock;
        
    TweetRetrievalEvent event;
    List<Status> tweets;
    
    @Before
    public void before() {
        event = new TweetRetrievalEvent();
        tweets = new LinkedList<>();
        tweets.add(Mockito.mock(Status.class));
        tweets.add(Mockito.mock(Status.class));
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void testNewTweetsFromThisRunAreImmutable() {
        // setup
        event.setNewTweetsFromThisRun(tweets);
        
        // action
        List<Status> l = event.getNewTweetsFromThisRun();
        
        // assert
        Assert.assertEquals(2, l.size());
        l.add(Mockito.mock(Status.class));
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void testTweetsFromTwitterAreImmutable() {
        // setup
        event.setTweetsFromTwitter(tweets);
        
        // action
        List<Status> l = event.getTweetsFromTwitter();
        
        // assert
        Assert.assertEquals(2, l.size());
        l.add(Mockito.mock(Status.class));
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void testTweetsFromLastRunAreImmutable() {
        // setup
        event.setTweetsFromLastRun(tweets);
        
        // action
        List<Status> l = event.getTweetsFromLastRun();
        
        // assert
        Assert.assertEquals(2, l.size());
        l.add(Mockito.mock(Status.class));
    }
        
    
}
