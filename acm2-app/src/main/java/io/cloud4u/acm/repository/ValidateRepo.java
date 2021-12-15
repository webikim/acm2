package io.cloud4u.acm.repository;

import org.springframework.stereotype.Repository;

import io.cloud4u.acm.dto.ValidateDTO;

@Repository
public interface ValidateRepo {
	public ValidateDTO select(ValidateDTO param);
	int insert(ValidateDTO param);
	int delete(String valid_id);
}
