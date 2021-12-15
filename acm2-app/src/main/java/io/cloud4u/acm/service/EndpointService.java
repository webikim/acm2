package io.cloud4u.acm.service;

import java.util.List;

import io.cloud4u.acm.dto.MethodDTO;
import io.cloud4u.acm.vo.EndpointVO;

public interface EndpointService {

	List<EndpointVO> getEndpoints();

	void saveEndpoint(EndpointVO vo);

	int saveMethod(MethodDTO dto);

}
