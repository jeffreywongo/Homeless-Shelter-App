package edu.gatech.mhiggins36.homeless_shelter_app.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.ShelterManager;
import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;

/**
 * activity that displays the map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /**
     * sets up map
     * @param savedInstanceState generic state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     * @param mMap the google map
     */
    @Override
    public void onMapReady(GoogleMap mMap) {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        List<String> shelterNames = getIntent().getStringArrayListExtra("shelterNameList");
        for (String name : shelterNames) {
            Shelter s = ShelterManager.shelterMap.get(name);
            LatLng loc = new LatLng(s.getLatitude(), s.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title(s.getName())
                    .snippet(s.getPhoneNumber()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }
}
