package me.jduv.java.email;

import java.util.Properties;

import javax.mail.Session;

/**
 * Creates unauthenticated sessions.
 */
public class UnauthenticatedSessionProvider extends SessionProvider {

    /**
     * Initializes a new instance of the UnauthenticatedSessionProvider class.
     * 
     * @param builder
     *            The builder.
     */
    public UnauthenticatedSessionProvider(Builder builder) {
        super(builder);
    }

    @Override
    public Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.host", this.getSmtpHost());
        props.put("mail.smtp.port", Integer.toString(this.getPort()));
        return Session.getInstance(props);
    }

}
