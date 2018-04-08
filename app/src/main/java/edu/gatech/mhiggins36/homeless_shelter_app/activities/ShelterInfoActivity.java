package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.ShelterManager;
import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;

public class ShelterInfoActivity extends AppCompatActivity {

    private TextView name;
    private TextView address;
    private TextView phoneNumber;
    private TextView restrictions;
    private TextView specialNotes;
    private TextView capacity;
    private TextView latlong;
    private TextView claimStatus;
    private EditText numBeds;
    private final String TAG = "ShelterInfo";

    //boolean to tell if the claim server call was successful
    public static boolean claimed = false;
    public static boolean unclaimed = false;

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
        capacity = findViewById(R.id.vacancies);
        latlong = findViewById(R.id.latlong);
        claimStatus = findViewById(R.id.claimStatus);
        numBeds = findViewById(R.id.numBeds);

        Shelter shelter = (Shelter) getIntent().getExtras().get("Shelter");

        String addressWithCommas = shelter.getAddress().replace('#',',');
        String notesWithCommas = shelter.getSpecialNotes().replace('#', ',');
        String restrictionsWithCommas = shelter.getRestrictions().replace('#',',');
        String gpsLocation = shelter.getLatitude() + "/" + shelter.getLongitude();

        name.setText(shelter.getName());
        address.setText(addressWithCommas);
        phoneNumber.setText(shelter.getPhoneNumber());
        restrictions.setText(restrictionsWithCommas);
        capacity.setText("" + shelter.getVacancies());
        specialNotes.setText(notesWithCommas);
        latlong.setText(gpsLocation);
        claimStatus.setVisibility(View.INVISIBLE);
    }

    public void claim(View view) {
        Shelter shelter = (Shelter) getIntent().getExtras().get("Shelter");
        String name = shelter.getName();
        int id = shelter.getUniqueKey();
        SharedPreferences pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("currentUser", "");
        // this is final bcs it's being accessed from inner class below
        final User currentUser = gson.fromJson(json, User.class);
        int userId = currentUser.getUserId();
        Log.d(TAG, ""+userId);
        Log.d(TAG, currentUser.getJwt());
        int bedCount = 1;
        try {
            if (!numBeds.getText().equals("")) {
                bedCount = Integer.parseInt(numBeds.getText().toString());
            }
        } catch (Throwable e) {
            claimStatus.setText("beds input not a number");
            return;
        }

        String url = "http://shelter.lmc.gatech.edu/user/checkIn/"+ id;
        ShelterManager.claim(getApplicationContext(), url, currentUser, bedCount);

        if (claimed) {
            //the call was successful
            //ShelterManager.getShelterInfo(getApplicationContext(), shelter.getUniqueKey());
            shelter = ShelterManager.shelterMap.get(name);
            Log.d("vacancies", "claim: " + shelter.getVacancies());
            capacity.setText("" + shelter.getVacancies());
            claimStatus.setText("claim successful");
            claimStatus.setVisibility(View.VISIBLE);
//            finish();
//            startActivity(getIntent());
        } else {
            //call was unsuccessful
            claimStatus.setText("claim unsuccessful");
            claimStatus.setVisibility(View.VISIBLE);
        }

    }

    public void clearReservation(View view) {
        Shelter shelter = (Shelter) getIntent().getExtras().get("Shelter");
        String name = shelter.getName();
        int id = shelter.getUniqueKey();
        SharedPreferences pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("currentUser", "");
        // this is final bcs it's being accessed from inner class below
        final User currentUser = gson.fromJson(json, User.class);

        ShelterManager.clearBed(getApplicationContext(), currentUser, shelter.getUniqueKey());
        if (unclaimed) {
            //call was successful so update view
            //ShelterManager.getShelterInfo(getApplicationContext(), shelter.getUniqueKey());
            shelter = ShelterManager.shelterMap.get(name);
            Log.d("vacancies", "clearReservation: " + shelter.getVacancies());
            capacity.setText("" + shelter.getVacancies());
            claimStatus.setText("clear successful");
            claimStatus.setVisibility(View.VISIBLE);
//            finish();
//            startActivity(getIntent());
        } else {
            claimStatus.setText("clear unsuccessful");
            claimStatus.setVisibility(View.VISIBLE);
        }
    }
}
