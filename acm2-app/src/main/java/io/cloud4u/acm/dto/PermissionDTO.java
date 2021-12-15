package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("permissionDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PermissionDTO {
	String owner;
	String target;
	String permission;

	@Builder
	public PermissionDTO(String owner, String target, String permission) {
		super();
		this.owner = owner;
		this.target = target;
		this.permission = permission;
	}

}
