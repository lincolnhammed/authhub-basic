package dev.lincolnsilva.authhub.controller;

import dev.lincolnsilva.authhub.dto.LoginRequest;
import dev.lincolnsilva.authhub.dto.LoginResponse;
import dev.lincolnsilva.authhub.service.RefreshTokenService;
import dev.lincolnsilva.authhub.service.TokenService;
import dev.lincolnsilva.authhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public LoginController(
            UserService userService,
            TokenService tokenService,
            RefreshTokenService refreshTokenService
    ) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest
    ) {

        var user = userService.findByEmail(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha inválidos");
        }

        String accessToken = tokenService.generateToken(user);

        var refreshToken = refreshTokenService.create(user);

        return ResponseEntity.ok(
                new LoginResponse(
                        accessToken,
                        refreshToken.getToken()
                )
        );
    }
}