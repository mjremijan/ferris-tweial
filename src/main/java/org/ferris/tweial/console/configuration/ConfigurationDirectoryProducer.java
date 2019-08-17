package org.ferris.tweial.console.configuration;

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
public class ConfigurationDirectoryProducer {

    protected ConfigurationDirectory configurationDirectory;

    @Inject
    protected ApplicationDirectory applicationDirectory;

    @PostConstruct
    public void postConstruct() {
        configurationDirectory = new ConfigurationDirectory(applicationDirectory);
    }

    @Produces
    public ConfigurationDirectory produceConfigurationDirectory() {
        return configurationDirectory;
    }
}
