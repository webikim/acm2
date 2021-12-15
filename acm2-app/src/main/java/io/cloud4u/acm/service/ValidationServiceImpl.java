package io.cloud4u.acm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import io.cloud4u.acm.config.property.AcmConfigure;
import io.cloud4u.acm.define.ValidType;
import io.cloud4u.acm.dto.UserDTO;
import io.cloud4u.acm.dto.ValidateDTO;
import io.cloud4u.acm.exception.NotValidException;
import io.cloud4u.acm.repository.ValidateRepo;
import io.cloud4u.acm.token.AccessTokenManager;
import io.cloud4u.acm.token.KeyPairManager;
import io.cloud4u.common.lib.util.DateUtil;
import io.cloud4u.common.lib.util.UUIDUtil;
import io.cloud4u.common.spring.define.TemplateType;
import io.cloud4u.common.spring.template.TemplateUtil;
import io.cloud4u.lib.acm.define.ClaimName;
import io.cloud4u.lib.acm.service.EmailService;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private AcmConfigure config;
	
	@Autowired
	private KeyPairManager keyMan;

	@Autowired
	private AccessTokenManager tokMan;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ValidateRepo validRepo;
	
	private TemplateEngine templateEngine;
	
	@PostConstruct
	private void templateEngineInit() {
		ITemplateResolver resolver1 = TemplateUtil.templateResolverFactory(1, TemplateType.HTML, "/templates/email/", "UTF-8");
		ITemplateResolver resolver2 = TemplateUtil.templateResolverFactory(1, TemplateType.TEXT, "/templates/email/", "UTF-8");
		ITemplateResolver resolver3 = TemplateUtil.templateResolverFactory(1, TemplateType.HTML, null, "UTF-8");
		List<ITemplateResolver> resolvers = new ArrayList<ITemplateResolver>();
		resolvers.add(resolver1);
		resolvers.add(resolver2);
		resolvers.add(resolver3);
		templateEngine = TemplateUtil.templateEngineFactory(resolvers);
	}
	
	private String createToken(long expire) {
		Map<String, Object> claims = new HashMap<String, Object>();
//		long expire = DateUtil.timeAfterMils(config.getResetExpire());
		claims.put(ClaimName.EXPIRE, Long.toString(expire));
		return tokMan.buildToken(keyMan.generateKid(), claims);
	}
	
	private String verifyUrl(ValidType type, String valid_id, String signature) {
		return new StringBuilder()
				.append(config.getVerify())
				.append("?")
				.append("c=")
				.append(type.getType())
				.append("&v=")
				.append(valid_id)
				.append("&s=")
				.append(signature).toString();
	}
	
//	private String validatePath(ValidType type) {
//		return new StringBuilder()
//				.append(VALIDATE_PATH)
//				.append("/")
//				.append(type.getPath())
//				.toString();
//	}

	@Override
	public void sendSignUpValidation(UserDTO userDto) {
		long expire = DateUtil.timeAfterMils(config.getSignUpExpire());
		String token = createToken(expire);
		ValidateDTO validDto = ValidateDTO.builder()
				.valid_id(UUIDUtil.createUUID())
				.user_id(userDto.getUser_id())
				.valid_type(ValidType.SIGNUP.getType())
				.valid_secret(tokMan.getHeaderClaim(token))
				.valid_expire(new Date(expire))
				.build();
		validRepo.insert(validDto);

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("name", userDto.getUser_name());
		args.put("url", verifyUrl(ValidType.SIGNUP, validDto.getValid_id(), tokMan.getSginature(token)));
		String html = TemplateUtil.processTemplate(templateEngine, ValidType.SIGNUP.getTemplate(), args, new Locale("UTF-8"));

		emailService.sendEmail(userDto.getUser_email(), "Welcome, please verify email and finish signng up.", html, true, null);
	}
	
	@Override
	public void sendResetValidation(UserDTO userDto) {
		long expire = DateUtil.timeAfterMils(config.getResetExpire());
		String token = createToken(expire);
		ValidateDTO validDto = ValidateDTO.builder()
				.valid_id(UUIDUtil.createUUID())
				.user_id(userDto.getUser_id())
				.valid_type(ValidType.RESET.getType())
				.valid_secret(tokMan.getHeaderClaim(token))
				.valid_expire(new Date(expire))
				.build();
		validRepo.insert(validDto);

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("name", userDto.getUser_name());
		args.put("url", verifyUrl(ValidType.RESET, validDto.getValid_id(), tokMan.getSginature(token)));

		String html = TemplateUtil.processTemplate(templateEngine, ValidType.RESET.getTemplate(), args, new Locale("UTF-8"));

		emailService.sendEmail(userDto.getUser_email(), "Welcome, please verify email to change password.", html, true, null);
	}
	
	@Override
	public String receiveValidation(char type, String valid_id, String signature) throws NotValidException {
		ValidateDTO validDto = checkValid(valid_id, signature);
		if (validDto != null) {
//		ValidateDTO validDto = validRepo.select(ValidateDTO.builder().valid_id(valid_id).build());
//		if (validDto != null && signature != null) {
//			if (DateUtil.compareNow(validDto.getValid_expire()) <= 0) {
//				if (tokMan.validateToken(new StringBuffer().append(validDto.getValid_secret()).append('.').append(signature).toString()) != null) {
					if (ValidType.SIGNUP.getType() == type)
						return ValidType.getValidType(type).getForward();
					else if (ValidType.RESET.getType() == type)
						return ValidType.getValidType(type).getForward();
//				}
//			} else
//				return ValidateVO.builder().redirect(false).url(EXPIRED_PAGE).build();
		}
		throw new NotValidException();
	}
	
	@Override
	public ValidateDTO checkValid(String valid_id, String signature) {
		ValidateDTO validDto = validRepo.select(ValidateDTO.builder().valid_id(valid_id).build());
		if (validDto != null && signature != null) {
			if (DateUtil.compareNow(validDto.getValid_expire()) > 0) {
				if (tokMan.validateToken(new StringBuffer().append(validDto.getValid_secret()).append('.').append(signature).toString()) != null) 
					return validDto;
			}
		}
		return null;
	}
	
	@Override
	public void removeValidRecord(String valid_id) {
		validRepo.delete(valid_id);
	}
}
