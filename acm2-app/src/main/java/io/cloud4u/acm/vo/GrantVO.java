package io.cloud4u.acm.vo;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.minidev.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GrantVO {
	String token;
	Map<String, JSONObject> keys;

	@Builder
	public GrantVO(String token, Map<String, JSONObject> keys) {
		super();
		this.token = token;
		this.keys = keys;
	}

}
