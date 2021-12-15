package io.cloud4u.acm.service;

import io.cloud4u.acm.vo.PasswordVO;

public interface PasswordService {

	boolean changePassword(PasswordVO vo);

	boolean resetRequest(String userId);

	boolean resetUpdate(String valid_id, String signature, String password);


}
