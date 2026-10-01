package dev.lincolnsilva.authhub.controller;

import dev.lincolnsilva.authhub.dto.RefreshTokenRequest;
import dev.lincolnsilva.authhub.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogoutController {

    private final RefreshTokenService refreshTokenService;

    public LogoutController(
            RefreshTokenService refreshTokenService
    ) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody RefreshTokenRequest request
    ) {

        refreshTokenService.revoke(
                request.refreshToken()
        );

        return ResponseEntity.ok(
                "Logout realizado com sucesso"
        );
    }
}