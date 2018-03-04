package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void search(View view) {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        Intent intent = getIntent();
        String user = intent.getStringExtra("userType");

        dashboardIntent.putExtra("userType", user);
        startActivity(dashboardIntent);
    }
}
