package com.login.micrologin.web.controller;

import com.login.micrologin.DAO.UserRepository;
import com.login.micrologin.Service.LoginService;
import com.login.micrologin.model.Account;
import com.login.micrologin.model.TokenDTO;
import com.login.micrologin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class LoginController {
    private final LoginService loginService;

    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
        this.loginService = new LoginService(userRepository);
    }

    @PostMapping(value = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDTO> login(@RequestBody User user) throws IOException {
        return loginService.login(user);
    }

    //Récupérer un produit par son Id
    @PostMapping(value = "/accounts/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Account> register(@RequestBody User user) {
        return loginService.register(user);
    }

}