package weg.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Bem-vindo à aplicação Spring Boot! Use /cadastro para cadastrar e /login para logar";
    }

    @GetMapping("/cadastro")
    public String cadastroGet() {
        return "Endpoint de cadastro - Use POST para enviar dados";
    }

    @PostMapping("/cadastro")
    public String cadastroPost(@RequestParam(required = false) String username, 
                              @RequestParam(required = false) String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return "Erro: Usuário e senha são obrigatórios";
        }
        return "Usuário " + username + " cadastrado com sucesso!";
    }

    @GetMapping("/login")
    public String loginGet() {
        return "Endpoint de login - Use POST para enviar dados";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam(required = false) String username, 
                           @RequestParam(required = false) String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return "Erro: Usuário e senha são obrigatórios";
        }
        return "Login realizado com sucesso para o usuário " + username + "!";
    }
}
