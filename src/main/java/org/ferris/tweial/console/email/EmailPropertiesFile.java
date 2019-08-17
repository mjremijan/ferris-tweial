package org.ferris.tweial.console.email;

import javax.inject.Inject;
import org.ferris.tweial.console.configuration.ConfigurationDirectory;
import org.ferris.tweial.console.io.AbstractPropertiesFile;

/**
 * This is a hard coded {@link AbstractPropertiesFile} object to "{@link ConfigurationDirectory}/email.properties"
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailPropertiesFile extends AbstractPropertiesFile {

    private static final long serialVersionUID = 12947850247524578L;

    /**
     * To file "{@link ConfigurationDirectory}/email.properties"
     *
     * @param confdir An {@link ConfigurationDirectory} representing the conf directory.
     */
    @Inject
    public EmailPropertiesFile(ConfigurationDirectory confdir) {
        super(confdir, "email.properties");
    }
}
