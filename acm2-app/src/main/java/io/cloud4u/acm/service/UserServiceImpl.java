package io.cloud4u.acm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cloud4u.acm.dto.UserDTO;
import io.cloud4u.acm.repository.UserRepo;
import lombok.NonNull;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public boolean existUser(@NonNull String userid) {
		return getUser(userid) != null;
	}

	@Override
	public UserDTO getUser(@NonNull String userid) {
		UserDTO user = userRepo.selectUser(UserDTO.builder().user_id(userid).build());
		return user;
	}
}
