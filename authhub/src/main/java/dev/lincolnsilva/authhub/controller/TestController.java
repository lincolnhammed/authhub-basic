package dev.lincolnsilva.authhub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/teste")
    public String teste() {
        return "Você está autenticado!";
    }

    @GetMapping("/user")
    public String user() {
        return "Você está na área USER!";
    }

    @GetMapping("/gerente")
    public String gerente() {
        return "Você está na área GERENTE!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Você está na área ADMIN!";
    }
}