package io.cloud4u.acm.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.cloud4u.acm.dto.EndpointDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EndpointVO {
	private Integer ord;
	private String svc_id;
	private String svc_name;
	private String svc_desc;
	private String url;
	private String suffix;
	private String method;

	@Builder
	public EndpointVO(Integer ord, String svc_id, String svc_name, String svc_desc, String url, String suffix,
			String method) {
		super();
		this.ord = ord;
		this.svc_id = svc_id;
		this.svc_name = svc_name;
		this.svc_desc = svc_desc;
		this.url = url;
		this.suffix = suffix;
		this.method = method;
	}
	
	public EndpointDTO toDTO() {
		return EndpointDTO.builder()
				.endpoint_ord(ord)
				.svc_id(svc_id)
				.svc_name(svc_name)
				.svc_desc(svc_desc)
				.svc_url(url)
				.suffix(suffix)
				.method(method)
				.build();
	}

}
