/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.log4j;

import org.ferris.tweial.console.log4j.Log4jRollingFileAppender;
import java.io.File;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class Log4jRollingFileAppenderTest {

    @Mock
    Logger log;

    @Test
    public void test_constructor_goes_to_tweial_log_file() throws Exception {
        Log4jRollingFileAppender appender
                = new Log4jRollingFileAppender();
        File file = new File(appender.getFile());
        Assert.assertEquals("tweial.log", file.getName());
        Assert.assertEquals("logs", file.getParentFile().getName());
    }

}
