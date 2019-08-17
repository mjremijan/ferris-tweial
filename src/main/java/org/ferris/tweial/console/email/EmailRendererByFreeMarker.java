package org.ferris.tweial.console.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.configuration.ConfigurationDirectory;
import org.ferris.tweial.console.preferences.PreferencesHandler;
import org.ferris.tweial.console.util.version.Version;
import org.jboss.weld.experimental.Priority;
import twitter4j.Status;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailRendererByFreeMarker {
    @Inject
    protected Logger log;

    @Inject
    protected ConfigurationDirectory configurationDirectory;

    @Inject
    protected Version version;

    @Inject
    protected EmailTweetBuilder emailTweetBuilder;

    @Inject
    protected PreferencesHandler preferencesHandler;

    protected String subjectTemplateName = "email_subject.ftlt";

    protected String emailTemplateName = "email_message.ftlt";

    private Template subjectTemplate, messageTemplate;

    @PostConstruct
    protected void postConstruct() {
        try {
            // -------------------- Create a configuration instance
            // Create your Configuration instance, and specify if up to what FreeMarker
            // version (here 2.3.25) do you want to apply the fixes that are not 100%
            // backward-compatible. See the Configuration JavaDoc for details.
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

            // Specify the source where the template files come from. Here I set a
            // plain directory for it, but non-file-system sources are possible too:
            cfg.setDirectoryForTemplateLoading(configurationDirectory);

            // Set the preferred charset template files are stored in. UTF-8 is
            // a good choice in most applications:
            cfg.setDefaultEncoding("UTF-8");

            // Sets how errors will appear.
            // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
            cfg.setLogTemplateExceptions(false);

            // Subject template
            subjectTemplate = cfg.getTemplate(subjectTemplateName);

            // Body template
            messageTemplate = cfg.getTemplate(emailTemplateName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void render(
        @Observes @Priority(EmailSendPriority.RENDER_EMAIL_MESSAGE)
        EmailSendEvent evnt
    ) {
        if (evnt.getTweets().isEmpty()) {
            evnt.setSubject("");
            evnt.setMessage("");
            return;
        }

        evnt.setSubject(renderSubject());
        evnt.setMessage(renderMessage(evnt.getTweets()));
    }



    protected String renderMessage(List<Status> statuses) {
        // List of tweets
        List<EmailTweet> tweets = new ArrayList<>(statuses.size());
        statuses.forEach(s -> {
            tweets.add(emailTweetBuilder.build(s));
        });

        // Project final name
        String projectFinalName
            = String.format("%s-%s", version.getImplementationTitle(), version.getImplementationVersion());

        // Project url
        String projectUrl
            = version.getImplementationUrl();

        // Render
        Writer out = new StringWriter();
        try {
            Map<String, Object> root = new HashMap<>();
            root.put("tweets", tweets);
            root.put("projectFinalName", projectFinalName);
            root.put("projectUrl", projectUrl);
            root.put("photoErrorUrl", preferencesHandler.findPreferences().getNoImageUrl());
            messageTemplate.process(root, out);
            out.flush();
        } catch (Exception ex) {
            throw new RuntimeException(
                  String.format("Problem rendering the email message")
                , ex
            );
        }
        return out.toString();
    }

    protected String renderSubject() {
        Writer out = new StringWriter();
        try {
            Map<String, Object> root = new HashMap<>();
            root.put("searchDescription", "Home activity");
            subjectTemplate.process(root, out);
            out.flush();
        } catch (Exception ex) {
            throw new RuntimeException(
                String.format("Problem rendering the email subject")
                , ex
            );
        }
        return out.toString();
    }
}
