package org.ferris.tweial.console.log4j;

import org.ferris.tweial.console.log4j.Log4jLogManagerTool;
import org.apache.log4j.LogManager;
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
@PrepareForTest(LogManager.class)
public class Log4jLogManagerToolTest {

    @Test
    public void resetConfiguration() throws Exception {
        // Prepare
        PowerMockito.mockStatic(LogManager.class);

        // Action
        new Log4jLogManagerTool().resetConfiguration();

        // Assert
        PowerMockito.verifyStatic(Mockito.times(1));
        LogManager.resetConfiguration();
    }
}
