package org.ferris.tweial.console.io;

import java.io.PrintWriter;

public class ConsoleWriter {

    private PrintWriter writer;

    public ConsoleWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public void printf(String format, Object... args) {
        writer.printf(format, args);
        writer.flush();
    }

    public void println() {
        writer.println();
        writer.flush();
    }
}
