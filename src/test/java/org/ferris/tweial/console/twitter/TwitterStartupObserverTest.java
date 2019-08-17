package org.ferris.tweial.console.twitter;

import java.util.HashSet;
import java.util.Set;
import javax.enterprise.event.Event;
import javax.validation.ConstraintViolation;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.exit.ExitEvent;
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
public class TwitterStartupObserverTest {
    
    @Mock
    Logger logMock;
    
    @Mock
    TwitterHandler handlerMock;
    
    @Mock
    TwitterPage pageMock;
    
    @Mock
    Event<ExitEvent> exitEventMock;
    
    TwitterStartupObserver observer;
    
    @Before
    public void before() {        
        observer = new TwitterStartupObserver();
        observer.log = logMock;
        observer.handler = handlerMock;
        observer.page = pageMock;
        observer.exitEvent = exitEventMock;
    }
    
    @Test
    public void keep() {}

    @Test
    public void test_what_to_do_if_data_source_is_missing() {
        // setup
        TwitterDataSource ds = new TwitterDataSource("fooz",false);
        Mockito.when(handlerMock.getTwitterDataSource()).thenReturn(ds);
        
        // action
        observer.twitterDataSourceVerification(null);
        
        // assert
        Mockito.verify(pageMock, Mockito.times(1)).viewDataSourceMissing("fooz");
        Mockito.verify(exitEventMock, Mockito.times(1)).fire(Mockito.any(ExitEvent.class));
    }
    
    
    @Test
    public void test_what_to_do_if_data_source_is_OK() {       
        // setup
        TwitterDataSource ds = new TwitterDataSource("fooz",true);
        Mockito.when(handlerMock.getTwitterDataSource()).thenReturn(ds);
        
        // action
        observer.twitterDataSourceVerification(null);
        
        // assert
        Mockito.verify(pageMock, Mockito.times(0)).viewDataSourceMissing("fooz");
        Mockito.verify(exitEventMock, Mockito.times(0)).fire(Mockito.any(ExitEvent.class));
    }
    
    
    @Test
    public void test_what_to_do_if_there_are_authentication_violations() {
        // SETUP
        // Create Set of ConstraintViolations to return
        Set<ConstraintViolation<TwitterAccount>> violations 
            = new HashSet<ConstraintViolation<TwitterAccount>>();
        {    
            // Add a violation to the set.
            violations.add(Mockito.mock(ConstraintViolation.class));
        }
        
        // Wire up mocks
        Mockito.when(handlerMock.validate(Mockito.any(TwitterAccount.class))).thenReturn(violations);
        
        // ACTION
        observer.twitterAccountVerification(null);
        
        // ASSERT
        Mockito.verify(pageMock, Mockito.times(1)).viewTwitterAccountMissing(violations);
        Mockito.verify(exitEventMock, Mockito.times(1)).fire(Mockito.any(ExitEvent.class));
    }
    
    
    @Test
    public void test_what_to_do_if_authentication_violations_OK() {
        // SETUP
        // Create Set of ConstraintViolations to return
        Set<ConstraintViolation<TwitterAccount>> violations 
            = new HashSet<ConstraintViolation<TwitterAccount>>();
        
        // Wire up mocks
        Mockito.when(handlerMock.validate(Mockito.any(TwitterAccount.class))).thenReturn(violations);
        
        // ACTION
        observer.twitterAccountVerification(null);
        
        // ASSERT
        Mockito.verify(pageMock, Mockito.times(0)).viewTwitterAccountMissing(violations);
        Mockito.verify(exitEventMock, Mockito.times(0)).fire(Mockito.any(ExitEvent.class));
    }
}
