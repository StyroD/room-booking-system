package com.damian.roombooking.dto;

import com.damian.roombooking.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;
    private UserResponse user;    // UserResponse (bez hesla), nie User entita
    private RoomResponse room;
}