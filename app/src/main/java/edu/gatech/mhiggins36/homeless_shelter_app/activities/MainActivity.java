package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.ShelterManager;
import edu.gatech.mhiggins36.homeless_shelter_app.R;

/**
 * the activity for the main page
 */
public class MainActivity extends AppCompatActivity {


    /**
     * initializes instance buttons and creates the shelter map
     * @param savedInstanceState generic instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShelterManager.createShelterMap(getApplicationContext());
    }

    /**
     * sends user to the login page
     * @param view generic view
     */
    public void openLoginPage(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }


    /**
     * sends user to registration page
     * @param view generic view
     */
    public void openRegistrationPage(View view) {
        Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registrationIntent);
    }

}
