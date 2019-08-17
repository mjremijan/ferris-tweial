package org.ferris.tweial.console.twitter;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.application.ApplicationDirectory;
import org.ferris.tweial.console.data.DataDirectory;
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
public class TwitterCacheFileTest {

    @Mock
    Logger logMock;

    ApplicationDirectory applicationDirectory;

    TwitterCacheFile cacheSpy;

    List<Status> list1;

    List<Status> list2;

    @Before
    public void before() {
        applicationDirectory
            = new ApplicationDirectory("src/test/junit/TwitterCacheFileTest");

        cacheSpy
            = Mockito.spy(new TwitterCacheFile(new DataDirectory(applicationDirectory)));
            cacheSpy.log = logMock;

        list1
            = new LinkedList<>();
            list1.add(Mockito.mock(Status.class));

        list2
            = new LinkedList<>();
            list2.add(Mockito.mock(Status.class));
            list2.add(Mockito.mock(Status.class));
    }

    @Test()
    public void test_getModifiableCache_returns_modifiable_and_empty_list_if_exception() {
        // setup
        ApplicationDirectory doesNotExist = new ApplicationDirectory("src/test/junit/TwitterCacheFileTestEmpty");
        TwitterCacheFile cache = new TwitterCacheFile(new DataDirectory(doesNotExist));
        cache.log = logMock;

        // action
        List<Status> l = cache.getModifiableCache();

        // assert
        Assert.assertNotNull(l);
        Assert.assertEquals(0, l.size());
        l.addAll(list2);
        Assert.assertEquals(2, l.size());
    }


    @Test()
    public void test_getModifiableCache_returns_modifiable_and_list() {
        // setup
        cacheSpy.set(list2);

        // action
        List<Status> l = cacheSpy.getModifiableCache();

        // assert
        Assert.assertNotNull(l);
        Assert.assertEquals(2, l.size());
        l.addAll(list1);
        Assert.assertEquals(3, l.size());
    }


    @Test
    public void test_addAll() {
        // setup
        Mockito.doReturn(list1).when(cacheSpy).getModifiableCache();
        Mockito.doNothing().when(cacheSpy).set(list1);

        // action
        cacheSpy.addAll(list2);

        // assert
        Assert.assertEquals(3, list1.size());
        Mockito.verify(cacheSpy, Mockito.times(1)).set(list1);
    }


    @Test
    public void test_set_creates_a_nonempty_file() {
        // action
        cacheSpy.set(list2);

        // assert
        Assert.assertTrue(cacheSpy.exists());
        Assert.assertTrue(cacheSpy.length() > 0);
    }


    @Test(expected = RuntimeException.class)
    public void test_set_throws_a_RuntimeException() {
        // setup
        ApplicationDirectory doesNotExist = new ApplicationDirectory("src/does/not/exist/file.ser");
        TwitterCacheFile cache = new TwitterCacheFile(new DataDirectory(doesNotExist));
        cache.log = logMock;

        // action
        cache.set(list2);
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testGetReturnsImmutableList() {
        // setup
        Mockito.doReturn(list1).when(cacheSpy).getModifiableCache();

        // action
        List<Status> l = cacheSpy.get();

        // assert
        Assert.assertNotNull(l);
        l.add(Mockito.mock(Status.class));
    }


    @Test()
    public void test_vacuum() {
        // setup
        // - need cache items older than 7 days
        list1.clear();
        {   // 9 days old
            Status s = Mockito.mock(Status.class);
            Mockito.when(s.getCreatedAt()).thenReturn(date(-9));
            list1.add(s);
        }
        {   // 7 days old
            Status s = Mockito.mock(Status.class);
            Mockito.when(s.getCreatedAt()).thenReturn(date(-7));
            list1.add(s);
        }
        {   // 3 days old
            Status s = Mockito.mock(Status.class);
            Mockito.when(s.getCreatedAt()).thenReturn(date(-3));
            list1.add(s);
        }
        {   // 0 days old...today
            Status s = Mockito.mock(Status.class);
            Mockito.when(s.getCreatedAt()).thenReturn(date(0));
            list1.add(s);
        }
        Mockito.doReturn(list1).when(cacheSpy).getModifiableCache();
        Mockito.doNothing().when(cacheSpy).set(list1);

        // action
        // - sleep just a few miliseconds so that "now" here in the test
        //   is just a little bit older than "now" in the TwitterCacheFile
        //   during the vacuum.
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignore) {}
        // - vacuum!
        cacheSpy.vacuum(7);

        // assert
        List<Status> cache = cacheSpy.get();
        Assert.assertEquals(2, cache.size());
        Mockito.verify(cacheSpy, Mockito.times(1)).set(list1);
    }

    private Date date(int daysPast) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, daysPast);
        return c.getTime();
    }
}
