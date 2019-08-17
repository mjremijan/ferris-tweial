/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.io;

import java.io.File;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 * This is a Runnable implementation given to a ScheduledExecutorService to
 * watch a file's lastModified property to detect if the file has changed. If
 * so, a FileLastModifiedEvent event is fired.
 *
 * This class is @Dependent scope so each injection point should get its own
 * instances. Hence, once injected into your bean it should be safe to call the
 * #setFile() method then hand this off to a ScheduledExecutorService.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Dependent
public class FileLastModifiedRunnable implements Runnable {

    @Inject
    protected Logger log;

    @Inject
    protected Event<FileLastModifiedEvent> event;

    protected File file;
    protected long lastModifiedThen;

    /**
     * Set the "file" and "lastModifiedThen" object properties
     *
     * @param f No error checking is performed.
     */
    protected FileLastModifiedRunnable(File f) {
        file = f;
        lastModifiedThen = file.lastModified();
    }

    /**
     * Basic getter
     *
     * @return Object property "file"
     */
    public File getFile() {
        return file;
    }

    /**
     * Compares {@link File#lastModified()} with object property
     * {@link #lastModifiedThen} and if there is a difference, fire a
     * {@link FileLastModifiedEvent}
     */
    @Override
    public void run() {
        log.debug(String.format("Checking lastModified for file \"%s\"", file.getAbsolutePath()));
        long lastModifiedNow = file.lastModified();
        if (lastModifiedNow != lastModifiedThen) {
            log.warn(String.format("Modification detected for file \"%s\"", file.getAbsolutePath()));
            FileLastModifiedEvent event
                    = new FileLastModifiedEvent(lastModifiedThen, lastModifiedNow, file);
            lastModifiedThen = lastModifiedNow;
            this.event.fire(event);
        }
    }
}
