package org.ferris.tweial.console.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class PatternForYouTube {

    protected Pattern p = Pattern.compile("https?:\\/\\/www\\.youtube\\.com\\/watch\\?v=(.+)");

    protected boolean matches = false;
    public boolean matches() {
        return matches;
    }

    public String vidId;
    public String getVidId() {
        return vidId;
    }

    public PatternForYouTube(String url) {
        Matcher m = p.matcher(url);
        if (m.matches() && m.groupCount() == 1) {
            this.matches = true;
            this.vidId = m.group(1);
        }
    }

}
