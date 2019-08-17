package org.ferris.tweial.console.exception;

import org.apache.log4j.Logger;
import org.ferris.tweial.console.io.Console;
import org.ferris.tweial.console.text.i18n.LocalizedString;
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
public class UncaughtExceptionPageTest {
    
    @Mock
    Logger logMock;
    
    @Mock
    Console consoleMock;
    
    UncaughtExceptionPage page;
    
    @Before
    public void before() {        
        page = new UncaughtExceptionPage();
        page.log = logMock;
        page.console = consoleMock;        
        page.heading = new LocalizedString("the heading");
        page.opps = new LocalizedString("opps, it's bad!");
    }

    @Test
    public void test_page_showing_exception() {
        // setup
        Exception frog = new Exception();
        
        // action
        page.view(frog);
                
        // assert
        Mockito.verify(
            consoleMock, Mockito.times(1)
        ).h1(page.heading);
        // assert
        Mockito.verify(
            consoleMock, Mockito.times(1)
        ).p(page.opps);
        // assert
        Mockito.verify(
            consoleMock, Mockito.times(1)
        ).p(frog);
    }
}
