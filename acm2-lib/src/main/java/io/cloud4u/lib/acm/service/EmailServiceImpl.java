package io.cloud4u.lib.acm.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import io.cloud4u.lib.acm.vo.ImageVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(String toAddress, String subject, String body) {
		sendEmail(toAddress, subject, body, false, null);
	}

	@Override
	public void sendEmail(String toAddress, String subject, String body, Boolean isHtml, List<ImageVO> images) {
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
//			message.setFrom("kihyeon.kim@gmail.com");
			message.setTo(toAddress);
			message.setSubject(subject);
			message.setText(body, isHtml);
			if (images != null && images.size() > 0) {
				for (ImageVO each : images)
			        message.addInline(each.getResourceName(), (InputStreamSource) each.getImageSource(), each.getContentType());
			}
		} catch (Exception e) {
			log.error("Cannot send email", e);
		}
		javaMailSender.send(mimeMessage);
	}

}
