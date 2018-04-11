package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.UserManager;
import edu.gatech.mhiggins36.homeless_shelter_app.R;

/**
 * Checks login credentials
 */

public class LoginActivity extends AppCompatActivity {

    //    private static final String usernameTest = "user";
//    private static final String passwordTest = "pass";
    private EditText userField;
    private EditText passField;
    private TextView incorrectText;
    static boolean login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userField = findViewById(R.id.usernameField);
        passField = findViewById(R.id.passwordField);
        incorrectText = findViewById(R.id.incorrectCred);
        incorrectText.setVisibility(View.INVISIBLE);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    /*
    functionality for back button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    /**
     * Checks and validates the credentials
     * @param view this is required by android so...
     */
    public void checkCredentials(View view) { // we need view because android makes us
        String username = userField.getText().toString().trim();
        String password = passField.getText().toString();
        //UserManager.checkLogin(getApplicationContext(), username, password);
        //TODO make sure that we are actually handling server errors properly

        UserManager.getInstance(getApplicationContext()).checkLogin(username, password);
        if (login) {
            Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
            searchIntent.putExtra("Sender", "LoginActivity");
            searchIntent.putExtra("userType", userField.getText().toString());
            // enables access to type of account
            incorrectText.setVisibility(View.INVISIBLE);
            startActivity(searchIntent);
//            incorrectText.setVisibility(View.INVISIBLE);
        } else {
            incorrectText.setVisibility(View.VISIBLE);
            incorrectText.setText("incorrect email or password");
        }
    }

    /**
     * checks if login was successful
     */
    public static void successfulLogin() {
       login = true;
    }

    /**
     * checks if login was
     */
    public static void failedLogin() {

        login = false;
    }



}
