package com.login.micrologin.Service;

import com.login.micrologin.DAO.LoginDAO;
import com.login.micrologin.DAO.LoginDAOImpl;
import com.login.micrologin.model.Account;
import com.login.micrologin.model.TokenDTO;
import com.login.micrologin.model.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Date;

public class LoginService {
    private final LoginDAOImpl loginDAO;

    public LoginService(LoginDAO loginDAO){
        this.loginDAO = new LoginDAOImpl();
    }

        public ResponseEntity<TokenDTO> login(@RequestBody User user){
        HttpStatus status;
        int id =-1;
        String role = "";
        User userDB = loginDAO.findByName(user.getUsername());

        if(userDB != null && user.getPassword().equals(userDB.getPassword())){
            id = userDB.getId();
            role = userDB.getRole();
            status =HttpStatus.OK;
        }
        else{
            status = HttpStatus.FORBIDDEN;
        }

        JwtBuilder jws = null;

        try{
            jws = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setIssuer("SHOPELS")
                    .setSubject(user.getUsername())
                    .claim("role", role)
                    // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
                    .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
                    // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                    .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
                    .signWith(
                            SignatureAlgorithm.HS256,
                            "czvFbg2kmvqbcu(7Ux+c".getBytes(Charset.forName("UTF-8"))
                    );
        }
        catch(Exception errorMessage){
            System.out.println(errorMessage);
        }

        TokenDTO loginResponse = new TokenDTO (new Account(id, user.getUsername(), role), jws.compact());
        return new ResponseEntity<TokenDTO>(loginResponse, status);
    }

    public ResponseEntity<Account> register(@RequestBody User user){
        HttpStatus status;
        int id =-1;
        String role = "";

        if(checkPassword(user.getPassword())){
            if(loginDAO.findByName(user.getUsername()) == null){
                role = "user";
                id = loginDAO.nbID();
                loginDAO.save(new User(id, user.getUsername(), role, user.getPassword()));
                status = HttpStatus.CREATED;
            }
            else {
                status = HttpStatus.CONFLICT;
            }
        }
        else{
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        Account loginResponse = new Account(id, user.getUsername(), role);
        return new ResponseEntity<Account>(loginResponse, status);
    }

    //https://codescracker.com/java/program/java-check-password-strength.htm
    private boolean checkPassword(String password){
        int passwordLength=8, size = 0;
        boolean special=false, digits=false, upChars=false, lowChars=false;
        char ch;

        size = password.length();
        if(size >= passwordLength)
        {
            for(int i=0; i<size; i++)
            {
                ch = password.charAt(i);
                if(Character.isUpperCase(ch))
                    upChars = true;
                else if(Character.isLowerCase(ch))
                    lowChars = true;
                else if(Character.isDigit(ch))
                    digits = true;
                else
                    special = true;
            }
        }
        return (upChars && lowChars && digits && special);
    }
}
