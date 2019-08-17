package org.ferris.tweial.console.email;

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
public class EmailPropertiesFileTest {

    @Mock
    Logger logMock;

    @Before
    public void before() {
    }

    @Test
    public void test_path_and_file_name_are_correct() {
        // Arrange
        new File("./target/conf").mkdirs();
        EmailPropertiesFile f = new EmailPropertiesFile(new ConfigurationDirectory(new ApplicationDirectory("target")));
        Assert.assertEquals("email.properties", f.getName());
        Assert.assertEquals("conf", f.getParentFile().getName());
    }

    @Test
    public void test_toProperties() throws Exception {
        // Setup
        EmailPropertiesFile emailPropertiesFile = new EmailPropertiesFile(new ConfigurationDirectory(new ApplicationDirectory("src/test/junit/EmailPropertiesFileTest/")));
        {
            Field logField = AbstractPropertiesFile.class.getDeclaredField("log");
            logField.setAccessible(true);
            logField.set(emailPropertiesFile, logMock);
        }

        // Action
        Properties props
                = emailPropertiesFile.toProperties();

        // Assert
        Assert.assertEquals(3, props.size());
    }


    @Test(expected = RuntimeException.class)
    public void test_toProperties_throws_RuntimeException() throws NoSuchFieldException, IllegalAccessException {
        // Setup
        EmailPropertiesFile emailPropertiesFile = new EmailPropertiesFile(new ConfigurationDirectory(new ApplicationDirectory("this/directory/does/not/exist")));
        {
            Field logField = AbstractPropertiesFile.class.getDeclaredField("log");
            logField.setAccessible(true);
            logField.set(emailPropertiesFile, logMock);
        }

        // Action
        Properties props
                = emailPropertiesFile.toProperties();
    }
}
