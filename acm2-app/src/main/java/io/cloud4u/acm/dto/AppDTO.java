package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import io.cloud4u.acm.vo.AppVO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("appDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppDTO {
	String app_id;
	String key_id;
	String app_endpoint;
	String app_token;

	@Builder
	public AppDTO(String app_id, String key_id, String app_endpoint, String app_token) {
		super();
		this.app_id = app_id;
		this.key_id = key_id;
		this.app_endpoint = app_endpoint;
		this.app_token = app_token;
	}

	public AppVO toVO() {
		return AppVO.builder().aid(app_id).kid(key_id).build();
	}

}
