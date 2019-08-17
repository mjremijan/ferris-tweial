package org.ferris.tweial.console.lang;

/**
 * Provides a few basic convenience methods when working with int values
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class LongTool {
    public static long parseLong(String parseMe, int defaultValue) {
        long retval = defaultValue;
        try {
            retval = Long.parseLong(parseMe);
        } catch (Exception notALong) {}
        return retval;
    }
}
