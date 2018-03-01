package edu.gatech.mhiggins36.homeless_shelter_app;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ShelterInfoActivity extends AppCompatActivity {

    TextView name;
    TextView address;
    TextView phoneNumber;
    TextView restrictions;
    TextView specialNotes;
    TextView capacity;

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

        Shelter shelter = (Shelter) getIntent().getExtras().get("Shelter");

        name.setText(shelter.getName());
        address.setText(shelter.getAddress());
        phoneNumber.setText(shelter.getPhoneNumber());
        restrictions.setText(shelter.getRestrictions());
        capacity.setText(shelter.getCapacity());
        specialNotes.setText(shelter.getSpecialNotes());
    }
}
