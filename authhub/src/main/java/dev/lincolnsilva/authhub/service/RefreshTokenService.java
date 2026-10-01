package dev.lincolnsilva.authhub.service;

import dev.lincolnsilva.authhub.exception.InvalidRefreshTokenException;
import dev.lincolnsilva.authhub.model.RefreshToken;
import dev.lincolnsilva.authhub.model.User;
import dev.lincolnsilva.authhub.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken create(User user) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(
                Instant.now().plusSeconds(60 * 60 * 24 * 7)
        );
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validate(String token) {

        var refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException(
                                "Refresh Token inválido"
                        )
                );

        if (refreshToken.isRevoked()) {
            throw new InvalidRefreshTokenException(
                    "Refresh Token revogado"
            );
        }

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException(
                    "Refresh Token expirado"
            );
        }

        return refreshToken;
    }

    public RefreshToken rotate(String token) {

        RefreshToken oldToken = validate(token);

        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        return create(oldToken.getUser());
    }
    public void revoke(String token) {

        var refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException(
                                "Refresh Token inválido"
                        )
                );

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}