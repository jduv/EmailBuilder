package me.jduv.java.email;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Creates authenticated sessions with SSL turned on.
 */
public class SslSessionProvider extends AuthenticatedSessionProvider {

    /**
     * Initializes a new instance of the SslSessionProvider class.
     * 
     * @param builder
     *            The builder.
     */
    public SslSessionProvider(AuthenticatedSessionProviderBuilder builder) {
        super(builder);
    }

    @Override
    public Session getSession() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.host", this.getSmtpHost());
        props.put("mail.smtps.port", Integer.toString(this.getPort()));
        props.put("mail.smtp.ssl.enable", "true");

        final String username = this.getUsername();
        final String password = this.getPassword();
        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

}
