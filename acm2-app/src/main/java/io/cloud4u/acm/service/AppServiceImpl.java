package io.cloud4u.acm.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cloud4u.acm.dto.AppDTO;
import io.cloud4u.acm.repository.AppRepo;
import io.cloud4u.acm.token.AccessTokenManager;
import io.cloud4u.acm.token.KeyPairManager;
import io.cloud4u.acm.vo.GrantVO;
import io.cloud4u.lib.acm.define.ClaimName;
import io.cloud4u.lib.acm.exception.UnauthorizedException;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private AppRepo appRepo;
	
	@Autowired
	private KeyPairManager keyMan;

	@Autowired
	private AccessTokenManager tokMan;
	
	@Override
	public GrantVO grantApp(String headerToken, String aid, String ip) throws UnauthorizedException {
		if (headerToken == null)
			throw new UnauthorizedException();

		String refToken = tokMan.extractToken(headerToken);
		String kid = tokMan.getKeyId(refToken);
		Map<String, Object> claims = tokMan.validateToken(refToken);
		if (claims == null)
			throw new UnauthorizedException();

		AppDTO dto = appRepo.selectApp(AppDTO.builder().app_id(aid).key_id(kid).build());
		if (dto != null) {
			claims = new HashMap<String, Object>();
			claims.put(ClaimName.IP, ip);
			String token = tokMan.buildToken(kid, claims);
			if (token == null)
				throw new UnauthorizedException();
			return GrantVO.builder()
					.token(token)
					.keys(keyMan.getOtherKeys()).build();
		}
		throw new UnauthorizedException();
	}
	
}
