package com.login.micrologin.model;

public class Account {
    private int id;
    private String nom;
    private String role;

    public Account(int id, String nom, String role) {
        this.id = id;
        this.nom = nom;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", role=" + role +
                '}';
    }
}