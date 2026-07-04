package com.damian.roombooking.controller;

import com.damian.roombooking.entity.Booking;
import com.damian.roombooking.service.BookingService;
import com.damian.roombooking.dto.BookingResponse;
import com.damian.roombooking.dto.UserResponse;
import com.damian.roombooking.dto.RoomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(b -> ResponseEntity.ok(toResponse(b)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(bookingService.getBookingsByRoom(roomId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody CreateBookingRequest request) {
        Booking booking = bookingService.createBooking(
                request.userId(),
                request.roomId(),
                request.startTime(),
                request.endTime()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(booking));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(bookingService.cancelBooking(id)));
    }

    private BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .status(booking.getStatus())
                .user(UserResponse.builder()
                        .id(booking.getUser().getId())
                        .email(booking.getUser().getEmail())
                        .name(booking.getUser().getName())
                        .role(booking.getUser().getRole())
                        .build())
                .room(RoomResponse.builder()
                        .id(booking.getRoom().getId())
                        .name(booking.getRoom().getName())
                        .capacity(booking.getRoom().getCapacity())
                        .location(booking.getRoom().getLocation())
                        .build())
                .build();
    }

    record CreateBookingRequest(
        Long userId,
        Long roomId,
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {}
}