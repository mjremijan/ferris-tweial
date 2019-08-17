package org.ferris.tweial.console.twitter;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.application.ApplicationDirectory;
import org.ferris.tweial.console.configuration.ConfigurationDirectory;
import org.ferris.tweial.console.io.AbstractPropertiesFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class TwitterPropertiesFileTest {

    @Mock
    Logger logMock;

    @Before
    public void before() {
    }

    @Test
    public void test_path_and_file_name_are_correct() {
        new File("target/conf").mkdirs();
        TwitterPropertiesFile f = new TwitterPropertiesFile(new ConfigurationDirectory(new ApplicationDirectory("target")));
        Assert.assertEquals("twitter.properties", f.getName());
        Assert.assertEquals("conf", f.getParentFile().getName());
    }

    @Test
    public void test_toProperties() throws Exception {
        // Setup
        TwitterPropertiesFile twitterPropertiesFile
                = new TwitterPropertiesFile(new ConfigurationDirectory(new ApplicationDirectory("src/test/junit/TwitterPropertiesFileTest/")));
        {
            Field logField = AbstractPropertiesFile.class.getDeclaredField("log");
            logField.setAccessible(true);
            logField.set(twitterPropertiesFile, logMock);
        }

        // Action
        Properties props
                = twitterPropertiesFile.toProperties();

        // Assert
        Assert.assertEquals(4, props.size());
    }


    @Test(expected = RuntimeException.class)
    public void test_toProperties_throws_RuntimeException() throws NoSuchFieldException, IllegalAccessException {
        // Setup
        TwitterPropertiesFile twitterPropertiesFile
                = new TwitterPropertiesFile(new ConfigurationDirectory(new ApplicationDirectory("this/directory/does/not/exist")));
        {
            Field logField = AbstractPropertiesFile.class.getDeclaredField("log");
            logField.setAccessible(true);
            logField.set(twitterPropertiesFile, logMock);
        }

        // Action
        Properties props
                = twitterPropertiesFile.toProperties();
    }
}
