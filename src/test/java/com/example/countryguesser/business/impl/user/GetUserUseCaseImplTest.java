package com.example.countryguesser.business.impl.user;

import com.example.countryguesser.business.ExtractUserFromAccessToken;
import com.example.countryguesser.domain.GetUserResponse;
import com.example.countryguesser.persitence.UserRepository;
import com.example.countryguesser.persitence.entity.UserEntity;
import com.example.countryguesser.persitence.entity.UserInfoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExtractUserFromAccessToken accessTokenEncoderDecoder;

    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Test
    void testGetUser_Success() {
        int expectedUserId = 1;
        String expectedUsername = "username";
        String expectedPassword = "password";
        String expectedEmail = "email@example.com";
        String expectedFirstName = "firstName";
        String expectedLastName = "lastName";

        when(accessTokenEncoderDecoder.extractUserFromAccessToken(anyString())).thenReturn(expectedUserId);

        UserInfoEntity userInfoEntity = UserInfoEntity.builder()
                .email(expectedEmail)
                .firstName(expectedFirstName)
                .lastName(expectedLastName)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .username(expectedUsername)
                .password(expectedPassword)
                .userInfoEntity(userInfoEntity)
                .build();

        when(userRepository.getReferenceById(anyInt())).thenReturn(userEntity);

        GetUserResponse response = getUserUseCase.getUser("Bearer 123456");

        assertEquals(expectedUsername, response.getUsername());
        assertEquals(expectedPassword, response.getPassword());
        assertEquals(expectedEmail, response.getEmail());
        assertEquals(expectedFirstName, response.getFirstName());
        assertEquals(expectedLastName, response.getLastName());
    }

    @Test
    void testGetUser_UserNotFound() {
        when(accessTokenEncoderDecoder.extractUserFromAccessToken(anyString())).thenReturn(1);
        when(userRepository.getReferenceById(anyInt())).thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(UsernameNotFoundException.class, () -> getUserUseCase.getUser("Bearer 123456"));
    }

    @Test
    void testGetUser_InvalidAccessToken() {
        when(accessTokenEncoderDecoder.extractUserFromAccessToken(anyString())).thenThrow(new IllegalArgumentException("Invalid access token"));

        assertThrows(IllegalArgumentException.class, () -> getUserUseCase.getUser("Bearer 123456"));
    }

    @Test
    void testGetUser_NullAccessToken() {
        when(accessTokenEncoderDecoder.extractUserFromAccessToken(null)).thenThrow(new IllegalArgumentException("Null access token"));

        assertThrows(IllegalArgumentException.class, () -> getUserUseCase.getUser(null));
    }
}