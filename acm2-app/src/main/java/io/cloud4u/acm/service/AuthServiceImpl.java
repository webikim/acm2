package io.cloud4u.acm.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cloud4u.acm.dto.AuthDTO;
import io.cloud4u.acm.dto.AuthEndpointDTO;
import io.cloud4u.acm.dto.IdentityAuthDTO;
import io.cloud4u.acm.repository.AuthRepo;
import io.cloud4u.acm.vo.AuthVO;
import io.cloud4u.common.lib.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthRepo authRepo;
	
	private List<AuthEndpointDTO> buildAuthEndpoints(String auth_id, List<Integer> ords) {
		List<AuthEndpointDTO> list = new ArrayList<AuthEndpointDTO>();
		for (Integer ord : ords) {
			list.add(AuthEndpointDTO.builder()
					.auth_id(auth_id)
					.endpoint_ord(ord).build());
		}
		return list;
	}
	
	private int addAuthEndpoints(String auth_id, List<Integer> ords) {
		List<AuthEndpointDTO> list = buildAuthEndpoints(auth_id, ords);
		int count = authRepo.insertAuthEndpoints(list);
		if (count < list.size())
			;	// TODO warning not all records are inserted
		return count;
	}
	
	private int removeAuthEndpoints(String auth_id, List<Integer> ords) {
		List<AuthEndpointDTO> list = buildAuthEndpoints(auth_id, ords);
		int count = authRepo.deleteAuthEndpoints(list);
		if (count < list.size())
			;	// TODO warning not all records are deleted
		return count;
	}
	
	@Override
	public List<AuthVO> getAuths() {
		List<AuthVO> auths = new ArrayList<AuthVO>();
		List<AuthDTO> dbresult = authRepo.selectAuth(new AuthDTO());
		dbresult.sort(new Comparator<AuthDTO>() {

			@Override
			public int compare(AuthDTO arg0, AuthDTO arg1) {
				return arg0.getAuth_name().compareTo(arg1.getAuth_name());
			}
		});
		AuthVO vo = null;
		String auth_name = null;
		List<Integer> ords = null;
		for (AuthDTO each : dbresult) {
			System.out.println(each);
			System.out.println(auth_name);
			if (each.getAuth_name().equals(auth_name)) {
				ords.add(each.getEndpoint_ord());
			} else {
				auth_name = each.getAuth_name();
				ords = new ArrayList<Integer>();
				ords.add(each.getEndpoint_ord());
				vo = AuthVO.builder()
						.auth_id(each.getAuth_id())
						.auth_name(each.getAuth_name())
						.ords(ords).build();
				auths.add(vo);
			}
		}
		return auths;
	}
	
	@Override
	public AuthVO getAuth(@Nonnull String auth_id, @Nonnull String auth_name) {
		AuthVO vo = null;
		List<AuthDTO> dbresult = authRepo.selectAuth(AuthDTO.builder().auth_id(auth_id).auth_name(auth_name).build());
		if (dbresult != null && dbresult.size() > 0) {
			List<Integer> ords = new ArrayList<Integer>();
			vo = AuthVO.builder()
					.auth_id(auth_id)
					.auth_name(auth_name)
					.ords(ords).build();
			for (AuthDTO each : dbresult)
				ords.add(each.getEndpoint_ord());
		}
		return vo;
	}
	
	@Override
	@Transactional
	public void saveAuth(@Nonnull AuthVO vo) {
		AuthVO save;
		if (vo.getAuth_id() != null) {
			AuthVO org = getAuth(vo.getAuth_id(), vo.getAuth_name());
			if (org == null)
				; // TODO error. id not exist.
			List<Integer> remove = new ArrayList<Integer>();
			remove.addAll(org.getOrds());
			log.debug("remove : {}", remove);
			remove.removeAll(vo.getOrds());

			log.debug("remove : {}", remove);

			removeAuthEndpoints(vo.getAuth_id(), remove);
			List<Integer> add = new ArrayList<Integer>();
			add.addAll(vo.getOrds());
			log.debug("add : {}", add);
			add.removeAll(org.getOrds());
			
			log.debug("add : {}", add);
			
			save = AuthVO.builder()
					.auth_id(vo.getAuth_id())
					.ords(add).build();
		} else {
			save = vo;
			save.setAuth_id(UUIDUtil.short_uuid());
		}
		authRepo.insertAuth(AuthDTO.builder()
				.auth_id(vo.getAuth_id())
				.auth_name(vo.getAuth_name()).build());
		addAuthEndpoints(save.getAuth_id(), save.getOrds());
	}
	
	@Override
	public List<String> getIdentityAuth(String iden_id) {
		List<String> list = new ArrayList<String>();
		for (IdentityAuthDTO each : authRepo.selectIdentityAuth(iden_id))
			list.add(each.getAuth_name());
		return list;
	}
	
	@Override
	@Transactional
	public void saveIdentityAuth(@Nonnull String iden_id, List<String> auth_names) {
		List<String> org = getIdentityAuth(iden_id);
		List<String> remove = new ArrayList<String>();
		remove.addAll(org);
		log.debug("remove : {}", remove);
		remove.removeAll(auth_names);
		log.debug("remove : {}", remove);
		List<IdentityAuthDTO> param = new ArrayList<IdentityAuthDTO>();
		for (String each : remove)
			param.add(IdentityAuthDTO.builder().iden_id(iden_id).auth_name(each).build());
		authRepo.deleteIdentityAuth(param);

		List<String> add = new ArrayList<String>();
		add.addAll(auth_names);
		log.debug("add : {}", add);
		add.removeAll(org);
		log.debug("add : {}", add);
		param = new ArrayList<IdentityAuthDTO>();
		for (String each : add)
			param.add(IdentityAuthDTO.builder().iden_id(iden_id).auth_name(each).build());
		authRepo.insertIdentityAuth(param);

	}
	
	
}
