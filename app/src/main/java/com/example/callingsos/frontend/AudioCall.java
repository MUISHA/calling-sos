package com.example.callingsos.frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.example.callingsos.R;
import com.example.callingsos.typesos.ShoisTypeSosFragment;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class AudioCall extends AppCompatActivity implements ShoisTypeSosFragment.SingleAppelsListSOS {
    private static final int REQUEST_PERMISSION = 1;
    TextView ur_sos1, ur_sos2;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_call);
        DialogFragment sigleChoiceDialog = new ShoisTypeSosFragment();
        sigleChoiceDialog.setCancelable(false);
        sigleChoiceDialog.show(getSupportFragmentManager(), "TYPE D'APPEL SOS");
        builder = new AlertDialog.Builder(this);
        ur_sos1 = (TextView) findViewById(R.id.ur_sos1_text);
        ur_sos2 = (TextView) findViewById(R.id.ur_sos1_location);

        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AudioCall.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, REQUEST_PERMISSION);
        } else {
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(30000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(AudioCall.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(AudioCall.this)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int lastLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(lastLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(lastLocationIndex).getLongitude();
                            ur_sos2.setText(String.format("Latitude : %s \nLongitude ", latitude, longitude));
                        }
                    }
                }, Looper.getMainLooper());
    }
    @Override
    public void onPositionButtonCliked(String[] list, int position) {
        ur_sos1.setText("" + list [position]);
    }
    @Override
    public void onPositionNegatifClicked() {
        ur_sos1.setText("ANNULER L'OPERATION");
    }
}