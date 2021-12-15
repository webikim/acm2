package io.cloud4u.acm.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cloud4u.acm.config.property.UserConfigure;
import io.cloud4u.acm.define.UserStatus;
import io.cloud4u.acm.define.UserType;
import io.cloud4u.acm.dto.IdentityDTO;
import io.cloud4u.acm.dto.SessionDTO;
import io.cloud4u.acm.dto.UserDTO;
import io.cloud4u.acm.dto.ValidateDTO;
import io.cloud4u.acm.exception.AlreadyExistException;
import io.cloud4u.acm.exception.InvalidUserException;
import io.cloud4u.acm.exception.NotEnabledException;
import io.cloud4u.acm.repository.SessionRepo;
import io.cloud4u.acm.repository.UserRepo;
import io.cloud4u.acm.token.KeyPairManager;
import io.cloud4u.acm.token.PasswordManager;
import io.cloud4u.acm.token.RefreshTokenManager;
import io.cloud4u.acm.vo.UserVO;
import io.cloud4u.common.lib.util.DateUtil;
import io.cloud4u.common.lib.util.UUIDUtil;

@Service
public class SiginServiceImpl implements SignService {
	
	@Autowired
	private ValidationService service;
	
	@Autowired
	private KeyPairManager keyMan;
	
	@Autowired
	private PasswordManager passMan;
	
	@Autowired
	private RefreshTokenManager tokMan;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private SessionRepo sessionRepo;
	
	@Autowired
	private UserConfigure config;

	@Transactional
	@Override
	public boolean signup(UserVO user) throws AlreadyExistException, InvalidUserException {
		if (userRepo.selectUser(UserDTO.builder().user_name(user.getUserid()).build()) != null)
			throw new AlreadyExistException("User already exist !");
		if (user.getUserid() != null && user.getUserid().length() > 0) {
			if (user.getPassword() != null && user.getPassword().length() > 0) {
				IdentityDTO idenDto = IdentityDTO.builder()
						.iden_id(UUIDUtil.short_uuid())
						.key_id(keyMan.generateKid())
						.build();
				userRepo.insertIdentity(idenDto);
				UserDTO userDto = UserDTO.builder()
						.user_id(UUIDUtil.createUUID())
						.iden_id(idenDto.getIden_id())
						.user_name(user.getUserid())
						.user_email(user.getEmail())
						.user_password(passMan.encryptPassword(user.getPassword()))
						.user_type(UserType.EMAIL.getType())
						.user_twopass(user.getTwopass())
						.user_secret(user.getSecret())
						.user_status(config.getSignup_verify() ? UserStatus.PENDING.getStatus() : UserStatus.ENABLED.getStatus())
						.build();
				userRepo.insertUser(userDto);
//					user.setIden_id(idenDto.getIden_id());
//					user.setUser_id(userDto.getUser_id());
				if (config.getSignup_verify())
					service.sendSignUpValidation(userDto);
				return true;
			}
		}
		throw new InvalidUserException("User Id is invalid !");
	}
	
	@Override
	public boolean signupVerified(String valid_id, String signature) {
		ValidateDTO validDto = service.checkValid(valid_id, signature);
		if (validDto != null) {
			userRepo.updateUser(UserDTO.builder()
					.user_id(validDto.getUser_id())
					.user_status(UserStatus.ENABLED.getStatus()).build());
			service.removeValidRecord(valid_id);
			return true;
		}
		return false;
	}
	
	@Override
	public String signin(UserVO user) throws NotEnabledException, InvalidUserException, AlreadyExistException {
		UserDTO userDto = userRepo.selectUserAccount(
				UserDTO.builder()
				.user_name(user.getUserid())
				.user_password(passMan.encryptPassword(user.getPassword()))
				.build());
		if (userDto != null) {
			if (userDto.getUser_status() == UserStatus.ENABLED.getStatus()) {
				if (config.getSession_enable()) {
					SessionDTO sesDto = sessionRepo.select(userDto.getUser_id());
					if (sesDto != null) {
						if (sesDto.getSes_ip().equals(user.getIp()) || DateUtil.compareNow(sesDto.getSes_expire()) <= 0)
							sessionRepo.delete(SessionDTO.builder().ses_id(sesDto.getSes_id()).build());
						else
							throw new AlreadyExistException();
					}
					sessionRepo.insert(SessionDTO.builder()
							.ses_id(UUIDUtil.short_uuid())
							.user_id(userDto.getUser_id())
							.ses_ip(user.getIp())
							.ses_detail(user.getSession())
							.ses_expire(new Date(DateUtil.timeAfterMils(config.getSession_time())))
							.build());
				}
				// TODO twopass
				// TODO add permission to claim (second argument)
				return tokMan.buildToken(userDto.getKey_id(), null);
			} else {
				throw new NotEnabledException();
			}
		}
//		headers.setLocation(new URI(""));
//		headers.set(headerName, headerValue);
		throw new InvalidUserException();
	}
	
	@Override
	public void signout(String user_id) {
		sessionRepo.delete(SessionDTO.builder()
				.user_id(user_id)
				.build());
	}

}
