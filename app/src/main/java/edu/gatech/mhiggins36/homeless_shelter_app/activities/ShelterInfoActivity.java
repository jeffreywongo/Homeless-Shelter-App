package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.VolleySingleton;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;

public class ShelterInfoActivity extends AppCompatActivity {

    TextView name;
    TextView address;
    TextView phoneNumber;
    TextView restrictions;
    TextView specialNotes;
    TextView capacity;
    TextView latlong;

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
        int userid = 1;
        String url = "http://shelter.lmc.gatech.edu/user/checkIn/" + Integer.toString(userid) +'/'+ Integer.toString(id);

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        capacity = findViewById(R.id.capacity);
                        capacity.setText(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        // Add the request to the queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
