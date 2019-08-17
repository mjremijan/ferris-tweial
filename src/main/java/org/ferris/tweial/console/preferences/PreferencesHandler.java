package org.ferris.tweial.console.preferences;

import java.util.Collections;
import java.util.List;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.lang.IntegerTool;
import org.ferris.tweial.console.lang.LongTool;
import org.ferris.tweial.console.twitter.TweetRetrievalEvent;
import org.ferris.tweial.console.twitter.TweetRetrievalPriority;
import org.ferris.tweial.console.util.PropertiesFile;
import org.jboss.weld.experimental.Priority;
import twitter4j.Status;

/**
 * This class is responsible for handling {@link Preferences} objects
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Singleton
public class PreferencesHandler {
    @Inject
    protected Logger log;

    @Inject
    protected PreferencesPropertiesFile file;

    /**
     * Return the user's {@link Preferences}.  If for some reason
     * the preferences cannot be returned, a {@link Preferences}
     * object with hard-coded default values is returned.
     *
     * @return {@link Preferences} object. Never null.
     */
    public Preferences findPreferences() {
        PropertiesFile props
            = new PropertiesFile(file);

        Preferences prefs
            = new Preferences();

        // sendOnlyIfTweetsGreaterThan
        prefs.setSendOnlyIfTweetsGreaterThan(
            IntegerTool.parseInt(props.find("sendOnlyIfTweetsGreaterThan", null), 0)
        );

        // getOnlyThisTweetId
        prefs.setGetOnlyThisTweetId(
            LongTool.parseLong(props.find("getOnlyThisTweetId", null), -1)
        );

        // noImageUrl
        prefs.setNoImageUrl(
            props.find("noImageUrl", "https://icon-library.net/images/no-image-available-icon/no-image-available-icon-6.jpg")
        );

        return prefs;
    }


    /**
     * Check if the the number of new tweets found in this run is greater
     * than the minimum number needed to send the email.  If not, short circut
     * the rest of the processes by setting the number of new tweets found
     * in this run to be 0.
     *
     * {@link TweetRetrievalEvent} parameter
     *
     * @param event No validation performed on this parameter.
     */
    protected void checkIfThereAreEnoughNewTweets(
        @Observes @Priority(TweetRetrievalPriority.CHECK_NEW_TWEETS_FROM_THIS_RUN_AGAINST_PREFERENCES) TweetRetrievalEvent event) {
        log.info("Check new tweets from this run against preferences");

        if (findPreferences().getGetOnlyThisTweetId() == -1)
        {
            List<Status> newTweetsFromThisRun
                = event.getNewTweetsFromThisRun();

            Integer i = findPreferences().getSendOnlyIfTweetsGreaterThan();
            if (newTweetsFromThisRun.size() <= i) {
                log.info(String.format(
                    "Number of new tweets in this run %d is not greater then preference %d"
                    , newTweetsFromThisRun.size()
                    , i.intValue()));
                event.setNewTweetsFromThisRun(Collections.emptyList());
            }
        }
    }
}
