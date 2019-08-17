package org.ferris.tweial.console.application;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.enterprise.inject.Produces;

public class ApplicationDirectoryProducer {

    @Produces
    public ApplicationDirectory getApplicationDirectory() throws URISyntaxException {
        // This code assumes the following directory structure
        //
        // /tweial
        //    /bin
        //    /lib
        //      ferris-tweial-app-1.0.0.0-SNAPSHOT.jar
        //    /logs
        //
        // So the the application directory will be 1 
        // directory up from where the JAR file is located.
        URL jarURL = ApplicationDirectory.class.getProtectionDomain().getCodeSource().getLocation();
        URI jarURI = jarURL.toURI();
        File jarFile = new File(jarURI);
        File appFile = jarFile.getParentFile().getParentFile();
        ApplicationDirectory appDir = new ApplicationDirectory(appFile.getAbsolutePath());
        return appDir;
    }
}
