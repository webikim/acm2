package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("methodDto")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class MethodDTO {
	String suffix;
	String method;

	@Builder
	public MethodDTO(String suffix, String method) {
		super();
		this.suffix = suffix;
		this.method = method;
	}

}
