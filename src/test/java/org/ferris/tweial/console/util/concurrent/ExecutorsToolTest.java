package org.ferris.tweial.console.util.concurrent;

import org.ferris.tweial.console.util.concurrent.ExecutorsTool;
import java.util.concurrent.Executors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ExecutorsTool.class)
public class ExecutorsToolTest {

    @Test
    public void newSingleThreadScheduledExecutor() throws Exception {
        // Prepare
        PowerMockito.mockStatic(Executors.class);

        // Action
        new ExecutorsTool().getScheduledExecutorService();

        // Assert
        PowerMockito.verifyStatic(Mockito.times(1));
        Executors.newScheduledThreadPool(2);
    }
}
