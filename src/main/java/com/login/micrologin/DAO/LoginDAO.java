package com.login.micrologin.DAO;


import com.login.micrologin.model.User;

public interface LoginDAO {
    User findById(int id);
    User findByName(String name);
    User save(User user);
    int nbID();
}
