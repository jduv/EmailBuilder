package me.jduv.java.email;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.stringtemplate.v4.ST;

import me.jduv.java.util.Strings;

/**
 * The body of an email.
 */
public final class EmailBody {
	private final Multipart multipartMail;
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
        this.multipartMail = builder.multipartMail;
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
     * Gets the MultipartMail.		
     * 		
     * @return The type.		
     */		
    public Multipart getMultipartMail() {		
        return this.multipartMail;		
    }

    /**
     * Handles building EmailBody objects.
     */
    public static class Builder implements EmailBodyBuilder {
        private String content;
        private String type;
        private Map<String, Object> replacements;
        private Character delimiter;
        private Multipart multipartMail;

        /**
         * Initializes a new instance of the Builder class.
         */
        public Builder() {
            this.replacements = new HashMap<String, Object>();
            this.type = "text/html";
            multipartMail = new MimeMultipart();
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
        public EmailBodyBuilder addFileAttachment(File file) {		
        	try {		
    			MimeBodyPart fileBodyPart = new MimeBodyPart();		
    			DataSource source = new FileDataSource(file);		
    			fileBodyPart.setDataHandler(new DataHandler(source));		
    			fileBodyPart.setFileName(file.getName());		
    			this.multipartMail.addBodyPart(fileBodyPart);		
    		} catch (MessagingException e) {		
    			throw new RuntimeException("ERROR ADDING FILE MAIL ATTACHMENT: " + file.getName(), e);		
    		}		
    		return this;		
        }		
        		
        @Override		
        public EmailBodyBuilder addFileAttachment(InputStream is, String fileName, String fileType) {		
    		try {		
    			MimeBodyPart fileBodyPart = new MimeBodyPart();		
    			DataSource source = new ByteArrayDataSource(is, fileType);		
    			fileBodyPart.setDataHandler(new DataHandler(source));		
    			fileBodyPart.setFileName(fileName);		
    			this.multipartMail.addBodyPart(fileBodyPart);		
    		} catch (IOException ioe) {		
    			throw new RuntimeException("ERROR ADDING FILE MAIL ATTACHMENT: " + fileName, ioe);		
    		} catch (MessagingException e) {		
    			throw new RuntimeException("ERROR ADDING FILE MAIL ATTACHMENT: " + fileName, e);		
    		}		
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
