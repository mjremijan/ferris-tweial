package org.ferris.tweial.research.properties;

import java.io.InputStream;
import java.util.Properties;
import org.junit.Before;

public abstract class AbstractPropertiesTest {

    protected Properties properties;

    @Before
    public void before() throws Exception {
        // This comes from the ferris-tweial-cfg project. Because this
        // project is on GitHub, ferris-tweial-cfg is configured to ignore
        // the /src/main/resources/private.properties file and not commit it
        // to git. This keeps the email account information private.
        InputStream is = getClass().getResourceAsStream("/private.properties");
        if (is != null) {
            properties = new Properties();
            properties.load(is);
            if (properties.size() == 0) {
                properties = null;
            }
        }
    }
}
