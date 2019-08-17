package org.ferris.tweial.console.email;

import java.util.Set;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.io.Console;
import org.ferris.tweial.console.text.i18n.LocalizedString;
import org.ferris.tweial.console.text.i18n.LocalizedStringKey;
import org.ferris.tweial.console.view.page.AbstractPage;
import org.jboss.weld.experimental.Priority;

/**
 * This is the console view for email related information.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailPage extends AbstractPage {

    @Inject
    protected Console console;

    @Inject
    protected Logger log;

    @Inject
    @LocalizedStringKey("EmailPage.DataSourceMissing")
    protected LocalizedString dataSourceMissing;

    @Inject
    @LocalizedStringKey("EmailPage.AccountDataMissing")
    protected LocalizedString accountDataMissing;

    @Inject
    @LocalizedStringKey("EmailPage.Heading")
    protected LocalizedString heading;

    public void viewDataSourceMissing(String description) {
        log.info("Email account configuration file is missing");
        console.h1(heading);
        console.p(dataSourceMissing, description);
    }

    public void viewEmailAccountMissing(Set<ConstraintViolation<EmailAccount>> violations) {
        log.info("Email account data is missing");
        console.h1(heading);
        console.p(accountDataMissing);
        console.print(violations);
    }
    
    public void render(
        @Observes @Priority(EmailSendPriority.PRINT_EMAIL_MESSAGE)
        EmailSendEvent evnt
    ) {
        log.info("Email message:");
        log.info(evnt.getMessage());
        console.p(new LocalizedString("See log file for email message"));
    }
}
