package dev.lincolnsilva.authhub.repository;

import dev.lincolnsilva.authhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

//    O Spring entende o nome:
//    findByEmail
//    como:
//            "procure um User cujo campo email seja igual ao valor que eu passar."
//    Então:
//            userRepository.findByEmail("lincoln@email.com");
//    conceitualmente vira algo parecido com:
//    SELECT * FROM users WHERE email = 'lincoln@email.com';
    Optional<User> findByEmail(String email);




}
