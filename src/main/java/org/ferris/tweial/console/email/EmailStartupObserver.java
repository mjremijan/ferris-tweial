package org.ferris.tweial.console.email;

import java.util.Set;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.exit.ExitEvent;
import org.ferris.tweial.console.main.StartupEvent;
import org.ferris.tweial.console.main.StartupPriority;
import org.jboss.weld.experimental.Priority;

/**
 * This class is an event observer for email related actions.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailStartupObserver {

    @Inject
    protected Logger log;

    @Inject
    protected EmailHandler handler;

    @Inject
    protected EmailPage page;

    @Inject
    protected Event<ExitEvent> exitEvent;

    /**
     * Observe the {@link StartupEvent} so the email datasource can be verified.
     * This process just checks to see that whatever is holding the email
     * data exists and is accessible.  It doesn't matter what is actually
     * holding the data (database, flat file, ldap, MongoDB, etc.) and it
     * does not matter if the data is actually in the data source. All this 
     * verification checks for is that the data source exists and is accessible
     *
     * @param event The {@link StartupEvent} which is not used by this method.
     */
    public void emailDataSourceVerification(
        @Observes @Priority(StartupPriority.EMAIL_DATASOURCE_VERIFICATION) 
        StartupEvent event
    ){
        log.info("Email data source verification");
        EmailDataSource dataSource = handler.getEmailAccountDataSource();
        if (!dataSource.exists()) {
            page.viewDataSourceMissing(dataSource.getDescription());
            exitEvent.fire(new ExitEvent());
        }
    }

    
    /**
     * Observes the {@link StartupEvent} so that the email account data can 
     * be verified. This is basic verification that the properties exist and 
     * have some reasonable checks on their values.  It does NOT verify that
     * the email account actually exists or that the account data can be used
     * to successfully send and email.
     * 
     * @param event he {@link StartupEvent} which is not used by this method.
     */
    public void emailAccountVerification(
        @Observes @Priority(StartupPriority.EMAIL_ACCOUNT_VERIFICATION) 
        StartupEvent event
    ){
        log.info("Email account verification");
        EmailAccount account = handler.getEmailAccount();
        Set<ConstraintViolation<EmailAccount>> violations = handler.validate(account);
        if (!violations.isEmpty()) {
            page.viewEmailAccountMissing(violations);
            exitEvent.fire(new ExitEvent());
        }
    }
}
