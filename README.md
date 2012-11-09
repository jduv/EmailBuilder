Java Mail + Builder pattern
===========================

EmailBuilder v1.0
License: MIT

SUMMARY
------

Sending emails is a common programming task in enterprise computing. The Java mail implementation is a very good starting point for performing this task, but it's quite verbose and most times feels very procedural in nature. During a recent project (as of November 2012) I thought to myself: what if we hid all the procedural nastiness in a fluent API that's easy to understand? After a furious night of coding and learning, the EmailBuilder project/snippet was born. In essence, it's Joshua Boch's builder pattern sitting on top of the Java mail APIs. Through a session provider builder implementation it can handle the following types of authentication:

- Username/Password
- SSL Certificate
- TLS security
- Unauthenticated security

By using the session provider builder classes you can build out whichever one of these session types you need and then pass that to an email builder to create and send the email. In addition to authentication, the builder also accepts template substitutions via the StringTemplate class. This is your one-stop fluent shop for mail-merge and email origination.


Comments and critiques are welcome, and feel free to use it in your own projects. If you do any vast improvements to the code base, I'd appreciate a pull request.

EXAMPLES:
---------

Send an email using unauthenticated security.
```java
    SessionProvider session = SessionProvider.unauthenticated("my.mail.host");

    EmailBody body = EmailBody.builder()
            .content("This is my email body")
            .build();

    Email.session(session)
            .from(new InternetAddress("from@myaddress.com"))
            .to(new InternetAddress("target@theiraddress.com"))
            .cc(new InternetAddress("cc@targetaddress.com"))
            .bcc(new InternetAddress("supersecret@observer.com"))
            .subject("Hello world")
            .body(body)
            .build()
            .send();
```

Send an email using TLS security.
```java
	SessionProvider session = AuthenticatedSessionProvider.host("my.mail.host").tlsAuth("user", "pass");

    EmailBody body = EmailBody.builder()
            .content("This is my email body")
            .build();

    Email.session(session)
            .from(new InternetAddress("from@myaddress.com"))
            .to(new InternetAddress("target@theiraddress.com"))
            .subject("Hello world")
            .body(body)
            .build()
            .send();
```

Send an email while defining some replacements.
```java
    SessionProvider session = SessionProvider.unauthenticated("my.mail.host");

    EmailBody body = EmailBody.builder()
            .replace("$name$", "jduv")
            .delimiter('$')
            .content("Hello $name$!")
            .build();

    Email.session(session)
            .from(new InternetAddress("from@myaddress.com"))
            .to(new InternetAddress("target@theiraddress.com"))
            .subject("Hello world")
            .body(body)
            .build()
            .send();
```

TODO:
-----
Here's a list of known issues that hopefully I'll get around to fixing one day.

- Multi-part email bodies are currently unsupported.