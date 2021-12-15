package io.cloud4u.acm.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResourceVO {
	String res_id;
	String res_name;
	String res_path;
	String res_info;
	String res_desc;

	String user_id;
	String permission;

	@Builder
	public ResourceVO(String res_id, String res_name, String res_path, String res_info, String res_desc, String user_id,
			String permission) {
		super();
		this.res_id = res_id;
		this.res_name = res_name;
		this.res_path = res_path;
		this.res_info = res_info;
		this.res_desc = res_desc;
		this.user_id = user_id;
		this.permission = permission;
	}

}
