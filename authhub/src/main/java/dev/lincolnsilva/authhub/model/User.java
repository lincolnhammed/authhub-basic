package dev.lincolnsilva.authhub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        private String nome;
        @Column(unique = true, nullable = false)
        private String email;

        private String password;
        private String role;

}
