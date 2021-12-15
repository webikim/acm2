package io.cloud4u.acm.repository;

import org.springframework.stereotype.Repository;

import io.cloud4u.acm.dto.SessionDTO;

@Repository
public interface SessionRepo {
	SessionDTO select(String user_id);
	int insert(SessionDTO param);
	int delete(SessionDTO param);
}
