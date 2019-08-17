package org.ferris.tweial.console.configuration;

import java.io.File;
import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;
import org.ferris.tweial.console.application.ApplicationDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class ConfigurationDirectory extends File {

    private static final long serialVersionUID = 7491901906021288631L;

    @Inject
    public ConfigurationDirectory(ApplicationDirectory appdir) {
        super(appdir, "conf");
        if (!exists()) {
            throw new RuntimeException(
                String.format("Configuration directory does not exist \"%s\"", getPath())
            );
        }
    }

}
