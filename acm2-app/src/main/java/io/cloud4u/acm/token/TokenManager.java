package io.cloud4u.acm.token;

import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.Ed25519Signer;
import com.nimbusds.jose.crypto.Ed25519Verifier;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.cloud4u.acm.config.property.TokenConfigure;
import io.cloud4u.lib.acm.define.ClaimName;
import io.cloud4u.lib.acm.define.HeaderName;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class TokenManager {

	public String buildToken(String key_id, Map<String, Object> claims) {
		return buildToken(key_id, claims, TimeZone.getDefault().getID());
	}
	
	public String buildToken(String key_id, Map<String, Object> claims, String timezoneId) {
		OctetKeyPair jwk = getKeyString(key_id);
		if (jwk != null) {
			try {
				JWSSigner signer = new Ed25519Signer(jwk);
				SignedJWT signedJWT = new SignedJWT(
						new JWSHeader.Builder(JWSAlgorithm.EdDSA).keyID(key_id).build(),
						buildClaim(claims, timezoneId));
				signedJWT.sign(signer);
				return signedJWT.serialize();
			} catch (JOSEException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String getSginature(String token) {
		return token.substring(token.lastIndexOf('.') + 1);
	}
	
	public String getHeaderClaim(String token) {
		return token.substring(0, token.lastIndexOf('.'));
	}

	JWTClaimsSet buildClaim(Map<String, Object> claims, String timezoneId) {
		JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
		if (claims != null && claims.size() > 0) {
			for (Entry<String, Object> each : claims.entrySet())
				if (!each.getKey().contentEquals(ClaimName.EXPIRE))
					claimsSet.claim(each.getKey(), each.getValue());
		}
		coreClaims(claimsSet);
		moreClaims(claimsSet, claims, timezoneId);
		log.debug("claimset = {}", claimsSet.build().getClaims());
		return claimsSet.build();
	}

	private JWTClaimsSet.Builder coreClaims(JWTClaimsSet.Builder claimsSet) {
		TokenConfigure config = getConfig();
		if (config.getSubject() != null)
			claimsSet.subject(config.getSubject());
		if (config.getIssuer() != null)
			claimsSet.issuer(config.getIssuer());
		return claimsSet;
	}

	public Map<String, Object> validateToken(String token) {
		try {
			JWSObject jws = JWSObject.parse(token);
			JWSHeader header = jws.getHeader();
			JWTClaimsSet claimsSet = JWTClaimsSet.parse(jws.getPayload().toJSONObject());
			if (validateCoreClaims(claimsSet)) {
				if (moreValidate(claimsSet)) {
					String kid = header.getKeyID();
					OctetKeyPair key = getKeyString(kid);
					if (key != null) {
						OctetKeyPair publicJWK = key.toPublicJWK();
						JWSVerifier verifier = new Ed25519Verifier(publicJWK);
						if (SignedJWT.parse(token).verify(verifier))
							return claimsSet.getClaims();
					}
				}
			}
		} catch (ParseException | JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getKeyId(String token) {
		JWSObject jws;
		try {
			jws = JWSObject.parse(token);
			JWSHeader header = jws.getHeader();
			return header.getKeyID();
		} catch (ParseException e) {
		}
		return null;
	}

	public String extractToken(String headerToken) {
		return headerToken.replace(HeaderName.BEARER, "").trim();
	}
	
	private boolean validateCoreClaims(JWTClaimsSet claimsSet) {
		TokenConfigure config = getConfig();
		String sub = (String) claimsSet.getClaim(ClaimName.SUBJECT);
		String iss = (String) claimsSet.getIssuer();
		boolean subValid = false;
		if (sub != null) {
			if (config.getSubject() != null && sub.contentEquals(config.getSubject()))
				subValid = true;
		} else {
			if (config.getSubject() == null)
				subValid = true;
		}
		boolean issValid = false;
		if (iss != null) {
			if (config.getIssuer() != null && iss.contentEquals(config.getIssuer()))
				issValid = true;
		} else {
			if (config.getIssuer() == null)
				issValid = true;
		}
		return subValid && issValid;
	}

	abstract TokenConfigure getConfig();
	abstract OctetKeyPair getKeyString(String kid);
	abstract JWTClaimsSet.Builder moreClaims(JWTClaimsSet.Builder claimsSet, Map<String, Object> claims, String timezoneId);
	abstract boolean moreValidate(JWTClaimsSet claimsSet);

}
