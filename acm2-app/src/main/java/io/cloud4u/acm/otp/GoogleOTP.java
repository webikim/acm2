package io.cloud4u.acm.otp;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

public class GoogleOTP {
	private static final String BARCODE_URL = "https://www.google.com/chart?chs=%s&chld=M|0&cht=qr&chl=otpauth://totp/%s?secret=%s&issuer=%s";
	private static final String BARCODE_SIZE = "200x200";
	private static final String BARCODE_ISSUER = "cloud4u.io";
	private static final int OPT_SECRETSIZE = 15;
	private static final long OPT_TIMESPAN = 30000;
	private static final String OPT_KEY_ALGORITHM = "HmacSHA1";

	public static String generateSecret() {
		return generateSecret(OPT_SECRETSIZE);
	}

	public static String generateSecret(int secretSize) {
		byte[] buffer = new byte[secretSize];
		new Random().nextBytes(buffer);
		Base32 codec = new Base32();
		byte[] secretKey = Arrays.copyOf(buffer, secretSize);
		byte[] encodedKey = codec.encode(secretKey);
		return new String(encodedKey).replaceAll("=", "");
	}

	public static String getQRBarcodeURL(String id, String secret) {
		return getQRBarcodeURL(BARCODE_SIZE, id, secret, BARCODE_ISSUER);
	}

	public static String getQRBarcodeURL(String imgSize, String id, String secret, String issuer) {
		return String.format(BARCODE_URL, imgSize, id, secret, issuer);
	}
	
	public static boolean check_code(String secret, String code) throws NoSuchAlgorithmException, InvalidKeyException {
		long time = new Date().getTime() / OPT_TIMESPAN;
		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(secret);
		// Window is used to check codes generated in the near past.
		// permit time window of ( -3 * TIMESPAN + 3 * TIMESPAN + TIMESPAN )
//		int window = 3;
//		for (int i = -window; i <= window; ++i) {
//			long hash = verify_code(decodedKey, time + i);
			long hash = verify_code(decodedKey, time);
			if (hash == Long.valueOf(code)) {
				return true;
			}
//		}
		return false;
	}
	
	private static int verify_code(byte[] key, long time)
			throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = new byte[8];
		long value = time;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = (byte) value;
		}
		SecretKeySpec signKey = new SecretKeySpec(key, OPT_KEY_ALGORITHM);
		Mac mac = Mac.getInstance(OPT_KEY_ALGORITHM);
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);
		int offset = hash[20 - 1] & 0xF;
		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			truncatedHash |= (hash[offset + i] & 0xFF);
		}
		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;
		return (int) truncatedHash;
	}
}
