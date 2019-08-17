/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.log4j;

import java.io.File;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.configuration.ConfigurationDirectory;
import org.ferris.tweial.console.io.FileLastModifiedEvent;
import org.ferris.tweial.console.io.FileLastModifiedRunnable;
import org.ferris.tweial.console.log4j.Log4jLogManagerTool;
import org.ferris.tweial.console.log4j.Log4jPropertyConfiguratorTool;

/**
 * This class is responsible for watching the Log4j configuration file and
 * automatically reconfiguring Log4j if the configuration file changes.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Dependent
public class Log4jReconfigure extends FileLastModifiedRunnable {

    @Inject
    protected Logger log;

    @Inject
    protected Log4jLogManagerTool logManagerTool;

    @Inject
    protected Log4jPropertyConfiguratorTool propertyConfiguratorTool;

    @Inject
    public Log4jReconfigure(ConfigurationDirectory confdir) {
        super(new File(confdir, "log4j.properties"));
    }

    protected void observeFileLastModifiedEvent(@Observes FileLastModifiedEvent event) {
        File file = event.getFile();
        if (file.getName().equalsIgnoreCase("log4j.properties")) {
            log.warn(String.format("Reconfiguring Log4j with file \"%s\"", file.getAbsolutePath()));
            logManagerTool.resetConfiguration();
            propertyConfiguratorTool.configure(file.getAbsolutePath());
        }
    }
}
