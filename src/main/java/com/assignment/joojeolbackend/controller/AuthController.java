package com.assignment.joojeolbackend.controller;

import com.assignment.joojeolbackend.dto.auth.AuthResponse;
import com.assignment.joojeolbackend.dto.auth.TossAuthRequest;
import com.assignment.joojeolbackend.service.TossService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TossService tossService;

    @PostMapping("/toss/login")
    public ResponseEntity<AuthResponse> loginWithToss(@RequestBody TossAuthRequest request) {
        AuthResponse response = tossService.login(request.getAuthorizationCode(), request.getReferrer());
        return ResponseEntity.ok(response);
    }
}
