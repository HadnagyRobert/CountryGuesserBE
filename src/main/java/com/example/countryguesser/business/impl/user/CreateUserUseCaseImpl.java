package com.example.countryguesser.business.impl.user;

import com.example.countryguesser.business.CreateUserUseCase;
import com.example.countryguesser.business.exception.UsernameExistsException;
import com.example.countryguesser.domain.CreateUserRequest;
import com.example.countryguesser.domain.CreateUserResponse;
import com.example.countryguesser.persitence.UserInfoRepository;
import com.example.countryguesser.persitence.UserRepository;
import com.example.countryguesser.persitence.entity.RoleEnum;
import com.example.countryguesser.persitence.entity.UserEntity;
import com.example.countryguesser.persitence.entity.UserInfoEntity;
import com.example.countryguesser.persitence.entity.UserRoleEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private UserRepository userRepository;
    private UserInfoRepository userInfoRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new UsernameExistsException();
        }

        UserEntity userEntity = saveNewAccount(request);
        return CreateUserResponse.builder()
                .id(userEntity.getId())
                .build();
    }

    private UserEntity saveNewAccount(CreateUserRequest request){
        UserInfoEntity userInfo = UserInfoEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();

        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userInfoEntity(userInfoRepository.save(userInfo))
                .build();

        userEntity.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(userEntity)
                        .role(RoleEnum.USER)
                        .build()));

        return userRepository.save(userEntity);
    }
}
