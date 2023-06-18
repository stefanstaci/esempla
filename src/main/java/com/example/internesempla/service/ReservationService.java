package com.example.internesempla.service;

import com.example.internesempla.repository.UserRepository;
import com.example.internesempla.repository.UserReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final UserReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationService(UserReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }


}
