package org.ferris.tweial.console.text.i18n;

import org.ferris.tweial.console.text.i18n.LocalizedStringList;
import org.junit.Assert;
import org.junit.Test;

public class LocalizedStringListTest {
	
	@Test
	public void test_replace() {
		// setup
		LocalizedStringList list 
			= new LocalizedStringList();
		list.add("t{foo} world");
		list.add("hello t{foo}");
		list.add("ritat{foo}red");
		
		// action
		list.replace("t{foo}", "bar");
		
		// assert
		Assert.assertEquals("bar world", list.get(0).toString());
		Assert.assertEquals("hello bar", list.get(1).toString());
		Assert.assertEquals("ritabarred", list.get(2).toString());
	}

}
