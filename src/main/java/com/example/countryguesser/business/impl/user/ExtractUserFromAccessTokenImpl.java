package com.example.countryguesser.business.impl.user;

import com.example.countryguesser.business.ExtractUserFromAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractUserFromAccessTokenImpl implements ExtractUserFromAccessToken {

    private final AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;

    public int extractUserFromAccessToken(String accessToken) {
        String[] parts = accessToken.split(" ");
        if (parts.length == 2 && parts[0].equalsIgnoreCase("Bearer")) {
            return accessTokenEncoderDecoder.decode(parts[1]).getUserId();
        } else {
            throw new IllegalArgumentException("Invalid access token");
        }
    }
}
