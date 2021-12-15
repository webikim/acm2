package io.cloud4u.acm.token;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jwt.JWTClaimsSet;

import io.cloud4u.acm.config.property.TokenConfigure;
import io.cloud4u.common.lib.util.DateUtil;
import io.cloud4u.lib.acm.define.ClaimName;

@Component
public class AuthTokenManager extends TokenManager {

	@Autowired
	TokenConfigure config;

	@Autowired
	KeyPairManager keyMan;

	@Override
	TokenConfigure getConfig() {
		return config;
	}

	@Override
	OctetKeyPair getKeyString(String kid) {
		return keyMan.getRollingKey(kid);
	}

	private long calcExpire(String tzId) { 
		long after = 0;
		Map<String, Integer> expire = config.getExpire();
		if (expire.get(TokenConfigure.TOKEN_MIN) != null)
			after += expire.get(TokenConfigure.TOKEN_MIN).longValue() * DateUtil.MINUTE;
		if (expire.get(TokenConfigure.TOKEN_HOUR) != null)
			after += expire.get(TokenConfigure.TOKEN_HOUR).longValue() * DateUtil.HOUR;
		if (expire.get(TokenConfigure.TOKEN_DAY) != null)
			after += expire.get(TokenConfigure.TOKEN_DAY).longValue() * DateUtil.DAY;
		return DateUtil.timeAfterMils(tzId, after);
	}

	@Override
	public JWTClaimsSet.Builder moreClaims(JWTClaimsSet.Builder claimsSet, Map<String, Object> claims, String timezoneId) {
		TimeZone tz = TimeZone.getTimeZone(timezoneId);
		if (!tz.getID().contentEquals(timezoneId))	// TODO warn timezoneId is wrong
			tz = TimeZone.getDefault();
		String tzId = tz.getID();
		claimsSet.claim(ClaimName.TIMEZONE, tzId);
		long expireTime = calcExpire(tzId);

		if (claims != null && claims.size() > 0) {
			String exp = (String) claims.get(ClaimName.EXPIRE);
			if (exp != null) {
				expireTime = Long.valueOf(exp);
			}
		}
		claimsSet.expirationTime(new Date(expireTime));
		return claimsSet;
	}
	
	public boolean isExpired(String token) {
		JWSObject jws;
		JWTClaimsSet claimsSet = null;
		try {
			jws = JWSObject.parse(token);
			claimsSet = JWTClaimsSet.parse(jws.getPayload().toJSONObject());
			return isExpired(claimsSet);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean isExpired(JWTClaimsSet claimsSet) {
		if (claimsSet == null || claimsSet.getClaims().size() == 0)
			return false;
		return !(DateUtil.compareNow((String) claimsSet.getClaim(ClaimName.TIMEZONE), claimsSet.getExpirationTime()) > 0);
	}

	@Override
	public boolean moreValidate(JWTClaimsSet claimsSet) {
		return !isExpired(claimsSet);
	}
}
