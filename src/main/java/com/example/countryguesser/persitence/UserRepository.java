package com.example.countryguesser.persitence;

import com.example.countryguesser.persitence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);
}
