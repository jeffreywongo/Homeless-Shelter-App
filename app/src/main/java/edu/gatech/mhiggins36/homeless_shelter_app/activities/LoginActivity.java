package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.mhiggins36.homeless_shelter_app.Controller;
import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.VolleySingleton;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;

/**
 * Checks login credentials
 */

public class LoginActivity extends AppCompatActivity {

//    private static final String usernameTest = "user";
//    private static final String passwordTest = "pass";
    EditText userField;
    EditText passField;
    TextView incorrectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userField = findViewById(R.id.usernameField);
        passField = findViewById(R.id.passwordField);
        incorrectText = findViewById(R.id.incorrectCred);
        incorrectText.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*
    functionality for back button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    /*
    listener for sign in button
     */
    public void checkCredentials(View view) {
        String username = userField.getText().toString().trim();
        String password = passField.getText().toString();

//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
//            //incorrectText.setText("Email format is not valid");
//            incorrectText.setVisibility(View.VISIBLE);
//            return;
//        }

        //if the username (email) is in the user hash map and the password is the same
        //password mapped to that email then go to the dashboard
//        if (Controller.userMap.containsKey(username)
//                && Controller.userMap.get(username).getPassword().equals(password)) {
//            Intent searchIntent = new Intent(this, SearchActivity.class);
//            searchIntent.putExtra("Sender", "LoginActivity");
//            searchIntent.putExtra("userType", userField.getText().toString());
//            // enables access to type of account
//            startActivity(searchIntent);
//            incorrectText.setVisibility(View.INVISIBLE);
//        } else {
//            incorrectText.setVisibility(View.VISIBLE);
//        }
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
                            // todo: maybe put this in a static method within this activity or somn'
                            SharedPreferences pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = pref.edit();
                            Gson gson = new Gson();
                            String json = pref.getString("currentUser", "");
                            Log.d("Login", json);
                            User currentUser = gson.fromJson(json, User.class);
                            currentUser.setUserId(userId);
                            currentUser.setJwt(token);
                            prefsEditor.putString("currentUser", json);
                            prefsEditor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                        searchIntent.putExtra("Sender", "LoginActivity");
                        searchIntent.putExtra("userType", userField.getText().toString());
                        // enables access to type of account
                        startActivity(searchIntent);
                        incorrectText.setVisibility(View.INVISIBLE);
                        Log.d("volley", "onResponse: success");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                incorrectText.setVisibility(View.VISIBLE);
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", userField.getText().toString().trim());
                params.put("password", passField.getText().toString());
                return params;
            }
        };
        // Add the request to the queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

}