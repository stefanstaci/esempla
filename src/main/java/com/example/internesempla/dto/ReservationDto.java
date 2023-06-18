package com.example.internesempla.dto;

import java.time.LocalDate;

public record ReservationDto(Integer id, Integer totalSize, Integer usedSize, Boolean activated, String createdBy) {
}
