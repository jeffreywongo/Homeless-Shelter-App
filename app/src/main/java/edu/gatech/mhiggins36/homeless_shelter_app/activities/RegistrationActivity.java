package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.UserManager;
import edu.gatech.mhiggins36.homeless_shelter_app.R;

/**
 * activity for registration
 */
public class RegistrationActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText nameField;
    private EditText passField;
    private EditText passField2;
    private TextView errorMessageReg;
    private Spinner userTypeSpinner;
    private static boolean register;

    /**
     * initializes instance fields
     * @param savedInstanceState generic state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        nameField = findViewById(R.id.nameFieldReg);
        emailField = findViewById(R.id.emailFieldReg);
        passField = findViewById(R.id.passwordFieldReg);
        passField2 = findViewById(R.id.passwordFieldReg2);
        errorMessageReg = findViewById(R.id.errorMessageReg);

        //stuff needed for the spinner found online
        //https://developer.android.com/guide/topics/ui/controls/spinner.html
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.userTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

    }

    /**
     * checks that all of the text views are satisfactory i.e. not blank
     * then makes a call to register a user based on the text view data
     * if the register boolean is set to true then that call was successful and we send the  user
     * to the search activity
     * if that call is not successful then register will be false and we will
     * set the error text to tell the user that something was wrong
     * @param view generic view
     */
    public void register(View view) {
        if (checkRegistrationErrors(view)) {
            return;
        }
        /*
        check that email is a valid email form
        check that all fields are filled in
        */
            UserManager.register(getApplicationContext(),
                    nameField.getText().toString().trim(), emailField.getText()
                            .toString().trim(), passField.getText().toString(),
                    userTypeSpinner.getSelectedItem().toString());
            if (register) {
                Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                searchIntent.putExtra("Sender", "RegistrationActivity");
                searchIntent.putExtra("userType", emailField.getText().toString());
                // enables access to type of account
                startActivity(searchIntent);
            } else {
                errorMessageReg.setText("Email Entered Already In Use");
                errorMessageReg.setVisibility(View.VISIBLE);
            }

    }

    /**
     * contains logic for checking issues with registration
     * @param view generic view
     * @return whether or not there is an error
     */
    private boolean checkRegistrationErrors(View view) {
        if ("".equals(nameField.getText().toString().trim())) {
            errorMessageReg.setText("Name Is Empty");
            errorMessageReg.setVisibility(View.VISIBLE);
            return true;
        }
        if ("".equals(emailField.getText().toString().trim())) {
            errorMessageReg.setText("Email Is Empty");
            errorMessageReg.setVisibility(View.VISIBLE);
            return true;
        }
        //checks if the email is in an email format
        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(emailField.getText().toString().trim()).matches()) {
            errorMessageReg.setText("Email format is not valid");
            errorMessageReg.setVisibility(View.VISIBLE);
            return true;
        }
        if ("".equals(passField.getText().toString())) {
            errorMessageReg.setText("No Password Entered");
            errorMessageReg.setVisibility(View.VISIBLE);
            return true;
        }
        if ("".equals(passField2.getText().toString())) {
            errorMessageReg.setText("Must Re-Enter Password");
            errorMessageReg.setVisibility(View.VISIBLE);
            return true;
        }
        if (!(passField.getText().toString().equals(passField2.getText().toString()))) {
            errorMessageReg.setText("Passwords do not match");
            errorMessageReg.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    /**
     * sets the register boolean to true, signaling that the api call was successful
     */
    public static void successfulRegistration() {
        register = true;
    }

    /**
     * sets the register boolean to false signalling that the api was unsuccessful
     */
    public static void failedRegistration() {
        register = false;
    }

    /**
     * sends the user to the main page
     * @param view generic view
     */
    public void cancelRegistration(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}
