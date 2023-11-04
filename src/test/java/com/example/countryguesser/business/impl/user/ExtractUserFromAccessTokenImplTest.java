package com.example.countryguesser.business.impl.user;

import com.example.countryguesser.domain.AccessToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExtractUserFromAccessTokenImplTest {

    @Mock
    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;

    @InjectMocks
    private ExtractUserFromAccessTokenImpl extractUserFromAccessToken;

    @Test
    void testExtractUserFromAccessToken_InvalidToken() {
        assertThrows(IllegalArgumentException.class, () -> {
            extractUserFromAccessToken.extractUserFromAccessToken("InvalidToken");
        });
    }

    @Test
    void testExtractUserFromAccessToken_NoBearer() {
        assertThrows(IllegalArgumentException.class, () -> {
            extractUserFromAccessToken.extractUserFromAccessToken("NotBearer 123456");
        });
    }

    @Test
    void testExtractUserFromAccessToken_Success() {
        int expectedUserId = 1;
        AccessToken token = new AccessToken();
        token.setUserId(expectedUserId);

        when(accessTokenEncoderDecoder.decode(anyString())).thenReturn(token);

        int actualUserId = extractUserFromAccessToken.extractUserFromAccessToken("Bearer 123456");

        assertEquals(expectedUserId, actualUserId);
    }
}