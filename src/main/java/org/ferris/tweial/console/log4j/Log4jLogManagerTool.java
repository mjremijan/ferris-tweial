package org.ferris.tweial.console.log4j;

import org.apache.log4j.LogManager;

/**
 * This class exists specifically for unit testing static methods on the
 * {@link LogManager} class. A unit test exists for this class which mocks the
 * static method call and verifies the static method is being called correctly.
 *
 * Classes in this application which need to use this static method should
 * instead inject and use this tool.
 */
public class Log4jLogManagerTool {

    /**
     * Wrapper for {@link LogManager#resetConfiguration()}
     */
    public void resetConfiguration() {
        LogManager.resetConfiguration();
    }
}
