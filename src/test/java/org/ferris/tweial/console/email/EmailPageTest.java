package org.ferris.tweial.console.email;

import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
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
public class EmailPageTest {
    
    @Mock
    Logger logMock;
    
    @Mock
    Console consoleMock;
    
    EmailPage page;
    
    @Before
    public void before() {        
        page = new EmailPage();
        page.log = logMock;
        page.console = consoleMock;
        page.dataSourceMissing = new LocalizedString("ds missing");
        page.accountDataMissing = new LocalizedString("acct missing");                
        page.heading = new LocalizedString("the heading");
    }

    @Test
    public void test_page_view_if_data_source_is_missing() {
        // setup
        
        // action
        page.viewDataSourceMissing("oops");
                
        // assert
        Mockito.verify(
            consoleMock, Mockito.times(1)
        ).h1(page.heading);
        // assert
        Mockito.verify(
            consoleMock, Mockito.times(1)
        ).p(page.dataSourceMissing, "oops");
    }
    
    @Test
    public void test_page_view_if_account_is_missing() {
        // setup
        // Create Set of ConstraintViolations to return
        Set<ConstraintViolation<EmailAccount>> violations 
            = new HashSet<ConstraintViolation<EmailAccount>>();
        
        // action
        page.viewEmailAccountMissing(violations);
                
        // assert
        Mockito.verify(consoleMock, Mockito.times(1)).h1(page.heading);
        // assert
        Mockito.verify(consoleMock, Mockito.times(1)).p(page.accountDataMissing);
        // assert
        Mockito.verify(consoleMock, Mockito.times(1)).print(violations);
    }
    
}
