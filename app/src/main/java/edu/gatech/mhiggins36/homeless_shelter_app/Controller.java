package edu.gatech.mhiggins36.homeless_shelter_app;

import android.util.Log;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by mhigg on 2/21/2018.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Controller {

    static HashMap<String, User> userMap = new HashMap<>();
    static final HashMap<String, Shelter> shelterMap = new HashMap<>();

    /*
    parse the csv file and enter data into shelterMap
     */
    public static void createMapFromcsv() {
        String csvFile = "Homeless_Shelter_Database.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = "";
            String cvsSplitBy = ",";
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] info = line.split(cvsSplitBy);

                // create fields for shelter object
                int uniqueKey = Integer.parseInt(info[0]);
                String name = info[1];
                System.out.println(name);
                int capacity = Integer.parseInt(info[2]);
                String restrictions = info[3];
                float longitude = Float.parseFloat(info[4]);
                float latitude = Float.parseFloat(info[5]);
                String address = info[6];
                String specialNotes = info[7];
                int phoneNumber = Integer.parseInt(info[8]);

                //put a new shelter in the hashmap
                shelterMap.put(name, new Shelter(uniqueKey, name, capacity, restrictions, longitude,
                        latitude, address, specialNotes, phoneNumber));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User newUser) {
        userMap.put(newUser.getEmail(), newUser);
    }
}
