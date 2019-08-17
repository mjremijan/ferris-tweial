package org.ferris.tweial.console.lang;

import org.ferris.tweial.console.util.ArrayTools;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class UnicodeStringsAndCharacterArraysTest {

    @Test
    public void test_unicode_string_to_array_and_back_again_with_string() {
        // Arrange
        //
        // \ud83c\udf0e are the utf-16 codes returned by Twitter for the Earth globe emoji
        // \ud83d\udccf are the utf-16 codes returned by Twitter for the Ruler emoji
        // u+1F440      is  the utf-32 code of the Eyes emoji
        //
        // @see https://www.fileformat.info
        //
        String unicodeString = "hello doctor \ud83c\udf0e name continue \ud83d\udccf yesterday \u1F440 tomorrow";

        // Act
        char[] utf16codes = new char[]{};
        for (int offset = 0; offset < unicodeString.length();) {
            // Get the codepoint at the offset
            int codepoint = unicodeString.codePointAt(offset);

            // Get utf-16 characters
            char[] chars = Character.toChars(codepoint);

            // Change the offset appropriately based on codepoint
            offset += Character.charCount(codepoint);

            // Add to list array
            utf16codes = ArrayTools.concat(utf16codes, chars);
        }

        // Assert
        Assert.assertEquals(unicodeString, new String(utf16codes));
    }

    @Test
    public void test_unicode_string_to_array_and_back_again_with_stringbuilder_1() {
        // Arrange
        //
        // \ud83c\udf0e are the utf-16 codes returned by Twitter for the Earth globe emoji
        // \ud83d\udccf are the utf-16 codes returned by Twitter for the Ruler emoji
        // u+1F440      is  the utf-32 code of the Eyes emoji
        //
        // @see https://www.fileformat.info
        //
        String unicodeString = "hello doctor \ud83c\udf0e name continue \ud83d\udccf yesterday \u1F440 tomorrow";

        // Act
        char[] utf16codes = new char[]{};
        for (int offset = 0; offset < unicodeString.length();) {
            // Get the codepoint at the offset
            int codepoint = unicodeString.codePointAt(offset);

            // Get utf-16 characters
            char[] chars = Character.toChars(codepoint);

            // Change the offset appropriately based on codepoint
            offset += Character.charCount(codepoint);

            // Add to list array
            utf16codes = ArrayTools.concat(utf16codes, chars);
        }

        StringBuilder sp = new StringBuilder();
        for (int i=0; i<utf16codes.length; i++) {
            sp.append(utf16codes[i]);
        }

        // Assert
        Assert.assertEquals(unicodeString, sp.toString());
    }


    @Test
    public void test_unicode_string_to_array_and_back_again_with_stringbuilder_2() {
        // Arrange
        //
        // \ud83c\udf0e are the utf-16 codes returned by Twitter for the Earth globe emoji
        // \ud83d\udccf are the utf-16 codes returned by Twitter for the Ruler emoji
        // u+1F440      is  the utf-32 code of the Eyes emoji
        //
        // @see https://www.fileformat.info
        //
        String unicodeStringf = "hello doctor \ud83c\udf0e name continue \ud83d\udccf yesterday \u1F440 tomorrow";
        String unicodeString1 = "hello doctor \ud83c\udf0e";
        String regularString1 = " name continue ";
        String unicodeString2 = "\ud83d\udccf yesterday \u1F440 tomorrow";

        // Act
        char[] chars1 = new char[]{};
        for (int offset = 0; offset < unicodeString1.length();) {
            // Get the codepoint at the offset
            int codepoint = unicodeString1.codePointAt(offset);

            // Get utf-16 characters
            char[] chars = Character.toChars(codepoint);

            // Change the offset appropriately based on codepoint
            offset += Character.charCount(codepoint);

            // Add to list array
            chars1 = ArrayTools.concat(chars1, chars);
        }

        char[] chars2 = new char[]{};
        for (int offset = 0; offset < unicodeString2.length();) {
            // Get the codepoint at the offset
            int codepoint = unicodeString2.codePointAt(offset);

            // Get utf-16 characters
            char[] chars = Character.toChars(codepoint);

            // Change the offset appropriately based on codepoint
            offset += Character.charCount(codepoint);

            // Add to list array
            chars2 = ArrayTools.concat(chars2, chars);
        }

        StringBuilder sp = new StringBuilder();
        for (int i=0; i<chars1.length; i++) {
            sp.append(chars1[i]);
        }
        sp.append(regularString1);
        for (int i=0; i<chars2.length; i++) {
            sp.append(chars2[i]);
        }

        // Assert
        Assert.assertEquals(unicodeStringf, sp.toString());
    }
}
