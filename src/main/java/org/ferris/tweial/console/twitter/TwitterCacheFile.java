package org.ferris.tweial.console.twitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.data.DataDirectory;
import twitter4j.Status;

/**
 * This is a hard coded {@link File} object to
 * "{@link DataDirectory}/tweets-cache.ser".  This file is used
 * to store a history of tweets already seen by Tweial.  This is how the
 * application can then only send new tweets every time it runs. Serialized
 * Java objects are written to the file.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TwitterCacheFile extends File {

    private static final long serialVersionUID = 4326757809880954L;

    @Inject
    protected Logger log;

    /**
     * To file "{@link DataDirectory}/tweets-cache.ser"
     *
     * @param datadir An {@link DataDirectory} representing the data directory
     */
    @Inject
    public TwitterCacheFile(DataDirectory datadir) {
        super(datadir, "tweets-cache.ser");
    }


    /**
     * This method will de-serialize the data file and return a
     * {@link List}&lt;{@link Status}&gt;.  The list returned by
     * this method is modifiable.
     *
     * @return
     *  A {@link List}&lt;{@link Status}&gt; is always returned. {@code null} is
     *  never returned. If de-serialization fails for any reason, the exception
     *  is logged and empty list is returned.
     *
     */
    protected List<Status> getModifiableCache() {
        log.debug("Read serialized object from file");
        List<Status> retval = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this));) {
            retval = (List<Status>) ois.readObject();
        } catch (IOException|ClassNotFoundException e) {
            log.warn("Failure getting cache of stored tweets", e);
            retval = new LinkedList<>();
        }
        return retval;
    }


    /**
     * A getter method which returns an unmodifiable list.  This is a
     * wrapper over {@link #getModifiableCache() }
     *
     * @see #getModifiableCache()
     *
     * @return
     *  The same as {@link #getModifiableCache() } only unmodifiable
     */
    protected List<Status> get() {
        log.info("Get cache data");
        List<Status> cache = getModifiableCache();
        return Collections.unmodifiableList(cache);
    }


    /**
     * Serializes the {@code stored} parameter to the data file. If anything
     * goes wrong during serialization the exception is logged and then the
     * exception is wrapped in a {@link RuntimeException} and re-thrown;
     *
     * @param stored The objects to be serialized.  No validation of it's value.
     *
     * @throws RuntimeException
     *  If anything goes wrong, it's wrapped in a {@link RuntimeException}
     *  and re-thrown.
     */
    protected void set(List<Status> stored) {
        log.debug("Set cache data");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this));) {
            oos.writeObject(stored);
            oos.flush();
        } catch (IOException e) {
            String msg = "Failure writing cache of stored tweets";
            log.fatal(msg, e);
            throw new RuntimeException(msg, e);
        }
    }


    /**
     * Adds all objects in the {@code addToCache} parameter to cache then
     * re-writes the data file. This method is a wrapper around
     * {@link #getModifiableCache()} and {@link #set(java.util.List)}.
     *
     * @param addToCache
     *  The objects to added to the cache.  No validation of it's value.
     *
     * @throws RuntimeException
     *  Same as {@link #set(java.util.List) }
     */
    protected void addAll(List<Status> addToCache) {
        List<Status> cache = getModifiableCache();
        cache.addAll(addToCache);
        set(cache);
    }


    /**
     * Removes all objects from the cache which are older than
     * {@code daysOld}. This method is a wrapper around
     * {@link #getModifiableCache()} and {@link #set(java.util.List)}.
     *
     * @param daysOld Remove data that is older than {@code daysOld}
     * @throws RuntimeException
     *  Same as {@link #set(java.util.List) }
     */
    protected void vacuum(int daysOld) {
        // Last week
        LocalDateTime lastWeek
            = LocalDateTime.now().minusDays(daysOld);

        // Date ts = ...;
        // Instant instant = Instant.ofEpochMilli(ts.getTime());
        // LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // Remove if the Status createdAt date is more than a week old.
        List<Status> cache = getModifiableCache();
        cache.removeIf(s -> LocalDateTime.ofInstant(s.getCreatedAt().toInstant(), ZoneId.systemDefault()).isBefore(lastWeek) );

        // Save the cache again
        set(cache);
    }
}
