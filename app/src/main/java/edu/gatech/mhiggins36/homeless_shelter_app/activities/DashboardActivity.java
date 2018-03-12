package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gatech.mhiggins36.homeless_shelter_app.CSVFile;
import edu.gatech.mhiggins36.homeless_shelter_app.Controller;
import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;

/**
 * App home page
 */

public class DashboardActivity extends AppCompatActivity {

    Button logoutButton;
    TextView userTypeMessage;
    ListView shelterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputStream inputStream = getResources().openRawResource(R.raw.homeless_shelter_database);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> list = csvFile.read();

        Controller.createMapFromcsv(list);
        boolean b = Controller.shelterMap.containsKey("Eden Village ");
        if (b) {
            Log.d("devin", " is a key");
        } else {
            Log.d("devin", "not a key");
        }

        setContentView(R.layout.activity_dashboard);
        userTypeMessage = findViewById(R.id.userType);
        logoutButton = findViewById(R.id.logoutButton);
        shelterList = findViewById(R.id.shelterList);
        // set listener for if an item is clicked
        shelterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView dataHolder = view.findViewById(R.id.listItem);
                String shelterName = (String) dataHolder.getText();
//                System.out.println(dataHolder.getText().toString());
                Intent shelterInfoIntent = new Intent(getBaseContext(), ShelterInfoActivity.class);
                // query shelters hashmap and put the shelter in the intent
                //Log.d("devin", Controller.shelterMap.get(shelterName).getName());
                shelterInfoIntent.putExtra("Shelter", Controller.shelterMap.get(shelterName));
                startActivity(shelterInfoIntent);
            }
        });

        Intent intent = getIntent();
        // check which intent is being handled with this
        String sender = intent.getStringExtra("Sender");
        if (sender.equals("SearchActivity")) {
            String name = intent.getStringExtra("name");
            String age = intent.getStringExtra("age");
            String gender = intent.getStringExtra("gender");
            // if search bar used then disregard spinner selections
            if (name != null && name.trim().length() != 0) {
                Log.d("Dash", "called with " + name);
                listShelters(name, null, null);
            } else {
                listShelters(null, age, gender);
            }
        }
        String user = intent.getStringExtra("userType");
        String userType = Controller.userMap.get(user).getUserType();
        userTypeMessage.setText("Logged in as a(n) " + userType);
    }

    /*
    called on create of the dashboard
    displays all the shelters on the dashboard
     */
    private void listShelters(String name, String age, String gender) {
//        String[] names = getResources().getStringArray(R.array.shelters);
        HashMap<String, Shelter> shelters = Controller.shelterMap;
        List<String> shelterNames = new ArrayList<>();
        // if searched by name
        if (name != null) {
            for (String key : shelters.keySet()) {
                if (key.contains(name)) {
                    shelterNames.add(key);
                }
            }
        } else {
            if (age.equals("Anyone") && !gender.equals("Anyone")) {
                for (Shelter shelter : shelters.values()) {
                    String restrictions = shelter.getRestrictions();
                    if (restrictions.contains(gender)) {
                        shelterNames.add(shelter.getName());
                    }
                }
            }
            if (!age.equals("Anyone") && gender.equals("Anyone")) {
                for (Shelter shelter : shelters.values()) {
                    String restrictions = shelter.getRestrictions();
                    if (restrictions.contains(age)) {
                        shelterNames.add(shelter.getName());
                    }
                }
            }
            if (age.equals("Anyone") && gender.equals("Anyone")) {
                for (Shelter shelter : shelters.values()) {
                    shelterNames.add(shelter.getName());
                }
            }
            if (!age.equals("Anyone") && !gender.equals("Anyone")) {
                for (Shelter shelter : shelters.values()) {
                    String restrictions = shelter.getRestrictions();
                    if (restrictions.contains(age) && restrictions.contains(gender)) {
                        shelterNames.add(shelter.getName());
                    }
                }
            }
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item,
                R.id.listItem, shelterNames);
        shelterList.setAdapter(adapter);
    }


    public void logout(View view) {
        Intent logoutIntent = new Intent(this, MainActivity.class);
        startActivity(logoutIntent);
    }
}
