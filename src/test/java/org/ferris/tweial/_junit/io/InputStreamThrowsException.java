package org.ferris.tweial._junit.io;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan Remijan mjremijan@yahoo.com @mjremijan
 */
public class InputStreamThrowsException extends InputStream {

    @Override
    public int read(byte b[]) throws IOException {
        throw new IOException("Spy exception");
    }
    
    @Override
    public int read() throws IOException {
        throw new IOException("Spy exception");
    }
    
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        throw new IOException("Spy exception");
    }
}
