package org.ferris.tweial.console.email;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.inject.Inject;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.lang.StringDecorator;
import org.ferris.tweial.console.preferences.PreferencesHandler;
import org.ferris.tweial.console.util.PatternForYouTube;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.MediaEntity.Variant;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailTweetBuilder {

    @Inject
    protected Logger log;

    @Inject
    protected PreferencesHandler preferencesHandler;

    public EmailTweet build(Status status)
    {
        EmailTweet t = new EmailTweet();

        // Retweeter name
        t.retweeterName = (status.isRetweet()) ? status.getUser().getName() : null;

        // Which status to use for the tweet
        Status s = (status.isRetweet() ? status.getRetweetedStatus() : status);

        // User
        t.user = s.getUser();

        // Id
        t.id = Long.toString(s.getId());

        // CreatedAt
        t.createdAt = (s.getCreatedAt() == null) ? null :
            new SimpleDateFormat("M/d h:mma")
            .format(s.getCreatedAt())
            .toLowerCase()
            .replace("m", "")
        ;

        // List of YouTube videos
        t.youTubeVideos = new LinkedList<>();

        // Status Text
        StringDecorator statusText = (s.getText() == null) ? new StringDecorator("") : new StringDecorator(s.getText());
        {
            // List of unique URLs to decorate/remove from the tweet text
            Set<String> urlsToRemoveFromTweetText = new HashSet<>();

            // URLs
            URLEntity[] urlEntities = s.getURLEntities();
            if (urlEntities != null) {
                for (URLEntity entity : urlEntities) {
                    String expandedUrl = entity.getExpandedURL();
                    if (s.getQuotedStatus() != null && expandedUrl.contains(String.format("%d",s.getQuotedStatus().getId()))) {
                        statusText.decorate(
                              entity.getStart(), entity.getEnd() - 1
                            , (m) -> ""
                        );
                    } else {
                        PatternForYouTube p = new PatternForYouTube(expandedUrl);
                        if (p.matches()) {
                            t.youTubeVideos.add(p.getVidId());
                        }
                        statusText.decorate(
                              entity.getStart(), entity.getEnd() - 1
                            , (m) -> String.format("<a href=\"%1$s\">%1$s</a>", expandedUrl)
                        );
                    }
                }
            }

            // Hashtags
            // https://twitter.com/search?q=%23UnlimitedScreaming
            HashtagEntity[] hashtagEntities = s.getHashtagEntities();
            if (hashtagEntities != null) {
                for (HashtagEntity entity : hashtagEntities) {
                    statusText.decorate(
                          entity.getStart(), entity.getEnd() - 1
                        , (m) -> String.format(
                              "<a href=\"https://twitter.com/search?f=tweets&vertical=default&q=%%23%1$s\">#%1$s</a>"
                            , entity.getText()
                          )
                    );
                }
            }

            // User mentions
            // https://twitter.com/farmtrukstl/
            // https://twitter.com/PTXofficial/
            UserMentionEntity[] userMentionEntities = s.getUserMentionEntities();
            if (userMentionEntities != null) {
                for (UserMentionEntity entity : userMentionEntities) {
                    statusText.decorate(
                          entity.getStart(), entity.getEnd()-1
                        , (m) -> String.format("<a href=\"https://twitter.com/%1$s\">@%1$s</a>", entity.getScreenName())
                    );
                }
            }

            // Media photos
            t.photoUrls = new LinkedList<>(); {
            MediaEntity[] mediaEntities = s.getMediaEntities();
                if (mediaEntities != null && mediaEntities.length >= 1) {
                    for (MediaEntity entity : mediaEntities) {
                        if ("photo".equalsIgnoreCase(entity.getType())) {
                            // URL of photo in tweet (possibly)
                            urlsToRemoveFromTweetText.add(entity.getURL());
                            // URL of photo
                            if (entity.getMediaURLHttps() != null && !entity.getMediaURLHttps().isEmpty()) {
                                t.photoUrls.add(entity.getMediaURLHttps());
                            }
                            else
                            if (entity.getMediaURL() != null && !entity.getMediaURL().isEmpty()) {
                                t.photoUrls.add(entity.getMediaURL());
                            }
                            else {
                                t.photoUrls.add(preferencesHandler.findPreferences().getNoImageUrl());
                            }
                        }
                    }
                }
            }

            // Media videos
            t.videoMedia = new LinkedList<>(); {
                MediaEntity[] mediaEntities = s.getMediaEntities();
                if (mediaEntities != null && mediaEntities.length >= 1) {
                    for (MediaEntity entity : mediaEntities) {
                        if ("video".equalsIgnoreCase(entity.getType())) {
                            Arrays.stream(entity.getVideoVariants())
                                .filter(v -> v.getContentType().contains("mp4"))
                                .max(Comparator.comparing(Variant::getBitrate))
                                .ifPresent( v -> {
                                        urlsToRemoveFromTweetText.add(entity.getURL());
                                        t.videoMedia.add(
                                            new EmailVideo.Builder()
                                                .poster(entity.getMediaURLHttps())
                                                .src(v.getUrl())
                                                .type(v.getContentType())
                                                .millis(entity.getVideoDurationMillis())
                                                .build()
                                        );
                                    }
                                )
                            ;
                        }
                    }
                }
            }

            // Media animated_gif
            t.animatedGifMedia = new LinkedList<>(); {
                MediaEntity[] mediaEntities = s.getMediaEntities();
                if (mediaEntities != null && mediaEntities.length >= 1) {
                    for (MediaEntity entity : mediaEntities) {
                        if ("animated_gif".equalsIgnoreCase(entity.getType())) {
                            Arrays.stream(entity.getVideoVariants())
                                .filter(v -> v.getContentType().contains("mp4"))
                                .max(Comparator.comparing(Variant::getBitrate))
                                .ifPresent( v ->
                                    t.animatedGifMedia.add(
                                        new EmailAnimatedGif.Builder()
                                            .poster(entity.getMediaURLHttps())
                                            .src(v.getUrl())
                                            .type(v.getContentType())
                                            .build()
                                    )
                                )
                            ;
                        }
                    }
                }
            }

            // Media non-photo and non-video
            t.nonPhotoMedia = new LinkedList<>(); {
                MediaEntity[] mediaEntities = s.getMediaEntities();
                if (mediaEntities != null && mediaEntities.length >= 1) {
                    for (MediaEntity entity : mediaEntities) {
                        switch (entity.getType().toLowerCase()) {
                            case "photo":
                            case "video":
                            case "animated_gif":
                                continue;
                            default:
                                t.nonPhotoMedia.add(
                                    ToStringBuilder.reflectionToString(entity)
                                );
                        }
                    }
                }
            }

            // Remove urls from tweet text;
            for (String url : urlsToRemoveFromTweetText) {
                statusText.decorate(url, l->"");
            }

            // Convert newline to Line break
            statusText.decorate("\n", l->"<br />");
        }
        t.text = statusText.toStringDecorated();

        // Quoted tweet
        t.quotedTweet = (s.getQuotedStatus() == null) ? null : this.build(s.getQuotedStatus());

        // Return
        return t;
    }
}
