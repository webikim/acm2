package io.cloud4u.acm.token;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.crypto.tink.subtle.Random;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;

import io.cloud4u.acm.config.property.AcmConfigure;
import io.cloud4u.acm.define.KeyType;
import io.cloud4u.acm.dto.KeyStoreDTO;
import io.cloud4u.acm.repository.KeyStoreRepo;
import io.cloud4u.lib.acm.exception.DuplicateException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

@Component
@Slf4j
public class KeyPairManager {
	
	@Autowired
	private KeyStoreRepo keystoreRepo;
	
	@Autowired
	AcmConfigure config;
	
	Map<String, OctetKeyPair> keystore;
	
	@PostConstruct
	private void buildRollingKeys() {
		keystore = new HashMap<String, OctetKeyPair>();
		OctetKeyPair jwk;
		List<KeyStoreDTO> list = keystoreRepo.select(new KeyStoreDTO());
		try {
			if (list == null || list.size() < config.getKeyLimit())
				initKeystore();
			else {
				for (KeyStoreDTO each : keystoreRepo.select(new KeyStoreDTO())) {
					if (each.getKey_type() == KeyType.IDENTITY_KEY.value()) {
						jwk = OctetKeyPair.parse(each.getKey_body());
						keystore.put(jwk.getKeyID(), jwk);
					}
				}
			}
			log.info("keys are initialized. key count = {}", config.getKeyLimit());
		} catch (ParseException | JOSEException e) {
			log.error("building rolling keys has failed.");
			e.printStackTrace();
		}
	}
	
	private void initKeystore() throws JOSEException {
		// Generate a key pair with Ed25519 curve (for faster token generation)
		log.info("delete and create new keys. delete count = {}", keystoreRepo.delete(new KeyStoreDTO()));
		OctetKeyPairGenerator keygen = new OctetKeyPairGenerator(Curve.Ed25519);
		OctetKeyPair jwk;
		KeyStoreDTO dto;
		for (int i = 0; i < config.getKeyLimit(); i++) {
			jwk = keygen.keyID(Integer.toString(i)).generate();
			dto = KeyStoreDTO.builder()
					.key_id(Integer.toString(i))
					.key_body(jwk.toJSONString())
					.key_type(KeyType.IDENTITY_KEY.value()).build();
			keystoreRepo.insert(dto);
			keystore.put(jwk.getKeyID(), jwk);
		}
	}

	public OctetKeyPair generateKey(String kid, KeyType keyType) throws JOSEException, DuplicateException {
		List<KeyStoreDTO> list = keystoreRepo.select(KeyStoreDTO.builder().key_id(kid).build());
		if (list != null && list.size() > 0)
			throw new DuplicateException("key id already exist in keystore !");
		OctetKeyPairGenerator keygen = new OctetKeyPairGenerator(Curve.Ed25519);
//		OctetKeyPair jwk = generateKey(keygen, kid);
		OctetKeyPair jwk = keygen.keyID(kid).generate();
		keystoreRepo.insert(KeyStoreDTO.builder()
					.key_id(kid)
					.key_body(jwk.toJSONString())
					.key_type(keyType.value()).build());
//					.key_type(KeyType.IDENTITY_KEY.value()).build());
		return jwk;
	}

//	public OctetKeyPair generateKey(OctetKeyPairGenerator keygen, String kid) throws JOSEException {
//		return keygen.keyID(kid).generate();
//	}
		
	public void removeKey(String kid) {
		keystore.remove(kid);
		keystoreRepo.delete(KeyStoreDTO.builder().key_id(kid).build());
	}
	
	public Map<String, JSONObject> getRollingKeys() {
		Map<String, JSONObject> keys = new HashMap<String, JSONObject>();
		OctetKeyPair publicJWK;
		for (Entry<String, OctetKeyPair> each : keystore.entrySet()) {
			publicJWK = each.getValue().toPublicJWK();
			keys.put(each.getKey(), publicJWK.toJSONObject());
		}
		return keys;
	}
	
	public OctetKeyPair getRollingKey(String kid) {
		return keystore.get(kid);
	}
	
	public Map<String, JSONObject> getOtherKeys() {
		Map<String, JSONObject> keys = new HashMap<String, JSONObject>();
		List<KeyStoreDTO> list = keystoreRepo.select(KeyStoreDTO.builder().key_type(KeyType.OTHER_KEY.value()).build());
		if (list != null && list.size() > 0) {
			OctetKeyPair publicJWK;
			try {
				for (KeyStoreDTO each : list) {
					publicJWK = OctetKeyPair.parse(each.getKey_body()).toPublicJWK();
					keys.put(each.getKey_id(), publicJWK.toJSONObject());
				}
			} catch (ParseException e) {
				log.error("keystore was damaged !! re-initialize keystore and start again !!");
				e.printStackTrace();
			}
		}
		return keys;
	}

	public String generateKid() {
		return Integer.toString(Random.randInt(config.getKeyLimit()));
	}
	
	// TODO consider remove. for test purpose duuring dev.
	public void walkRollingKey() throws ParseException {
		OctetKeyPair jwk, publicJWK;
		for (KeyStoreDTO each : keystoreRepo.select(new KeyStoreDTO())) {
			if (each.getKey_type() == KeyType.IDENTITY_KEY.value()) {
				jwk = OctetKeyPair.parse(each.getKey_body());
				publicJWK = jwk.toPublicJWK();
				System.out.println(publicJWK);
			}
		}
	}
}
