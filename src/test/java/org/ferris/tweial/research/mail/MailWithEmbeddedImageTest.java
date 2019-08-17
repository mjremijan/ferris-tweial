package org.ferris.tweial.research.mail;

import java.util.Properties;
import java.util.UUID;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.ferris.tweial.research.properties.AbstractPropertiesTest;
import org.junit.Test;

/**
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan Remijan mjremijan@yahoo.com @mjremijan
 *         http://blog.smartbear.com/how-to/how-to-send-email-with-embedded-images-using-java/
 */
public class MailWithEmbeddedImageTest extends AbstractPropertiesTest {

    @Test
    public void sendEmailWithEmbeddedImage() throws Exception {

        if (properties == null) {
            System.out.printf("Properties not found...skipping test (%s).\n", getClass().getName());
            return;
        }

        // Create MimeMultipart
        MimeMultipart content = new MimeMultipart("related");

        // Create cid for image reference
        String cid = UUID.randomUUID().toString();

        // html part
        {
            MimeBodyPart textPart = new MimeBodyPart();

            StringBuilder sp = new StringBuilder();

            sp.append("<html><head>" + "<title>This is not usually displayed</title>");
            sp.append("</head>");
            sp.append("<body><div><strong>Babylon 5</strong></div>");
            sp.append("<div>Sending HTML in email is so <em>cool!</em> </div>");
            sp.append("<div>And so it begins!<br/> <img src=\"cid:");
            sp.append(cid);
            sp.append("\" /></div>" + "<div>I hope you like it!</div></body></html>");

            textPart.setText(sp.toString(), "US-ASCII", "html");

            content.addBodyPart(textPart);
        }

        // image part
        {
            // MimeBodyPart imagePart = new MimeBodyPart();
            // imagePart.attachFile("/home/michael/a/Untitled.jpg");
            // imagePart.setContentID("<" + cid + ">");
            // imagePart.setDisposition(MimeBodyPart.INLINE);
            // content.addBodyPart(imagePart);

            MimeBodyPart imagePart = new MimeBodyPart();
            ByteArrayDataSource ds =
                new ByteArrayDataSource(getClass().getResourceAsStream("/Kosh.jpg"), "image/jpeg");
            imagePart.setDataHandler(new DataHandler(ds));
            imagePart.setFileName("Kosh.jpg");
            imagePart.setContentID("<" + cid + ">");
            imagePart.setDisposition(MimeBodyPart.INLINE);

            content.addBodyPart(imagePart);
        }

        Session smtp = null;
        boolean ssl = false;
        boolean tls = true;
        // https://www.geeksforgeeks.org/sending-email-java-ssltls-authentication/
        if (tls) {
            Properties props = new Properties();
            props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", properties.getProperty("email.smtp.host"));
            props.put("mail.smtp.port", properties.getProperty("email.smtp.port"));

            System.out.printf("%s%n", props.toString());

            smtp = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("email.username"), properties
                                    .getProperty("email.password"));
                }
            });
        }
        else
        if (ssl)
        {
            Properties props = new Properties();
            props = new Properties();
            props.put("mail.smtp.host", properties.getProperty("email.smtp.host"));
            props.put("mail.smtp.port", properties.getProperty("email.smtp.port"));
            props.put("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            //props.setProperty("mail.smtp.socketFactory.port", properties.getProperty("email.smtp.port"));

            System.out.printf("%s%n", props.toString());

            smtp = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("email.username"), properties
                                    .getProperty("email.password"));
                }
            });
        }
        else {
            //props.setProperty("mail.smtp.socketFactory.class", "javax.net.SocketFactory");
            //props.setProperty("mail.smtp.socketFactory.port", properties.getProperty("email.smtp.port"));
        }
        smtp.setDebug(true);
        smtp.setDebugOut(System.out);


        // SMTPMessage m = new SMTPMessage(smtp);
        MimeMessage m = new MimeMessage(smtp);
        m.setContent(content);
        m.setSubject("Message through ferris-prayer");
        String emailAddress
            = properties.getProperty("email.address")
            //= "mjremijan@live.com"
        ;
        m.setRecipient(Message.RecipientType.TO, new InternetAddress("mjremijan@live.com"));
        m.setReplyTo(new InternetAddress[] {new InternetAddress(emailAddress)});
        m.setFrom(new InternetAddress(emailAddress));

        Transport.send(m);
        System.out.printf("***********done");
    }
}
