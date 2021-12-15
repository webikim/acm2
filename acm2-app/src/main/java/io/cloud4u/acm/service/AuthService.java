package io.cloud4u.acm.service;

import java.util.List;

import io.cloud4u.acm.vo.AuthVO;

public interface AuthService {

	List<AuthVO> getAuths();
	AuthVO getAuth(String auth_id, String auth_name);
	void saveAuth(AuthVO vo);
	List<String> getIdentityAuth(String iden_id);
	void saveIdentityAuth(String iden_id, List<String> auth_names);

}
