package org.ferris.tweial.console.email;

import java.util.List;
import twitter4j.User;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailTweet {

    /*
     * Retweeter
     */
    protected String retweeterName;
    public String getRetweeterName() {
        return retweeterName;
    }
    public boolean isRetweet() {
        return getRetweeterName() != null;
    }

    /*
     * User
     */
    protected User user;
    public User getUser() {
        return user;
    }

    /*
     * Id
     */
    protected String id;
    public String getId() {
        return id;
    }

    /*
     * CreatedAt
     */
    protected String createdAt;
    public String getCreatedAt() {
        return createdAt;
    }

    /*
     * Tweet text
     */
    protected String text;
    public String getText() {
        return text;
    }

    /*
     * Quoted tweet
     */
    protected EmailTweet quotedTweet;
    public EmailTweet getQuotedTweet() {
        return quotedTweet;
    }

    /*
     * Media photo URLs
     */
    protected List<String> photoUrls;
    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    /*
     * YouTube watchIds
     */
    protected List<String> youTubeVideos;
    public List<String> getYouTubeVideos() {
        return youTubeVideos;
    }
}
