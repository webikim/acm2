package io.cloud4u.acm.service;

import io.cloud4u.acm.dto.UserDTO;
import io.cloud4u.acm.dto.ValidateDTO;
import io.cloud4u.acm.exception.NotValidException;

public interface ValidationService {

	void sendSignUpValidation(UserDTO userDto);

	void sendResetValidation(UserDTO userDto);

	String receiveValidation(char type, String valid_id, String signature) throws NotValidException;

	ValidateDTO checkValid(String valid_id, String signature);

	void removeValidRecord(String valid_id);

}
