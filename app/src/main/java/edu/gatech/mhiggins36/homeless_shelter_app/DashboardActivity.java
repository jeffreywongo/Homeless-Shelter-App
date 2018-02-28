package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * App home page
 */

public class DashboardActivity extends AppCompatActivity {

    Button logoutButton;
    TextView userTypeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userTypeMessage = findViewById(R.id.userType);
        logoutButton = findViewById(R.id.logoutButton);
        Intent intent = getIntent();
        String user = intent.getStringExtra("userType");
        String userType = Controller.userMap.get(user).getUserType();
        userTypeMessage.setText("Logged in as a(n) " + userType);

        listShelters();
    }

    /*
    called on create of the dashboard
    displays all the shelters on the dashboard
     */
    private void listShelters() {
        for (Shelter s : Controller.shelterMap.values()) {
            //iterates through all the shelters in the shelter hash map from the controller
            //not in the same order everytime


        }
    }
    public void logout(View view) {
        Intent logoutIntent = new Intent(this, MainActivity.class);
        startActivity(logoutIntent);
    }
}
