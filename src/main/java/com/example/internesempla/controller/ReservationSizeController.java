package com.example.internesempla.controller;

import com.example.internesempla.dto.ReservationDto;
import com.example.internesempla.entity.UserEntity;
import com.example.internesempla.entity.UserReservationEntity;
import com.example.internesempla.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/size")
public class ReservationSizeController {
    private final ReservationService reservationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    public ReservationSizeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/demand")
    public ResponseEntity<ReservationDto> demandSize(@RequestBody ReservationDto request) {
        logger.info("space was requested for uploading files");
        return new ResponseEntity<>(reservationService.demandReservationSize(request), HttpStatus.ACCEPTED);
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<UserReservationEntity> activate(@PathVariable Integer id) {
        logger.info("the size has been activated");
        return ResponseEntity.ok(reservationService.activateSize(id));
    }
}
