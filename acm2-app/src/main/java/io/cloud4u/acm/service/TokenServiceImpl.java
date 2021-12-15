package io.cloud4u.acm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cloud4u.acm.repository.AppRepo;
import io.cloud4u.acm.token.AccessTokenManager;
import io.cloud4u.acm.token.AuthTokenManager;
import io.cloud4u.acm.token.RefreshTokenManager;
import io.cloud4u.acm.vo.AppVO;

@Service
public class TokenServiceImpl {
	
	@Autowired
	private AppRepo appRepo;

	@Autowired
	private AuthTokenManager authTokenMan;
	
	@Autowired
	private AccessTokenManager accTokenMan;
	
	@Autowired
	private RefreshTokenManager refTokenMan;
	
	private void generateAuthToken() {
		// TODO Auto-generated method stub

	}
	
	private void generateAccessToken() {
		// TODO Auto-generated method stub

	}
	
	private void generateRefreshToken(AppVO vo) {
		// TODO Auto-generated method stub

	}
}
