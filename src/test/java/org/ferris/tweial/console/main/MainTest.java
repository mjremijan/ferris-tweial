package org.ferris.tweial.console.main;

import java.util.LinkedList;
import javax.enterprise.event.Event;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.email.EmailSendEvent;
import org.ferris.tweial.console.exit.ExitEvent;
import org.ferris.tweial.console.twitter.TweetRetrievalEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MainTest {

    Main controller;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        controller = new Main();
        {
            controller.log
                    = Mockito.mock(Logger.class);
            controller.startupEvent
                    = (Event<StartupEvent>) Mockito.mock(Event.class);
            controller.tweetRetrievalEvent
                    = (Event<TweetRetrievalEvent>) Mockito.mock(Event.class);
            controller.emailSendEvent
                    = (Event<EmailSendEvent>) Mockito.mock(Event.class);
            controller.exitEvent
                    = (Event<ExitEvent>) Mockito.mock(Event.class);            
        }
    }

    @Test
    public void test_events_are_fired() {
        // action
        controller.main(new LinkedList<>());

        // assert		
        Mockito.verify(controller.startupEvent, Mockito.times(1)).fire(Mockito.any(StartupEvent.class));
        Mockito.verify(controller.tweetRetrievalEvent, Mockito.times(1)).fire(Mockito.any(TweetRetrievalEvent.class));
        Mockito.verify(controller.emailSendEvent, Mockito.times(1)).fire(Mockito.any(EmailSendEvent.class));
        Mockito.verify(controller.exitEvent, Mockito.times(1)).fire(Mockito.any(ExitEvent.class));
    }
}
