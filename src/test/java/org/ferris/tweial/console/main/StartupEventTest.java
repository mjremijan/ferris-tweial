/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.main;

import org.ferris.tweial.console.main.StartupEvent;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class StartupEventTest {
    
    @Test
    public void test_all() {
        StartupEvent event
            = new StartupEvent();
        Assert.assertNotNull(event);
    }
    
}
