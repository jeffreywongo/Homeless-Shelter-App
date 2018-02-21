package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
        if (!(passField.getText().toString().equals(passField2.getText().toString()))) {
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        if (emailField.getText().toString().equals("")) {
            errorMessageReg.setText("email is empty");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        if (nameField.getText().toString().equals("")) {
            errorMessageReg.setText("name is empty");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        if (passField.getText().toString().equals("")) {
            errorMessageReg.setText("no password entered");
            errorMessageReg.setVisibility(View.VISIBLE);
            return;
        }
        /*
        check that email is a valid email form
        check that all fields are filled in
        */

        User newUser = new User(nameField.getText().toString(),
                emailField.getText().toString(), passField.getText().toString());

        Controller.addUser(newUser);
        
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        startActivity(dashboardIntent);

    }
}
