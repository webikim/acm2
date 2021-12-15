package io.cloud4u.acm.service;

import io.cloud4u.acm.vo.GrantVO;
import io.cloud4u.lib.acm.exception.UnauthorizedException;

public interface AppService {

	GrantVO grantApp(String headerToken, String aid, String ip) throws UnauthorizedException;

//	int saveApp(AppDTO dto);

}
