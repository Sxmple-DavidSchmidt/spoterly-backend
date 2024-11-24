package com.tdcollab.spoterly.core.services;

import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.model.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(UserEntity request);
    AuthenticationResponse authenticate(UserEntity request);
}
