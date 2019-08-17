/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.log4j;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.main.StartupEvent;
import org.ferris.tweial.console.main.StartupPriority;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Log4jConfigurationObserver {

    @Inject
    protected Logger log;

    @Inject
    protected ScheduledExecutorService scheduledExecutorService;

    @Inject
    protected Log4jReconfigure reconfigureLog4j;

    public void observes(
            @Observes @Priority(StartupPriority.LOG4J) StartupEvent event
    ) {
        log.info("Log4jConfiguration startup configuration observer");
        scheduledExecutorService.scheduleAtFixedRate(reconfigureLog4j, 10, 10, TimeUnit.SECONDS);
    }
}
