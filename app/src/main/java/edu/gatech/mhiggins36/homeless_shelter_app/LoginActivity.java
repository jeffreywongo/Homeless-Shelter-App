package edu.gatech.mhiggins36.homeless_shelter_app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Checks login credentials
 */

public class LoginActivity extends AppCompatActivity {

    private static final String usernameTest = "user";
    private static final String passwordTest = "pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
