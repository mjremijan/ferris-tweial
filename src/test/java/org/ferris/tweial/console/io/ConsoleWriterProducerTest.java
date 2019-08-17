package org.ferris.tweial.console.io;


import org.ferris.tweial.console.io.ConsoleWriter;
import org.ferris.tweial.console.io.ConsoleWriterProducer;
import org.junit.Assert;
import org.junit.Test;

public class ConsoleWriterProducerTest {

	@Test
	public void testProduceConsoleWriter() {
		ConsoleWriter writer
			= new ConsoleWriterProducer().produceConsoleWriter();
		Assert.assertNotNull(writer);
	}

}
