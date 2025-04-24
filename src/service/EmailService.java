package service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {

    private final Session session;
    private final String from;

    public EmailService(String host, int port,
                        String username, String password, String from) {

        this.from = from;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");  // STARTTLS
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));


        // Authentification
        session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public void send(String to, String subject, String html)
            throws MessagingException {

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(Message.RecipientType.TO,
                new InternetAddress(to));
        msg.setSubject(subject);
        msg.setContent(html, "text/html; charset=utf-8");

        Transport.send(msg);
    }
}
