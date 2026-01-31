package com.assignment.joojeolbackend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String accessToken; // Our JWT
    private String nickname;
}
