package io.cloud4u.acm.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.cloud4u.acm.dto.KeyStoreDTO;

@Repository
public interface KeyStoreRepo {
	public List<KeyStoreDTO> select(KeyStoreDTO param);
	public int insert(KeyStoreDTO param);
	public int delete(KeyStoreDTO param);
}
