package org.ferris.tweial.console.util;

import java.util.Arrays;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class ArrayTools {

    public static char[] concat(char[] first, char[] second) {
        char[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static char[] subArray(char[] source, int beginIndexInclusive, int endIndexExclusive) {
        char[] result = new char[endIndexExclusive - beginIndexInclusive];
        System.arraycopy(source, beginIndexInclusive, result, 0, result.length);
        return result;
    }

    public static int indexOfSubArray(char[] source, char[] target, int fromIndex) {
        int sourceSize = source.length;
        int targetSize = target.length;
        int maximumPossibleIndexOfSubArray = sourceSize - targetSize;

        nextCand:
            for (int candidateIndex = fromIndex; candidateIndex <= maximumPossibleIndexOfSubArray; candidateIndex++) {
                for (int t=0, s=candidateIndex; t<targetSize; t++, s++) {
                    if (target[t] != source[s]) {
                        continue nextCand;
                    }
                }
                return candidateIndex;
            }

        return -1;  // No candidate matched the target
    }
}
