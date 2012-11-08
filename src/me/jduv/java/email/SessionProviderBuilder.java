package me.jduv.java.email;

/**
 * Defines a contract for a basic SessionProvider builder. This follows the standard
 * builder pattern described by Joshua Boch in the "Effective Java Reloaded" series.
 */
public interface SessionProviderBuilder {
    /**
     * Gets the host.
     * 
     * @return The host.
     */
    public String getSmtpHost();

    /**
     * Gets the port.
     * 
     * @return The port.
     */
    public int getPort();
}
