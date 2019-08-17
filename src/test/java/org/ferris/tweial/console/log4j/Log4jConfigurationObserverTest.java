package org.ferris.tweial.console.log4j;

import org.ferris.tweial.console.log4j.Log4jReconfigure;
import org.ferris.tweial.console.log4j.Log4jConfigurationObserver;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.main.StartupEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class Log4jConfigurationObserverTest {

    Log4jConfigurationObserver observer;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        observer = new Log4jConfigurationObserver();
        {
            observer.log
                    = Mockito.mock(Logger.class);
            observer.scheduledExecutorService
                    = Mockito.mock(ScheduledExecutorService.class);
            observer.reconfigureLog4j
                    = Mockito.mock(Log4jReconfigure.class);
        }
    }

    @Test
    public void test_that_watching_log4j_config_file_for_changes_was_started() throws Exception {
        // action
        observer.observes(new StartupEvent());

        // assert
        Mockito.verify(observer.scheduledExecutorService, Mockito.times(1)).scheduleAtFixedRate(observer.reconfigureLog4j, 10, 10, TimeUnit.SECONDS);
    }
}
