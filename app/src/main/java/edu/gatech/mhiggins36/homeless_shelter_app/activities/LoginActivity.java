package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserManager;
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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        //UserManager.checkLogin(getApplicationContext(), username, password);
        try {
            edu.gatech.mhiggins36.homeless_shelter_app.Controllers.UserManager.checkLogin(getApplicationContext(), username, password);
            Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
            searchIntent.putExtra("Sender", "LoginActivity");
            searchIntent.putExtra("userType", userField.getText().toString());
            // enables access to type of account
            startActivity(searchIntent);
            incorrectText.setVisibility(View.INVISIBLE);
        } catch (Error e) {
            incorrectText.setText("incorrect email or password");
        }

    }

}
