package org.ferris.tweial.console.splash;


import org.apache.log4j.Logger;
import org.ferris.tweial.console.main.StartupEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SplashScreenObserverTest {

    SplashScreenObserver observer;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        observer = new SplashScreenObserver();
        {
            observer.log
                    = Mockito.mock(Logger.class);
            observer.splashScreenPage
                    = Mockito.mock(SplashScreenPage.class);
        }
    }

    @Test
    public void test_that_view_page_is_called() throws Exception {
        // action
        observer.observes(new StartupEvent());

        // assert
        Mockito.verify(observer.splashScreenPage, Mockito.times(1)).view();
    }
}
