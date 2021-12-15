package io.cloud4u.acm.service;

import io.cloud4u.acm.dto.UserDTO;
import lombok.NonNull;

public interface UserService {

	boolean existUser(@NonNull String userid);

	UserDTO getUser(@NonNull String userid);

}
