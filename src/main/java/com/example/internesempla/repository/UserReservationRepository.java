package com.example.internesempla.repository;

import com.example.internesempla.entity.UserReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReservationRepository extends JpaRepository<UserReservationEntity, Integer> {
}
