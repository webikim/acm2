package io.cloud4u.acm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cloud4u.acm.dto.UserDTO;
import io.cloud4u.acm.dto.ValidateDTO;
import io.cloud4u.acm.repository.UserRepo;
import io.cloud4u.acm.token.PasswordManager;
import io.cloud4u.acm.vo.PasswordVO;

@Service
public class PasswordServiceImpl implements PasswordService {
	
	@Autowired
	private ValidationService validService;
	
	@Autowired
	private PasswordManager passMan;

	@Autowired
	private UserRepo userRepo;

	@Override
	public boolean changePassword(PasswordVO vo) {
		ValidateDTO validDto = validService.checkValid(vo.getV(), vo.getS());
		if (validDto != null) {
			UserDTO userDto = userRepo.selectUser(UserDTO.builder().user_id(validDto.getUser_id()).build());
			if (userDto != null && vo.getOldPassword() != null && vo.getNewPassword() != null) {
				if (userDto.getUser_password().contentEquals(passMan.encryptPassword(vo.getOldPassword()))) {
					userRepo.updateUser(UserDTO.builder()
							.user_id(validDto.getUser_id())
							.user_password(passMan.encryptPassword(vo.getNewPassword())).build());
					validService.removeValidRecord(validDto.getValid_id());
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean resetRequest(String userId) {
		UserDTO userDto = userRepo.selectUser(UserDTO.builder().user_name(userId).build());
		if (userDto != null) {
			validService.sendResetValidation(userDto);
			return true;
		}
		return false;
	}

	@Override
	public boolean resetUpdate(String valid_id, String signature, String password) {
		ValidateDTO validDto = validService.checkValid(valid_id, signature);
		if (validDto != null) {
			userRepo.updateUser(UserDTO.builder().user_id(validDto.getUser_id()).user_password(passMan.encryptPassword(password)).build());
			return true;
		}
		return false;
	}
}
