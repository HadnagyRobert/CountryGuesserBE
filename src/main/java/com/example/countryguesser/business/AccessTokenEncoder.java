package com.example.countryguesser.business;

import com.example.countryguesser.domain.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
