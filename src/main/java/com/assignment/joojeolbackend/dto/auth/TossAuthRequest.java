package com.assignment.joojeolbackend.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TossAuthRequest {
    private String authorizationCode;
    private String referrer;
}
