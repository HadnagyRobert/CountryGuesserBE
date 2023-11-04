package com.example.countryguesser.business;

import com.example.countryguesser.domain.CreateUserRequest;
import com.example.countryguesser.domain.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
