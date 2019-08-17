package org.ferris.tweial.console.lang;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * This class exists only for unit testing that the static method
 * Thread.setDefaultUncaughtExceptionHandler() is called.
 *
 */
public class ThreadTool {

    public void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

}
