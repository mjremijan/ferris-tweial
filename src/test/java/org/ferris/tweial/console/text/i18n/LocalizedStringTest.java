package org.ferris.tweial.console.text.i18n;

import org.ferris.tweial.console.text.i18n.LocalizedString;
import org.junit.Assert;
import org.junit.Test;

public class LocalizedStringTest {
	
	@Test
	public void test_toString() {
		// setup
		LocalizedString ls = new LocalizedString("hello");
		// assert
		Assert.assertEquals("hello", ls.toString());
	}

	@Test
	public void test_intValue_returns_default() {
		// setup
		LocalizedString ls = new LocalizedString("NaN");
		// assert
		Assert.assertEquals(99, ls.intValue(99));
	}
	
	@Test
	public void test_intValue_returns_int() {
		// setup
		LocalizedString ls = new LocalizedString("42");
		// assert
		Assert.assertEquals(42, ls.intValue(0));
	}
	
	@Test
	public void test_length() {
		// setup
		LocalizedString ls = new LocalizedString("123456789");
		// assert
		Assert.assertEquals(9, ls.length());
	}
}
