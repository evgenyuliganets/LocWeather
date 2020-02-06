package com.locweather;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;

import java.util.ArrayList;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng sydney = new LatLng(-8.579892, 116.095239);
    private static final String TAG = "MainActivity";
    private final int REQUEST_LOCATION_PERMISSION = 1;
    Button LocButton;
    private FusedLocationProviderClient fusedLocationClient;
    private SupportMapFragment mapFragment;
    Location LocPoint;
    LatLng Point = null;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        LocButton = findViewById(R.id.button);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setupAutoCompleteFragment();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            requestCoarseLocationPermission();
            //commit
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LocPoint = location;
                        }
                    }
                });

    }


    private void setupAutoCompleteFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                sydney = place.getLatLng();
                mapFragment.getMapAsync(MapsActivity.this);
            }


            @Override
            public void onError(Status status) {
                Log.e("Error", status.getStatusMessage());
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                ArrayList<LatLng> allPoints = new ArrayList<>();
                allPoints.add(point);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 5.5f));
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point)
                        .title("Ur location")
                        .snippet("OnClick"));
            }
        });

        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Ur location")
                .snippet("By Search")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        LocButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Point = new LatLng(LocPoint.getLatitude(), LocPoint.getLongitude());
                requestLocationPermission();
                requestCoarseLocationPermission();
                    mMap.addMarker(new MarkerOptions()
                            .position(Point)
                            .title("Ur location")
                            .snippet("By GPS")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                             mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, 5.5f));
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {

            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }

    }
    public void requestCoarseLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {


            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }

    }
    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {

            }
        }
    };

}
