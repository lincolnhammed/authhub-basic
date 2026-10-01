package dev.lincolnsilva.authhub.dto;

import lombok.Getter;

import java.util.UUID;
@Getter
public class UserResponse {

    private UUID id;
    private String nome;
    private String email;

    public UserResponse(UUID id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

}