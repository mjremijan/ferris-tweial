package org.ferris.tweial.console.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class PatternForYouTube {

    protected Pattern p1 = Pattern.compile("https?:\\/\\/www\\.youtube\\.com\\/watch\\?(.*)");
    protected Pattern p2 = Pattern.compile("https?:\\/\\/youtu\\.be\\/(.*)");

    protected boolean matches = false;
    public boolean matches() {
        return matches;
    }

    public String vidId;
    public String getVidId() {
        return vidId;
    }

    public PatternForYouTube(String url) {
        pattern1(url);
        pattern2(url);
    }

    private void pattern1(String url) {
        if (this.matches == false) {
            Matcher m = p1.matcher(url);
            if (m.matches() && m.groupCount() == 1) {
                this.matches = true;
                this.vidId = Arrays.stream(m.group(1).split("&"))
                    .filter(nv -> nv.startsWith("v="))
                    .map((nv -> nv.substring(2)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(String.format("Unable to find YouTube video ID in \"%s\"", m.group(1))))
                ;
            }
        }
    }

     private void pattern2(String url) {
        if (this.matches == false) {
            Matcher m = p2.matcher(url);
            if (m.matches() && m.groupCount() == 1) {
                this.matches = true;
                this.vidId = m.group(1).trim();
                if (this.vidId.isEmpty()) {
                    throw new RuntimeException(String.format("Unable to find YouTube video ID in \"%s\"", url));
                }

            }
        }
    }



}
