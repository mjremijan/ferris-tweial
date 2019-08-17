package org.ferris.tweial.console.lang;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *  *
 *  * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 
 */
@RunWith(MockitoJUnitRunner.class)
public class StringDecoratorTest {

    @Test
    public void overlapp() {

        StringDecorator.Decorating a1 = new StringDecorator.Decorating(3, 6, null);
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(1,4,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(5,7,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(4,5,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(2,7,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(1,3,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(6,8,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(3,5,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(5,6,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(3,6,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(3,9,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(1,6,null);
            Assert.assertTrue(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(1,2,null);
            Assert.assertFalse(a1.isOverlapping(a2));
        }
        {
            StringDecorator.Decorating a2 = new StringDecorator.Decorating(7,9,null);
            Assert.assertFalse(a1.isOverlapping(a2));
        }

    }

    @Test
    public void test_toString_empty() {
        StringDecorator tt = new StringDecorator("");
        Assert.assertEquals("", tt.toString());;
    }

    @Test
    public void test_toStringDecorated_empty() {
        StringDecorator tt = new StringDecorator("");
        Assert.assertEquals("", tt.toStringDecorated());;
    }

    @Test
    public void test_toString_single_space() {
        StringDecorator tt = new StringDecorator(" ");
        Assert.assertEquals(" ", tt.toString());;
    }

    @Test
    public void test_toStringDecorated_single_space() {
        StringDecorator tt = new StringDecorator(" ");
        Assert.assertEquals(" ", tt.toStringDecorated());
    }

    @Test
    public void test_toString_with_one_decorator() {
        StringDecorator tt = new StringDecorator(
            "abc 123 xyz"
           //0123456789|
        );
        tt.decorate(4, 6, s -> String.format("[%s]",s));
        Assert.assertEquals("abc 123 xyz", tt.toString());
    }

    @Test
    public void test_toStringDecorated_with_one_decorator() {
        StringDecorator tt = new StringDecorator(
            "abc 123 xyz"
           //0123456789|
        );
        tt.decorate(4, 6, s -> String.format("[cdata[[%s]];",s));
        Assert.assertEquals("abc [cdata[[123]]; xyz", tt.toStringDecorated());
    }

    @Test
    public void test_toString_with_two_decorators() {
        StringDecorator tt = new StringDecorator(
            "abc 123 xyz"
           //0123456789|
        );
        tt.decorate(4, 6, s -> String.format("[%s]",s));
        tt.decorate(8, 10, s -> String.format("[%s]",s));
        Assert.assertEquals("abc 123 xyz", tt.toString());
    }

    @Test
    public void test_toStringDecorated_with_two_decorators() {
        StringDecorator tt = new StringDecorator(
            "abc 123 xyz"
           //0123456789|
        );
        tt.decorate(4, 6, s -> String.format("[cdata[[%s]];",s));
        tt.decorate(0, 2, s -> String.format("<div>%s</div>",s));
        Assert.assertEquals("<div>abc</div> [cdata[[123]]; xyz", tt.toStringDecorated());
    }

    @Test
    public void test_decorators_cannot_overlap() {
        StringDecorator tt = new StringDecorator(
            "abc 123 xyz"
           //0123456789|
        );
        tt.decorate(4, 6, s -> String.format("[%s];",s));
        RuntimeException caught = null;
        try {
            tt.decorate(0,5, s -> String.format("{%s}",s));
        } catch (RuntimeException e) {
            caught = e;
        }
        Assert.assertNotNull(caught);
        Assert.assertEquals("Decorator range [0,5] overlaps with existing range [4,6]", caught.getMessage());
    }

    @Test
    public void test_replace() {
        StringDecorator tt = new StringDecorator(
            "abc 123 xyz"
           //0123456789|
        );
        tt.decorate("123", s -> "");
        Assert.assertEquals("abc  xyz", tt.toStringDecorated());
    }

    @Test
    public void test_newline_1() {
        StringDecorator tt = new StringDecorator(
            "abc \n 123"
        );
        tt.decorate("\n", s -> "<br />");
        Assert.assertEquals("abc <br /> 123", tt.toStringDecorated());
    }

    @Test
    public void test_newline_2() {
        StringDecorator tt = new StringDecorator(
            "abc \n 123 \n xyz"
        );
        tt.decorate("\n", s -> "<br />");
        Assert.assertEquals("abc <br /> 123 <br /> xyz", tt.toStringDecorated());
    }
}
