package org.ferris.tweial.console.exit;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.lang.SystemTool;

public class ExitObserver {

    @Inject
    protected Logger log;

    @Inject
    protected SystemTool systemTool;

    @Inject
    protected ExitPage exitPage;

    public void observes(@Observes ExitEvent exitEvent) {
        log.info("View page");
        exitPage.view();
        log.info("Exit JVM");
        systemTool.exitAbnormal();
    }

}
