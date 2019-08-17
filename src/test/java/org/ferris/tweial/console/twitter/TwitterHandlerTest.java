/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.twitter;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.preferences.Preferences;
import org.ferris.tweial.console.preferences.PreferencesHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class TwitterHandlerTest {

    @Mock
    Logger logMock;

    @Mock
    TwitterPropertiesFile twitterPropertiesMock;

    @Mock
    TwitterCacheFile twitterCacheMock;

    @Mock
    ValidatorFactory validatorFactoryMock;

    @Mock
    Validator validatorMock;

    @Spy
    TwitterBuilder twitterBuilderSpy;

    @Mock
    PreferencesHandler preferencesHandlerMock;

    TwitterHandler handler;

    @Before
    public void before() {
        // ValidatorFactory
        Mockito.when(validatorFactoryMock.getValidator()).thenReturn(validatorMock);

        // PreferencesHandler
        Preferences preferencesMock = Mockito.mock(Preferences.class);
        Mockito.when(preferencesMock.getGetOnlyThisTweetId()).thenReturn(Long.valueOf(-1L));
        Mockito.when(preferencesHandlerMock.findPreferences()).thenReturn(preferencesMock);

        // TwitterProperties
        Properties props = new Properties();
        {
            props.setProperty("oAuthConsumerKey", "hello");
            props.setProperty("oAuthConsumerSecret", "doctor");
            props.setProperty("oAuthAccessToken", "name");
            props.setProperty("oAuthAccessTokenSecret", "continue");
            Mockito.when(twitterPropertiesMock.toProperties()).thenReturn(props);
        }

        // handler
        handler = Mockito.spy(new TwitterHandler());
        handler.log = logMock;
        handler.twitterProperties = twitterPropertiesMock;
        handler.twitterCache = twitterCacheMock;
        handler.validatorFactory = validatorFactoryMock;
        handler.twitterBuilder = twitterBuilderSpy;
        handler.preferencesHandler = preferencesHandlerMock;
    }

    @Test
    public void keep(){}

    @Test
    public void test_getting_twitter_account() {
        // setup

        // action
        TwitterAccount auth
            = handler.getTwitterAccount();

        // assert
        Assert.assertNotNull(auth);
        Assert.assertEquals("hello", auth.getConsumerKey());
        Assert.assertEquals("doctor", auth.getConsumerSecret());
        Assert.assertEquals("name", auth.getAccessToken());
        Assert.assertEquals("continue", auth.getAccessTokenSecret());
    }


    @Test
    public void test_validate() {
        // setup
        // TwitterAccount object
        TwitterAccount auth
            = Mockito.mock(TwitterAccount.class);
        // Create Set of ConstraintViolations to return
        Set<ConstraintViolation<TwitterAccount>> expected
            = new HashSet<ConstraintViolation<TwitterAccount>>();
        {
            // Add a violation to the set.
            expected.add(Mockito.mock(ConstraintViolation.class));

            // Validator retuns set with 1 violation on validate
            Mockito.when(validatorMock.validate(auth)).thenReturn(expected);
        }

        // action
        Set<ConstraintViolation<TwitterAccount>> actual = handler.validate(auth);

        // assert
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void test_twitter_data_source() {
        // setup
        Mockito.when(twitterPropertiesMock.getAbsolutePath()).thenReturn("/junit/mock/shrubbery.dat");
        Mockito.when(twitterPropertiesMock.exists()).thenReturn(Boolean.TRUE);

        // action
        TwitterDataSource ds = handler.getTwitterDataSource();

        // assert
        Assert.assertEquals("/junit/mock/shrubbery.dat", ds.getDescription());
        Assert.assertTrue(ds.exists());
    }


    @Test
    public void test_load_EMPTY_tweets_from_twitter_without_exceptions() throws Exception {
        // setup
        // - mock the Twitter class from Twitter4j because this class
        //   is ultimately what is returned from TwitterBuilder
        Twitter twitterMock = Mockito.mock(Twitter.class);

        //Mockito.when(twitterMock.getHomeTimeline()).thenReturn(Mockito.mock(ResponseList.class));
        // - the TwitterBuilder spy can do most of the work
        //   but have build() return the Twitter mock.
        Mockito.doReturn(twitterMock).when(twitterBuilderSpy).build();
        // - mock TweetRunEvent to verify its setter method is called.
        TweetRetrievalEvent event = Mockito.mock(TweetRetrievalEvent.class);

        // action
        handler.loadTweetsFromTwitter(event);

        // assert
        Mockito.verify(twitterMock, Mockito.times(1)).getHomeTimeline();
        Mockito.verify(event, Mockito.times(1)).setTweetsFromTwitter(Collections.emptyList());
    }


    @Test
    public void test_load_NONEMPTY_tweets_from_twitter_without_exceptions() throws Exception {
        // setup
        // - mock the Twitter class from Twitter4j because this class
        //   is ultimately what is returned from TwitterBuilder
        Twitter twitterMock = Mockito.mock(Twitter.class);
        ResponseList<Status> rl = Mockito.mock(ResponseList.class);
        Mockito.when(twitterMock.getHomeTimeline()).thenReturn(rl);

        // - the TwitterBuilder spy can do most of the work
        //   but have build() return the Twitter mock.
        Mockito.doReturn(twitterMock).when(twitterBuilderSpy).build();
        // - mock TweetRunEvent to verify its setter method is called.
        TweetRetrievalEvent event = Mockito.mock(TweetRetrievalEvent.class);

        // action
        handler.loadTweetsFromTwitter(event);

        // assert
        Mockito.verify(twitterMock, Mockito.times(1)).getHomeTimeline();
        Mockito.verify(event, Mockito.times(1)).setTweetsFromTwitter(rl);
    }


    @Test(expected = RuntimeException.class)
    public void test_load_tweets_from_twitter_with_exceptions() {
        // setup
        // - mock the Twitter class from Twitter4j because this class
        //   is ultimately what is returned from TwitterBuilder. Simulate
        //   throwing an exception when getting the timeline.
        Twitter twitterMock = Mockito.mock(Twitter.class);
        try {
            Mockito.doThrow(new TwitterException("oops!")).when(twitterMock).getHomeTimeline();
        } catch (TwitterException e) {
            Assert.fail("TwitterException should never be caught here.");
        }
        // - the TwitterBuilder spy can do most of the work
        //   but have build() return the Twitter mock.
        Mockito.doReturn(twitterMock).when(twitterBuilderSpy).build();
        // - mock TweetRunEvent to verify its setter method is called.
        TweetRetrievalEvent event = Mockito.mock(TweetRetrievalEvent.class);

        // action
        handler.loadTweetsFromTwitter(event);

        // assert
        // - RuntimeException expected
    }


    @Test
    public void test_load_tweets_from_last_run() {
        // setup
        List<Status> expected = new LinkedList<Status>();
        Mockito.when(twitterCacheMock.get()).thenReturn(expected);
        TweetRetrievalEvent event = new TweetRetrievalEvent();

        // action
        handler.loadTweetsFromLastRun(event);

        // assert
        Assert.assertEquals(expected, event.getTweetsFromLastRun());
    }


    @Test
    public void test_save_new_tweets_from_this_run() {
        // setup
        List<Status> expected = new LinkedList<Status>();
        TweetRetrievalEvent event = new TweetRetrievalEvent();
        event.setNewTweetsFromThisRun(expected);

        // action
        handler.saveNewTweetsFromThisRun(event);

        // assert
        Mockito.verify(twitterCacheMock, Mockito.times(1)).addAll(expected);
    }


    @Test
    public void test_remove_tweets_that_are_too_old() {
        // setup
        // - nothing

        // action
        handler.removeTweetsThatAreTooOld(new TweetRetrievalEvent());

        // assert
        Mockito.verify(twitterCacheMock, Mockito.times(1)).vacuum(7);

    }


}
