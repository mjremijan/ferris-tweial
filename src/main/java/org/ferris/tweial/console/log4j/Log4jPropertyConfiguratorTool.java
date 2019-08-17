package org.ferris.tweial.console.log4j;

import org.apache.log4j.PropertyConfigurator;

/**
 * This class exists specifically for unit testing static methods on the
 * {@link PropertyConfigurator} class. A unit test exists for this class which
 * mocks the static method call and verifies the static method is being called
 * correctly.
 *
 * Classes in this application which need to use this static method should
 * instead inject and use this tool.
 */
public class Log4jPropertyConfiguratorTool {

    /**
     * Wrapper for {@link PropertyConfigurator#configure(String)}
     * 
     * @param path A fully-qualified path to the configuration file
     */
    public void configure(String path) {
        PropertyConfigurator.configure(path);
    }
}
