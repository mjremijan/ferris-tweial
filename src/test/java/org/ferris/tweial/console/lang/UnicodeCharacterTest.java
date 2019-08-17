package org.ferris.tweial.console.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class UnicodeCharacterTest {

    @Test
    public void test_tostring_with_ascii_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("a".codePointAt(0));
        Assert.assertEquals("a", cas.getString());
    }

    @Test
    public void test_toUTF32Decimal_with_ascii_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("a".codePointAt(0));
        Assert.assertEquals(97, cas.toUTF32Decimal());
    }

    @Test
    public void test_toUTF32HexString_with_ascii_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("a".codePointAt(0));
        Assert.assertEquals("61", cas.toUTF32HexString());
    }

    @Test
    public void test_toUTF32UnicodeCode_with_ascii_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("a".codePointAt(0));
        Assert.assertEquals("\\u0061", cas.toUTF32UnicodeCode());
    }

    @Test
    public void test_toUTF16HexStrings_with_ascii_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("a".codePointAt(0));
        Assert.assertEquals(1, cas.toUTF16HexStrings().length);
        Assert.assertEquals("61", cas.toUTF16HexStrings()[0]);
    }

    @Test
    public void test_toUTF16UnicodeCodes_with_ascii_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("a".codePointAt(0));
        Assert.assertEquals(1, cas.toUTF16UnicodeCodes().length);
        Assert.assertEquals("\\u0061", cas.toUTF16UnicodeCodes()[0]);
    }

    @Test
    public void test_getLength_with_ascii_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("a".codePointAt(0));
        Assert.assertEquals(1, cas.getLength());
    }

    @Test
    public void test_tostring_with_emoji_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("\ud83c\udf0e".codePointAt(0));
        Assert.assertEquals("\ud83c\udf0e", cas.getString());
    }

    @Test
    public void test_toUTF32Decimal_with_emoji_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("\ud83c\udf0e".codePointAt(0));
        Assert.assertEquals(127758, cas.toUTF32Decimal());
    }

    @Test
    public void test_toUTF32HexString_with_emoji_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("\ud83c\udf0e".codePointAt(0));
        Assert.assertEquals("1f30e", cas.toUTF32HexString());
    }

    @Test
    public void test_toUTF32UnicodeCode_with_emoji_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("\ud83c\udf0e".codePointAt(0));
        Assert.assertEquals("\\u1f30e", cas.toUTF32UnicodeCode());
    }

    @Test
    public void test_toUTF16HexStrings_with_emoji_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("\ud83c\udf0e".codePointAt(0));
        Assert.assertEquals(2, cas.toUTF16HexStrings().length);
        Assert.assertEquals("d83c", cas.toUTF16HexStrings()[0]);
        Assert.assertEquals("df0e", cas.toUTF16HexStrings()[1]);
    }

    @Test
    public void test_toUTF16UnicodeCodes_with_emoji_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("\ud83c\udf0e".codePointAt(0));
        Assert.assertEquals(2, cas.toUTF16UnicodeCodes().length);
        Assert.assertEquals("\\ud83c", cas.toUTF16UnicodeCodes()[0]);
        Assert.assertEquals("\\udf0e", cas.toUTF16UnicodeCodes()[1]);
    }

    @Test
    public void test_getLength_with_emoji_character() {
        UnicodeCharacter cas
            = new UnicodeCharacter("\ud83c\udf0e".codePointAt(0));
        Assert.assertEquals(2, cas.getLength());
    }
}
