package org.ferris.tweial.console.util;

import org.junit.Assert;
import org.junit.Test;

/**
 *  *
 *  * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 
 */
public class PatternForYouTubeTest {

    @Test
    public void test_get_watch_id() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("https://www.youtube.com/watch?v=RaSmassvv4w");

        // Assert
        Assert.assertEquals("RaSmassvv4w", p.getVidId());
    }

    @Test
    public void test_matches_https_dash() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("https://www.youtube.com/watch?v=eqmD6oXw-co");

        // Assert
        Assert.assertTrue(p.matches());
    }

    @Test
    public void test_matches_http() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("http://www.youtube.com/watch?v=RaSmassvv4w");

        // Assert
        Assert.assertTrue(p.matches());
    }

    @Test
    public void test_matches_https() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("https://www.youtube.com/watch?v=RaSmassvv4w");

        // Assert
        Assert.assertTrue(p.matches());
    }
}
