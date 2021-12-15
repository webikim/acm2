package io.cloud4u.acm.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import io.cloud4u.acm.dto.PermissionDTO;
import io.cloud4u.acm.dto.ResourceDTO;

@Repository
public interface ResourceRepo {
	public ResourceDTO selectResource(ResourceDTO param);
	public int insertResource(ResourceDTO param);
	public int insertPermission(PermissionDTO param);
	public int updatePermission(PermissionDTO param);
	public String selectPermission(@Param("user_id")String user_id, @Param("res_name")String res_name, @Param("res_path")String res_path);
	public int deletePermission(@Param("user_id")String user_id, @Param("res_name")String res_name, @Param("res_path")String res_path);
}
