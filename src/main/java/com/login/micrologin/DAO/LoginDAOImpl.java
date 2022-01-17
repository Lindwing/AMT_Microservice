package com.login.micrologin.DAO;

import com.login.micrologin.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LoginDAOImpl implements LoginDAO{

    public static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Rui", "user", "1234"));
        users.add(new User(2, "Admin", "admin", "admin"));
        users.add(new User(3, "Filipe", "user", "AA12aa$$"));
    }


    @Override
    public User findById(int id) {
        for (User user : users){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByName(String name) {
        for (User user : users){
            if (user.getUsername().equals(name)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }

    @Override
    public int nbID() {
        return users.size();
    }
}
