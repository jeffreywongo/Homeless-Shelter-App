package edu.gatech.mhiggins36.homeless_shelter_app.models;

/**
 * Created by mhigg on 2/21/2018.
 */

public class User {

    private String password;
    private String name;
    private String email;
    private String userType;

    public User(String name, String email, String password, String userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
}
