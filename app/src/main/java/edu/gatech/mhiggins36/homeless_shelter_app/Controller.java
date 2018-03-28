package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.test.mock.MockContext;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by mhigg on 2/21/2018.
 */

import java.util.List;
import java.util.Map;

import edu.gatech.mhiggins36.homeless_shelter_app.activities.SearchActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;


public class Controller {

    public static HashMap<String, User> userMap = createUserMap();
    public static final HashMap<String, Shelter> shelterMap = new HashMap<>();
    public static List<Shelter> shelterList;

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

    private void createShelterMap() {
        String url = "http://shelter.lmc.gatech.edu/shelters";

        // Request a string response
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject shelter = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String name = shelter.getString("name");
                                int id = shelter.getInt("id");
                                String capacity = shelter.getString("capacity");
                                int vacancies = shelter.getInt("vacancies");
                                String restrictions = shelter.getString("restrictions");
                                float lattitude = (float)shelter.getDouble("lattitude");
                                float longitude = (float)shelter.getDouble("longitude");
                                String address = shelter.getString("address");
                                String phone = shelter.getString("phone");
                                String description = shelter.getString("description");

                                shelterMap.put(name, new Shelter(id, name, capacity,
                                        restrictions, longitude, lattitude, address, description, phone));

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        });
        // Add the request to the queue
        //dont know if mock context is what we need TODO
        VolleySingleton.getInstance(new MockContext()).addToRequestQueue(arrayRequest);
    }


    public static void addUser(User newUser) {
        userMap.put(newUser.getEmail(), newUser);
    }
}
