/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.io;

import java.io.File;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FileLastModifiedEvent {

    private long lastModifiedThen;
    private long lastModifiedNow;
    private File File;

    public FileLastModifiedEvent(long lastModifiedThen, long lastModifiedNow, File File) {
        this.lastModifiedThen = lastModifiedThen;
        this.lastModifiedNow = lastModifiedNow;
        this.File = File;
    }

    public long getLastModifiedThen() {
        return lastModifiedThen;
    }

    public long getLastModifiedNow() {
        return lastModifiedNow;
    }

    public File getFile() {
        return File;
    }
}
