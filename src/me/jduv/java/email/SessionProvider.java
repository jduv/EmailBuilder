package me.jduv.java.email;

import javax.mail.Session;

import me.jduv.java.util.Strings;


/**
 * Provides sessions for mail clients.
 */
public abstract class SessionProvider {
    public static final int DEFAULT_SMTP_PORT = 25;
    private final String smtpHost;
    private final int port;

    /**
     * Initializes a new instance of the SessionProvider class.
     * 
     * @param builder
     *            The builder.
     */
    public SessionProvider(SessionProviderBuilder builder) {
        this.smtpHost = builder.getSmtpHost();
        this.port = builder.getPort();
    }

    /**
     * Creates a new builder with the target host and a default port number.
     * 
     * @param host
     *            The host.
     * @return An unauthenticated session provider.
     */
    public static SessionProvider unauthenticated(String host) {
        return new Builder(host).unauthenticated();
    }

    /**
     * Creates a new builder with the target host and port number.
     * 
     * @param host
     *            The host.
     * @param port
     *            The port number.
     * @return An unauthenticated session provider.
     */
    public static SessionProvider unauthenticated(String host, int port) {
        return new Builder(host, port).unauthenticated();
    }

    /**
     * Gets the host.
     * 
     * @return The host.
     */
    public String getSmtpHost() {
        return this.smtpHost;
    }

    /**
     * Gets the port.
     * 
     * @return The port.
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Gets the session.
     * 
     * @return The session.
     */
    public abstract Session getSession();

    /**
     * Builds session providers.
     */
    public static class Builder implements SessionProviderBuilder {
        private String host;
        private int port;

        /**
         * Creates a default session provider builder.
         * 
         * @param host
         *            The host.
         */
        public Builder(String host) {
            this(host, DEFAULT_SMTP_PORT);
        }

        /**
         * Creates a default session provider builder.
         * 
         * @param host
         *            The host.
         * @param port
         *            The port.
         */
        public Builder(String host, int port) {
            if (Strings.isNullOrEmpty(host)) {
                throw new IllegalArgumentException("Host cannot be null or empty!");
            }
            if (port < 0 || port > 65535) {
                throw new IllegalArgumentException("Invalid port number! It's out of range.");
            }

            this.host = host;
            this.port = port;
        }

        @Override
        public String getSmtpHost() {
            return this.host;
        }

        @Override
        public int getPort() {
            return this.port;
        }

        /**
         * Creates an unauthenticated session provider.
         * 
         * @return An unauthenticated session provider implementation.
         */
        public SessionProvider unauthenticated() {
            return new UnauthenticatedSessionProvider(this);
        }
    }
}
