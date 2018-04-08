package edu.gatech.mhiggins36.homeless_shelter_app.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.mhiggins36.homeless_shelter_app.VolleySingleton;
import edu.gatech.mhiggins36.homeless_shelter_app.activities.LoginActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.activities.RegistrationActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.activities.SearchActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mhigg on 4/1/2018.
 */

public class UserManager {

    public static void checkLogin(final Context context, final String username,
                                        final String password) {
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
                            // todo: make server send full user info in response
                            // todo: or query databse for user info in the future
                            /*
                            Get the currentUser from the SharedPreference
                             */
                            SharedPreferences pref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
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
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                // Error handling
                //sets login boolean to false
                LoginActivity.failedLogin();
                Log.d("login server error: ", error.networkResponse.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);
                return params;
            }
        };
        // Add the request to the queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public static void register(final Context context, final String name, final String email, final String pass,
                                final String userType) {
        //ToDo get the specific path for this call
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
                            SharedPreferences pref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", pass);
                return params;
            }
        };
        // Add the request to the queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}

