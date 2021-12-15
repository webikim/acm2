package io.cloud4u.acm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cloud4u.acm.dto.EndpointDTO;
import io.cloud4u.acm.dto.MethodDTO;
import io.cloud4u.acm.repository.EndpointRepo;
import io.cloud4u.acm.vo.EndpointVO;
import io.cloud4u.common.lib.util.UUIDUtil;
import io.cloud4u.lib.acm.Const;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Service
public class EndpointServiceImpl implements EndpointService {
	private static final Pattern SPRING_PATH_VARIABLE_PATTERN = Pattern.compile("\\{.*\\}");

	@Autowired
	private EndpointRepo endpointRepo;

	@Getter
	@Setter
	@Builder
	final static class ParsedURL {
		String[] parts;
		int suffix;
	}
	
	@Override
	public List<EndpointVO> getEndpoints() {
		List<EndpointVO> endpoints = new ArrayList<>();
		EndpointVO vo;
		for (EndpointDTO each : endpointRepo.selectEndpoint(new EndpointDTO())) {
			vo = EndpointVO.builder()
					.ord(each.getEndpoint_ord())
					.url(each.getSvc_url())
					.suffix(each.getSuffix())
					.method(each.getMethod())
					.build();
			endpoints.add(vo);
		}
		return endpoints;
	}
	
	private ParsedURL parseURL(String orgUrl) {
		String[] parted = null;
		int suffix = 0;
		if (orgUrl != null) {
			String url = orgUrl;
			if (url.startsWith(Const.URI_SEPERATOR))
				url = url.substring(Const.URI_SEPERATOR.length());
			parted = url.split(Const.URI_SEPERATOR);
			suffix = parted.length - 1;
			for (int i = suffix; i >= 0; i--) {
				if (!SPRING_PATH_VARIABLE_PATTERN.matcher(parted[i]).find()) {
					suffix = 1;
					break;
				}
			}
			return ParsedURL.builder().parts(parted).suffix(suffix).build();
		}
		return null;
	}

	@Override
	public void saveEndpoint(EndpointVO vo) {
		EndpointDTO dto = vo.toDTO();
		if (vo.getUrl() != null) {
			ParsedURL parsed = parseURL(vo.getUrl());
			int suffixIndex = parsed.suffix;
			String suffix = parsed.parts[suffixIndex];
			List<MethodDTO> methods = endpointRepo.selectMethod(MethodDTO.builder().suffix(suffix).method(vo.getMethod()).build());
			if (methods.size() == 0) {
				vo.setSuffix(null);
				vo.setMethod(null);
				suffixIndex++;
			} else {
				vo.setSuffix(suffix);
			}
			vo.setUrl(String.join(Const.URI_SEPERATOR, Arrays.copyOfRange(parsed.parts, 0, suffixIndex)));
		}
		if (vo.getSvc_id() == null)
			dto.setSvc_id(UUIDUtil.short_uuid());
		endpointRepo.insertService(dto);

		endpointRepo.insertEndpoint(dto);
	}

	@Override
	public int saveMethod(MethodDTO dto) {
		return endpointRepo.insertMethod(dto);
	}
	
}
