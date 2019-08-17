package org.ferris.tweial.console.data;

import org.ferris.tweial.console.configuration.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.tweial.console.application.ApplicationDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class DataDirectoryProducer {

    protected DataDirectory dataDirectory;

    @Inject
    protected ApplicationDirectory applicationDirectory;

    @PostConstruct
    public void postConstruct() {
        dataDirectory = new DataDirectory(applicationDirectory);
    }

    @Produces
    public DataDirectory produceDataDirectory() {
        return dataDirectory;
    }
}
