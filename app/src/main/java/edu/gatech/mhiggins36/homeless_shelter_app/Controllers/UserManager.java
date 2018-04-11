package edu.gatech.mhiggins36.homeless_shelter_app.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.mhiggins36.homeless_shelter_app.VolleySingleton;
import edu.gatech.mhiggins36.homeless_shelter_app.activities.LoginActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.activities.RegistrationActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Controller used to interface with the server in order to access user data
 */
public class UserManager {

    private static UserManager mInstance;
    private static Context cxt;

    private UserManager(Context context) {
        cxt = context;
    }

    /**
     * makes a singleton instance of user manager
     * @param context the current context of the application
     * @return a singleton userManager
     */
    public static synchronized UserManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserManager(context);
        }
        return mInstance;
    }
    /**
     * makes an API call to the server that gives the username and password as parameters
     * on an error response calls the method in LoginActivity to signal a failed login. Otherwise
     * signals a successful login in LoginActivity after parsing the JSON response
     * @param username entered username of person trying to login
     * @param password entered password of person trying to login
     */
    public void checkLogin(final String username, final String password) {
        String url = "http://shelter.lmc.gatech.edu/user/login";
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Result handling
                        try {
                            JSONObject obj = new JSONObject(response);
                            int userId = obj.getInt("id");
                            String token = obj.getString("token");
                            //Get the currentUser from the SharedPreference
                            SharedPreferences pref = cxt.getSharedPreferences("myPrefs",
                                    MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = pref.edit();
                            Gson gson = new Gson();
                            String json = pref.getString("currentUser", "");
                            Log.d("Login", json);
                            User currentUser = gson.fromJson(json, User.class);
                            currentUser.setUserId(userId);
                            currentUser.setJwt(token);
                            prefsEditor.putString("currentUser", json);
                            prefsEditor.commit();

                            //sets the login boolean to true in LoginActivity
                            LoginActivity.successfulLogin();
                        } catch (JSONException e) {
                            LoginActivity.failedLogin();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                // Error handling
                //sets login boolean to false
                LoginActivity.failedLogin();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);
                return params;
            }
        };
        // Add the request to the queue
        VolleySingleton.getInstance(cxt).addToRequestQueue(stringRequest);

    }

    /**
     * makes an API call to the server to make a new user and passes in the necessary requirements
     * @param name the name of the person registering
     * @param email the email of the person registering
     * @param pass the password of the person registering
     * @param userType the user type of the person registering
     */
    public void register(final String name, final String email, final String pass,
                                final String userType) {
        String url = "http://shelter.lmc.gatech.edu/user/register";

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Register", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            int userId = obj.getInt("id");
                            String token = obj.getString("token");
                            User currentUser =
                                    new User(name,
                                            email,
                                            userType,
                                            userId, token);
                            /*
                            All this is to put currentUser in a SharedPreference. Using a static
                            field in Controller won't work since it gets wiped when the app is
                            closed. A SharedPreference saves it on the device and lets you access
                            it in any activity.
                             */
                            SharedPreferences pref = cxt.getSharedPreferences("myPrefs",
                                    MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = pref.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(currentUser);
                            prefsEditor.putString("currentUser", json);
                            prefsEditor.commit();
                            RegistrationActivity.successfulRegistration();

                        } catch (JSONException e) {
                            RegistrationActivity.failedRegistration();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                RegistrationActivity.failedRegistration();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", pass);
                return params;
            }
        };
        // Add the request to the queue
        VolleySingleton.getInstance(cxt).addToRequestQueue(stringRequest);
    }
}

