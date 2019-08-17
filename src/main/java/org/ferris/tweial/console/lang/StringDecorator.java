package org.ferris.tweial.console.lang;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ferris.tweial.console.util.ArrayTools;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class StringDecorator {

    @FunctionalInterface
    public static interface Decorator {

        /**
         * The string parameter is decorated and returned
         *
         * @param decorateMe The string that's to be decorated
         *
         * @return The decorated string
         */
        public String decorate(String decorateMe);
    }

    protected static class Decorating {

        int min, max;
        Decorator decorator;
        String text;

        public Decorating(int min, int max, Decorator decorator) {
            this.min = min;
            this.max = max;
            this.decorator = decorator;
            this.text = "";
        }

        public Decorating setString(String s) {
            text = s;
            return this;
        }

        @Override
        public String toString() {
            return this.text;
        }

        public String toStringDecorated() {
            return decorator.decorate(this.text);
        }

        public boolean isOverlapping(Decorating a) {
            return Math.max(this.min, a.min) <= Math.min(this.max, a.max);
        }
    }

    private char[] codePointCharacters;
    private Map<Integer, Decorating> decorators;
    private List<UnicodeCharacter> unicodeCharacters;

    public StringDecorator(String text) {
        codePointCharacters
            = new char[]{};

        unicodeCharacters
            = new LinkedList<>();

        decorators
            = new HashMap<>();

        for (int offset = 0; offset < text.length();) {
            // Get the codepoint at the offset
            UnicodeCharacter unicodeCharacter
                = new UnicodeCharacter(text.codePointAt(offset));

            // Get utf-16 characters
            char[] chars = unicodeCharacter.chars();

            // Change the offset appropriately based on codepoint
            offset += unicodeCharacter.charCount();

            // Store
            codePointCharacters = ArrayTools.concat(codePointCharacters, chars);
            unicodeCharacters.add(unicodeCharacter);
        }
    }

    public List<UnicodeCharacter> getUnicodeCharacters() {
        return unicodeCharacters;
    }

    public void decorate(int startInclusive, int endInclusive, Decorator decorator) {
        // Basic index out of bounds error checking
        if (startInclusive < 0) {
            throw new ArrayIndexOutOfBoundsException(
                String.format(
                    "Value for startInclusive [%d] is out of range",
                    startInclusive
                ));
        }
        if (endInclusive >= codePointCharacters.length) {
            throw new ArrayIndexOutOfBoundsException(
                String.format(
                    "Value for endInclusive [%d] is out of range [%d] ",
                    endInclusive,
                    codePointCharacters.length
                ));
        }
        if (endInclusive < startInclusive) {
            throw new ArrayIndexOutOfBoundsException(
                String.format(
                    "Value for index range not valid: startInclusive [%d], endInclusive [%d]",
                    startInclusive, endInclusive
                ));
        }

        // Holder for decorator information
        Decorating holder = new Decorating(startInclusive, endInclusive, decorator);

        // Does this range overlap with any other range?
        decorators.values().forEach(a -> {
            if (a.isOverlapping(holder)) {
                throw new RuntimeException(
                    String.format(
                        "Decorator range [%d,%d] overlaps with existing range [%d,%d]",
                        holder.min, holder.max,
                        a.min, a.max
                    )
                );
            }
        });

        // No overlap, so build string to store in the holder.
        holder.setString(
            new String(Arrays.copyOfRange(codePointCharacters, holder.min, holder.max + 1))
        );

        // Store the holder
        decorators.put(holder.min, holder);
    }

    public void decorate(String src, Decorator decorator) {
        StringDecorator param = new StringDecorator(src);
        int i = 0;
        while ((i = ArrayTools.indexOfSubArray(this.codePointCharacters, param.codePointCharacters, i)) != -1) {
            this.decorate(i, i + (param.codePointCharacters.length - 1), decorator);
            i = i + param.codePointCharacters.length;
        }
    }

    @Override
    public String toString() {
        if (codePointCharacters.length == 0) {
            return "";
        } else {
            return new String(codePointCharacters);
        }
    }

    public String toStringDecorated() {
        int[] idx = new int[]{0};
        StringBuilder sp = new StringBuilder();

        for (int i = 0; i < codePointCharacters.length; i++) {
            // Does a Decorator exists at this index?
            if (decorators.containsKey(i)) {
                Decorating d = decorators.get(i);
                sp.append(d.toStringDecorated());
                i = d.max;
            } // No Decorator, just add the character
            else {
                sp.append(codePointCharacters[i]);
            }
        }
        return sp.toString();
    }
}
