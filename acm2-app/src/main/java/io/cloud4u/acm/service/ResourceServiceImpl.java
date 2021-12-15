package io.cloud4u.acm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cloud4u.acm.dto.PermissionDTO;
import io.cloud4u.acm.dto.ResourceDTO;
import io.cloud4u.acm.dto.UserDTO;
import io.cloud4u.acm.repository.ResourceRepo;
import io.cloud4u.acm.repository.UserRepo;
import io.cloud4u.acm.vo.ResourceVO;
import io.cloud4u.common.lib.util.UUIDUtil;
import lombok.NonNull;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepo resRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public boolean registerPermission(@NonNull ResourceVO vo) {
		UserDTO user = userRepo.selectUser(UserDTO.builder().user_id(vo.getUser_id()).build());
		if (user != null) {
			ResourceDTO resDto = ResourceDTO.builder()
					.res_id(UUIDUtil.short_uuid())
					.res_name(vo.getRes_name())
					.res_path(vo.getRes_path())
					.res_info(vo.getRes_info())
					.res_desc(vo.getRes_desc()).build();
			if (resRepo.insertResource(resDto) == 1) {
				resRepo.insertPermission(PermissionDTO.builder()
						.owner(user.getIden_id())
						.target(resDto.getRes_id())
						.permission(vo.getPermission()).build());
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getPermission(@NonNull ResourceVO vo) {
		return resRepo.selectPermission(vo.getUser_id(), vo.getRes_name(), vo.getRes_path());
	}
	
	@Override
	public boolean updatePermission(@NonNull ResourceVO vo) {
		UserDTO user = userRepo.selectUser(UserDTO.builder().user_id(vo.getUser_id()).build());
		if (user != null) {
			ResourceDTO resDto = resRepo.selectResource(ResourceDTO.builder()
					.res_name(vo.getRes_name())
					.res_path(vo.getRes_path()).build());
			if (resDto != null) {
				resRepo.updatePermission(PermissionDTO.builder()
						.owner(user.getIden_id())
						.target(resDto.getRes_id())
						.permission(vo.getPermission()).build());
				return true;
			}
		}
		return false;
	}

	@Override
	public int removePermission(@NonNull ResourceVO vo) {
		return resRepo.deletePermission(vo.getUser_id(), vo.getRes_name(), vo.getRes_path());
	}
	
}
