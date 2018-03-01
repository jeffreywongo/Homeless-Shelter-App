package edu.gatech.mhiggins36.homeless_shelter_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        //setup textviews with appropriate Info from the selected shelter
        name.setText(Controller.selectedShelter.getName());
        address.setText(Controller.selectedShelter.getAddress());
        phoneNumber.setText(Controller.selectedShelter.getPhoneNumber());

//        String restrictionstring = "";
//        for (String s : Controller.selectedShelter.getRestrictions()) {
//            restrictionstring += String.format("%s\n", s);
//        }
//        restrictions.setText(restrictionstring);

//        String notes = "";
//        for (String s : Controller.selectedShelter.getSpecialNotes()) {
//            notes += String.format("%s\n", s);
//        }
//        specialNotes.setText(notes);

        capacity.setText(Controller.selectedShelter.getCapacity());
    }
}
