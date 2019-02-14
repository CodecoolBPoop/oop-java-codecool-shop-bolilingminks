package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.JdbcConnectivity;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDaoJDBC implements UserDao {
    private static UserDaoJDBC instance = null;

    private JdbcConnectivity JDBCInstance = JdbcConnectivity.getInstance();

    public static UserDaoJDBC getInstance() {
        if (instance == null) {
            instance = new UserDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(User user) {
        JDBCInstance.executeQuery("INSERT INTO users (first_name, last_name, email, hashed_password, address, state, zip, country, is_admin)" +
                "VALUES ('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getEmail() + "', '" + user.getHashedPassword() + "', '" + user.getAddress() + "','" + user.getState() + "', '" + user.getZip() + "', '" + user.getCountry() + "', '" + "false" + "');");
    }

    @Override
    public User findByEmail(String email) {
        try {
            List<HashMap<String, String>> hashMaps = JDBCInstance.executeQuerySelect("SELECT * FROM users WHERE email= '" + email + "' ;");
            List<User> users = getUserListFromHashMap(hashMaps);
            return users.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException("email isn't found!");
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    private List<User> getUserListFromHashMap(List<HashMap<String, String>> hashMaps) {
        List<User> resultList = new ArrayList<>();
        for (HashMap<String, String> hashMap : hashMaps) {
            User user = new User(hashMap.get("first_name"), hashMap.get("last_name"), hashMap.get("email"),
                    hashMap.get("hashed_password"), hashMap.get("address"), hashMap.get("state"),
                    hashMap.get("zip"), hashMap.get("country"));
            resultList.add(user);
        }
        return resultList;
    }
}
