package org.ferris.tweial.console.util;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class ArrayToolsTest {

    @Test
    public void test_indexOfSubArray1() {
        // Arrange
        char[] a = new char[]{'a', 'b', 'c', '2', '3', 'd', 'e'};
        char[] b = new char[]{ '2', '3', 'd'};

        // Act
        int idx = ArrayTools.indexOfSubArray(a, b ,0);

        // Assert
        Assert.assertEquals(3, idx);
    }

    @Test
    public void test_indexOfSubArray2() {
        // Arrange
        char[] a = new char[]{'a', 'b', 'c', '2', '3', 'd', 'e'};
        char[] b = new char[]{'b', 'c'};

        // Act
        int idx = ArrayTools.indexOfSubArray(a, b, 0);

        // Assert
        Assert.assertEquals(1, idx);
    }

    @Test
    public void test_indexOfSubArray3() {
        // Arrange
        char[] a = new char[]{'a', 'b', 'c', '2', '3', 'd', 'e'};
        char[] b = new char[]{'d'};

        // Act
        int idx = ArrayTools.indexOfSubArray(a, b, 0);

        // Assert
        Assert.assertEquals(5, idx);
    }

    @Test
    public void test_indexOfSubArray4() {
        // Arrange
        char[] a = new char[]{'a', 'b', 'c', '2', '3', 'd', 'e'};
        char[] b = new char[]{'e'};

        // Act
        int idx = ArrayTools.indexOfSubArray(a, b, 0);

        // Assert
        Assert.assertEquals(6, idx);
    }

    @Test
    public void test_indexOfSubArray5() {
        // Arrange
        char[] a = new char[]{'a', 'b', 'c', '2', '3', 'd', 'e'};
        char[] b = new char[]{'q'};

        // Act
        int idx = ArrayTools.indexOfSubArray(a, b, 0);

        // Assert
        Assert.assertEquals(-1, idx);
    }

    @Test
    public void test_indexOfSubArray6() {
        // Arrange
        char[] a = new char[]{'a', 'b', 'c', '2', '3', 'd', 'e'};
        char[] b = new char[]{ 'c', '2', '3', 'd', 'x'};

        // Act
        int idx = ArrayTools.indexOfSubArray(a, b, 0);

        // Assert
        Assert.assertEquals(-1, idx);
    }

    @Test
    public void test_indexOfSubArray7() {
        // Arrange
        //                     0    1    2    3    4    5    6    7    8    9
        char[] a = new char[]{'a', 'x', 'x', 'a', 'x', 'x', 'a', 'a', 'x', 'x'};
        char[] b = new char[]{ 'x', 'x'};

        // Act
        int idx0 = ArrayTools.indexOfSubArray(a, b, 0);
        int idx1 = ArrayTools.indexOfSubArray(a, b, 1);
        int idx2 = ArrayTools.indexOfSubArray(a, b, 2);
        int idx3 = ArrayTools.indexOfSubArray(a, b, 3);
        int idx4 = ArrayTools.indexOfSubArray(a, b, 4);
        int idx5 = ArrayTools.indexOfSubArray(a, b, 5);
        int idx6 = ArrayTools.indexOfSubArray(a, b, 6);
        int idx7 = ArrayTools.indexOfSubArray(a, b, 7);
        int idx8 = ArrayTools.indexOfSubArray(a, b, 8);
        int idx9 = ArrayTools.indexOfSubArray(a, b, 9);

        // Assert
        Assert.assertEquals(1, idx0);
        Assert.assertEquals(1, idx1);
        Assert.assertEquals(4, idx2);
        Assert.assertEquals(4, idx3);
        Assert.assertEquals(4, idx4);
        Assert.assertEquals(8, idx5);
        Assert.assertEquals(8, idx6);
        Assert.assertEquals(8, idx7);
        Assert.assertEquals(8, idx8);
        Assert.assertEquals(-1, idx9);
    }
}

