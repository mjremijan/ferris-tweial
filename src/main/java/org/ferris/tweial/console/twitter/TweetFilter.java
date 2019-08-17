package org.ferris.tweial.console.twitter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.preferences.PreferencesHandler;
import org.jboss.weld.experimental.Priority;
import twitter4j.Status;

/**
 * The purpose of this class is to filter {@link List} objects of tweets
 * from Twitter.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TweetFilter {

    @Inject
    protected Logger log;

    @Inject
    protected PreferencesHandler preferencesHandler;

    /**
     * Use the data in the {@link TweetRetrievalEvent} {@code event} parameter to
     * filter out the tweets from Twitter that we have already seen. Set a new
     * list in the {@link TweetRetrievalEvent} {@code event} parameter with the
     * new tweets found in this run, in other words, all the tweets left over
     * after filtering out the ones we have already seen in previous runs.
     *
     * @param event The {@link TweetRetrievalEvent} this method is responsible
     * for calling {@link TweetRetrievalEvent#setNewTweetsFromThisRun(java.util.List)}
     */
    protected void filterTweets(
        @Observes @Priority(TweetRetrievalPriority.FIND_NEW_TWEETS_FROM_THIS_RUN)
        TweetRetrievalEvent event
    ) {
        log.info("Filter out tweets we've already seen");

        List<Status> newTweetsFromThisRun;

        if (preferencesHandler.findPreferences().getGetOnlyThisTweetId() == -1)
        {
            List<Long> idsFromLastRun
                = event.getTweetsFromLastRun().stream().map(t -> t.getId()).collect(Collectors.toList());

            newTweetsFromThisRun
                = new LinkedList<>(event.getTweetsFromTwitter());
            newTweetsFromThisRun.removeIf(t -> idsFromLastRun.contains(t.getId()));
        } else {
            newTweetsFromThisRun
                = new LinkedList<>(event.getTweetsFromTwitter());
        }
        event.setNewTweetsFromThisRun(newTweetsFromThisRun);
    }

}
