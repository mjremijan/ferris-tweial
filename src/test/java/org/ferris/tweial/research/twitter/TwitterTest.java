package org.ferris.tweial.research.twitter;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ferris.tweial.console.lang.StringDecorator;
import org.ferris.tweial.research.properties.AbstractPropertiesTest;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * http://twitter.com/oauth_clients/new
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan Remijan mjremijan@yahoo.com @mjremijan
 */
public class TwitterTest extends AbstractPropertiesTest {

    @Test
    public void getHomeTimeline() throws Exception {

        if (properties == null) {
            System.out.printf("Properties not found...skipping test (%s).\n", getClass().getName());
            return;
        }

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(properties.getProperty("twitter.oAuthConsumerKey"))
                        .setOAuthConsumerSecret(properties.getProperty("twitter.oAuthConsumerSecret"))
                        .setOAuthAccessToken(properties.getProperty("twitter.oAuthAccessToken"))
                        .setOAuthAccessTokenSecret(properties.getProperty("twitter.oAuthAccessTokenSecret"));

        TwitterFactory tf = new TwitterFactory(cb.build());

        Twitter twitter = tf.getInstance();
        // Query query = new Query("source:twitter4j mjremijan");

        List<Status> statuses = null;
        int q = 3;
        // home
        if (1==q) {
            statuses = twitter.getHomeTimeline();
        }
        // search
        if (2==q) {
            Query query = new Query("source:twitter4j mjremijan");
            //Query query = new Query("babylon5");
            QueryResult result = twitter.search(query);
            statuses = result.getTweets();
        }
        // by id
        if (3==q) {
            Status status = twitter.showStatus(Long.parseLong("1282704838845923331"));
            statuses = Arrays.asList(status);
        }

        print(statuses);
        assertNotNull(statuses);
        assertTrue(statuses.size() > 0);

        // print(statuses);

    }

    private void print(List<Status> statuses) {
        System.out.printf("satuses size=%d\n", statuses.size());
        for (Status status : statuses) {
            System.out.printf("%n%n@%n");
            System.out.printf("id=%d%n", status.getId());
            System.out.printf("screenName=%s%n", status.getUser().getScreenName());
            System.out.printf("text:%n%s%n", status.getText());

            User user = status.getUser();
            System.out.printf("%n%n+%n");
            System.out.printf("User:%n%s%n", ToStringBuilder.reflectionToString(user));

            MediaEntity[] media = status.getMediaEntities();
            if (media != null) {
                for (MediaEntity m : media) {
                    System.out.printf("%n%n+%n");
                    System.out.printf("MediaEntity:%n%s%n", ToStringBuilder.reflectionToString(m));
                }
            }

            HashtagEntity[] hashtags = status.getHashtagEntities();
            if (hashtags != null) {
                for (HashtagEntity m : hashtags) {
                    System.out.printf("%n%n+%n");
                    System.out.printf("HashtagEntity:%n%s%n", ToStringBuilder.reflectionToString(m));
                }
            }

            URLEntity[] urlEntities = status.getURLEntities();
            if (urlEntities != null) {
                for (URLEntity m : urlEntities) {
                    System.out.printf("%n%n+%n");
                    System.out.printf("URLEntity:%n%s%n", ToStringBuilder.reflectionToString(m));
                }
            }

            StringDecorator sd = new StringDecorator(status.getText());
            System.out.printf("%n%n==%n");
            sd.getUnicodeCharacters().stream()
                .forEach(u -> System.out.printf("%s%n", u.toString()));
        }
    }


}
