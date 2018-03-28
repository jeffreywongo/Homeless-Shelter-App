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

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
