package org.ferris.tweial.console.twitter;

import javax.inject.Inject;
import org.ferris.tweial.console.configuration.ConfigurationDirectory;
import org.ferris.tweial.console.io.AbstractPropertiesFile;

/**
 * This is a hard coded {@link AbstractPropertiesFile} object to "{@link ConfigurationDirectory}/twitter.properties"
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TwitterPropertiesFile extends AbstractPropertiesFile {

    private static final long serialVersionUID = 3535856455970864559L;

    /**
     * To file "{@link ConfigurationDirectory}/twitter.properties"
     *
     * @param confdir Assumed OK, not error checked.
     */
    @Inject
    public TwitterPropertiesFile(ConfigurationDirectory confdir) {
        super(confdir, "twitter.properties");
    }
}
