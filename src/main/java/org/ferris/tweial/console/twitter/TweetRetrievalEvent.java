package org.ferris.tweial.console.twitter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import twitter4j.Status;

/**
 * This event supports the data needed during the tweet retrieval process 
 * to determine which tweets, if any, will emailed to the user.
 * 
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TweetRetrievalEvent {
    private List<Status> tweetsFromLastRun;
    private List<Status> tweetsFromTwitter;
    private List<Status> newTweetsFromThisRun;
    
    /** 
     * Store the list of tweets found during the last time Tweial ran. A
     * reference to the {@code tweetsFromLastRun} property is NOT kept. A
     * new {@link List}&lt;{@link Status}&gt; is created and all the 
     * {@link Status} objects in the {@code tweetsFromLastRun} parameter are
     * copied into the new {@link List}
     * 
     * @param tweetsFromLastRun No validation on its value
     */
    public void setTweetsFromLastRun(List<Status> tweetsFromLastRun) {
        this.tweetsFromLastRun = new LinkedList<Status>();
        this.tweetsFromLastRun.addAll(tweetsFromLastRun);
    }
    
    
    /** 
     * Store the list of tweets from twitter found during this run. A
     * reference to the {@code tweetsFromTwitter} property is NOT kept. A
     * new {@link List}&lt;{@link Status}&gt; is created and all the 
     * {@link Status} objects in the {@code tweetsFromTwitter} parameter are
     * copied into the new {@link List}
     * 
     * @param tweetsFromTwitter No validation on its value
     */
    public void setTweetsFromTwitter(List<Status> tweetsFromTwitter) {
        this.tweetsFromTwitter = new LinkedList<Status>();
        this.tweetsFromTwitter.addAll(tweetsFromTwitter);
    }
    
    
    /** 
     * Store the list of tweets from twitter found during this run which
     * were NOT found during a previous run of Tweial.  See the 
     * {@link TweetFilter} which actually produces this list. A
     * reference to the {@code newTweetsFromThisRun} property is NOT kept. A
     * new {@link List}&lt;{@link Status}&gt; is created and all the 
     * {@link Status} objects in the {@code newTweetsFromThisRun} parameter are
     * copied into the new {@link List}
     * 
     * @param newTweetsFromThisRun No validation on its value
     */
   
    public void setNewTweetsFromThisRun(List<Status> newTweetsFromThisRun) {
        this.newTweetsFromThisRun = new LinkedList<Status>();
        this.newTweetsFromThisRun.addAll(newTweetsFromThisRun);
    }

    
    /**
     * Return an unmodifiable {@link List}&lt;{@link Status}&gt;.
     * 
     * @return 
     *  A {@link List}&lt;{@link Status}&gt; is always returned, never
     *  {@code null}.  An empty {@link List}&lt;{@link Status}&gt; is
     *  returned in place of {@code null}.
     */
    public List<Status> getTweetsFromLastRun() {
        return (tweetsFromLastRun == null) ? Collections.emptyList() : Collections.unmodifiableList(tweetsFromLastRun);
    }

    
    /**
     * Return an unmodifiable {@link List}&lt;{@link Status}&gt;.
     * 
     * @return 
     *  A {@link List}&lt;{@link Status}&gt; is always returned, never
     *  {@code null}.  An empty {@link List}&lt;{@link Status}&gt; is
     *  returned in place of {@code null}.
     */
    public List<Status> getTweetsFromTwitter() {
        return (tweetsFromTwitter == null) ? Collections.emptyList() : Collections.unmodifiableList(tweetsFromTwitter);
    }
    
    
    /**
     * Return an unmodifiable {@link List}&lt;{@link Status}&gt;.
     * 
     * @return 
     *  A {@link List}&lt;{@link Status}&gt; is always returned, never
     *  {@code null}.  An empty {@link List}&lt;{@link Status}&gt; is
     *  returned in place of {@code null}.
     */
    public List<Status> getNewTweetsFromThisRun() {
        return (newTweetsFromThisRun == null) ? Collections.emptyList() : Collections.unmodifiableList(newTweetsFromThisRun);
    }
}
