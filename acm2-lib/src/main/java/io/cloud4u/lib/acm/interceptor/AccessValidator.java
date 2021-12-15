package io.cloud4u.lib.acm.interceptor;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.Ed25519Verifier;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.cloud4u.lib.acm.define.HeaderName;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

@Component
@Slf4j
public class AccessValidator {

	private Map<String, OctetKeyPair> keystore;

	public void setKeystore(Map<String, JSONObject> keys) {
		keystore = new HashMap<String, OctetKeyPair>();
		try {
			for (Entry<String, JSONObject> each : keys.entrySet()) {
				keystore.put(each.getKey(), OctetKeyPair.parse(each.getValue()));
			}
		} catch (ParseException e) {
			log.error("loading keystore failed !");
			e.printStackTrace();
		}
	}

	public Map<String, Object> validateToken(String headerToken) {
		if (headerToken == null)
			return null;
		String token = null;
		try {
			token = extractToken(headerToken);
			JWSObject jws = JWSObject.parse(token);
			JWSHeader header = jws.getHeader();
			JWTClaimsSet claimsSet = JWTClaimsSet.parse(jws.getPayload().toJSONObject());

			String kid = header.getKeyID();
			OctetKeyPair key = keystore.get(kid);
			if (key != null) {
				JWSVerifier verifier = new Ed25519Verifier(key);
				if (SignedJWT.parse(token).verify(verifier))
					return claimsSet.getClaims();
			}
		} catch (ParseException | JOSEException e) {
			log.error("parsing token failed !! toke = {}", token);
			e.printStackTrace();
		}
		return null;
	}

	public String extractToken(String headerToken) {
		return headerToken.replace(HeaderName.BEARER, "").trim();
	}

	
}
