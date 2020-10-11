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
        Assert.assertEquals("eqmD6oXw-co", p.getVidId());
    }

    @Test
    public void test_matches_http() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("http://www.youtube.com/watch?v=RaSmassvv4w");

        // Assert
        Assert.assertTrue(p.matches());
        Assert.assertEquals("RaSmassvv4w", p.getVidId());
    }

    @Test
    public void test_matches_https() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("https://www.youtube.com/watch?v=RaSmassvv4w");

        // Assert
        Assert.assertTrue(p.matches());
        Assert.assertEquals("RaSmassvv4w", p.getVidId());
    }


    @Test
    public void test_matches_with_v_having_request_parameters() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("https://www.youtube.com/watch?v=TGjjTc6Hqzs&feature=youtu.be");

        // Assert
        Assert.assertTrue(p.matches());
        Assert.assertEquals("TGjjTc6Hqzs", p.getVidId());
    }

    @Test
    public void test_matches_with_v_having_request_parameters_backwards() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("https://www.youtube.com/watch?feature=youtu.be&v=TGjjTc6Hqzs");

        // Assert
        Assert.assertTrue(p.matches());
        Assert.assertEquals("TGjjTc6Hqzs", p.getVidId());
    }

    @Test
    public void test_matches_with_v_having_multiple_request_parameters() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("https://www.youtube.com/watch?v=TGjjTc6Hqzs&a=one&b=two&feature=youtu.be&");

        // Assert
        Assert.assertTrue(p.matches());
        Assert.assertEquals("TGjjTc6Hqzs", p.getVidId());
    }

    @Test
    public void test_throws_exception_without_v_parameter() {
        // Arrange
        RuntimeException expected = null;

        // Act
        try {
            new PatternForYouTube("https://www.youtube.com/watch?m=TGjjTc6Hqzs&feature=youtu.be");
        } catch (RuntimeException e) {
            expected = e;
        }

        // Assert
        Assert.assertNotNull(expected);
        Assert.assertEquals(
            "Unable to find YouTube video ID in \"m=TGjjTc6Hqzs&feature=youtu.be\""
            , expected.getMessage()
        );
    }

    @Test
    public void test_shortened_url_ok() {
        // Arrange
        PatternForYouTube p;

        // Act
        p = new PatternForYouTube("https://youtu.be/O4HbKE50OBU");

        // Assert
        Assert.assertTrue(p.matches());
        Assert.assertEquals("O4HbKE50OBU", p.getVidId());
    }

    @Test
    public void test_shorted_url_throws_exception_without_value() {
        // Arrange
        RuntimeException expected = null;

        // Act
        try {
            new PatternForYouTube("https://youtu.be/");
        } catch (RuntimeException e) {
            expected = e;
        }

        // Assert
        Assert.assertNotNull(expected);
        Assert.assertEquals(
            "Unable to find YouTube video ID in \"https://youtu.be/\""
            , expected.getMessage()
        );
    }
}
