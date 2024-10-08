package com.ttcollab.spoterly.services;

import com.ttcollab.spoterly.core.User;

import java.util.UUID;

public interface UserService {
    User findUserById(UUID id);

}
