package dev.lincolnsilva.authhub.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}