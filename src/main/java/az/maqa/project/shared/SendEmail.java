package az.maqa.project.shared;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.print.attribute.standard.Destination;
import javax.mail.Message;

public class SendEmail {

	public static boolean sendMail(String to) {

		// The subject line for the email.
		final String SUBJECT = "One last step to complete your registration with PhotoApp";

		// The HTML body for the email.
		final String HTMLBODY = "<h1>Please verify your email address</h1>"
				+ "<p>Thank you for registering with our mobile app. To complete registration process and be able to log in,"
				+ " click on the following link: "
				+ "<a href='http://localhost:8081/verification-service/email-verification.html?token=$tokenValue'>"
				+ "Final step to complete your registration" + "</a><br/><br/>"
				+ "Thank you! And we are waiting for you inside!";

		// The email body for recipients with non-HTML email clients.
		final String TEXTBODY = "Please verify your email address. "
				+ "Thank you for registering with our mobile app. To complete registration process and be able to log in,"
				+ " open then the following URL in your browser window: "
				+ " http://localhost:8081/verification-service/email-verification.html?token=$tokenValue"
				+ " Thank you! And we are waiting for you inside!";

		boolean result = false;

		final String username = "heri.men1995@gmail.com";
		final String password = "herimen1995";

		Properties p = new Properties();
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "25");

		Session session = Session.getInstance(p, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("heri.men1995@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("heri.men1995@gmail.com"));
			message.setSubject(SUBJECT);
			message.setContent(HTMLBODY, "text/html");

			Transport.send(message);

			result = true;
			System.out.println("Message Sended...");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public static boolean sendPasswordResetRequest(String firstName, String email, String token) {
		final String PASSWORD_RESET_HTMLBODY = "<h1>A request to reset your password</h1>" + "<p>Hi, $firstName!</p> "
				+ "<p>Someone has requested to reset your password with our project. If it were not you, please ignore it."
				+ " otherwise please click on the link below to set a new password: "
				+ "<a href='http://localhost:8081/verification-service/password-reset.html?token=$tokenValue'>"
				+ " Click this link to Reset Password" + "</a><br/><br/>" + "Thank you!";

		boolean returnValue = false;

		final String PASSWORD_RESET_SUBJECT = "Password reset request";

		String htmlBodyWithToken = PASSWORD_RESET_HTMLBODY.replace("$tokenValue", token);
		htmlBodyWithToken = htmlBodyWithToken.replace("$firstName", firstName);

		final String username = "heri.men1995@gmail.com";
		final String password = "herimen1995";

		Properties p = new Properties();
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "25");

		Session session = Session.getInstance(p, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("heri.men1995@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("heri.men1995@gmail.com"));
			message.setSubject(PASSWORD_RESET_SUBJECT);
			message.setContent(htmlBodyWithToken, "text/html");

			Transport.send(message);

			returnValue = true;
			System.out.println("Message Sended...");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnValue;

	}
}
