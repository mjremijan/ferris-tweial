package org.ferris.tweial.console.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesFile extends File {

    private static final long serialVersionUID = 187767656554508796L;

    public PropertiesFile(File absolutePathOfFile) {
        super(absolutePathOfFile.getAbsolutePath());
    }
    
    public PropertiesFile(File parent, String relativePathOfFile) {
        super(parent, relativePathOfFile);
    }

    protected InputStream getInputStream() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(this);
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format(
                            "Problem getting InputStream to file \"%s\"", String.valueOf(this)), e
            );
        }
        return fis;
    }

    protected OutputStream getOutputStream() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(this);
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format(
                            "Problem getting OutputStream to file \"%s\"", String.valueOf(this)), e
            );
        }
        return fos;
    }

    protected Properties getProperties() {
        try (InputStream is = getInputStream()) {
            Properties p = new Properties();
            p.load(is);
            return p;
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format(
                            "Problem loading properties from file \"%s\"", String.valueOf(this)), e
            );
        }

    }

    /**
     * Find and return a property
     * 
     * @param key The key of the {@link Properties}. Assumed OK, not checked.
     * @param defaultValue Default value if property not found. Assumed OK, not checked.
     * 
     * @return Either the value found by the key or the defaultValue.
     */
    public String find(String key, String defaultValue) {
        Properties props
                = getProperties();
        return props.getProperty(key, defaultValue);
    }

    /**
     * Store a property, write properties file to disc.
     * 
     * @param key The key of the {@link Properties}. Assumed OK, not checked.
     * @param value The value of the {@link Properties}. Assumed OK, not checked.
     */
    public void persist(String key, String value) {
        Properties props = getProperties();
        props.setProperty(key, value);

        try (OutputStream os = getOutputStream()) {
            props.store(os, "");
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format(
                            "Problem storing properties in file \"%s\"", String.valueOf(this)), e);
        }
    }
}
