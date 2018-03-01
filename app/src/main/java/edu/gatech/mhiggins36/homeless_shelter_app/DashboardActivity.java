package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

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
        setContentView(R.layout.activity_dashboard);
        userTypeMessage = findViewById(R.id.userType);
        logoutButton = findViewById(R.id.logoutButton);
        shelterList = findViewById(R.id.shelterList);
        listShelters();
        // set listener for if an item is clicked
        shelterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView dataHolder = view.findViewById(R.id.listItem);
                String shelterName = (String) dataHolder.getText();
//                System.out.println(dataHolder.getText().toString());
                Intent shelterInfoIntent = new Intent(getBaseContext(), ShelterInfoActivity.class);
                // query shelters hashmap and put the shelter in the intent
                Log.d("devin", shelterName);
                Log.d("devin", Controller.shelterMap.get(shelterName).getName());
                shelterInfoIntent.putExtra("Shelter", Controller.shelterMap.get(shelterName));
                startActivity(shelterInfoIntent);
            }
        });

        Intent intent = getIntent();
        String user = intent.getStringExtra("userType");
        String userType = Controller.userMap.get(user).getUserType();
        userTypeMessage.setText("Logged in as a(n) " + userType);
    }

    /*
    called on create of the dashboard
    displays all the shelters on the dashboard
     */
    private void listShelters() {
        String[] names = getResources().getStringArray(R.array.shelters);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item,
                R.id.listItem, names);
        shelterList.setAdapter(adapter);
    }


    public void logout(View view) {
        Intent logoutIntent = new Intent(this, MainActivity.class);
        startActivity(logoutIntent);
    }
}
