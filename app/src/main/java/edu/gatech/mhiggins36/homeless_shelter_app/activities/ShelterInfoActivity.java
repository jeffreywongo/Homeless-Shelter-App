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

/**
 * Activity for displaying a selected shelter's information
 */
public class ShelterInfoActivity extends AppCompatActivity {

    private TextView capacity;
    private TextView claimStatus;
    private EditText numBeds;
    private Shelter shelter;

    //boolean to tell if the claim server call was successful
    private static boolean claimed;
    private static boolean unclaimed;

    //made it an instance variable so that I can make my test -devin
    TextView shelterName;

    /**
     * initializes instance fields
     * @param savedInstanceState generic state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);

        //find all of the text views by ID
        shelterName = findViewById(R.id.shelterName);
        TextView address = findViewById(R.id.address);
        TextView phoneNumber = findViewById(R.id.phoneNumber);
        TextView restrictions = findViewById(R.id.restrictions);
        TextView specialNotes = findViewById(R.id.specialNotes);
        capacity = findViewById(R.id.vacancies);
        TextView latlong = findViewById(R.id.latlong);
        claimStatus = findViewById(R.id.claimStatus);
        numBeds = findViewById(R.id.numBeds);
        //noinspection ConstantConditions

        if (getIntent().getExtras().containsKey("Shelter")) {
            shelter = (Shelter) getIntent().getExtras().get("Shelter");
        }

        assert shelter != null;
        String addressWithCommas = shelter.getAddress().replace('#',',');
        String notesWithCommas = shelter.getSpecialNotes().replace('#', ',');
        String restrictionsWithCommas = shelter.getRestrictions().replace('#',',');
        String gpsLocation = shelter.getLatitude() + "/" + shelter.getLongitude();

        shelterName.setText(shelter.getName());
        address.setText(addressWithCommas);
        phoneNumber.setText(shelter.getPhoneNumber());
        restrictions.setText(restrictionsWithCommas);
        capacity.setText("" + shelter.getVacancies());
        specialNotes.setText(notesWithCommas);
        latlong.setText(gpsLocation);
        claimStatus.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets whether the shelter is claimed or not
     * @param condition whether the shelter is claimed now or not
     */
    public static void setClaimed(boolean condition) {
        claimed = condition;
    }

    /**
     * Sets whether the shelter is unclaimed or not
     * @param condition whether the shelter is unclaimed now or not
     */
    public static void setUnclaimed(boolean condition) {
        unclaimed = condition;
    }

    /**
     * onClick method that gets the current shelter, and current user and makes a call to
     * claim a certain amount of beds . If that call was successful then it will update the
     * view with the correct info. If it was not successful it will tell the user
     * @param view generic view
     */
    public void claim(View view) {
        //changed to make my test -devin
        String name = (String) shelterName.getText();

        int id = shelter.getUniqueKey();
        SharedPreferences pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("currentUser", "");
        // this is final bcs it's being accessed from inner class below
        final User currentUser = gson.fromJson(json, User.class);
        int bedCount = 1;
        try {
            if (!"".equals(numBeds.getText().toString())) {
                bedCount = Integer.parseInt(numBeds.getText().toString());
            }
        } catch (Throwable e) {
            claimStatus.setText("beds input not a number");
            return;
        }

        String url = "http://shelter.lmc.gatech.edu/user/checkIn/"+ id;
        ShelterManager.getInstance(getApplicationContext()).claim(url, currentUser, bedCount);

        if (claimed) {
            //the call was successful
            //ShelterManager.getShelterInfo(getApplicationContext(), shelter.getUniqueKey());
            shelter = ShelterManager.shelterMap.get(name);
            Log.d("vacancies", "claim: " + shelter.getVacancies());
            capacity.setText("" + shelter.getVacancies());
            claimStatus.setText("claim successful");
            claimStatus.setVisibility(View.VISIBLE);
        } else {
            //call was unsuccessful
            claimStatus.setText("claim unsuccessful");
            claimStatus.setVisibility(View.VISIBLE);
        }

    }

    /**
     * onClick method that gets the current user and shelter, and makes a call to clear the user's
     * reservation for that shelter. If successful, this method will update the view accordingly; if
     * not, it will give the user an error message.
     * @param view generic view
     */
    public void clearReservation(View view) {
        String name = shelter.getName();
        SharedPreferences pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("currentUser", "");
        // this is final bcs it's being accessed from inner class below
        final User currentUser = gson.fromJson(json, User.class);

        ShelterManager.getInstance(getApplicationContext()).clearBed(currentUser, shelter.getUniqueKey());
        if (unclaimed) {
            //call was successful so update view
            //ShelterManager.getShelterInfo(getApplicationContext(), shelter.getUniqueKey());
            shelter = ShelterManager.shelterMap.get(name);
            Log.d("vacancies", "clearReservation: " + shelter.getVacancies());
            capacity.setText("" + shelter.getVacancies());
            claimStatus.setText("clear successful");
            claimStatus.setVisibility(View.VISIBLE);
        } else {
            claimStatus.setText("clear unsuccessful");
            claimStatus.setVisibility(View.VISIBLE);
        }
    }

}
