package org.ferris.tweial.console.twitter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.io.Console;
import org.ferris.tweial.console.text.i18n.LocalizedString;
import org.ferris.tweial.console.text.i18n.LocalizedStringKey;
import org.ferris.tweial.console.view.page.AbstractPage;
import org.jboss.weld.experimental.Priority;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

/**
 * This is the consoles view for Twitter related information.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TwitterPage extends AbstractPage {

    @Inject
    protected Console console;

    @Inject
    protected Logger log;

    @Inject
    @LocalizedStringKey("TwitterPage.DataSourceMissing")
    protected LocalizedString dataSourceMissing;

    @Inject
    @LocalizedStringKey("TwitterPage.AuthDataMissing")
    protected LocalizedString authDataMissing;

    @Inject
    @LocalizedStringKey("TwitterPage.Heading")
    protected LocalizedString heading;

    public void viewDataSourceMissing(String description) {
        log.info("Twitter configuration file is missing");
        console.h1(heading);
        console.p(dataSourceMissing, description);
    }

    public void viewTwitterAccountMissing(Set<ConstraintViolation<TwitterAccount>> violations) {
        log.info("Twitter auth data is missing");
        console.h1(heading);
        console.p(authDataMissing);
        console.print(violations);
    }
    
    
    /**
     * Print the tweets
     * 
     * @param event This method uses {@link TweetRetrievalEvent#getTweetsFromTwitter() }
     * and prints the tweets using a {@link Logger}
     */
    protected void printTweets(
            @Observes @Priority(TweetRetrievalPriority.PRINT_TWEETS_FROM_TWITTER) 
            TweetRetrievalEvent event
    ){
        List<Status> tweetsFromTwitter = event.getTweetsFromTwitter();
        
        for (Status s : tweetsFromTwitter) {
            log.info("======================================================================================");
                       
            // is retweet?
            if (s.isRetweet()) {
                String name = s.getUser().getName();
                log.info(String.format("%s retweeted\n", name));
                s = s.getRetweetedStatus();
            }
                      
            // Profile image
            String profileImageUrl = s.getUser().getProfileImageURL();
            log.info(profileImageUrl);
            
            // Name
            String name = s.getUser().getName();            
            // Screen name
            String screenName = s.getUser().getScreenName();
            // Time since tweet
            SimpleDateFormat sdf = new SimpleDateFormat("(EEE, dd MMM yyyy, hh:mm:ss a)");
            log.info(String.format("%s @%s  %s", name, screenName, sdf.format(s.getCreatedAt())));
                
            // Text
            String text = s.getText();
            {
                // Hyperlinks
                URLEntity[] urlEntities = s.getURLEntities();
                if (urlEntities != null) {
                    for (URLEntity urlEntity : urlEntities) {
                        text = text.replaceAll(urlEntity.getURL(), urlEntity.getExpandedURL());
                    }
                }
            }
            log.info(text);
            
            // Hashtags
            // https://twitter.com/search?q=%23UnlimitedScreaming
            HashtagEntity[] hashtagEntities = s.getHashtagEntities();
            if (hashtagEntities != null) {
                log.info("[HASHTAGS]");
                for (HashtagEntity entity : hashtagEntities) {
                    log.info(ToStringBuilder.reflectionToString(entity));
                }
            }
            
            // User mentions
            UserMentionEntity[] userMentionEntities = s.getUserMentionEntities();
            if (userMentionEntities != null) {
                log.info("[USER MENTIONS]");
                for (UserMentionEntity entity : userMentionEntities) {
                    log.info(ToStringBuilder.reflectionToString(entity));                   
                }
            }
            
            // URL Entities
            URLEntity[] urlEntities = s.getURLEntities();
            if (urlEntities != null) {
                log.info("[URL ENTITIES]");
                for (URLEntity entity : urlEntities) {
                    log.info(ToStringBuilder.reflectionToString(entity));
                }
        }
            

            
            log.info("--------------------------------------------------------------------------------------");
            log.info(ToStringBuilder.reflectionToString(s));

            log.info("======================================================================================");
        }
        
        console.p(new LocalizedString("See log file for tweet details"));
    }

}
