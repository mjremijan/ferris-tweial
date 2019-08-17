package org.ferris.tweial.console.application;

import java.io.File;
import java.net.URISyntaxException;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan Remijan mjremijan@yahoo.com @mjremijan
 */
public class ApplicationDirectoryProducerTest {
    
    @Test
    public void getIt() throws URISyntaxException {
        ApplicationDirectory tad 
            = new ApplicationDirectoryProducer().getApplicationDirectory();
        assertNotNull(tad);
        File pwd = new File("").getAbsoluteFile(); 
        assertTrue(pwd.getName().equals(tad.getName()) || "target".equals(tad.getName()));
    }
    
}
