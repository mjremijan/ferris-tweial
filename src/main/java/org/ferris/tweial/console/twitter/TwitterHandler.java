package org.ferris.tweial.console.twitter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.preferences.PreferencesHandler;
import org.ferris.tweial.console.retry.ExceptionRetry;
import org.jboss.weld.experimental.Priority;
import twitter4j.Status;
import twitter4j.Twitter;

/**
 * This class handles dealing with Twitter data
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TwitterHandler {

    @Inject
    protected Logger log;

    @Inject
    protected TwitterPropertiesFile twitterProperties;

    @Inject
    protected TwitterCacheFile twitterCache;

    @Inject
    protected ValidatorFactory validatorFactory;

    @Inject
    protected TwitterBuilder twitterBuilder;

    @Inject
    protected PreferencesHandler preferencesHandler;

    /**
     * Return {@link TwitterAccount} instance.
     *
     * @return A {@link TwitterAccount} instance is returned. Never
     * returns <code>null</code>. If no authentication data is found, a
     * {@link TwitterAccount} instance is returned with all its
     * properties set to null.
     */
    public TwitterAccount getTwitterAccount() {
        log.info("Create TwitterAccount from properties");
        return new TwitterAccount(twitterProperties.toProperties());
    }

    /**
     * Validates the Twitter authentication data and returns the constraints. If
     * there are no constraint violations, an empty set is returned.
     *
     * @param validateMe The {@link TwitterAccount} to validate

     * @return Returns constraint violations or an empty set if none.
     */
    public Set<ConstraintViolation<TwitterAccount>> validate(TwitterAccount validateMe) {
        log.info("Validate the TwitterAccount object");
        Set<ConstraintViolation<TwitterAccount>> violations
                = validatorFactory.getValidator().validate(validateMe);
        return violations;
    }

    /**
     * The purpose of this method is to provide the application an abstraction
     * about the source of the twitter data. The specifics of what this source
     * is doesn't matter (database, file, ldap, etc.).
     *
     * @return A {@link TwitterDataSource}.
     */
    public TwitterDataSource getTwitterDataSource() {
        log.info("Get the Twitter data source");
        String desc = twitterProperties.getAbsolutePath();
        boolean exists = twitterProperties.exists();
        return new TwitterDataSource(desc, exists);
    }

    /**
     * Get the status from your twitter home timeline.
     *
     * @param event This method responsible for calling
     * {@link TweetRetrievalEvent#setTweetsFromTwitter(java.util.List)}
     */
    @ExceptionRetry
    protected void loadTweetsFromTwitter(@Observes @Priority(TweetRetrievalPriority.LOAD_TWEETS_FROM_TWITTER) TweetRetrievalEvent event) {
        log.info("Attempt to load the Twitter home timeline");

        Twitter twitter = twitterBuilder
            .setAccount(getTwitterAccount())
            .build();

        List<Status> retval = null;
        try {
            Long id = preferencesHandler.findPreferences().getGetOnlyThisTweetId();
            if (id == -1) {
                retval = twitter.getHomeTimeline();
            } else {
                retval = new LinkedList<>();
                Collections.addAll(retval, twitter.showStatus(id));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failure getting Twitter home timeline", e);
        }
        if (retval == null) {
            retval = Collections.emptyList();
        }

        event.setTweetsFromTwitter(retval);
    }


    /**
     * Get the tweets Tweial found during its last run and load them into the
     * {@link TweetRetrievalEvent} parameter
     *
     * @param event No validation performed on this parameter.
     */
    protected void loadTweetsFromLastRun(@Observes @Priority(TweetRetrievalPriority.LOAD_TWEETS_FROM_LAST_RUNS) TweetRetrievalEvent event) {
        log.info("Load tweets from the last run");
        event.setTweetsFromLastRun(twitterCache.get());
    }


    /**
     * From the {@link TweetRetrievalEvent} parameter, get the list of
     * new tweets Tweial found during this run and store those tweets so
     * that the next time Tweial runs it knows to filter them out.
     *
     * @param event No validation performed on this parameter.
     */
    protected void saveNewTweetsFromThisRun(@Observes @Priority(TweetRetrievalPriority.SAVE_NEW_TWEETS_FROM_THIS_RUN) TweetRetrievalEvent event) {
        log.info("Save tweets not in the last run");
        twitterCache.addAll(event.getNewTweetsFromThisRun());
    }


    /**
     * Remove from Tweial's tweet store the tweets which are more than 7
     * days old.  7 is hard coded and cannot be changed.
     *
     * @param event This parameter is not used.
     */
    protected void removeTweetsThatAreTooOld(@Observes @Priority(TweetRetrievalPriority.REMOVE_TWEETS_FROM_LAST_RUNS_THAT_ARE_TOO_OLD) TweetRetrievalEvent event) {
        log.info("Remote tweets that are too old");
        twitterCache.vacuum(7);
    }
}
