package com.example.countryguesser.persitence;

import com.example.countryguesser.persitence.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
}
