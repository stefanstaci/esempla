package com.example.internesempla.repository;

import com.example.internesempla.entity.UserEntity;
import com.example.internesempla.entity.UserReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReservationRepository extends JpaRepository<UserReservationEntity, Integer> {
    Optional<UserReservationEntity> findByUserId(UserEntity userId);
}
