package dev.lincolnsilva.authhub.service;

import dev.lincolnsilva.authhub.exception.EmailAlreadyExistsException;
import dev.lincolnsilva.authhub.model.User;
import dev.lincolnsilva.authhub.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(
                    "Email já está cadastrado"
            );
        }

        String passwordHash = passwordEncoder.encode(
                user.getPassword()
        );

        user.setPassword(passwordHash);
        user.setRole("USER");

        return userRepository.save(user);
    }

    public User findByEmail(String email, String password) {

        User user = userRepository
                .findByEmail(email)
                .orElse(null);

        if (user == null) {
            return null;
        }

        boolean passwordCorrect = passwordEncoder.matches(
                password,
                user.getPassword()
        );

        if (!passwordCorrect) {
            return null;
        }

        return user;
    }
}