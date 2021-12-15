package io.cloud4u.acm.token;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jwt.JWTClaimsSet;

import io.cloud4u.acm.config.property.TokenConfigure;
import io.cloud4u.acm.dto.KeyStoreDTO;
import io.cloud4u.acm.repository.KeyStoreRepo;

@Component
public class RefreshTokenManager extends TokenManager {
	@Autowired
	TokenConfigure config;

	@Autowired
	KeyStoreRepo keystoreRepo;

	@Override
	TokenConfigure getConfig() {
		return config;
	}

	@Override
	OctetKeyPair getKeyString(String kid) {
		List<KeyStoreDTO> keys = keystoreRepo.select(KeyStoreDTO.builder().key_id(kid).build());
		if (keys.size() == 1)
			try {
				return OctetKeyPair.parse(keys.get(0).getKey_body());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	@Override
	public JWTClaimsSet.Builder moreClaims(JWTClaimsSet.Builder claimsSet, Map<String, Object> claims, String timezoneId) {
		return claimsSet;
	}
	
	@Override
	public boolean moreValidate(JWTClaimsSet claimsSet) {
		return true;
	}
}
