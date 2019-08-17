package org.ferris.tweial.console.email;

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
public class EmailStartupObserverTest {
    
    @Mock
    Logger logMock;
    
    @Mock
    EmailHandler handlerMock;
    
    @Mock
    EmailPage pageMock;
    
    @Mock
    Event<ExitEvent> exitEventMock;
    
    EmailStartupObserver observer;
    
    @Before
    public void before() {        
        observer = new EmailStartupObserver();
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
        EmailDataSource ds = new EmailDataSource("fooz",false);
        Mockito.when(handlerMock.getEmailAccountDataSource()).thenReturn(ds);
        
        // action
        observer.emailDataSourceVerification(null);
        
        // assert
        Mockito.verify(pageMock, Mockito.times(1)).viewDataSourceMissing("fooz");
        Mockito.verify(exitEventMock, Mockito.times(1)).fire(Mockito.any(ExitEvent.class));
    }
    
    @Test
    public void test_what_to_do_if_there_are_account_violations() {
        // SETUP
        // Create Set of ConstraintViolations to return
        Set<ConstraintViolation<EmailAccount>> violations 
            = new HashSet<ConstraintViolation<EmailAccount>>();
        {    
            // Add a violation to the set.
            violations.add(Mockito.mock(ConstraintViolation.class));
        }
        
        // Wire up mocks        
        Mockito.when(handlerMock.validate(Mockito.any(EmailAccount.class))).thenReturn(violations);
        
        // ACTION
        observer.emailAccountVerification(null);
        
        // ASSERT
        Mockito.verify(pageMock, Mockito.times(1)).viewEmailAccountMissing(violations);
        Mockito.verify(exitEventMock, Mockito.times(1)).fire(Mockito.any(ExitEvent.class));
    }
}
