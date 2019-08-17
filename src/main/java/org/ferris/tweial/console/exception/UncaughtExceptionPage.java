package org.ferris.tweial.console.exception;

import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.io.Console;
import org.ferris.tweial.console.text.i18n.LocalizedString;
import org.ferris.tweial.console.text.i18n.LocalizedStringKey;
import org.ferris.tweial.console.view.page.AbstractPage;

public class UncaughtExceptionPage extends AbstractPage {

    @Inject
    protected Logger log;

    @Inject
    protected Console console;

    @Inject
    @LocalizedStringKey("UncaughtExceptionPage.Heading")
    protected LocalizedString heading;

    @Inject
    @LocalizedStringKey("UncaughtExceptionPage.Opps")
    protected LocalizedString opps;

    public void view(Throwable e) {
        log.fatal(e.getMessage(), e);

        console.h1(heading);
        console.p(opps);
        console.p(e);
    }
}
