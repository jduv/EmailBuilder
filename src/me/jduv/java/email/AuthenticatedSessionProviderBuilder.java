package me.jduv.java.email;

/**
 * Defines a contract for a basic Authenticated SessionProvider builder. This follows the
 * standard builder pattern described by Joshua Boch in the "Effective Java Reloaded"
 * series.
 */
public interface AuthenticatedSessionProviderBuilder extends SessionProviderBuilder {
    /**
     * Gets the username.
     * 
     * @return The username.
     */
    public String getUsername();

    /**
     * Gets the password.
     * 
     * @return The password.
     */
    public String getPassword();
}
