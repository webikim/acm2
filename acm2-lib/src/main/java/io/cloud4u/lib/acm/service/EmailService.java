package io.cloud4u.lib.acm.service;

import java.util.List;

import io.cloud4u.lib.acm.vo.ImageVO;

public interface EmailService {

	void sendEmail(String toAddress, String subject, String body);

	void sendEmail(String toAddress, String subject, String body, Boolean isHtml, List<ImageVO> images);

}
