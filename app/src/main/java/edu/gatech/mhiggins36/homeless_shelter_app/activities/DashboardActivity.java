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

import java.util.ArrayList;
import java.util.HashMap;

import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.ShelterManager;
import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;

/**
 * App home page
 */

public class DashboardActivity extends AppCompatActivity {

    Button logoutButton;
    TextView userTypeMessage;
    ListView shelterList;
    ArrayList<String> shelterNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent shelterInfoIntent = new Intent(getBaseContext(), ShelterInfoActivity.class);
                // query shelters hashmap and put the shelter in the intent
                shelterInfoIntent.putExtra("Shelter", ShelterManager.shelterMap.get(shelterName));
                startActivity(shelterInfoIntent);
            }
        });
        // list all shelters initially
        shelterNameList = listShelters(null, "anyone", "anyone");

        Intent intent = getIntent();
        // check which intent is being handled with this
        String sender = intent.getStringExtra("Sender");
        if (sender.equals("SearchActivity")) {
            String name = intent.getStringExtra("name").toLowerCase();
            String age = intent.getStringExtra("age").toLowerCase();
            String gender = intent.getStringExtra("gender").toLowerCase();
            // if search bar used then disregard spinner selections
            if (name != null && name.trim().length() != 0) {
                Log.d("Dash", "called with " + name);
                shelterNameList = listShelters(name, null, null);
            } else {
                Log.d("Dash", "Filtering logic used");
                shelterNameList = listShelters(null, age, gender);
            }
        }
        //TODO make this server compatible
//        String user = intent.getStringExtra("userType");
//        String userType = Controller.userMap.get(user).getUserType();
//        userTypeMessage.setText("Logged in as a(n) " + userType);
    }

    /*
    called on create of the dashboard
    displays all the shelters on the dashboard
     */
    private ArrayList<String> listShelters(String name, String age, String gender) {
        HashMap<String, Shelter> shelters = ShelterManager.shelterMap;
        ArrayList<String> shelterNames = new ArrayList<>();
        String anyone = "anyone";
        // if searched by name
        if (name != null) {
            for (String key : shelters.keySet()) {
                if ((key.toLowerCase()).contains(name)) {
                    shelterNames.add(key);
                }
            }
        } else {
            if (age.equals(anyone) && !gender.equals(anyone)) {
                for (Shelter shelter : shelters.values()) {
                    String restrictions = shelter.getRestrictions().toLowerCase();
                    Log.d("Dash", shelter.getName() + ": " + restrictions);
                    if (restrictions.contains(gender)) {
                        shelterNames.add(shelter.getName());
                    }
                }
            }
            if (!age.equals(anyone) && gender.equals(anyone)) {
                for (Shelter shelter : shelters.values()) {
                    String restrictions = shelter.getRestrictions().toLowerCase();
                    if (restrictions.contains(age)) {
                        shelterNames.add(shelter.getName());
                    }
                }
            }
            if (age.equals(anyone) && gender.equals(anyone)) {
                for (Shelter shelter : shelters.values()) {
                    shelterNames.add(shelter.getName());
                }
            }
            if (!age.equals(anyone) && !gender.equals(anyone)) {
                for (Shelter shelter : shelters.values()) {
                    String restrictions = shelter.getRestrictions().toLowerCase();
                    if (restrictions.contains(age) && restrictions.contains(gender)) {
                        shelterNames.add(shelter.getName());
                    }
                }
            }
        }
        Log.d("Dash", shelterNames.toString());
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item,
                R.id.listItem, shelterNames);
        shelterList.setAdapter(adapter);
        return shelterNames;
    }


    public void logout(View view) {
        Intent logoutIntent = new Intent(this, MainActivity.class);
        startActivity(logoutIntent);
    }


    public void mapper(View view) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        for (String s : shelterNameList) {
            Log.d("shelterNames", s);
        }
        mapIntent.putStringArrayListExtra("shelterNameList", shelterNameList);
        startActivity(mapIntent);
    }

}
