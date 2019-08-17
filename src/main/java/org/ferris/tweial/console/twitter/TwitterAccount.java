package org.ferris.tweial.console.twitter;

import java.util.Properties;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class represent the authentication information needed to access a user's
 * Twitter account.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TwitterAccount {

    @NotNull(message = "{TwitterAccount.consumerKey.NotNull.message}")
    @Size(message = "{TwitterAccount.consumerKey.Size.message}", min = 1)
    private String consumerKey;

    @NotNull(message = "{TwitterAccount.consumerSecret.NotNull.message}")
    @Size(message = "{TwitterAccount.consumerSecret.Size.message}", min = 1)
    private String consumerSecret;

    @NotNull(message = "{TwitterAccount.accessToken.NotNull.message}")
    @Size(message = "{TwitterAccount.accessToken.Size.message}", min = 1)
    private String accessToken;

    @NotNull(message = "{TwitterAccount.accessTokenSecret.NotNull.message}")
    @Size(message = "{TwitterAccount.accessTokenSecret.Size.message}", min = 1)
    private String accessTokenSecret;

    public TwitterAccount(Properties props) {
        this.consumerKey = props.getProperty("oAuthConsumerKey", null);
        this.consumerSecret = props.getProperty("oAuthConsumerSecret", null);
        this.accessToken = props.getProperty("oAuthAccessToken", null);
        this.accessTokenSecret = props.getProperty("oAuthAccessTokenSecret", null);
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }
}
