package org.ferris.tweial.console.lang;

import org.ferris.tweial.console.lang.ThreadTool;
import java.lang.Thread.UncaughtExceptionHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * References - https://gist.github.com/fatmind/4110984
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ThreadTool.class)
public class ThreadToolTest {

    @Test
    public void testSetDefaultUncaughtExceptionHandleIsCalled() throws Exception {
        //prepare
        PowerMockito.mockStatic(Thread.class);

        // mock
        UncaughtExceptionHandler handler
                = Mockito.mock(UncaughtExceptionHandler.class);

        //action
        new ThreadTool().setDefaultUncaughtExceptionHandler(handler);

        //assert
        PowerMockito.verifyStatic(Mockito.times(1));
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }
}
