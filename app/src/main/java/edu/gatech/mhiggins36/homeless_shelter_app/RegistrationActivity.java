package edu.gatech.mhiggins36.homeless_shelter_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    EditText userField;
    EditText passField;
    EditText passField2;
    TextView nonMatchingPasswords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userField = findViewById(R.id.usernameFieldReg);
        passField = findViewById(R.id.passwordFieldReg);
        passField2 = findViewById(R.id.passwordFieldReg2);
        nonMatchingPasswords = findViewById(R.id.nonMatchingPasswords);
    }

    public void register(View view) {
        //if the passwords do not match then reveal red text saying so
        if (!(passField.equals(passField2))) {
            nonMatchingPasswords.setVisibility(View.VISIBLE);
        }
    }
}
