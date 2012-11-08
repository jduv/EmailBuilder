package me.jduv.java.email;

import javax.mail.internet.InternetAddress;


/**
 * Defines a contract for a basic Email builder. This follows the standard builder pattern
 * described by Joshua Boch in the "Effective Java Reloaded" series.
 */
public interface EmailBuilder {

    /**
     * Adds an address to the TO list.
     * 
     * @param toAddress
     *            The address to add.
     * @return The builder.
     */
    public EmailBuilder to(InternetAddress toAddress);

    /**
     * Sets the from address.
     * 
     * @param fromAddress
     *            The from address.
     * @return The builder.
     */
    public EmailBuilder from(InternetAddress fromAddress);

    /**
     * Adds an address to the CC list.
     * 
     * @param ccAddress
     *            The address to add.
     * @return The builder.
     */
    public EmailBuilder cc(InternetAddress ccAddress);

    /**
     * Adds an address to the BCC list.
     * 
     * @param bccAddress
     *            The address to add.
     * @return The builder.
     */
    public EmailBuilder bcc(InternetAddress bccAddress);

    /**
     * Sets the subject line of the email.
     * 
     * @param subject
     *            The subject line.
     * @return The builder.
     */
    public EmailBuilder subject(String subject);

    /**
     * Sets the body of the email.
     * 
     * @param body
     *            The body.
     * @return The builder.
     */
    public EmailBuilder body(EmailBody body);

    /**
     * Builds and returns an email.
     * 
     * @return An email created by this builder implementation.
     */
    public Email build();
}
