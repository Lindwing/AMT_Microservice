package com.login.micrologin.Service;

import com.login.micrologin.DAO.UserRepository;
import com.login.micrologin.model.Account;
import com.login.micrologin.model.TokenDTO;
import com.login.micrologin.model.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;

public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

        public ResponseEntity<TokenDTO> login(@RequestBody User user) throws IOException {
        final String jwtSecretPath = "/home/admin/Secret/SecretJWT";

        HttpStatus status;
        int id =-1;
        String role = "";
        User userDB = userRepository.findByUsername(user.getUsername());

        //read the url file
        File fileJWT = new File(jwtSecretPath);
        BufferedReader brJWT = new BufferedReader(new FileReader(fileJWT));
        String tokenSecret = brJWT.readLine();

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
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + (2 * 60 * 60 * 1000)))//ajoute 2 heure
                    .signWith(
                            SignatureAlgorithm.HS256,
                            tokenSecret.getBytes(Charset.forName("UTF-8"))
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
        String role = "user";

        if(checkPassword(user.getPassword())){
            if(userRepository.findByUsername(user.getUsername()) == null){
                User userDB = new User();
                userDB.setUsername(user.getUsername());
                userDB.setPassword(user.getPassword());
                userDB.setRole(role);
                userDB = userRepository.save(userDB);
                id = userDB.getId();
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
