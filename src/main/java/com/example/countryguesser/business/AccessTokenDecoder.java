package com.example.countryguesser.business;

import com.example.countryguesser.domain.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}

