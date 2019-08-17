/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.log4j;

import java.io.File;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.application.ApplicationDirectory;
import org.ferris.tweial.console.configuration.ConfigurationDirectory;
import org.ferris.tweial.console.io.FileLastModifiedEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class Log4jReconfigureTest {

    @Mock
    FileLastModifiedEvent eventMock;

    @Mock
    Logger log;

    @Mock
    Log4jLogManagerTool logManagerToolMock;

    @Mock
    Log4jPropertyConfiguratorTool propertyConfiguratorToolMock;

    Log4jReconfigure observer;

    @Before
    public void before() {
        new File("target/conf");
        observer = new Log4jReconfigure(new ConfigurationDirectory(new ApplicationDirectory("target")));
        observer.log = log;
        observer.logManagerTool = logManagerToolMock;
        observer.propertyConfiguratorTool = propertyConfiguratorToolMock;
    }

    @Test
    public void test_log4j_properties_file_is_targeted() {
        // Assert
        Assert.assertEquals(
                new File("target/conf/log4j.properties").getPath(), observer.getFile().getPath()
        );
    }

    @Test
    public void test_observeFileLastModifiedEvent_if_its_the_correct_filename() {
        File expected = new File("log4j.properties");
        Mockito.when(eventMock.getFile()).thenReturn(expected);
        observer.observeFileLastModifiedEvent(eventMock);
        Mockito.verify(logManagerToolMock, Mockito.times(1)).resetConfiguration();
        Mockito.verify(propertyConfiguratorToolMock, Mockito.times(1)).configure(expected.getAbsolutePath());
    }

    @Test
    public void test_observeFileLastModifiedEvent_if_its_NOT_the_correct_filename() {
        File wrong = new File("mercy.properties");
        Mockito.when(eventMock.getFile()).thenReturn(wrong);
        observer.observeFileLastModifiedEvent(eventMock);
        Mockito.verify(logManagerToolMock, Mockito.times(0)).resetConfiguration();
        Mockito.verify(propertyConfiguratorToolMock, Mockito.times(0)).configure(Mockito.anyString());
    }
}
