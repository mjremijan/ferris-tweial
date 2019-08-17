/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.exception;

import java.lang.Thread.UncaughtExceptionHandler;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.exit.ExitEvent;
import org.ferris.tweial.console.main.StartupEvent;
import org.ferris.tweial.console.main.StartupPriority;
import org.ferris.tweial.console.lang.ThreadTool;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class UncaughtExceptionObserver implements UncaughtExceptionHandler {

    @Inject
    protected Logger log;

    @Inject
    protected ThreadTool threadTool;

    @Inject
    protected UncaughtExceptionPage uncaughtExceptionPage;

    @Inject
    protected Event<ExitEvent> exitEvent;

    public void observes(
            @Observes @Priority(StartupPriority.EXCEPTION) StartupEvent event
    ) {
        log.info("UncaughtExceptionObserver startup configuration observer");
        threadTool.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        uncaughtExceptionPage.view(e);
        exitEvent.fire(new ExitEvent());
    }
}
