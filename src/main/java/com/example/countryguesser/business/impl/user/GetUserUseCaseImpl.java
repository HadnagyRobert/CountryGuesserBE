package com.example.countryguesser.business.impl.user;

import com.example.countryguesser.business.ExtractUserFromAccessToken;
import com.example.countryguesser.business.GetUserUseCase;
import com.example.countryguesser.domain.GetUserResponse;
import com.example.countryguesser.persitence.UserRepository;
import com.example.countryguesser.persitence.entity.UserEntity;
import com.example.countryguesser.persitence.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final UserRepository userRepository;
    private final ExtractUserFromAccessToken accessTokenEncoderDecoder;

    @Override
    @Transactional
    public GetUserResponse getUser(String accessToken) {
        int userId = accessTokenEncoderDecoder.extractUserFromAccessToken(accessToken);
        UserEntity user = userRepository.getReferenceById(userId);
        UserInfoEntity userInfo = user.getUserInfoEntity();

        return GetUserResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(userInfo.getEmail())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .build();
    }
}
