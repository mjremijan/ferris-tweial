package org.ferris.tweial.console.email;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailVideo {

    private EmailVideo() {}

    static class Builder {
        String poster;
        Builder poster(String poster) {
            this.poster = poster;
            return this;
        }

        String src;
        Builder src(String src) {
            this.src = src;
            return this;
        }

        String type;
        Builder type(String type) {
            this.type = type;
            return this;
        }

        long millis;
        Builder millis(long millis) {
            this.millis = millis;
            return this;
        }

        EmailVideo build() {
            EmailVideo v = new EmailVideo();

            v.posterUrl = this.poster;
            v.srcUrl = this.src;
            v.srcContentType = this.type;

            if (millis >= 60000) {
                long minutes = (millis / 1000)  / 60;
                int seconds = (int)((millis / 1000) % 60);
                v.length = String.format("%d:%d", minutes, seconds);
            } else {
                long seconds  = millis / 1000;
                v.length = String.format("0:%d", seconds);
            }

            return v;
        }
    }

    private String posterUrl;
    public String getPosterUrl() {
        return posterUrl;
    }

    private String srcUrl;
    public String getSrcUrl() {
        return srcUrl;
    }

    private String srcContentType;
    public String getSrcContentType() {
        return srcContentType;
    }

    private String length;
    public String getLength() {
        return length;
    }
}
