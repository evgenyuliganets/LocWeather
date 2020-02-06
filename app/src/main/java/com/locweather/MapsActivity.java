package com.locweather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
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

import pub.devrel.easypermissions.EasyPermissions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng sydney = new LatLng(-8.579892, 116.095239);
    private static final String TAG = "MainActivity";
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private final int REQUEST_COARSE_LOCATION_PERMISSION = 1;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    Button LocButton;
    private FusedLocationProviderClient fusedLocationClient;
    private SupportMapFragment mapFragment;
    LatLng Point ;
    Location Location;
    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        LocButton = findViewById(R.id.button);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setupAutoCompleteFragment();
        requestLocationPermission();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(20 * 1000);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
                            @SuppressLint("MissingPermission")
                            public void onSuccess(final Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    Location = location;
                                    }

                            }
                        });
        locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        Location = location;
                    }
                }
            }

        };
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




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            public void onClick(View v) {
                requestLocationPermission();
                requestCoarseLocationPermission();
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(MapsActivity.this, "Gps not enabled", Toast.LENGTH_SHORT).show();
                    enableLoc();
                }
                else {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
                                @SuppressLint("MissingPermission")
                                public void onSuccess(final Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location == null) {
                                        if (Location != null) {
                                            Point = new LatLng(Location.getLatitude(), Location.getLongitude());
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(Point)
                                                    .title("Ur location")
                                                    .snippet("By GPS")
                                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, 5.5f));
                                            if (fusedLocationClient != null) {
                                                fusedLocationClient.removeLocationUpdates(locationCallback);
                                            }
                                        }
                                        else {
                                            Toast.makeText(MapsActivity.this, "Cant take location right now, please reload App and turn on gps", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else  {

                                        Point = new LatLng(location.getLatitude(), location.getLongitude());
                                        mMap.addMarker(new MarkerOptions()
                                                .position(Point)
                                                .title("Ur location")
                                                .snippet("By GPS")
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, 5.5f));
                                        if (fusedLocationClient != null) {
                                            fusedLocationClient.removeLocationUpdates(locationCallback);
                                        }
                                    }
                       }     });
                }
            }
        });



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

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {

            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }

    }

    public void requestCoarseLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {


            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_COARSE_LOCATION_PERMISSION, perms);
        }

    }private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MapsActivity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                    }
                }
            });
        }

    }

}
