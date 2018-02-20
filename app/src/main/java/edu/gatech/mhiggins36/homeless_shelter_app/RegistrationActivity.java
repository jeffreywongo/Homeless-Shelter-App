package edu.gatech.mhiggins36.homeless_shelter_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    EditText userField;
    EditText passField;
    EditText passField2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userField = findViewById(R.id.usernameFieldReg);
        passField = findViewById(R.id.passwordFieldReg);
        passField2 = findViewById(R.id.passwordFieldReg2);
    }
}
