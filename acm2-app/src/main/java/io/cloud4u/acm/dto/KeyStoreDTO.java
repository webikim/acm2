package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("keystoreDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class KeyStoreDTO {
	String key_id;
	char key_type;
	String key_body;
	
	@Builder
	public KeyStoreDTO(String key_id, char key_type, String key_body) {
		this.key_id = key_id;
		this.key_type = key_type;
		this.key_body = key_body;
	}
}
