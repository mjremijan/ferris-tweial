package org.ferris.tweial.console.email;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Properties;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.log4j.Log4jRollingFileAppender;
import org.ferris.tweial.console.retry.ExceptionRetry;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailSender {

    @Inject
    protected Logger log;

    @Inject
    protected EmailHandler emailHandler;



    @ExceptionRetry
    public void sendEmail(
        @Observes @Priority(EmailSendPriority.SEND_EMAIL_MESSAGE)
        EmailSendEvent evnt
    ) throws MessagingException, IOException {

        if (evnt.getSubject().isEmpty() || evnt.getMessage().isEmpty()) {
            return;
        }

        // Create MimeMultipart
        MimeMultipart content = new MimeMultipart("related");

        // html part
        {
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(evnt.getMessage(), "UTF8", "html");
            content.addBodyPart(textPart);
        }


        // properties
        EmailAccount emailAccount = emailHandler.getEmailAccount();
        Properties props = new Properties();
        if (emailAccount.isSslEnabled()) {
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.host", emailAccount.getHost());
            props.setProperty("mail.smtp.socketFactory.port", emailAccount.getPort().toString());
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        } else {
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.host", emailAccount.getHost());
            props.setProperty("mail.smtp.port", emailAccount.getPort().toString());
            props.setProperty("mail.smtp.starttls.enable", "true");
        }

        Session smtp = null;
        {
            smtp = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                          emailAccount.getUsername()
                        , emailAccount.getPassword()
                    );
                }
            });
            smtp.setDebug(true);
            smtp.setDebugOut(getPrintStream());
        }

        MimeMessage m = new MimeMessage(smtp);
        {
            // to
            m.setRecipient(
                  Message.RecipientType.TO
                , new InternetAddress(emailAccount.getSendToAddress())
            );

            // subject
            m.setSubject(evnt.getSubject());

            // from
            {
                InternetAddress from = new InternetAddress(emailAccount.getEmailAddress());
                from.setPersonal("Tweial");
                m.setFrom(from);
            }

            // reply
            {
                InternetAddress reply = new InternetAddress(emailAccount.getEmailAddress());
                reply.setPersonal("Tweial");
                m.setReplyTo(new InternetAddress[] {reply});
            }

            m.setContent(content);
        }

        log.info(String.format("Attempt email with %s", emailAccount.toString()));
        Transport.send(m);
    }

    protected PrintStream getPrintStream() {
        Enumeration enu = log.getAllAppenders();
        while (enu.hasMoreElements()) {
            Object o = enu.nextElement();
            if (o instanceof Log4jRollingFileAppender) {
                return ((Log4jRollingFileAppender)o).getPrintStream();
            }
        }
        return System.out;
    }
}
