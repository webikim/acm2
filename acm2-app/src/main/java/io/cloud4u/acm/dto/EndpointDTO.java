package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import io.cloud4u.acm.vo.EndpointVO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("endpointDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EndpointDTO {
	private Integer endpoint_ord;
	private String svc_id;
	private String svc_name;
	private String app_id;
	private String svc_url;
	private String svc_desc;
	private String suffix;
	private String method;

	@Builder
	public EndpointDTO(Integer endpoint_ord, String svc_id, String svc_name, String app_id, String svc_url,
			String svc_desc, String suffix, String method) {
		super();
		this.endpoint_ord = endpoint_ord;
		this.svc_id = svc_id;
		this.svc_name = svc_name;
		this.app_id = app_id;
		this.svc_url = svc_url;
		this.svc_desc = svc_desc;
		this.suffix = suffix;
		this.method = method;
	}
	
	public EndpointVO toVO() {
		return EndpointVO.builder()
				.ord(endpoint_ord)
				.svc_id(svc_id)
				.svc_name(svc_name)
				.svc_desc(svc_desc)
				.url(svc_url)
				.suffix(suffix)
				.method(method)
				.build();
	}

}
