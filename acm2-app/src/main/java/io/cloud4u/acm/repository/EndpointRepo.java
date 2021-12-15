package io.cloud4u.acm.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.cloud4u.acm.dto.AppDTO;
import io.cloud4u.acm.dto.EndpointDTO;
import io.cloud4u.acm.dto.MethodDTO;

@Repository
public interface EndpointRepo {
	public List<EndpointDTO> selectEndpoint(EndpointDTO param);
	public int insertEndpoint(EndpointDTO param);
	public int insertService(EndpointDTO param);
	public List<MethodDTO> selectMethod(MethodDTO param);
	public int insertMethod(MethodDTO param);
	public int insertApp(AppDTO param);
}
