package org.ferris.tweial.console.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.application.ApplicationDirectory;
import org.ferris.tweial.console.configuration.ConfigurationDirectory;
import org.ferris.tweial.console.preferences.Preferences;
import org.ferris.tweial.console.preferences.PreferencesHandler;
import org.ferris.tweial.console.util.version.Version;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

/**
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailRendererByFreeMarkerTest {

    @Mock
    Logger logMock;

    @Mock
    Version version;

    @Mock
    Status status;

    EmailRendererByFreeMarker renderer;

    @Before
    public void before() {

        PreferencesHandler preferencesHandlerMock = Mockito.mock(PreferencesHandler.class);
        {
            Preferences preferencesMock = Mockito.mock(Preferences.class);
            Mockito.when(preferencesMock.getNoImageUrl()).thenReturn("https://img.co/noimg.jpg");
            Mockito.when(preferencesHandlerMock.findPreferences()).thenReturn(preferencesMock);
        }

        EmailTweetBuilder emailTweetBuilder = new EmailTweetBuilder();
        {
            emailTweetBuilder.log = logMock;
            emailTweetBuilder.preferencesHandler = preferencesHandlerMock;
        }

        renderer = new EmailRendererByFreeMarker();
        {

            renderer.log = logMock;
            renderer.configurationDirectory = new ConfigurationDirectory(new ApplicationDirectory("src/test/junit/EmailRenderer"));
            renderer.version = version;
            renderer.emailTemplateName   = "email_message_empty.ftlt";
            renderer.subjectTemplateName = "email_message_empty.ftlt";
            renderer.emailTweetBuilder = emailTweetBuilder;
            renderer.preferencesHandler = preferencesHandlerMock;
            {
                Mockito.when(version.getImplementationTitle()).thenReturn("Tweial");
                Mockito.when(version.getImplementationVersion()).thenReturn("1.0.0.0");
                Mockito.when(version.getImplementationUrl()).thenReturn("http://test.tweial.net");
            }
        }
        renderer.postConstruct();

        //
        // Arrange
        //
        // Retweeted status
        {
            // retweeted user
            User retweetedUser = Mockito.mock(User.class);
            {
                Mockito.when(retweetedUser.getName()).thenReturn("Oscar Orange");
                Mockito.when(retweetedUser.getProfileImageURL()).thenReturn("https://test.tweial.net?imgurl=12345");
                Mockito.when(retweetedUser.getScreenName()).thenReturn("oorange");
            }

            // retweeted status
            Status retweetedStatus = Mockito.mock(Status.class);
            Mockito.when(status.getRetweetedStatus()).thenReturn(retweetedStatus);
            {
                Mockito.when(retweetedStatus.isRetweet()).thenReturn(false);
                Mockito.when(retweetedStatus.getUser()).thenReturn(retweetedUser);
                Mockito.when(retweetedStatus.getId()).thenReturn(100L);
                Mockito.when(retweetedStatus.getCreatedAt()).thenReturn(new GregorianCalendar(2019, Calendar.AUGUST, 8, 21, 56, 0).getTime());

                /*                                                            10        20        30        40        50        60        70        80        90        100       110       120
                                                                     123456789|123456789|123456789|123456789|123456789|123456789|123456789|123456789|123456789|123456789|123456789|123456789|123456789| */
                 Mockito.when(retweetedStatus.getText()).thenReturn("Tweet begin url:http://m.co/ywehry #amazon  ipsum lorem  #oracle @rred x http://flickr.com/abc.jpg y https://t.co/apGpQQwtsk end");
                 {
                     // URLEntity
                     {
                         URLEntity url0 = Mockito.mock(URLEntity.class);
                         {
                             Mockito.when(url0.getStart()).thenReturn(16);
                             Mockito.when(url0.getEnd()).thenReturn(34);
                             Mockito.when(url0.getExpandedURL()).thenReturn("http://junit.org/m/resource/ywehry");
                         }
                         // https://www.youtube.com/watch?=RaSmassvv4w
                         URLEntity url1 = Mockito.mock(URLEntity.class);
                         {
                             Mockito.when(url1.getStart()).thenReturn(101);
                             Mockito.when(url1.getEnd()).thenReturn(124);
                             Mockito.when(url1.getExpandedURL()).thenReturn("https://www.youtube.com/watch?v=RaSmassvv4w");
                         }
                         Mockito.doReturn(new URLEntity[]{url0, url1}).when(retweetedStatus).getURLEntities();
                     }
                     // HashtagEntity
                     {
                         HashtagEntity hte0 = Mockito.mock(HashtagEntity.class);
                         {
                             Mockito.when(hte0.getStart()).thenReturn(35);
                             Mockito.when(hte0.getEnd()).thenReturn(42);
                             Mockito.when(hte0.getText()).thenReturn("amazon");
                         }
                         HashtagEntity hte1 = Mockito.mock(HashtagEntity.class);
                         {
                             Mockito.when(hte1.getStart()).thenReturn(57);
                             Mockito.when(hte1.getEnd()).thenReturn(64);
                             Mockito.when(hte1.getText()).thenReturn("oracle");
                         }
                         Mockito.doReturn(new HashtagEntity[]{hte0,hte1}).when(retweetedStatus).getHashtagEntities();
                     }
                     // UserMentionEntity
                     {
                         UserMentionEntity ume0 = Mockito.mock(UserMentionEntity.class);
                         {
                             Mockito.when(ume0.getStart()).thenReturn(65);
                             Mockito.when(ume0.getEnd()).thenReturn(70);
                             Mockito.when(ume0.getScreenName()).thenReturn("rred");
                         }
                         Mockito.doReturn(new UserMentionEntity[]{ume0}).when(retweetedStatus).getUserMentionEntities();
                     }
                     // MediaEntity
                     {
                         MediaEntity me0 = Mockito.mock(MediaEntity.class);
                         {
                             Mockito.when(me0.getType()).thenReturn("photo");
                             Mockito.when(me0.getURL()).thenReturn("http://flickr.com/abc.jpg");
                             Mockito.when(me0.getMediaURL()).thenReturn("http://flickr.com/oorange/media/abc.jpg");
                         }
                         MediaEntity me1 = Mockito.mock(MediaEntity.class);
                         {
                             Mockito.when(me1.getType()).thenReturn("photo");
                             Mockito.when(me1.getURL()).thenReturn("https://flickr.com/xyz.jpg");
                             Mockito.when(me1.getMediaURL()).thenReturn(null);
                             Mockito.when(me1.getMediaURLHttps()).thenReturn("https://flickr.com/oorange/media/xyz.jpg");
                         }
                         Mockito.doReturn(new MediaEntity[]{me0,me1}).when(retweetedStatus).getMediaEntities();
                     }
                     // Quoted status
                     {
                        // user
                        User quotedUser = Mockito.mock(User.class);
                        {
                            Mockito.when(quotedUser.getName()).thenReturn("Yet Yellow");
                            Mockito.when(quotedUser.getScreenName()).thenReturn("yyellow_1");
                        }

                        // status
                        Status quotedStatus = Mockito.mock(Status.class);
                        Mockito.when(retweetedStatus.getQuotedStatus()).thenReturn(quotedStatus);
                        {
                            Mockito.when(quotedStatus.getId()).thenReturn(88888L);
                            Mockito.when(quotedStatus.isRetweet()).thenReturn(false);
                            Mockito.when(quotedStatus.getUser()).thenReturn(quotedUser);
                            Mockito.when(quotedStatus.getText()).thenReturn("Simple tweet text");
                        }
                     }
                 }
            }
        }

        // User
        User user = Mockito.mock(User.class);
        {
            Mockito.when(user.getName()).thenReturn("Rita Red");
            Mockito.when(user.getProfileImageURL()).thenReturn("https://test.tweial.net?imgurl=67890");
            Mockito.when(user.getScreenName()).thenReturn("rred");
        }

        // Status
        Mockito.when(status.isRetweet()).thenReturn(true);
        Mockito.when(status.getUser()).thenReturn(user);
        Mockito.when(status.getId()).thenReturn(200L);
        Mockito.when(status.getCreatedAt()).thenReturn(new GregorianCalendar(2019, Calendar.AUGUST, 8, 21, 56, 0).getTime());
    }

    @Test
    public void test_photo_error_url() throws IOException {
        // Arrange
        renderer.emailTemplateName = "email_message_photo_error_url.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("https://img.co/noimg.jpg", message.trim());
    }

    @Test
    public void test_youtube_vidoes() throws IOException {
        // Arrange
        renderer.emailTemplateName = "email_message_youtube_videos.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("RaSmassvv4w", message.trim());
    }

    @Test
    public void test_media_photos() throws IOException {
        // Arrange
        renderer.emailTemplateName = "email_message_media_photos.ftlt";
        renderer.postConstruct();

        // Act
        BufferedReader br = new BufferedReader(new StringReader(renderer.renderMessage(Arrays.asList(status))));
        List<String> lines = new ArrayList<>(2);
        for(String line=br.readLine(); line!=null; line=br.readLine()) {
            String s = line.trim();
            if (!s.isEmpty()) {
                lines.add(s);
            }
        }

        // Assert
        Assert.assertEquals(2, lines.size());
        Assert.assertEquals("http://flickr.com/oorange/media/abc.jpg", lines.get(0));
        Assert.assertEquals("https://flickr.com/oorange/media/xyz.jpg", lines.get(1));
    }

    @Test
    public void test_quote_test() {
        // Arrange
        renderer.emailTemplateName = "email_message_quote_text.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("Simple tweet text", message.trim());
    }

    @Test
    public void test_quote_screen_name() {
        // Arrange
        renderer.emailTemplateName = "email_message_quote_screen_name.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("yyellow_1", message.trim());
    }

    @Test
    public void test_quote_user_name() {
        // Arrange
        renderer.emailTemplateName = "email_message_quote_user_name.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("Yet Yellow", message.trim());
    }

    @Test
    public void test_quote_false() {
        // Arrange
        renderer.emailTemplateName = "email_message_quote_false.ftlt";
        renderer.postConstruct();
        Mockito.when(status.getRetweetedStatus().getQuotedStatus()).thenReturn(null);

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("FALSE", message.trim());
    }

    @Test
    public void test_quote_true() {
        // Arrange
        renderer.emailTemplateName = "email_message_quote_true.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("TRUE", message.trim());
    }

    @Test
    public void test_text() {
        // Arrange
        renderer.emailTemplateName = "email_message_text.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals(
              "Tweet begin url:<a href=\"http://junit.org/m/resource/ywehry\">http://junit.org/m/resource/ywehry</a> <a href=\"https://twitter.com/search?f=tweets&vertical=default&q=%23amazon\">#amazon</a>  ipsum lorem  <a href=\"https://twitter.com/search?f=tweets&vertical=default&q=%23oracle\">#oracle</a> <a href=\"https://twitter.com/rred\">@rred</a> x  y  end"
            , message.trim()
        );
    }

    @Test
    public void test_project_url() {
        // Arrange
        renderer.emailTemplateName = "email_message_project_url.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("http://test.tweial.net", message.trim());
    }

    @Test
    public void test_project_final_name() {
        // Arrange
        renderer.emailTemplateName = "email_message_project_final_name.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("Tweial-1.0.0.0", message.trim());
    }

    @Test
    public void test_created_at() {
        // Arrange
        renderer.emailTemplateName = "email_message_created_at.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("8/8 9:56p", message.trim());
    }

    @Test
    public void test_user_name() {
        // Arrange
        renderer.emailTemplateName = "email_message_user_name.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("Oscar Orange", message.trim());
    }

    // 1161675083955523584
    @Test
    public void test_id_with_big_value() {
        // Arrange
        renderer.emailTemplateName = "email_message_id.ftlt";
        Mockito.when(status.getRetweetedStatus().getId()).thenReturn(1161675083955523584L);
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("1161675083955523584", message.trim());
    }

    @Test
    public void test_id() {
        // Arrange
        renderer.emailTemplateName = "email_message_id.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("100", message.trim());
    }

    @Test
    public void test_screen_name() {
        // Arrange
        renderer.emailTemplateName = "email_message_screen_name.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("oorange", message.trim());
    }

    @Test
    public void test_profile_image_url() {
        // Arrange
        renderer.emailTemplateName = "email_message_profile_image_url.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("https://test.tweial.net?imgurl=12345", message.trim());
    }

    @Test
    public void test_retweet_false() {
        // Arrange
        renderer.emailTemplateName = "email_message_retweet_false.ftlt";
        renderer.postConstruct();
        Mockito.when(status.isRetweet()).thenReturn(false);

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("FALSE", message.trim());
    }

    @Test
    public void test_retweet_true() {
        // Arrange
        renderer.emailTemplateName = "email_message_retweet_true.ftlt";
        renderer.postConstruct();

        // Act
        String message = renderer.renderMessage(Arrays.asList(status));

        // Assert
        Assert.assertEquals("Rita Red", message.trim());
    }

    @Test
    public void test_subject_rendered() {
        // Arrange
        renderer.subjectTemplateName = "email_subject.ftlt";
        renderer.postConstruct();

        // Act
        String subject = renderer.renderSubject();

        // Assert
        Assert.assertEquals("JUnit: Home activity", subject);
    }
}
