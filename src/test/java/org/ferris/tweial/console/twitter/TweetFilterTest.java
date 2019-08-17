package org.ferris.tweial.console.twitter;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.preferences.Preferences;
import org.ferris.tweial.console.preferences.PreferencesHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.Status;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class TweetFilterTest {

    @Mock
    Logger logMock;

    @Mock
    PreferencesHandler preferencesHandlerMock;

    TweetFilter filter;
    List<Status> tweetsFromLastRun;
    List<Status> tweetsFromTwitter;

    @Before
    public void beforeTweetFilter() {
        // PreferencesHandler
        Preferences preferencesMock = Mockito.mock(Preferences.class);
        Mockito.when(preferencesMock.getGetOnlyThisTweetId()).thenReturn(Long.valueOf(-1L));
        Mockito.when(preferencesHandlerMock.findPreferences()).thenReturn(preferencesMock);

        filter = new TweetFilter();
        filter.log = logMock;
        filter.preferencesHandler = preferencesHandlerMock;
    }

    @Before
    public void beforeTweetsFromLastRun() {
        tweetsFromLastRun = new LinkedList<>();
        {
            Status s = Mockito.mock(Status.class);
            Mockito.when(s.getId()).thenReturn(1L);
            tweetsFromLastRun.add(s);
        }
        {
            Status s = Mockito.mock(Status.class);
            Mockito.when(s.getId()).thenReturn(2L);
            tweetsFromLastRun.add(s);
        }
    }

    @Before
    public void beforeTweetsFromTwitter() {
        tweetsFromTwitter = new LinkedList<>(); {
            Status s = Mockito.mock(Status.class);
            Mockito.when(s.getId()).thenReturn(1L);
            tweetsFromTwitter.add(s);
        }{
            Status s = Mockito.mock(Status.class);
            Mockito.when(s.getId()).thenReturn(2L);
            tweetsFromTwitter.add(s);
        }{
            Status s = Mockito.mock(Status.class);
            Mockito.when(s.getId()).thenReturn(3L);
            tweetsFromTwitter.add(s);
        }
    }

    @Test
    public void testFilteringCorrectly() {
        // setup
        TweetRetrievalEvent event = new TweetRetrievalEvent();
        event.setTweetsFromLastRun(tweetsFromLastRun);
        event.setTweetsFromTwitter(tweetsFromTwitter);

        // action
        filter.filterTweets(event);

        // assert
        Assert.assertNotNull(event.getNewTweetsFromThisRun().size());
        Assert.assertEquals(1, event.getNewTweetsFromThisRun().size());
        Assert.assertEquals(3L, event.getNewTweetsFromThisRun().get(0).getId());
    }
}
