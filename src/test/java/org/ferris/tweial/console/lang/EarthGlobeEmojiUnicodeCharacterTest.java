package org.ferris.tweial.console.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * This tweet by NASA Earth...
 *
 * https://twitter.com/NASAEarth/status/1129105989452615680
 *
 * ...uses Earth globe emoji character. Tweial was not parsing
 * this tweet properly. The reason has to do with how Twitter
 * is returning Unicode characters versus how Java is processing
 * the Unicode characters. For this Earth globe emoji character,
 * Twitter is returning 2 utf-16 codes: \ud83c\udf0e. However
 * Once Java converts these unicode codes into a String object,
 * the String object returns only 1 utf-32 code: \u1f30e. Because
 * of this "character" count difference, the parsing doesn't work
 * properly
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EarthGlobeEmojiUnicodeCharacterTest {


    @Test
    public void test_utf32_hex_value_returns_char_array_of_utf16_hex_values() {
        //
        // \u1f30e is the utf-32 hex value of the Earth globe emoji.
        //
        // @see https://www.fileformat.info/info/unicode/char/1f30e/index.htm
        //
        // Validate that from this utf-32 hex value I can get the 2 utf-16 hex values
        //
        char[] chars = Character.toChars(0x1f30e);
        Assert.assertEquals(2, chars.length);
        Assert.assertEquals("d83c", Integer.toHexString((int)chars[0]));
        Assert.assertEquals("df0e", Integer.toHexString((int)chars[1]));
    }

    @Test
    public void test_that_the_2_utf16_codes_become_1_utf32_code() {
        //
        // \ud83c\udf0e are the utf-16 codes returned by Twitter for the Earth globe emoji
        //
        // @see https://www.fileformat.info/info/unicode/char/1f30e/index.htm
        //
        String earthGlobEmoji = "\ud83c\udf0e";

        // Validate Java turns these 2 utf-16 codes into 1 utf-32 code.
        Assert.assertEquals(1, earthGlobEmoji.codePointCount(0, earthGlobEmoji.length()));
    }

    @Test
    public void test_that_the_codepoint_decimal_value_is_the_same_as_the_utf32_hex_value() {
        //
        // \ud83c\udf0e are the utf-16 codes returned by Twitter for the Earth globe emoji
        // \u1f30e is the utf-32 value returned by a Java Strng.
        //
        // @see https://www.fileformat.info/info/unicode/char/1f30e/index.htm
        //
        String earthGlobEmoji = "\ud83c\udf0e";

        // Validate the codepoint decimal value is the same as the utf-32 value
        int codePoint = earthGlobEmoji.codePointAt(0);
        Assert.assertEquals(127758, codePoint);
        Assert.assertEquals("1f30e", Integer.toHexString(codePoint));
    }

    @Test
    public void test_i_can_start_with_2_utf16_codes_and_get_them_back_again() {
        //
        // \ud83c\udf0e are the utf-16 codes returned by Twitter for the Earth globe emoji
        //
        // @see https://www.fileformat.info/info/unicode/char/1f30e/index.htm
        //
        String earthGlobEmoji = "\ud83c\udf0e";

        // Validate I can get these 2 utf-16 codes back again
        char[] chars = Character.toChars(earthGlobEmoji.codePointAt(0));
        Assert.assertEquals(2, chars.length);
        Assert.assertEquals("d83c", Integer.toHexString((int)chars[0]));
        Assert.assertEquals("df0e", Integer.toHexString((int)chars[1]));
    }
}
