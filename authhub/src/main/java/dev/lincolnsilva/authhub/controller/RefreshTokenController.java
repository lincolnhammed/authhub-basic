package dev.lincolnsilva.authhub.controller;

import dev.lincolnsilva.authhub.dto.LoginResponse;
import dev.lincolnsilva.authhub.dto.RefreshTokenRequest;
import dev.lincolnsilva.authhub.service.RefreshTokenService;
import dev.lincolnsilva.authhub.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;

    public RefreshTokenController(
            RefreshTokenService refreshTokenService,
            TokenService tokenService
    ) {
        this.refreshTokenService = refreshTokenService;
        this.tokenService = tokenService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            @RequestBody RefreshTokenRequest request
    ) {

        var refreshToken = refreshTokenService.rotate(
                request.refreshToken()
        );

        var user = refreshToken.getUser();

        String accessToken = tokenService.generateToken(user);

        return ResponseEntity.ok(
                new LoginResponse(
                        accessToken,
                        refreshToken.getToken()
                )
        );
    }
}