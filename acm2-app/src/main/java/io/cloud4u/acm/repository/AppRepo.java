package io.cloud4u.acm.repository;

import org.springframework.stereotype.Repository;

import io.cloud4u.acm.dto.AppDTO;

@Repository
public interface AppRepo {
	public AppDTO selectApp(AppDTO param);
	public int insertApp(AppDTO param);
	public AppDTO select(String app_id);
	public int delete(String app_id);
}
