package com.example.internesempla.repository;

import com.example.internesempla.entity.UserEntity;
import com.example.internesempla.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByEmail(String email);

    Optional<Object> findByActivationKey(String activationKey);

    Object findByRoles(RoleEnum roles);
}


