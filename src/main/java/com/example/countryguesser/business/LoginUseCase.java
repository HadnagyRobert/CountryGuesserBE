package com.example.countryguesser.business;

import com.example.countryguesser.domain.LoginRequest;
import com.example.countryguesser.domain.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
}
