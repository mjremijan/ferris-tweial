package org.ferris.tweial.console.exit;

import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.io.Console;
import org.ferris.tweial.console.text.i18n.LocalizedString;
import org.ferris.tweial.console.text.i18n.LocalizedStringKey;
import org.ferris.tweial.console.view.page.AbstractPage;

public class ExitPage extends AbstractPage {

    @Inject
    protected Console console;

    @Inject
    protected Logger log;

    @Inject
    @LocalizedStringKey("ExitPage.Heading")
    protected LocalizedString heading;

    @Inject
    @LocalizedStringKey("ExitPage.Goodbye")
    protected LocalizedString goodbye;

    public void view() {
        log.info("Application exiting");
        console.h1(heading);
        console.p(goodbye);
    }

}
