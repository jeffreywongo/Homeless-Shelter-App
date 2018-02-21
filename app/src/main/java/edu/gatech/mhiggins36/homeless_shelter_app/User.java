package edu.gatech.mhiggins36.homeless_shelter_app;

/**
 * Created by mhigg on 2/21/2018.
 */

public class User {

    private String password;
    private String name;
    private String email;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this. password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
