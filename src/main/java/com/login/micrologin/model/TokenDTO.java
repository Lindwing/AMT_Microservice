package com.login.micrologin.model;

public class TokenDTO {
    private Account account;
    private String token;

    public TokenDTO(Account account, String token) {
        this.account = account;
        this.token = token;
    }

    public Account getAccount() {
        return account;
    }

    public String getToken() {
        return token;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenDTO{" +
                "account=" + account +
                ", token='" + token + '\'' +
                '}';
    }
}