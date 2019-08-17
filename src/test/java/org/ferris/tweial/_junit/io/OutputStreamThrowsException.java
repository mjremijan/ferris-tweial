package org.ferris.tweial._junit.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan Remijan mjremijan@yahoo.com @mjremijan
 */
public class OutputStreamThrowsException extends OutputStream {
    
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        throw new IOException("Spy exception");
    }
    
    @Override
    public void write(byte[] b) throws IOException {
        throw new IOException("Spy exception");
    }
    
    @Override
    public void write(int b) throws IOException {
        throw new IOException("Spy exception");
    }

}
