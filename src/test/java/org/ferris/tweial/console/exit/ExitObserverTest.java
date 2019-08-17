package org.ferris.tweial.console.exit;

import org.apache.log4j.Logger;
import org.ferris.tweial.console.lang.SystemTool;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ExitObserverTest {

    ExitObserver controller;

    @Before
    public void before() {
        controller = new ExitObserver();
        {
            controller.log
                    = Mockito.mock(Logger.class);
            controller.exitPage
                    = Mockito.mock(ExitPage.class);
            controller.systemTool
                    = Mockito.mock(SystemTool.class);
        }
    }

    @Test
    public void test_view_exit_page() {
        // action
        controller.observes(null);

        // assert		
        Mockito.verify(controller.exitPage, Mockito.times(1)).view();
    }

    @Test
    public void test_exit_abnormal() {
        // action
        controller.observes(null);

        // assert		
        Mockito.verify(controller.systemTool, Mockito.times(1)).exitAbnormal();
    }
}
