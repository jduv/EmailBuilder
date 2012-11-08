package me.jduv.java.email;

/**
 * Defines a contract for implementations that can create basic email bodies. This follows
 * the standard builder pattern described by Joshua Boch in the "Effective Java Reloaded"
 * series. It's slightly modified because my builders are "inspectable" but that's the
 * only difference.
 */
public interface EmailBodyBuilder {

    /**
     * Sets the content for the body.
     * 
     * @param body
     *            The body.
     * @return The builder.
     */
    public EmailBodyBuilder content(String body);

    /**
     * Sets the type for the body.
     * 
     * @param type
     *            The type.
     * @return The builder.
     */
    public EmailBodyBuilder type(String type);

    /**
     * Sets content for the target tag.
     * 
     * @param tag
     *            The tag.
     * @param content
     *            The content.
     * @return The builder.
     */
    public EmailBodyBuilder replace(String tag, Object content);

    /**
     * Sets the delimiter for templates.
     * 
     * @param delimiter
     *            The delimiter.
     * @return The builder.
     */
    public EmailBodyBuilder delimiter(char delimiter);

    /**
     * Creates an EmailBody.
     * 
     * @return A new EmailBody instance.
     */
    public EmailBody build();
}
