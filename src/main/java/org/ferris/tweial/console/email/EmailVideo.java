package org.ferris.tweial.console.email;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailVideo {

    static class Builder {
        String poster;
        Builder poster(String poster) {
            this.poster = poster;
            return this;
        }

        int width;
        Builder width(int width) {
            this.width = width;
            return this;
        }

        int height;
        Builder height(int height) {
            this.height = height;
            return this;
        }


        EmailVideo build() {
            return null;
        }
    }
}
