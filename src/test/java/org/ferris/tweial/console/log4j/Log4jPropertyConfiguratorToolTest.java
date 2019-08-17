package org.ferris.tweial.console.log4j;

import org.ferris.tweial.console.log4j.Log4jPropertyConfiguratorTool;
import org.apache.log4j.PropertyConfigurator;
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
@PrepareForTest(PropertyConfigurator.class)
public class Log4jPropertyConfiguratorToolTest {

    @Test
    public void configure() throws Exception {
        // Prepare
        PowerMockito.mockStatic(PropertyConfigurator.class);
        
        // Action
        new Log4jPropertyConfiguratorTool().configure("foo.properties");
        
        // Assert
        PowerMockito.verifyStatic(Mockito.times(1));
        PropertyConfigurator.configure("foo.properties");
    }
}
