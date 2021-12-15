package io.cloud4u.acm.service;

import io.cloud4u.acm.exception.AlreadyExistException;
import io.cloud4u.acm.exception.InvalidUserException;
import io.cloud4u.acm.exception.NotEnabledException;
import io.cloud4u.acm.vo.UserVO;

public interface SignService {

	String signin(UserVO user) throws NotEnabledException, InvalidUserException, AlreadyExistException;

	boolean signup(UserVO user) throws AlreadyExistException, InvalidUserException;

	boolean signupVerified(String valid_id, String signature);

	void signout(String user_id);


}
