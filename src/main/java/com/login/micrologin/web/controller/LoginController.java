package com.login.micrologin.web.controller;

import com.login.micrologin.DAO.LoginDAO;
import com.login.micrologin.DAO.LoginDAOImpl;
import com.login.micrologin.Service.LoginService;
import com.login.micrologin.model.Account;
import com.login.micrologin.model.TokenDTO;
import com.login.micrologin.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private final LoginService loginService;

    private final LoginDAO loginDAO;

    public LoginController(LoginDAO loginDAO){
        this.loginDAO = new LoginDAOImpl();
        this.loginService = new LoginService(loginDAO);
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDTO> login(@RequestBody User user) {
        //TokenDTO loginResponse = LoginService.login(user);
        //return new ResponseEntity<TokenDTO>(loginResponse, HttpStatus.OK);
        return loginService.login(user);
    }

    //Récupérer un produit par son Id
    @PostMapping(value = "/accounts/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Account> register(@RequestBody User user) {
        //User user = new User(id, "Rui", "user", "1234");
        //Account loginResponse = LoginService.register(user);
        //return new ResponseEntity<Account>(loginResponse, HttpStatus.CREATED);
        return loginService.register(user);
    }

}