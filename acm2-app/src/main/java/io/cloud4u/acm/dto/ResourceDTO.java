package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("resourceDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResourceDTO {
	String res_id;
	String res_name;
	String res_path;
	String res_info;
	String res_desc;

	@Builder
	public ResourceDTO(String res_id, String res_name, String res_path, String res_info, String res_desc) {
		super();
		this.res_id = res_id;
		this.res_name = res_name;
		this.res_path = res_path;
		this.res_info = res_info;
		this.res_desc = res_desc;
	}

}
