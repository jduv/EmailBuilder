package me.jduv.java.email;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.jduv.java.util.Strings;

import org.stringtemplate.v4.ST;

/**
 * The body of an email.
 */
public final class EmailBody {
    private final String content;
    private final String type;

    /**
     * Initializes a new instance of the EmailBody class.
     * 
     * @param builder
     *            The builder.
     */
    protected EmailBody(Builder builder) {
        this.content = builder.content;
        this.type = builder.type;
    }

    /**
     * Gets a default builder instance.
     * 
     * @return A default builder.
     */
    public static EmailBodyBuilder builder() {
        return new EmailBody.Builder();
    }

    /**
     * Creates an email body from the target string.
     * 
     * @param body
     *            The string.
     * @return An email body.
     */
    public static EmailBody fromString(String body) {
        return builder().content(body).build();
    }

    /**
     * Gets the content.
     * 
     * @return The content.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Gets the type.
     * 
     * @return The type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Handles building EmailBody objects.
     */
    public static class Builder implements EmailBodyBuilder {
        private String content;
        private String type;
        private Map<String, Object> replacements;
        private Character delimiter;

        /**
         * Initializes a new instance of the Builder class.
         */
        public Builder() {
            this.replacements = new HashMap<String, Object>();
            this.type = "text/html";
        }

        @Override
        public EmailBodyBuilder content(String body) {
            this.content = body;
            return this;
        }

        @Override
        public EmailBodyBuilder type(String type) {
            this.type = type;
            return this;
        }

        @Override
        public EmailBodyBuilder replace(String tag, Object content) {
            this.replacements.put(tag, content);
            return this;
        }

        @Override
        public EmailBodyBuilder delimiter(char delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        @Override
        public EmailBody build() {
            if (!Strings.isNullOrEmpty(this.content) && this.replacements.size() != 0) {
                // Use StringTemplate for replacements.
                ST template = this.delimiter == null ?
                        new ST(this.content) :
                            new ST(this.content, this.delimiter, this.delimiter);

                for (Entry<String, Object> entry : this.replacements.entrySet()) {
                    template.add(entry.getKey(), entry.getValue());
                }
                this.content = template.render();
            }

            return new EmailBody(this);
        }
    }
}
