package com.assignment.joojeolbackend.service;

import com.assignment.joojeolbackend.dto.auth.AuthResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.transaction.annotation.Transactional;

public interface TossService {

    void validateTossResponse(JsonNode node, String errorMsg);

    @Transactional
    AuthResponse login(String authorizationCode, String referrer);
}
