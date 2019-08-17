/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.twitter;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import twitter4j.Status;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class TweetRetrievalEventTest {

    @Mock
    Logger logMock;

    TweetRetrievalEvent evnt;
    
    @Before
    public void before() {
        evnt = new TweetRetrievalEvent();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_setTweetsFromLastRun() {
        // setup
        List<Status> status = new LinkedList<Status>();
        status.add(Mockito.mock(Status.class));
        status.add(Mockito.mock(Status.class));
        
        // action
        evnt.setTweetsFromLastRun(status);
        
        // assert
        // - size of the list should be 2
        Assert.assertEquals(2, evnt.getTweetsFromLastRun().size());
        // - address comparision between the lists should fail because
        //   the event should create a new list object, i.e. it won't
        //   just store a reference to the List passed to the setter method.
        Assert.assertFalse(status == evnt.getTweetsFromLastRun());
        // - list should not be modifiable...see @Test annotation.
        evnt.getTweetsFromLastRun().remove(0);
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void test_setTweetsFromTwitter() {
        // setup
        List<Status> status = new LinkedList<Status>();
        status.add(Mockito.mock(Status.class));
        status.add(Mockito.mock(Status.class));
        
        // action
        evnt.setTweetsFromTwitter(status);
        
        // assert
        // - size of the list should be 2
        Assert.assertEquals(2, evnt.getTweetsFromTwitter().size());
        // - address comparision between the lists should fail because
        //   the event should create a new list object, i.e. it won't
        //   just store a reference to the List passed to the setter method.
        Assert.assertFalse(status == evnt.getTweetsFromTwitter());
        // - list should not be modifiable...see @Test annotation.
        evnt.getTweetsFromTwitter().remove(0);
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void test_setNewTweetsFromThisRun() {
        // setup
        List<Status> status = new LinkedList<Status>();
        status.add(Mockito.mock(Status.class));
        status.add(Mockito.mock(Status.class));
        
        // action
        evnt.setNewTweetsFromThisRun(status);
        
        // assert
        // - size of the list should be 2
        Assert.assertEquals(2, evnt.getNewTweetsFromThisRun().size());
        // - address comparision between the lists should fail because
        //   the event should create a new list object, i.e. it won't
        //   just store a reference to the List passed to the setter method.
        Assert.assertFalse(status == evnt.getNewTweetsFromThisRun());
        // - list should not be modifiable...see @Test annotation.
        evnt.getNewTweetsFromThisRun().remove(0);
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void test_getTweetsFromLastRun_returns_empty_list() {
        // action
        List<Status> status = evnt.getTweetsFromLastRun();
        
        // assert
        Assert.assertNotNull(status);
        Assert.assertEquals(0, status.size());
        status.add(Mockito.mock(Status.class)); // should throw exception
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void test_getTweetsFromTwitter_returns_empty_list() {
        // action
        List<Status> status = evnt.getTweetsFromTwitter();
        
        // assert
        Assert.assertNotNull(status);
        Assert.assertEquals(0, status.size());
        status.add(Mockito.mock(Status.class)); // should throw exception
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void test_getNewTweetsFromThisRun_returns_empty_list() {
        // action
        List<Status> status = evnt.getNewTweetsFromThisRun();
        
        // assert
        Assert.assertNotNull(status);
        Assert.assertEquals(0, status.size());
        status.add(Mockito.mock(Status.class)); // should throw exception
    }
}
