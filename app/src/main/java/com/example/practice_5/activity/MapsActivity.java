package com.example.practice_5.activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practice_5.R;
import com.example.practice_5.model.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private TextView textViewOption1, textViewOption2, textViewOption3, textViewOption4;
    private Location selectedLocation;

    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        /** Instantiate the UI Elements */
        instantiateUIElements();
        /** Include a listener to the searchView */
    }

     @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.option_clear_map: mMap.clear(); break;
             case R.id.option_random_marker: createRandomMarker(); break;
             case R.id.option_zoom_in: zoomIn(); break;
             case R.id.option_zoom_out: zoomOut(); break;
             default: break;
         }
    }

    public void setLocationCoordinates(LatLng locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

    public LatLng locationCoordinates;

    private void setSelectedLocation() {
        /*Gets the intent passed from MainActivity and the selected patent passed in the extra */
        Intent intent = getIntent();

        // Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            // Check for coordinates first
            if (extras.containsKey("coordinates")) {
                LatLng coords = getIntent().getExtras().getParcelable("coordinates");
                if (coords != null) {
                    locationCoordinates = coords;
                    return;
                }
            }
        }
        selectedLocation = (Location) intent.getSerializableExtra("location");
        if(selectedLocation != null){
            Toast.makeText(MapsActivity.this,"Location received by MapsActivity",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MapsActivity.this,"MapsActivity failed to retrieve location",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        setSelectedLocation();

        // If coordinates were set use them
        if (locationCoordinates != null) {
            setCoordinates(locationCoordinates);
        }
        // Use Address
        else {
            String locationAddress = selectedLocation.getAddress();
            setCoordinatesFromAddress(locationAddress);
        }
    }

    private void addMarker (String location, LatLng coordinates, int zoom){
        /*Add a marker to the map and and move the camera with specified zoom */
        mMap.addMarker(new MarkerOptions().position(coordinates).title(location));
        /** move the camera to a specific location, and set up the zoom */
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom));
    }

    private void zoomIn (){
        mMap.moveCamera(CameraUpdateFactory.zoomIn());
    }

    private void zoomOut (){
        mMap.moveCamera(CameraUpdateFactory.zoomOut());
    }

    private void instantiateUIElements(){
        mFloatingActionButton = findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionsMenu();
            }
        });

        textViewOption1 = findViewById(R.id.option_clear_map);
        textViewOption2 = findViewById(R.id.option_random_marker);
        textViewOption3 = findViewById(R.id.option_zoom_in);
        textViewOption4 = findViewById(R.id.option_zoom_out);

        textViewOption1.setOnClickListener(this);
        textViewOption2.setOnClickListener(this);
        textViewOption3.setOnClickListener(this);
        textViewOption4.setOnClickListener(this);
    }

    private void showOptionsMenu() {
        ConstraintLayout optionsMenu = findViewById(R.id.options_menu);
        if (optionsMenu.getVisibility() == View.VISIBLE) {
            /** Hide the options menu */
            optionsMenu.setVisibility(View.GONE);
        } else {
            /** Show the options menu */
            optionsMenu.setVisibility(View.VISIBLE);
        }
    }

    private void createRandomMarker(){
        /** Add marker to random coordinates */
        double latMin = -90;
        double latMax = 90;
        double randomLat = ThreadLocalRandom.current().nextDouble(latMin, latMax + 1);
        double longitudeMin = -180;
        double longitudeMax = 180;
        double randomLongitude = ThreadLocalRandom.current().nextDouble(longitudeMin, longitudeMax + 1);

        LatLng randomLocation = new LatLng(randomLat, randomLongitude);

        String locationName = randomLocation.toString();
        addMarker(locationName, randomLocation, 3);
    }

    private void setCoordinatesFromAddress(String address){
        /** Add a marker and move camera to provided address */
        Geocoder geocoder = new Geocoder(this);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    List<Address> addressList = geocoder.getFromLocationName(address, 1);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(addressList != null){
                                setLocationCoordinates(new LatLng(addressList.get(0).getLatitude(),addressList.get(0).getLongitude()));
                                mMap.addMarker(new MarkerOptions().position(locationCoordinates).title(selectedLocation.getName()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationCoordinates, 10));
                            }else{
                                Toast.makeText(MapsActivity.this,"Failed to retrieve coordinates at that address",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setCoordinates(LatLng coords){
        /** Add a marker and move camera to provided coordinates */
        Geocoder geocoder = new Geocoder(this);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setLocationCoordinates(coords);
                            mMap.addMarker(new MarkerOptions().position(locationCoordinates).title(locationCoordinates.toString()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationCoordinates, 10));
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}