package org.ferris.tweial.console.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class LineFeedNewLineTest {

    @Test
    public void test_newline_characters_are_equal() {
        // Arrange
        long codepointCount = "\n".codePoints().count();
        int codepointAt = "\n".codePointAt(0);

        // Assert
        Assert.assertEquals(1, codepointCount);
        Assert.assertEquals(10, codepointAt);
    }
}
