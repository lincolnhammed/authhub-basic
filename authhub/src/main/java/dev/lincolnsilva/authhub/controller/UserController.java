package dev.lincolnsilva.authhub.controller;

import dev.lincolnsilva.authhub.dto.UserResponse;
import dev.lincolnsilva.authhub.model.User;

import dev.lincolnsilva.authhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody User user) {

        var usuario = userService.create(user);

        var responseDto = new UserResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
