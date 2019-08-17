package org.ferris.tweial.console.twitter;

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
 * This class is an event observer for Twitter related actions.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TwitterStartupObserver {

    @Inject
    protected Logger log;

    @Inject
    protected TwitterHandler handler;

    @Inject
    protected TwitterPage page;

    @Inject
    protected Event<ExitEvent> exitEvent;

    /**
     * Observe the {@link StartupEvent} so the application can verify the information needed for the source of
     * the Twitter authentication data exists. This can either be in a properties file, a database, or an LDAP
     * server. It really doesn't matter, just as long as the source of the Twitter authentication data exits.
     *
     * @param event Not used.
     */
    public void twitterDataSourceVerification(
            @Observes @Priority(StartupPriority.TWITTER_DATASOURCE_VERIFICATION) StartupEvent event
    ) {
        log.info("Twitter data source verification");
        TwitterDataSource dataSource = handler.getTwitterDataSource();
        if (!dataSource.exists()) {
            page.viewDataSourceMissing(dataSource.getDescription());
            exitEvent.fire(new ExitEvent());
        }
    }

    /**
     * Observe the {@link StartupEvent} so the application can verify the Twitter account data itself.
     *
     * @param event Not used.
     */
    public void twitterAccountVerification(
            @Observes @Priority(StartupPriority.TWITTER_ACCOUNT_VERIFICATION) StartupEvent event
    ) {
        log.info("Twitter account verification");
        TwitterAccount auth = handler.getTwitterAccount();
        Set<ConstraintViolation<TwitterAccount>> violations
                = handler.validate(auth);
        if (!violations.isEmpty()) {
            page.viewTwitterAccountMissing(violations);
            exitEvent.fire(new ExitEvent());
        }
    }
}
