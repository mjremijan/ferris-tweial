package org.ferris.tweial.console.io;

import java.io.PrintWriter;
import javax.enterprise.inject.Produces;


public class ConsoleWriterProducer {

    @Produces
    public ConsoleWriter produceConsoleWriter() {
        return new ConsoleWriter(new PrintWriter(System.out));
    }
}
