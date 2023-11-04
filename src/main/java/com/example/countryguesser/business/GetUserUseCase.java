package com.example.countryguesser.business;

import com.example.countryguesser.domain.GetUserResponse;

public interface GetUserUseCase {
    GetUserResponse getUser(String accessToken);
}
