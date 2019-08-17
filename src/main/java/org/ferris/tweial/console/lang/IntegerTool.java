package org.ferris.tweial.console.lang;

/**
 * Provides a few basic convenience methods when working with int values
 * 
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class IntegerTool {
    public static int parseInt(String parseMe, int defaultValue) {
        int retval = defaultValue;
        try {
            retval = Integer.parseInt(parseMe);
        } catch (Exception notAnInt) {}
        return retval;
    }
}
