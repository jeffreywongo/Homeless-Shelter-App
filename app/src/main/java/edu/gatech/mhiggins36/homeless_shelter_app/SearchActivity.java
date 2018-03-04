package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private TextView nameSearch;
    private Spinner ageSpinner;
    private Spinner genderSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        nameSearch = findViewById(R.id.nameSearch);
        ageSpinner  = findViewById(R.id.ageSpinner);
        genderSpinner = findViewById(R.id.genderSpinner);
    }

    public void search(View view) {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        Intent intent = getIntent();
        String user = intent.getStringExtra("userType");

        dashboardIntent.putExtra("userType", user);
        startActivity(dashboardIntent);
    }
}
