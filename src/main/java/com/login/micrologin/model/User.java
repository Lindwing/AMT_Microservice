package com.login.micrologin.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(nullable = false)
    private String password;

    public User(){}

    public User(int id, String username, String role, String password) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}