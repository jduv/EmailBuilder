package me.jduv.java.email;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Creates authenticated sessions with transport layer security turned on.
 */
public class TlsSessionProvider extends AuthenticatedSessionProvider {

    /**
     * Initializes a new instance of the TlsSessionProvider class.
     * 
     * @param builder
     *            The builder.
     */
    public TlsSessionProvider(AuthenticatedSessionProviderBuilder builder) {
        super(builder);
    }

    @Override
    public Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", this.getSmtpHost());
        props.put("mail.smtp.port", Integer.toString(this.getPort()));
        props.put("mail.smtp.starttls.enable", "true");

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
