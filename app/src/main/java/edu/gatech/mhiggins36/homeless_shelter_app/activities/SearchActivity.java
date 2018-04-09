package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import edu.gatech.mhiggins36.homeless_shelter_app.R;

/**
 * activity for the search page
 */
public class SearchActivity extends AppCompatActivity {

    private EditText nameSearch;
    private Spinner ageSpinner;
    private Spinner genderSpinner;


    /**
     * initializes instance fields
     * @param savedInstanceState generic instance state
     */
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

    /**
     * onClick Method that gets all the info from the textviews and sends the user to the dashboard
     * and sends the data to be searched with, with that call
     * @param view generic view
     */
    public void search(View view) {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        Intent intent = getIntent();
        String user = intent.getStringExtra("userType");
        // get the spinner info

        dashboardIntent.putExtra("Sender", "SearchActivity");
        dashboardIntent.putExtra("name", nameSearch.getText().toString());
        dashboardIntent.putExtra("age", ageSpinner.getSelectedItem().toString());
        dashboardIntent.putExtra("gender", genderSpinner.getSelectedItem().toString());

        dashboardIntent.putExtra("userType", user);
        startActivity(dashboardIntent);
    }
}
