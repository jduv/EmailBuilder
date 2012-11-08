package me.jduv.java.email;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import me.jduv.java.util.Collections;
import me.jduv.java.util.Strings;

/**
 * Abstract object representing an email.
 */
public final class Email {
    private final InternetAddress fromAddress;
    private final List<InternetAddress> toAddresses;
    private final List<InternetAddress> ccAddresses;
    private final List<InternetAddress> bccAddresses;
    private final String subject;
    private final EmailBody body;
    private final SessionProvider sessionProvider;

    /**
     * Initializes a new instance of the Email class.
     * 
     * @param builder
     *            The email builder to initialize with.
     */
    protected Email(Builder builder) {

        // Set fields.
        this.fromAddress = builder.fromAddress;
        this.toAddresses = builder.toAddresses;
        this.ccAddresses = builder.ccAddresses;
        this.bccAddresses = builder.bccAddresses;
        this.subject = builder.subject;
        this.body = builder.body;
        this.sessionProvider = builder.sessionProvider;
    }

    /**
     * Creates a builder with the required session information.
     * 
     * @param provider
     *            The session provider.
     * @return An EmailBuilder.
     */
    public static EmailBuilder session(SessionProvider provider) {
        return new Builder(provider);
    }

    /**
     * Gets the from address.
     * 
     * @return The from address.
     */
    public InternetAddress getFromAddress() {
        return this.fromAddress;
    }

    /**
     * Gets the TO address list. Unmodifiable.
     * 
     * @return An unmodifiable list of TO addresses.
     */
    public List<InternetAddress> getToAddresses() {
        return java.util.Collections.unmodifiableList(this.toAddresses);
    }

    /**
     * Gets the CC address list. Unmodifiable.
     * 
     * @return An unmodifiable list of CC addresses.
     */
    public List<InternetAddress> getCcAddresses() {
        return java.util.Collections.unmodifiableList(this.ccAddresses);
    }

    /**
     * Gets the BCC address list. Unmodifiable.
     * 
     * @return An unmodifiable list of BCC addresses.
     */
    public List<InternetAddress> getBccAddresses() {
        return java.util.Collections.unmodifiableList(this.bccAddresses);
    }

    /**
     * Gets the subject line.
     * 
     * @return The subject line.
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * Gets the email body.
     * 
     * @return The email body.
     */
    public EmailBody getBody() {
        return this.body;
    }

    /**
     * Sends the email.
     * 
     * @throws MessagingException
     */
    public void send() throws MessagingException {

        MimeMessage message = new MimeMessage(this.sessionProvider.getSession());
        message.setSubject(this.getSubject());
        message.setContent(this.getBody().getContent(), this.getBody().getType());

        // Set from address.
        message.setFrom(this.getFromAddress());

        // Set TO addresses.
        if (!Collections.isNullOrEmpty(this.getToAddresses())) {
            message.addRecipients(
                    Message.RecipientType.TO,
                    this.getToAddresses().toArray(new InternetAddress[this.getToAddresses().size()]));
        }

        // Set CC addresses
        if (!Collections.isNullOrEmpty(this.getCcAddresses())) {
            message.addRecipients(
                    Message.RecipientType.CC,
                    this.getCcAddresses().toArray(new InternetAddress[this.getCcAddresses().size()]));
        }

        // Set BCC addresses
        if (!Collections.isNullOrEmpty(this.getBccAddresses())) {
            message.addRecipients(
                    Message.RecipientType.BCC,
                    this.getBccAddresses().toArray(new InternetAddress[this.getBccAddresses().size()]));
        }

        // Away with it.
        Transport.send(message);
    }

    /**
     * Builds email objects. I opted for an interface and a default implementation here
     * instead of concrete implementations and redirection trickery. It's just easier to
     * understand in my opinion. You can still subclass this and override it's behavior if
     * needed, but it should do pretty much everything you'd need.
     */
    public final static class Builder implements EmailBuilder {
        private SessionProvider sessionProvider;
        private InternetAddress fromAddress;
        private List<InternetAddress> toAddresses;
        private List<InternetAddress> ccAddresses;
        private List<InternetAddress> bccAddresses;
        private String subject;
        private EmailBody body;

        /**
         * Initializes a new instance of the Email.Builder class.
         * 
         * @param sessionProvider
         *            The session provider.
         */
        public Builder(SessionProvider sessionProvider) {
            if (sessionProvider == null) {
                throw new IllegalArgumentException("Session provider cannot be null!");
            }

            this.fromAddress = null;
            this.toAddresses = new ArrayList<InternetAddress>();
            this.ccAddresses = new ArrayList<InternetAddress>();
            this.bccAddresses = new ArrayList<InternetAddress>();
            this.subject = Strings.empty();
            this.body = EmailBody.fromString(Strings.empty());
            this.sessionProvider = sessionProvider;
        }

        @Override
        public EmailBuilder to(InternetAddress toAddress) {
            if (toAddress == null) {
                throw new IllegalArgumentException("TO address cannot be null!");
            }
            this.toAddresses.add(toAddress);
            return this;
        }

        @Override
        public EmailBuilder from(InternetAddress fromAddress) {
            if (fromAddress == null) {
                throw new IllegalArgumentException("FROM address cannot be null!");
            }
            this.fromAddress = fromAddress;
            return this;
        }

        @Override
        public EmailBuilder cc(InternetAddress ccAddress) {
            if (ccAddress == null) {
                throw new IllegalArgumentException("CC address cannot be null!");
            }
            this.ccAddresses.add(ccAddress);
            return this;
        }

        @Override
        public EmailBuilder bcc(InternetAddress bccAddress) {
            if (bccAddress == null) {
                throw new IllegalArgumentException("BCC address cannot be null!");
            }
            this.bccAddresses.add(bccAddress);
            return this;
        }

        @Override
        public EmailBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        @Override
        public EmailBuilder body(EmailBody body) {
            if (body == null) {
                throw new IllegalArgumentException("Body cannot be null!");
            }
            this.body = body;
            return this;
        }

        @Override
        public Email build() {
            // Do some validation. We at least need a from address and one to address.
            if (this.fromAddress == null) {
                throw new IllegalStateException("From address cannot be null or empty!");
            }
            if (Collections.isNullOrEmpty(this.toAddresses)) {
                throw new IllegalArgumentException("At least one TO: address is required!");
            }

            return new Email(this);
        }
    }
}
