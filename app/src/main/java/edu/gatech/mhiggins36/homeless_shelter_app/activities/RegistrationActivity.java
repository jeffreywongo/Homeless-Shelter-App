package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.mhiggins36.homeless_shelter_app.R;

public class RegistrationActivity extends AppCompatActivity {

    EditText emailField;
    EditText nameField;
    EditText passField;
    EditText passField2;
    TextView errorMessageReg;
    Spinner userTypeSpinner;
    static boolean register = false;

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

    public void register(View view) {
        //if the passwords do not match then reveal red text saying so
        //TODO talk about when we want to trim and when not

        if (nameField.getText().toString().trim().equals("")) {
            errorMessageReg.setText("Name Is Empty");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        if (emailField.getText().toString().trim().equals("")) {
            errorMessageReg.setText("Email Is Empty");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        //checks if the email is in an email format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailField.getText().toString().trim()).matches()) {
            errorMessageReg.setText("Email format is not valid");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        if (passField.getText().toString().equals("")) {
            errorMessageReg.setText("No Password Entered");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        if (passField2.getText().toString().equals("")) {
            errorMessageReg.setText("Must Re-Enter Password");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        if (!(passField.getText().toString().equals(passField2.getText().toString()))) {
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        /*
        check that email is a valid email form
        check that all fields are filled in
        */
            edu.gatech.mhiggins36.homeless_shelter_app.Controllers.UserManager.register(getApplicationContext(),
                    nameField.getText().toString().trim(), emailField.getText().toString().trim(), passField.getText().toString(),
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

    public static void successfulRegistration() {
        register = true;
    }

    public static void failedRegistration() {
        register = false;
    }

    public void cancelRegistration(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}
