package org.ferris.tweial.console.io;

import org.ferris.tweial.console.io.ConsoleWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.Assert;
import org.junit.Test;

public class ConsoleWriterTest {

	@Test
	public void testPrintf() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ConsoleWriter writer = new ConsoleWriter(pw);
		writer.printf("%s %s", "hello", "java");
		Assert.assertEquals("hello java", sw.toString());
	}

	@Test
	public void testPrintln() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ConsoleWriter writer = new ConsoleWriter(pw);
		writer.println();
		Assert.assertEquals(System.getProperty("line.separator"), sw.toString());
	}

}
