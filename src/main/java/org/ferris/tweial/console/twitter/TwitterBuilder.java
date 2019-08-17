package org.ferris.tweial.console.twitter;

import javax.enterprise.context.Dependent;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Dependent
public class TwitterBuilder {

    protected TwitterAccount account;

    public TwitterBuilder setAccount(TwitterAccount account) {
        this.account = account;
        return this;
    }

    public Twitter build() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(account.getConsumerKey())
            .setOAuthConsumerSecret(account.getConsumerSecret())
            .setOAuthAccessToken(account.getAccessToken())
            .setOAuthAccessTokenSecret(account.getAccessTokenSecret());

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }
}
