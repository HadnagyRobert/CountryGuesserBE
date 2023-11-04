package com.example.countryguesser.business.impl.user;

import com.example.countryguesser.business.AccessTokenEncoder;
import com.example.countryguesser.business.exception.InvalidCredentialsException;
import com.example.countryguesser.domain.AccessToken;
import com.example.countryguesser.domain.LoginRequest;
import com.example.countryguesser.domain.LoginResponse;
import com.example.countryguesser.persitence.UserRepository;
import com.example.countryguesser.persitence.entity.RoleEnum;
import com.example.countryguesser.persitence.entity.UserEntity;
import com.example.countryguesser.persitence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @Test
    void testLogin_Success() {
        Set<UserRoleEntity> userRoles = new HashSet<>();
        userRoles.add(UserRoleEntity.builder().user(null).role(RoleEnum.USER).build());
        UserEntity user = UserEntity.builder().id(1).username("testUser").password("encodedPassword").userRoles(userRoles).build();
        LoginRequest loginRequest = LoginRequest.builder().username("testUser").password("testPassword").build();
        String accessToken = "accessToken";

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(any(AccessToken.class))).thenReturn(accessToken);

        LoginResponse response = loginUseCase.login(loginRequest);
        assertEquals(accessToken, response.getAccessToken());
    }


    @Test
    void testLogin_InvalidUsername() {
        LoginRequest loginRequest = LoginRequest.builder().username("testUser").password("testPassword").build();

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(null);

        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
    }

    @Test
    void testLogin_InvalidPassword() {
        UserEntity user = UserEntity.builder().id(1).username("testUser").password("encodedPassword").build();
        LoginRequest loginRequest = LoginRequest.builder().username("testUser").password("testPassword").build();

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
    }
}