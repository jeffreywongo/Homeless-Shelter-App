package edu.gatech.mhiggins36.homeless_shelter_app.Controllers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.mhiggins36.homeless_shelter_app.VolleySingleton;
import edu.gatech.mhiggins36.homeless_shelter_app.activities.ShelterInfoActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;

/**
 * Controller used to interface with the server in order to access shelter data
 */
public class ShelterManager {

    public static final Map<String, Shelter> shelterMap = new HashMap<>();
    private static ShelterManager shelterManagerInstance;
    private static Context myContext;

    private ShelterManager(Context context) {
        myContext = context;
    }


    /**
     * makes a singleton instance of shelter manager
     * @param context the current context of the application
     * @return a singleton shelterManager
     */
    public static synchronized ShelterManager getInstance(Context context) {
        if (shelterManagerInstance == null) {
            shelterManagerInstance = new ShelterManager(context);
        }
        return shelterManagerInstance;
    }

    /**
     * Creates full shelter map used for activities throughout app. It does this by making a request
     * to the API and using the information retrieved
     */
    public void createShelterMap() {
        String url = "http://shelter.lmc.gatech.edu/shelters";
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
                                float latitude = (float)shelter.getDouble("latitude");
                                float longitude = (float)shelter.getDouble("longitude");
                                String address = shelter.getString("address");
                                String phone = shelter.getString("phone");
                                String description = shelter.getString("description");
                                shelterMap.put(name, new Shelter(id, name, capacity, vacancies,
                                        restrictions, longitude, latitude, address, description,
                                        phone));

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
                error.printStackTrace();
            }
        });
        // Add the request to the queue
        VolleySingleton.getInstance(myContext).addToRequestQueue(arrayRequest);
    }

    /**
     * Handles the server side functionality of making a reservation at a shelter
     * @param url uri used to make the API request
     * @param currentUser user currently using the app
     * @param bedCount number of beds current user wants to reserve
     */
    public void claim(String url, final User currentUser, final int bedCount) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject shelter = new JSONObject(response);
                            String name = shelter.getString("name");
                            int vacancies = shelter.getInt("vacancies");
                            Shelter mapShelter = shelterMap.get(name);
                            mapShelter.setVacancies(vacancies);
                        } catch (Exception e) {
                            ShelterInfoActivity.setClaimed(false);
                        }
                        ShelterInfoActivity.setClaimed(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShelterInfoActivity.setClaimed(false);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                String jwt = currentUser.getJwt();
                params.put("x-access-token", jwt);
                return params;
            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("count", ""+ bedCount);
                return params;
            }
        };
        // Add the request to the queue
        VolleySingleton.getInstance(myContext).addToRequestQueue(stringRequest);
    }

    /**
     * Handles the server side functionality of canceling a reservation at a shelter
     * @param currentUser user currently using the app
     * @param shelterID id of the shelter at which to cancel the reservation
     */
    public void clearBed(final User currentUser, final int shelterID) {
        String uri = "http://shelter.lmc.gatech.edu/user/checkOut/" + shelterID;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject shelter = new JSONObject(response);
                            String name = shelter.getString("name");
                            int vacancies = shelter.getInt("vacancies");
                            Shelter mapShelter = shelterMap.get(name);
                            mapShelter.setVacancies(vacancies);
                        } catch (Exception e) {
                            ShelterInfoActivity.setUnclaimed(false);
                        }

                        ShelterInfoActivity.setUnclaimed(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShelterInfoActivity.setUnclaimed(false);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                String jwt = currentUser.getJwt();
                params.put("x-access-token", jwt);
                return params;
            }
        };

        // Add the request to the queue
        VolleySingleton.getInstance(myContext).addToRequestQueue(stringRequest);
    }


}
