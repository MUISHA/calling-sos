package com.example.callingsos.maps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.callingsos.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Maps extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng latLng;
    private FloatingActionButton fb;
    private EditText _lat,_longi;
    private TextView _getLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        _getLocation = (TextView)findViewById(R.id.ViewLocation);
        _lat = (EditText)findViewById(R.id.text_latitude);
        _longi = (EditText)findViewById(R.id.text_longitude);
        fb = (FloatingActionButton)findViewById(R.id.floating_getlocation);
        fb.setOnClickListener(this);
        latLng = new LatLng(-1.6766, 29.2306);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng mergency = new LatLng(-1.6766, 29.2306);
        mMap.addMarker(new MarkerOptions().position(mergency).title(getString(R.string.emergecy)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mergency));

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.emergecy)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    String PhoneNumber = "0970117312";
                    String myLatitude = String.valueOf(location.getLatitude());
                    String myLongitude = String.valueOf(location.getLongitude());

                    String message = "Latitude" + myLatitude + "Longitude";
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PhoneNumber, null,message, null, null);


                }catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME , MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME , MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, MIN_TIME , MIN_DIST, locationListener);
        }catch (Exception exp){
            exp.printStackTrace();
        }

    }
    @SuppressLint("SetTextI18n")
    public void getLocationDetail(View view)
    {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        if (!(_lat.getText().toString().isEmpty() || _longi.getText().toString().isEmpty()))
        {
            latitude = Double.parseDouble(_lat.getText().toString());
            longitude = Double.parseDouble(_longi.getText().toString());
            latLng = new LatLng(latitude, longitude);
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList = null;
        String addresse = null;
        String city = null;
        String state = null;
        String contry = null;
        String postalcode = null;
        String knkName = null;
        String sub = null;
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
            addresse = addressList.get(0).getAddressLine(0);
            city = addressList.get(0).getLocality();
            state = addressList.get(0).getAdminArea();
            contry = addressList.get(0).getCountryName();
            postalcode = addressList.get(0).getPostalCode();
            knkName = addressList.get(0).getFeatureName();
            sub = addressList.get(0).getSubLocality();

        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.emergecy) + addresse + city +state
                + contry + postalcode + knkName + sub));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setMaxZoomPreference(11);
        _getLocation.setText(addresse + city +state + contry + postalcode + knkName + sub);
    }

    @Override
    public void onClick(View view) {
        int index = view.getId();
        switch (index)
        {
            case R.id.floating_getlocation : getLocationDetail(view);
                break;
        }
    }
}