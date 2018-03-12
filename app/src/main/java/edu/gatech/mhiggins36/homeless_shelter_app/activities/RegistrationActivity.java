package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.mhiggins36.homeless_shelter_app.Controller;
import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;

public class RegistrationActivity extends AppCompatActivity {

    EditText emailField;
    EditText nameField;
    EditText passField;
    EditText passField2;
    TextView errorMessageReg;
    Spinner userTypeSpinner;

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

        if (nameField.getText().toString().equals("")) {
            errorMessageReg.setText("Name Is Empty");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        if (emailField.getText().toString().equals("")) {
            errorMessageReg.setText("Email Is Empty");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        if (Controller.userMap.containsKey(emailField.getText().toString())) {
            errorMessageReg.setText("Email Entered Already In Use");
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

        User newUser = new User(nameField.getText().toString(), emailField.getText().toString(),
                passField.getText().toString(), userTypeSpinner.getSelectedItem().toString());

        Controller.addUser(newUser);

        // todo: discuss this later
        Intent searchIntent = new Intent(this, SearchActivity.class);
        searchIntent.putExtra("Sender", "RegistrationActivity");
        searchIntent.putExtra("userType", emailField.getText().toString());
        // enables access to type of account
        startActivity(searchIntent);

    }

    public void cancelRegistration(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}
