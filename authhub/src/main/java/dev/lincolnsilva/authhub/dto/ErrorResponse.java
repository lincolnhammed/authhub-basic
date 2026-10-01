package dev.lincolnsilva.authhub.dto;

public record ErrorResponse(
        int status,
        String error,
        String message
) {
}