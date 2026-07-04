package com.damian.roombooking.dto;

import com.damian.roombooking.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private Role role;
    // POZNI: žiadne pole "password" — toto je úmyselné
}