package edu.gatech.mhiggins36.homeless_shelter_app.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.VolleySingleton;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mhigg on 4/1/2018.
 */

public class ShelterManager {

    public static HashMap<String, Shelter> shelterMap = new HashMap<>();

    public static void createShelterMap(Context context) {
        String url = "http://shelter.lmc.gatech.edu/shelters";
        Log.d("devin", "onResponse: start");
        // Request a string response
        StringRequest arrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject shelters = new JSONObject(response);
                            JSONArray array = shelters.getJSONArray("shelters");
                            // Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                // Get current json object


                                JSONObject shelter = array.getJSONObject(i);

                                // Get the current student (json object) data
                                String name = shelter.getString("name");
                                int id = shelter.getInt("id");
                                int capacity = shelter.getInt("capacity");
                                int vacancies = shelter.getInt("vacancies");
                                String restrictions = shelter.getString("restrictions");
                                float lattitude = (float)shelter.getDouble("latitude");
                                float longitude = (float)shelter.getDouble("longitude");
                                String address = shelter.getString("address");
                                String phone = shelter.getString("phone");
                                String description = shelter.getString("description");
                                shelterMap.put(name, new Shelter(id, name, capacity, vacancies,
                                        restrictions, longitude, lattitude, address, description, phone));

                            }
                        }catch (JSONException e){
                            Log.d("devin", "onResponse: there " + e.getMessage());
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
        VolleySingleton.getInstance(context).addToRequestQueue(arrayRequest);
    }

    public static void claim(final Context context, String url, final User currentUser) throws Error {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new Error(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String jwt = currentUser.getJwt();
                params.put("x-access-token", jwt);
                return params;
            }
        };
        // Add the request to the queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getShelterInfo(Context context, int shelterID) {
        String uri = "http://shelter.lmc.gatech.edu/shelters/" + shelterID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject shelter = new JSONObject(response);
                            String name = shelter.getString("name");
                            int id = shelter.getInt("id");
                            int capacity = shelter.getInt("capacity");
                            int vacancies = shelter.getInt("vacancies");
                            String restrictions = shelter.getString("restrictions");
                            float lattitude = (float)shelter.getDouble("latitude");
                            float longitude = (float)shelter.getDouble("longitude");
                            String address = shelter.getString("address");
                            String phone = shelter.getString("phone");
                            String description = shelter.getString("description");
                            shelterMap.put(name, new Shelter(id, name, capacity, vacancies,
                                    restrictions, longitude, lattitude, address, description, phone));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new Error(error.getMessage());
            }
        });

        // Add the request to the queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public static void clearBed(Context context, int shelterID, final User currentUser) {
        String uri = "http://shelter.lmc.gatech.edu/user/checkOut/" + shelterID ;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new Error(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String jwt = currentUser.getJwt();
                params.put("x-access-token", jwt);
                return params;
            }
        };

        // Add the request to the queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


}
