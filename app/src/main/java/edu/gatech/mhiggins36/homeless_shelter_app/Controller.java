package edu.gatech.mhiggins36.homeless_shelter_app;

import java.util.HashMap;

/**
 * Created by mhigg on 2/21/2018.
 */

public class Controller {

    static HashMap<String, User> userMap = new HashMap<>();
    static HashMap<Integer, Shelter> shelterMap = new HashMap<>();

    public static void addUser(User newUser) {
        userMap.put(newUser.getEmail(), newUser);
    }
}
