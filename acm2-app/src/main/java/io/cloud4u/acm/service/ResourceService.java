package io.cloud4u.acm.service;

import io.cloud4u.acm.vo.ResourceVO;
import lombok.NonNull;

public interface ResourceService {

	boolean registerPermission(ResourceVO vo);

	String getPermission(ResourceVO vo);

	boolean updatePermission(ResourceVO vo);

	int removePermission(@NonNull ResourceVO vo);

}
