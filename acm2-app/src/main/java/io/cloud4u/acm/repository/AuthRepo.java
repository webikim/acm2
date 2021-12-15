package io.cloud4u.acm.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import io.cloud4u.acm.dto.AuthDTO;
import io.cloud4u.acm.dto.AuthEndpointDTO;
import io.cloud4u.acm.dto.IdentityAuthDTO;

@Repository
public interface AuthRepo {
	int insertAuth(AuthDTO param);
	List<AuthDTO> selectAuth(AuthDTO param);
	int insertAuthEndpoints(List<AuthEndpointDTO> param);
	int deleteAuthEndpoints(List<AuthEndpointDTO> param);
	List<IdentityAuthDTO> selectIdentityAuth(@Param("iden_id") String param);
	int insertIdentityAuth(List<IdentityAuthDTO> param);
	int deleteIdentityAuth(List<IdentityAuthDTO> param);
}
