package org.ferris.tweial.console.lang;

import org.ferris.tweial.console.lang.SystemTool;
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
@PrepareForTest(SystemTool.class)
public class SystemToolTest {

    @Test
    public void test_abnormal_system_exit() throws Exception {
        // prepare
        PowerMockito.mockStatic(System.class);

        // action
        new SystemTool().exitAbnormal();

        // assert
        PowerMockito.verifyStatic(Mockito.times(1));
        System.exit(1);
    }

    @Test
    public void test_normal_system_exit() throws Exception {
        // prepare
        PowerMockito.mockStatic(System.class);

        // action
        new SystemTool().exitNormal();

        // assert
        PowerMockito.verifyStatic(Mockito.times(1));
        System.exit(0);
    }
}
