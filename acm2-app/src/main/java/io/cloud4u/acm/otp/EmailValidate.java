//package io.cloud4u.acm.otp;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.regex.Pattern;
//
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMessage.RecipientType;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class EmailValidate {
//	private static final String CONTENT_FILE_PATH = "data/resetPasswd.html";
//	
//	private static final String RESET_PASSWD_MAIL_TITLE = "VisCAt Password Reset";
//	
//	private static final Pattern ACTION_URL_REGEX = Pattern.compile("\\{\\{action_url\\}\\}");
//	private static final Pattern USER_NAME_REGEX = Pattern.compile("\\{\\{name\\}\\}");
//	private static final Pattern SECRET_REGEX = Pattern.compile("\\{\\{secret\\}\\}");
//	
//	@Autowired
//	private JavaMailSender javaMailSender;
//	
//	public String resetMailBody(String url, String userName, String secret) {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(CONTENT_FILE_PATH)));
//		String line = null;
//		StringBuilder builder = new StringBuilder();
//		try {
//			while((line = reader.readLine()) != null) {
//				builder.append(line);
//			}
//			reader.close();
//			String text = builder.toString();
//			text = ACTION_URL_REGEX.matcher(text).replaceAll(url);
//			text = USER_NAME_REGEX.matcher(text).replaceAll(userName);
//			text = SECRET_REGEX.matcher(text).replaceAll(secret);
//			return text;
//		} catch (IOException e) {
//			log.error("cannot read file : {}", CONTENT_FILE_PATH);
//		}
//		return null;
//	}
//	
//	public void sendEmail(String email, String body) throws Exception {
//		try {
//			final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
//			message.setFrom("sender@example.com");
//			message.setTo("recipient@example.com");
//			message.setSubject("This is the message subject");
//			message.setText("This is the message body");
//			javaMailSender.send(mimeMessage);
//
////			MimeMessage msg = javaMailSender.createMimeMessage();
////			msg.addRecipient(RecipientType.TO, new InternetAddress(email));
////			msg.setSubject(RESET_PASSWD_MAIL_TITLE);
////			msg.setText(body, "UTF-8", "html");
////			
////			javaMailSender.send(msg);
//		} catch (Exception e) {
//			log.error("Cannot send email", e);
//			throw e;
//		}
//	}
//
//}
