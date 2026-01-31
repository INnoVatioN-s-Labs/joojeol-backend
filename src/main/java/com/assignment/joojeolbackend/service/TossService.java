package com.assignment.joojeolbackend.service;

import com.assignment.joojeolbackend.domain.user.User;
import com.assignment.joojeolbackend.dto.auth.AuthResponse;
import com.assignment.joojeolbackend.repository.UserRepository;
import com.assignment.joojeolbackend.security.jwt.JwtTokenProvider;
import com.assignment.joojeolbackend.thirdparty.toss.TossApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossService {

    private final TossApiClient tossApiClient;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    @Value("${toss.cert-path}")
    private String certPath;

    @Value("${toss.key-path}")
    private String keyPath;

    private static final String TOSS_GENERATE_TOKEN_URL = "https://apps-in-toss-api.toss.im/api-partner/v1/apps-in-toss/user/oauth2/generate-token";
    private static final String TOSS_LOGIN_ME_URL = "https://apps-in-toss-api.toss.im/api-partner/v1/apps-in-toss/user/oauth2/login-me";

    @Transactional
    public AuthResponse login(String authorizationCode, String referrer) {
        try {
            Resource certResource = resourceLoader.getResource(certPath);
            Resource keyResource = resourceLoader.getResource(keyPath);
            
            SSLContext sslContext = tossApiClient.createSSLContext(certResource, keyResource);

            // 1. Generate Token
            String refererVal = (referrer == null || referrer.isBlank()) ? "DEFAULT" : referrer;
            String jsonBody = String.format("{\"authorizationCode\": \"%s\", \"referrer\": \"%s\"}", authorizationCode, refererVal);
            
            String tokenResponse = tossApiClient.makeRequest(TOSS_GENERATE_TOKEN_URL, jsonBody, sslContext);
            JsonNode tokenNode = objectMapper.readTree(tokenResponse);

            validateTossResponse(tokenNode, "toss token response");

            JsonNode successNode = tokenNode.get("success");
            String accessToken = successNode.get("accessToken").asText();
            // String refreshToken = successNode.get("refreshToken").asText(); // We can save this if needed for refresh

            // 2. Login Me (Get User Info)
            String loginMeResponse = tossApiClient.makeBearerRequest(TOSS_LOGIN_ME_URL, "GET", null, sslContext, accessToken);
            JsonNode loginMeNode = objectMapper.readTree(loginMeResponse);

            validateTossResponse(loginMeNode, "toss login-me response");

            JsonNode loginSuccessNode = loginMeNode.get("success");
            Long tossUserKey = loginSuccessNode.get("userKey").asLong();
            
            // 3. Create or Get User
            User user = userRepository.findByTossUserKey(tossUserKey)
                    .orElseGet(() -> userRepository.save(User.builder()
                            .tossUserKey(tossUserKey)
                            .nickname("TossUser" + tossUserKey) // Default nickname
                            .build()));

            // 4. Create JWT
            String jwtToken = jwtTokenProvider.createToken(String.valueOf(user.getTossUserKey()));

            return new AuthResponse(jwtToken, user.getNickname());

        } catch (Exception e) {
            log.error("Toss Login Error", e);
            throw new RuntimeException("Toss Login Failed: " + e.getMessage());
        }
    }

    private void validateTossResponse(JsonNode node, String errorMsg) {
        if (!node.has("resultType") || !"SUCCESS".equals(node.get("resultType").asText())) {
            String errorDetail = node.has("error") ? node.get("error").toString() : "Unknown Error";
            throw new RuntimeException(errorMsg + " invalid: " + errorDetail);
        }
    }
}
