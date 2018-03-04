package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText nameSearch;
    private Spinner ageSpinner;
    private Spinner genderSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        nameSearch = findViewById(R.id.nameSearch);
        ageSpinner  = findViewById(R.id.ageSpinner);
        genderSpinner = findViewById(R.id.genderSpinner);
        // populate the spinners
        // create adapter for ageSpinner
        String[] ageSpinnerElems = getResources().getStringArray(R.array.shelterAge);
        ArrayAdapter<String> ageSpinnerAdapter = new ArrayAdapter<>(this,
                 android.R.layout.simple_spinner_item, ageSpinnerElems);
        ageSpinner.setAdapter(ageSpinnerAdapter);
        // create adapter for genderSpinner
        String[] genderSpinnerElems = getResources().getStringArray(R.array.shelterGenders);
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genderSpinnerElems);
        genderSpinner.setAdapter(genderSpinnerAdapter);
    }

    public void search(View view) {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        Intent intent = getIntent();
        String user = intent.getStringExtra("userType");
        // get the spinner info

        dashboardIntent.putExtra("name", nameSearch.getText());
        dashboardIntent.putExtra("age", ageSpinner.getSelectedItem().toString());
        dashboardIntent.putExtra("gender", genderSpinner.getSelectedItem().toString());

        dashboardIntent.putExtra("userType", user);
        startActivity(dashboardIntent);
    }
}
