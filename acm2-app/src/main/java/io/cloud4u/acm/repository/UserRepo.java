package io.cloud4u.acm.repository;

import org.springframework.stereotype.Repository;

import io.cloud4u.acm.dto.IdentityDTO;
import io.cloud4u.acm.dto.UserDTO;

@Repository
public interface UserRepo {
	public UserDTO selectUser(UserDTO param);
	public UserDTO selectUserAccount(UserDTO param);
	public int insertUser(UserDTO param);
	public int updateUser(UserDTO param);
	public int insertIdentity(IdentityDTO param);
}
