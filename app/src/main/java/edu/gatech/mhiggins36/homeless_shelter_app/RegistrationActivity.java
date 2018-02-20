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

    EditText userField;
    EditText passField;
    EditText passField2;
    TextView nonMatchingPasswords;
    Spinner userTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userField = findViewById(R.id.usernameFieldReg);
        passField = findViewById(R.id.passwordFieldReg);
        passField2 = findViewById(R.id.passwordFieldReg2);
        nonMatchingPasswords = findViewById(R.id.nonMatchingPasswords);

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
            nonMatchingPasswords.setVisibility(View.VISIBLE);
            return;
        }

        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        startActivity(dashboardIntent);

    }
}
