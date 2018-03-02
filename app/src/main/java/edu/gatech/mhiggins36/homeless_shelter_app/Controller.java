package edu.gatech.mhiggins36.homeless_shelter_app;

import android.bluetooth.BluetoothAssignedNumbers;
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

    static HashMap<String, User> userMap = createUserMap();
    static final HashMap<String, Shelter> shelterMap = new HashMap<>();


    public static HashMap<String, User> createUserMap() {
        HashMap<String, User> u = new HashMap<>();
        u.put("user", new User("user", "user", "pass", "User"));
        return u;
    }
    public static void createMapFromcsv(List<String[]> list) {
        Log.d("csv", "here");
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length; j++) {
                Log.d("csv", list.get(i)[j]);
            }
            Log.d("csv", "---------------------------------------------------");
            int uniqueKey = Integer.parseInt(list.get(i)[0]);
            String name = list.get(i)[1].trim();
            String capacity = list.get(i)[2];
            String restrictions = list.get(i)[3];
            float longitude = Float.parseFloat(list.get(i)[4]);
            float lattitude = Float.parseFloat(list.get(i)[5]);
            String address = list.get(i)[6];
            String notes = list.get(i)[7];
            String phone = list.get(i)[8];

            shelterMap.put(name, new Shelter(uniqueKey, name, capacity,
                    restrictions, longitude, lattitude, address, notes, phone));


        }
    }

    /*
    parse the csv file and enter data into shelterMap

    public static void createMapFromcsv(List shelterList) {

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
    } */

    public static void addUser(User newUser) {
        userMap.put(newUser.getEmail(), newUser);
    }
}
