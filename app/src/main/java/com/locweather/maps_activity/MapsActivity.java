package com.locweather.maps_activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
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
import com.locweather.R;
import com.locweather.adapter.NoticeAdapter;
import com.locweather.model.Notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, MainContract.MainView {
    private ProgressBar progressBar;
    private MainContract.presenter presenter;
    private GoogleMap mMap;
    private LatLng beginPoint=new LatLng(48.61667, 22.3);
    Button locButton;
    public static LatLng currentLocation = new LatLng(48.61667, 22.3);
    private FusedLocationProviderClient fusedLocationClient;
    private SupportMapFragment mapFragment;
    LatLng Point;
    private static Location Location;
    Geocoder geo;
    List<Address> addresses;
    public static String address;
    private static String addressMap;
    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;
    private RecyclerView recyclerView;

    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locButton = findViewById(R.id.button);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        geo = new Geocoder(this, Locale.ENGLISH);
        setupAutoCompleteFragment();
        requestLocationPermission();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest locationRequest = LocationRequest.create();
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
    }

    private void setupAutoCompleteFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                beginPoint = place.getLatLng();
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
        initializeToolbarAndRecyclerView();
        initProgressBar();
        presenter = new MainPresenterImpl(this, new GetNoticeIntractorImpl());
        setLoc(currentLocation);
        presenter.requestDataFromServer();
        locButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestLocationPermission();
                requestCoarseLocationPermission();
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(MapsActivity.this, "Gps not enabled", Toast.LENGTH_SHORT).show();
                    enableLoc();
                } else {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
                                @TargetApi(Build.VERSION_CODES.N)
                                @SuppressLint("MissingPermission")
                                public void onSuccess(final Location location) {
                                    if (location == null) {
                                        if (Location != null) {
                                            Point = new LatLng(Location.getLatitude(), Location.getLongitude());
                                            setLoc(currentLocation = Point);
                                            presenter.requestDataFromServer();
                                            try {
                                                addresses = geo.getFromLocation(Location.getLatitude(), Location.getLongitude(), 1);
                                                address = addresses.get(0).getAddressLine(0);
                                            } catch (IOException e) {
                                                if (address == null) {
                                                    address = "UnknownLocation";
                                                    Toast.makeText(MapsActivity.this, "Cant take address, please turn on network", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            mMap.clear();
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(Point)
                                                    .title(address)
                                                    .snippet("By GPS")
                                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, 5.5f));
                                        } else {
                                            Toast.makeText(MapsActivity.this, "Cant take location right now, please reload App and turn on gps", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Point = new LatLng(location.getLatitude(), location.getLongitude());
                                        setLoc(currentLocation = Point);
                                        presenter.requestDataFromServer();
                                        try {
                                            addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            address = addresses.get(0).getAddressLine(0);
                                        } catch (IOException e) {
                                            if (address == null) {
                                                address = "UnknownLocation";
                                                Toast.makeText(MapsActivity.this, "Cant take address,please turn on network", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        mMap.clear();
                                        mMap.addMarker(new MarkerOptions()
                                                .position(Point)
                                                .title(address)
                                                .snippet("By GPS")
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, 5.5f));
                                    }
                                }
                            });
                }
            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                /*If need multiply markers
                 ArrayList<LatLng> allPoints = new ArrayList<>();
                 allPoints.add(point);*/
                setLoc(point);
                presenter.requestDataFromServer();
                try {
                    showProgress();
                    addresses = geo.getFromLocation(point.latitude, point.longitude, 1);
                    addressMap = addresses.get(0).getAddressLine(0);
                } catch (IOException e) {
                    if (addressMap == null) {
                        addressMap = "UnknownLocation";
                        Toast.makeText(MapsActivity.this, "Cant take address,please turn on network", Toast.LENGTH_SHORT).show();
                    }
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 5.5f));
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point)
                        .title(addressMap)
                        .snippet("OnClick"));
            }});
        if (beginPoint != null) {
            try {
                addresses = geo.getFromLocation(beginPoint.latitude, beginPoint.longitude, 1);
                addressMap = addresses.get(0).getAddressLine(0);
            } catch (IOException e) {
                if (addressMap == null) {
                    addressMap = "UnknownLocation";
                    Toast.makeText(MapsActivity.this, "Cant take address,please turn on network", Toast.LENGTH_SHORT).show();
                }
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(beginPoint, 5.5f));
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .position(beginPoint)
                    .title(addressMap)
                    .snippet("By Search")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {

            int REQUEST_LOCATION_PERMISSION = 1;
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }

    }

    public void requestCoarseLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {


            int REQUEST_COARSE_LOCATION_PERMISSION = 1;
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_COARSE_LOCATION_PERMISSION, perms);
        }

    }

    private void enableLoc() {

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
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
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
                            status.startResolutionForResult(MapsActivity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                    }
                }
            });
        }

    }

    private void initializeToolbarAndRecyclerView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_employee_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MapsActivity.this);
        recyclerView.setLayoutManager(layoutManager);


    }


    /**
     * Initializing progressbar programmatically
     * */
    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
    }


    /**
     * RecyclerItem click event listener
     * */
    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Notice notice) {

            Toast.makeText(MapsActivity.this,
                    "List title:  " + notice.getWeather(),
                    Toast.LENGTH_LONG).show();

        }
    };


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void setDataToRecyclerView(ArrayList<Notice> noticeArrayList) {

        NoticeAdapter adapter = new NoticeAdapter(noticeArrayList , recyclerItemClickListener);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(MapsActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            presenter.onRefreshButtonClick();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setLoc(LatLng loc) {
        currentLocation = loc;
    }



}
