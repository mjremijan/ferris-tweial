package org.ferris.tweial.console.util;

import org.ferris.tweial.console.util.PropertiesFile;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.ferris.tweial._junit.hamcrest.StartsWithMatcher;
import org.ferris.tweial._junit.io.InputStreamThrowsException;
import org.ferris.tweial._junit.io.OutputStreamThrowsException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertiesFileTest 
{
    private File junitDir = new File("src/test/junit/PropertiesFileTest/");
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void fileInputStreamOK() {
    	PropertiesFile spy = Mockito.spy(new PropertiesFile(junitDir, "streams.properties"));
        InputStream is
            = spy.getInputStream();
        assertNotNull(is);
        IOUtils.closeQuietly(is);
    }
    
    @Test
    public void fileInputStreamThrowsRuntimeException() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(
            new StartsWithMatcher("Problem getting InputStream to file"));
     
        PropertiesFile spy = Mockito.spy(new PropertiesFile(
            new File("this/does/not/exist/"), "missing.properties"));
        spy.getInputStream();        
    }
        
    @Test
    public void fileOutputStreamOK() {
        PropertiesFile spy = Mockito.spy(new PropertiesFile(junitDir, "streams.properties"));
        OutputStream is
            = spy.getOutputStream();
        assertNotNull(is);
        IOUtils.closeQuietly(is);
    }
    
    @Test
    public void fileOutputStreamThrowsRuntimeException() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(
            new StartsWithMatcher("Problem getting OutputStream to file"));
     
        PropertiesFile spy = Mockito.spy(new PropertiesFile(
            new File("this/does/not/exist/"), "missing.properties"));
        spy.getOutputStream();        
    }
    
    @Test
    public void getPropertiesOK() {
    	PropertiesFile spy = Mockito.spy(new PropertiesFile(junitDir, "get.properties"));
        Properties p = spy.getProperties();
        assertEquals(1, p.size());
    }
    
    @Test
    public void getPropertiesThrowsRuntimeException() throws Exception {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(
            new StartsWithMatcher("Problem loading properties from file"));

        PropertiesFile spy = Mockito.spy(new PropertiesFile(junitDir, "streams.properties"));
        InputStream is = new InputStreamThrowsException();
        Mockito.doReturn(is).when(spy).getInputStream();
        
        spy.getProperties();        
    }
    
    @Test
    public void findOk() {
    	PropertiesFile spy = Mockito.spy(new PropertiesFile(junitDir, "find.properties"));
        assertEquals("oscar orange", spy.find("name", ""));
    }
    
    @Test
    public void persistOk() {
    	PropertiesFile spy = Mockito.spy(new PropertiesFile(junitDir, "streams.properties"));
        Properties p = new Properties();
        Mockito.doReturn(p).when(spy).getProperties();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Mockito.doReturn(baos).when(spy).getOutputStream();
        
        spy.persist("spaghetti", "meatballs");                
        assertTrue(baos.toString().contains("spaghetti=meatballs"));
    }
    
    @Test
    public void persistThrowsIOException() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(
            new StartsWithMatcher("Problem storing properties in file"));

        Properties p = new Properties();
        PropertiesFile spy = Mockito.spy(new PropertiesFile(junitDir, "streams.properties"));
        Mockito.doReturn(p).when(spy).getProperties();
        
        OutputStream os = new OutputStreamThrowsException();
        Mockito.doReturn(os).when(spy).getOutputStream();
        
        spy.persist("throws", "exception");                
    }
}
