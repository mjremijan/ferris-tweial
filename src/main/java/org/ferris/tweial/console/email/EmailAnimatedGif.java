package org.ferris.tweial.console.email;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailAnimatedGif {

    private EmailAnimatedGif() {}

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

        EmailAnimatedGif build() {
            EmailAnimatedGif v = new EmailAnimatedGif();

            v.posterUrl = this.poster;
            v.srcUrl = this.src;
            v.srcContentType = this.type;

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
}
