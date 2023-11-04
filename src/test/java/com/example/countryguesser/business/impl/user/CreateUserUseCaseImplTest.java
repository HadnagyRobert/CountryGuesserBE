package com.example.countryguesser.business.impl.user;

import com.example.countryguesser.business.exception.UsernameExistsException;
import com.example.countryguesser.domain.CreateUserRequest;
import com.example.countryguesser.domain.CreateUserResponse;
import com.example.countryguesser.persitence.UserInfoRepository;
import com.example.countryguesser.persitence.UserRepository;
import com.example.countryguesser.persitence.entity.RoleEnum;
import com.example.countryguesser.persitence.entity.UserEntity;
import com.example.countryguesser.persitence.entity.UserInfoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<UserEntity> userCaptor;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void testCreateUser_UsernameExists() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("existingUser");

        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(UsernameExistsException.class, () -> {
            createUserUseCase.createUser(request);
        });
    }

    @Test
    void testCreateUser_Success() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("newUser");
        request.setPassword("password");
        request.setFirstName("first");
        request.setLastName("last");
        request.setEmail("email@test.com");

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userInfoRepository.save(any(UserInfoEntity.class))).thenReturn(new UserInfoEntity());
        when(userRepository.save(any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        CreateUserResponse response = createUserUseCase.createUser(request);

        assertNotNull(response);
        verify(userRepository, times(1)).save(userCaptor.capture());

        UserEntity savedUser = userCaptor.getValue();

        assertNotNull(savedUser);
        assertEquals("newUser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertNotNull(savedUser.getUserInfoEntity());
        assertEquals(1, savedUser.getUserRoles().size());
        assertTrue(savedUser.getUserRoles().stream().anyMatch(r -> r.getRole().equals(RoleEnum.USER)));
    }
}