package io.cloud4u.acm.service;

import java.util.Map;

import net.minidev.json.JSONObject;

public interface KeyService {

	Map<String, JSONObject> rollingKeys();

}
