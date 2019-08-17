/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.io;

import org.ferris.tweial.console.io.FileLastModifiedEvent;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FileLastModifiedEventTest {
    
    @Test
    public void test_all() {
        File target = new File("target");
        FileLastModifiedEvent event
            = new FileLastModifiedEvent(14L, 15L, target);
        
        Assert.assertEquals(14L, event.getLastModifiedThen());
        Assert.assertEquals(15L, event.getLastModifiedNow());
        Assert.assertEquals(target, event.getFile());
    }
    
}
