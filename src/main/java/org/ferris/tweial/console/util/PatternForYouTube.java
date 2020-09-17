package org.ferris.tweial.console.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class PatternForYouTube {

    protected Pattern p = Pattern.compile("https?:\\/\\/www\\.youtube\\.com\\/watch\\?(.*)");

    protected boolean matches = false;
    public boolean matches() {
        return matches;
    }

    public String vidId;
    public String getVidId() {
        return vidId;
    }
    private void setVidId(String requestParameters) {
        vidId = Arrays.stream(requestParameters.split("&"))
            .filter(nv -> nv.startsWith("v="))
            .map((nv -> nv.substring(2)))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(String.format("Unable to find YouTube video ID in \"%s\"", requestParameters)))
        ;
    }

    public PatternForYouTube(String url) {
        Matcher m = p.matcher(url);
        if (m.matches() && m.groupCount() == 1) {
            this.matches = true;
            this.setVidId(m.group(1));
        }
    }

}
