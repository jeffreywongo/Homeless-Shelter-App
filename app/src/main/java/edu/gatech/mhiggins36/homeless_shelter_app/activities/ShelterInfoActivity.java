package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.ShelterManager;
import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.UserManager;
import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.VolleySingleton;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;

public class ShelterInfoActivity extends AppCompatActivity {

    TextView name;
    TextView address;
    TextView phoneNumber;
    TextView restrictions;
    TextView specialNotes;
    TextView capacity;
    TextView latlong;
    private final String TAG = "ShelterInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);

        //find all the textviews by ID
        name = findViewById(R.id.shelterName);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);
        restrictions = findViewById(R.id.restrictions);
        specialNotes = findViewById(R.id.specialNotes);
        capacity = findViewById(R.id.capacity);
        latlong = findViewById(R.id.latlong);

        Shelter shelter = (Shelter) getIntent().getExtras().get("Shelter");

        String addressWithCommas = shelter.getAddress().replace('#',',');
        String notesWithCommas = shelter.getSpecialNotes().replace('#', ',');
        String restrictionsWithCommas = shelter.getRestrictions().replace('#',',');
        String gpsLocation = shelter.getLatitude() + "/" + shelter.getLongitude();

        name.setText(shelter.getName());
        address.setText(addressWithCommas);
        phoneNumber.setText(shelter.getPhoneNumber());
        restrictions.setText(restrictionsWithCommas);
        capacity.setText(shelter.getCapacity());
        specialNotes.setText(notesWithCommas);
        latlong.setText(gpsLocation);
    }

    protected void claim(View view) {
        Shelter shelter = (Shelter) getIntent().getExtras().get("Shelter");
        int id = shelter.getUniqueKey();
        SharedPreferences pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("currentUser", "");
        // this is final bcs it's being accessed from inner class below
        final User currentUser = gson.fromJson(json, User.class);
        int userId = currentUser.getUserId();
        Log.d(TAG, ""+userId);
        Log.d(TAG, currentUser.getJwt());
        String url = "http://shelter.lmc.gatech.edu/user/checkIn/"+'/'+ Integer.toString(id);

        //TODO implement this correctly
        try {
            capacity = findViewById(R.id.capacity);
            //capacity.setText(response);
        } catch (Error e) {

        }

        // Request a string response

    }
}
