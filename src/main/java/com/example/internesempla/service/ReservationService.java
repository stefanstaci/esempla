package com.example.internesempla.service;

import com.example.internesempla.dto.ReservationDto;
import com.example.internesempla.entity.UserEntity;
import com.example.internesempla.entity.UserReservationEntity;
import com.example.internesempla.repository.UserRepository;
import com.example.internesempla.repository.UserReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class ReservationService {
    private final UserReservationRepository reservationRepository;
    private final MailService mailService;
    private final UserRepository userRepository;

    public ReservationService(UserReservationRepository reservationRepository, MailService mailService, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.mailService = mailService;
        this.userRepository = userRepository;
    }

    public ReservationDto demandReservationSize(ReservationDto request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserReservationEntity userReservation = new UserReservationEntity();

        userReservation.setTotalSize(request.totalSize());
        userReservation.setUsedSize(0);
        userReservation.setUserId((UserEntity) auth.getPrincipal());
        userReservation.setActivated(false);
        userReservation.setCreatedBy(auth.getPrincipal().toString());
        userReservation.setCreatedDate(LocalDate.now());
        var exist = reservationRepository.findByUserId(userReservation.getUserId());

        if (exist.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you already demanded size!");
        } else {
            reservationRepository.save(userReservation);
            var message = String.format(
                    "press on link to activate your account http://localhost:8080/api/size/activate/%s", userReservation.getUserId().getId());
            mailService.sendMail("stefan.staci.md@gmail.com", "activate size", message);
            return new ReservationDto(
                    userReservation.getId(),
                    userReservation.getTotalSize(),
                    userReservation.getUsedSize(),
                    userReservation.getActivated(),
                    userReservation.getCreatedBy()
            );
        }
    }

    public void addUsedSize(UserEntity user, Integer usedSize) {
        var reservationEntity = reservationRepository.findByUserId(user)
                .orElseThrow(() -> new IllegalStateException("you not demanded size"));

        if (usedSize != null) {
            var size = reservationEntity.getUsedSize() + usedSize;
            reservationEntity.setUsedSize(size);
        }
        if (reservationEntity.getActivated() && reservationEntity.getUsedSize() < reservationEntity.getTotalSize()) {
            reservationRepository.save(reservationEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you don't have size!");
        }
    }

    public void deleteUsedSize(UserEntity user, Integer usedSize) {
        UserReservationEntity reservationEntity = reservationRepository.findByUserId(user)
                .orElseThrow(() -> new IllegalStateException("you not demanded size"));

        if (usedSize != null) {
            var size = reservationEntity.getUsedSize() - usedSize;
            reservationEntity.setUsedSize(size);
        }
        if (reservationEntity.getActivated() && reservationEntity.getUsedSize() < reservationEntity.getTotalSize()) {
            reservationRepository.save(reservationEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you don't have size!");
        }
    }
    public UserReservationEntity activateSize(Integer id) {
        UserEntity user = userRepository.getReferenceById(id);
        UserReservationEntity reservationEntity = reservationRepository.findByUserId(user).orElseThrow(() -> new IllegalStateException("Not activated"));
        reservationEntity.setActivated(true);
        return reservationRepository.save(reservationEntity);
    }

}
