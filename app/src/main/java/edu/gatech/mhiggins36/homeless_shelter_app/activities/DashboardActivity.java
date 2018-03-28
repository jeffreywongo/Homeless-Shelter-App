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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gatech.mhiggins36.homeless_shelter_app.CSVFile;
import edu.gatech.mhiggins36.homeless_shelter_app.Controller;
import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.VolleySingleton;
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
//        InputStream inputStream = getResources().openRawResource(R.raw.homeless_shelter_database);
//        CSVFile csvFile = new CSVFile(inputStream);
//        List<String[]> list = csvFile.read();
//
//        Controller.createMapFromcsv(list);
        createShelterMap();

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
            String name = intent.getStringExtra("name").toLowerCase();
            String age = intent.getStringExtra("age").toLowerCase();
            String gender = intent.getStringExtra("gender").toLowerCase();
            // if search bar used then disregard spinner selections
            if (name != null && name.trim().length() != 0) {
                Log.d("Dash", "called with " + name);
                listShelters(name, null, null);
            } else {
                Log.d("Dash", "Filtering logic used");
                listShelters(null, age, gender);
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
    private void listShelters(String name, String age, String gender) {
//        String[] names = getResources().getStringArray(R.array.shelters);
        HashMap<String, Shelter> shelters = Controller.shelterMap;
        Log.d("Dash", shelters.values().toString());
        List<String> shelterNames = new ArrayList<>();
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
    }


    public void logout(View view) {
        Intent logoutIntent = new Intent(this, MainActivity.class);
        startActivity(logoutIntent);
    }

    public void createShelterMap() {
        String url = "http://shelter.lmc.gatech.edu/shelters";
        Log.d("devin", "onResponse: start");
        // Request a string response
        StringRequest arrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("devin", "in on response");
                        try{
                            JSONObject shelters = new JSONObject(response);
                            JSONArray array = shelters.getJSONArray("shelters");
                            // Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                // Get current json object


                                JSONObject shelter = array.getJSONObject(i);

                                // Get the current student (json object) data
                                String name = shelter.getString("name");
                                int id = shelter.getInt("id");
                                String capacity = shelter.getString("capacity");
                                int vacancies = shelter.getInt("vacancies");
                                String restrictions = shelter.getString("restrictions");
                                float lattitude = (float)shelter.getDouble("latitude");
                                float longitude = (float)shelter.getDouble("longitude");
                                String address = shelter.getString("address");
                                String phone = shelter.getString("phone");
                                String description = shelter.getString("description");

                                Log.d("devinh", name);
                                Controller.shelterMap.put(name, new Shelter(id, name, capacity,
                                        restrictions, longitude, lattitude, address, description, phone));

                            }
                        }catch (JSONException e){
                            Log.d("devin", "onResponse: there " + e.getMessage());
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("devin", "onResponse: errrooorrrr" + error.getMessage());
                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        });
        // Add the request to the queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(arrayRequest);
        Log.d("devin", "onResponse: end");
    }
}
