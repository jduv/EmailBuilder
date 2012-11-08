package me.jduv.java.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Base authenticated session provider.
 */
public class AuthenticatedSessionProvider extends SessionProvider {
    private final String username;
    private final String password;

    /**
     * Initializes a new instance of the AuthenticatedSessionProvider class.
     * 
     * @param builder
     *            The builder.
     */
    public AuthenticatedSessionProvider(AuthenticatedSessionProviderBuilder builder) {
        super(builder);
        this.username = builder.getUsername();
        this.password = builder.getPassword();
    }

    /**
     * Gets the username.
     * 
     * @return The username.
     */
    protected String getUsername() {
        return this.username;
    }

    /**
     * Gets the password.
     * 
     * @return The password.
     */
    protected String getPassword() {
        return this.password;
    }

    /**
     * Creates a new builder with the target host and a default port number.
     * 
     * @param host
     *            The host.
     * @return A new builder.
     */
    public static Builder host(String host) {
        return new Builder(host);
    }

    /**
     * Creates a new builder with the target host and port number.
     * 
     * @param host
     *            The host.
     * @param port
     *            The port number.
     * @return A new builder.
     */
    public static Builder host(String host, int port) {
        return new Builder(host, port);
    }

    @Override
    public Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", this.getSmtpHost());
        props.put("mail.smtp.port", Integer.toString(this.getPort()));

        final String username = this.getUsername();
        final String password = this.getPassword();
        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    /**
     * Builds AuthenticatedSessionProvider objects.
     */
    public static class Builder extends SessionProvider.Builder implements AuthenticatedSessionProviderBuilder {
        private String username;
        private String password;

        /**
         * Initializes a new instance of the Builder class.
         * 
         * @param host
         *            The host.
         */
        public Builder(String host) {
            super(host);
        }

        /**
         * Initializes a new instance of the Builder class.
         * 
         * @param host
         *            The host.
         * @param port
         *            The port.
         */
        public Builder(String host, int port) {
            super(host, port);
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        /**
         * Creates an authenticated session provider.
         * 
         * @param username
         *            The username.
         * @param password
         *            The password.
         * @return An authenticated session provider implementation.
         */
        public SessionProvider userPassword(String username, String password) {
            this.username = username;
            this.password = password;
            return new AuthenticatedSessionProvider(this);
        }

        /**
         * Creates an authenticated session provider with transport layer security
         * enabled.
         * 
         * @param username
         *            The username.
         * @param password
         *            The password.
         * @return An authenticated session provider implemenation with transport layer
         *         security enabled.
         */
        public SessionProvider tlsAuth(String username, String password) {
            this.username = username;
            this.password = password;
            return new TlsSessionProvider(this);
        }

        /**
         * Creates an authenticated session provider with SSL enabled.
         * 
         * @param username
         *            The username.
         * @param password
         *            The password.
         * @return An authenticated session provider implementation with SSL enabled.
         */
        public SessionProvider sslAuth(String username, String password) {
            this.username = username;
            this.password = password;
            return new SslSessionProvider(this);
        }
    }
}
