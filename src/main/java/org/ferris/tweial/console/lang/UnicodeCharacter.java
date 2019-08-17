package org.ferris.tweial.console.lang;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class UnicodeCharacter {

    private final char[] buff;
    private final String string;
    private final int utf32decimal;
    private final String utf32hexstring;
    private final String utf32unicodecode;
    private final String[] utf16hexstrings;
    private final String[] utf16unicodecodes;

    public UnicodeCharacter(int utf_32_decimal_codepoint) {
        this.buff = Character.toChars(utf_32_decimal_codepoint);
        this.string = new String(buff);
        this.utf32decimal = utf_32_decimal_codepoint;
        this.utf32hexstring = Integer.toHexString(this.utf32decimal);
        this.utf32unicodecode = String.format("\\u%s",String.format("%s", String.format("%1$" + 4 + "s", this.utf32hexstring).replace(' ', '0')));
        this.utf16hexstrings = new String[this.buff.length]; {
            for(int i=0; i<this.buff.length; i++) {
                this.utf16hexstrings[i] = Integer.toHexString((int)this.buff[i]);
            }
        }
        this.utf16unicodecodes = new String[this.buff.length]; {
            for(int i=0; i<this.buff.length; i++) {
                this.utf16unicodecodes[i] = String.format("\\u%s",String.format("%s", String.format("%1$" + 4 + "s", this.utf16hexstrings[i]).replace(' ', '0')));
            }
        }
    }

    /**
     * @return UTF-16 Unicode code representation of this Unicode character
     */
    public String[] toUTF16UnicodeCodes() {
        return utf16unicodecodes;
    }

    /**
     * @return Attributes of this object
     */
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[UnicodeCharacter ", "]");
        sj.add(String.format("%-3s", getString()));
        sj.add(String.format("length=%d", getLength()));
        sj.add(String.format("codepoint=%d", getCodepoint()));
        sj.add(String.format("UTF32Decimal=%d", toUTF32Decimal()));
        sj.add(String.format("UTF32HexString=%s", toUTF32HexString()));
        sj.add(String.format("UTF32UnicodeCode=%s", toUTF32UnicodeCode()));
        sj.add(String.format("UTF16HexStrings=%s", Arrays.toString(toUTF16HexStrings())));
        sj.add(String.format("UTF16UnicodeCodes=%s", Arrays.toString(toUTF16UnicodeCodes())));
        return sj.toString();
    }

    /**
     * @return UTF-16 (hex) Strings representation of this Unicode character
     */
    public String[] toUTF16HexStrings() {
        return utf16hexstrings;
    }

    /**
     * @return UTF-32 Unicode code representation of this Unicode character
     */
    public String toUTF32UnicodeCode() {
        return utf32unicodecode;
    }

    /**
     * @return UTF-32 (hex) String representation of this Unicode character
     */
    public String toUTF32HexString() {
        return utf32hexstring;
    }

    /**
     * @return UTF-32 (decimal) representation of this Unicode character
     */
    public int toUTF32Decimal() {
        return utf32decimal;
    }

    /**
     * @return  Same as #toUTF32Decimal()
     */
    public int getCodepoint() {
        return toUTF32Decimal();
    }

    /**
     * @return length of the char[]
     */
    public int getLength() {
        return buff.length;
    }

    /**
     * @return the buffer
     */
    public char[] chars() {
        return buff;
    }

    /**
     * @return {@link Character#charCount(int)}
     */
    public int charCount() {
        return Character.charCount(getCodepoint());
    }

    /**
     * @return String representation of this Unicode character
     */
    public String getString() {
        return this.string;
    }

}
