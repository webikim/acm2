package io.cloud4u.lib.acm.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cloud4u.common.spring.util.URLscanner;

@Service
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private URLscanner scanner;

	@Override
	public Set<String> getScannedUris() {
		return scanner.getScannedUrls();
	}

}
