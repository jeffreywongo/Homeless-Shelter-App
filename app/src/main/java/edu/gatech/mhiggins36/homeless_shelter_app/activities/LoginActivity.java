package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.gatech.mhiggins36.homeless_shelter_app.Controller;
import edu.gatech.mhiggins36.homeless_shelter_app.R;

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
        if (Controller.userMap.containsKey(username)
                && Controller.userMap.get(username).getPassword().equals(password)) {
            Intent searchIntent = new Intent(this, SearchActivity.class);
            searchIntent.putExtra("Sender", "LoginActivity");
            searchIntent.putExtra("userType", userField.getText().toString());
            // enables access to type of account
            startActivity(searchIntent);
            incorrectText.setVisibility(View.INVISIBLE);
        } else {
            incorrectText.setVisibility(View.VISIBLE);
        }
    }

}
