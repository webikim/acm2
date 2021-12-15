package io.cloud4u.acm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cloud4u.acm.token.KeyPairManager;
import net.minidev.json.JSONObject;

@Service
public class KeyServiceImpl implements KeyService {

	@Autowired
	private KeyPairManager keyMan;

	@Override
	public Map<String, JSONObject> rollingKeys() {
		return keyMan.getRollingKeys();
	}
}
