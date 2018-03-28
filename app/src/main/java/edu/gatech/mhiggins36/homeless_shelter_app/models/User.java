package edu.gatech.mhiggins36.homeless_shelter_app.models;

/**
 * Created by mhigg on 2/21/2018.
 */

public class User {

    private String name;
    private String email;
    private String userType;
    private int userId;
    private String jwt;

    public User(String name, String email, String userType, int userId, String jwt) {
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.userId = userId;
        this.jwt = jwt;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }

    public int getUserId() {
        return userId;
    }

    public String getJwt() {
        return jwt;
    }
}
