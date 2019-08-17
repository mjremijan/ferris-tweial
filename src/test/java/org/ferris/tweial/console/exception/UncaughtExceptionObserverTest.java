package org.ferris.tweial.console.exception;


import javax.enterprise.event.Event;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.exit.ExitEvent;
import org.ferris.tweial.console.main.StartupEvent;
import org.ferris.tweial.console.lang.ThreadTool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UncaughtExceptionObserverTest 
{
    @Mock
    Logger logMock;
    
    @Mock
    ThreadTool threadToolMock;
    
    @Mock
    UncaughtExceptionPage pageMock;
    
    @Mock
    Event<ExitEvent> exitEventMock;
    
    UncaughtExceptionObserver observer;    
        
    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        observer = new UncaughtExceptionObserver();
        {
            observer.log = logMock;
            observer.threadTool = threadToolMock;
            observer.uncaughtExceptionPage = pageMock;
            observer.exitEvent = exitEventMock;
        }
    }

    @Test
    public void test_that_defaultUncaughtExceptionHandler_is_called() throws Exception {
        // action
        observer.observes(new StartupEvent());

        // assert
        Mockito.verify(observer.threadTool, Mockito.times(1)).setDefaultUncaughtExceptionHandler(observer);
    }
    
    @Test
    public void test_that_page_is_viewed() throws Exception {
        // setup
        Throwable t = new Throwable();
        
        // action
        observer.uncaughtException(Thread.currentThread(), t);

        // assert
        Mockito.verify(observer.uncaughtExceptionPage, Mockito.times(1)).view(t);
    }
    
    @Test
    public void test_that_exit_event_is_fired() throws Exception {
        // setup
        Throwable t = new Throwable();
        
        // action
        observer.uncaughtException(Thread.currentThread(), t);

        // assert
        Mockito.verify(exitEventMock, Mockito.times(1)).fire(Mockito.any(ExitEvent.class));
    }
}
