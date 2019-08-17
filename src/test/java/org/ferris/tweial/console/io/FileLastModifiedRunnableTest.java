/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.io;

import java.io.File;
import javax.enterprise.event.Event;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.io.FileLastModifiedEvent;
import org.ferris.tweial.console.io.FileLastModifiedRunnable;
import org.junit.Assert;
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
public class FileLastModifiedRunnableTest {

    @Mock
    File fileMock;

    @Mock
    Logger logMock;

    @Mock
    Event<FileLastModifiedEvent> eventMock;

    FileLastModifiedRunnable runnable;

    @Test
    public void test_file_and_lastModifiedThen_are_set_by_constructor() {
        // Setup
        Mockito.when(fileMock.lastModified()).thenReturn(88L);

        // Test
        runnable = new FileLastModifiedRunnable(fileMock);

        // Assert
        Assert.assertEquals(fileMock, runnable.file);
        Assert.assertEquals(88L, runnable.lastModifiedThen);
    }

    @Test
    public void test_nothing_happens_if_lastModfied_does_not_change() {
        // Setup
        Mockito.when(fileMock.lastModified()).thenReturn(88L);

        // Test
        runnable = new FileLastModifiedRunnable(fileMock);
        runnable.log = logMock;
        runnable.event = eventMock;
        runnable.run();

        // Assert
        Mockito.verify(eventMock, Mockito.never()).fire(Mockito.any(FileLastModifiedEvent.class));
    }

    @Test
    public void test_event_fired_if_lastModfied_changes() {
        // Setup
        Mockito.when(fileMock.lastModified()).thenReturn(88L);

        // Test
        runnable = new FileLastModifiedRunnable(fileMock);
        runnable.log = logMock;
        runnable.event = eventMock;
        Mockito.when(fileMock.lastModified()).thenReturn(22L);
        runnable.run();

        // Assert
        Mockito.verify(eventMock, Mockito.times(1)).fire(Mockito.any(FileLastModifiedEvent.class));
    }


    @Test
    public void test_lastModifiedThen_updated_if_lastModfied_changes() {
        // Setup
        Mockito.when(fileMock.lastModified()).thenReturn(88L);

        // Test
        runnable = new FileLastModifiedRunnable(fileMock);
        runnable.log = logMock;
        runnable.event = eventMock;
        Mockito.when(fileMock.lastModified()).thenReturn(22L);
        runnable.run();

        // Assert
        Assert.assertEquals(22L, runnable.lastModifiedThen);
    }



}
