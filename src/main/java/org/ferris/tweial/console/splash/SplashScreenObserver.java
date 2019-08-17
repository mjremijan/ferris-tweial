package org.ferris.tweial.console.splash;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.main.StartupEvent;
import org.ferris.tweial.console.main.StartupPriority;
import org.jboss.weld.experimental.Priority;

public class SplashScreenObserver {

    @Inject
    protected Logger log;

    @Inject
    protected SplashScreenPage splashScreenPage;

    public void observes(
            @Observes @Priority(StartupPriority.SPASH_SCREEN) StartupEvent event
    ) {
        log.info("SplashScreen startup configuration observer");
        splashScreenPage.view();
    }
}
