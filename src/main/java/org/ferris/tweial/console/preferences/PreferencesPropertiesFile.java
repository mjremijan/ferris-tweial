package org.ferris.tweial.console.preferences;

import javax.inject.Inject;
import org.ferris.tweial.console.configuration.ConfigurationDirectory;
import org.ferris.tweial.console.io.AbstractPropertiesFile;

/**
 * This is a hard coded {@link AbstractPropertiesFile} object to "{@link ConfigurationDirectory}/preferences.properties"
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class PreferencesPropertiesFile extends AbstractPropertiesFile  {

    private static final long serialVersionUID = 42656471698675647L;

    /**
     * To file "{@link ConfigurationDirectory}/preferences.properties"
     *
     * @param confdir A {@link ConfigurationDirectory} representing the conf directory.
     */
    @Inject
    public PreferencesPropertiesFile(ConfigurationDirectory confdir) {
        super(confdir, "preferences.properties");
    }
}
