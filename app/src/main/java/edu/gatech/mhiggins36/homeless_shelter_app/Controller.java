package edu.gatech.mhiggins36.homeless_shelter_app;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by mhigg on 2/21/2018.
 */

public class Controller {

    static HashMap<String, User> userMap = new HashMap<>();
    static final HashMap<String, Shelter> shelterMap = createMap();
    private static HashMap<String, Shelter> createMap() {
        HashMap<String, Shelter> s = new HashMap<>();
        s.put("My Sister's House", new Shelter(0,"My Sister's House",50,
                null, 0,0,"", null, 1));
        return s;
    }
    static Shelter selectedShelter;

    public static void addUser(User newUser) {
        userMap.put(newUser.getEmail(), newUser);
    }

    public static void setSelectedShelter(Shelter s) {
        selectedShelter = s;
    }
}
