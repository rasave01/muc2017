package com.example.rasave01.room354v2;

import android.Manifest;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final int CODE_PERMISSIONS=0;

    // set location of entry point to room 354
    private final double SET_LAT = 51.5222567029726;
    private final double SET_LONG = -0.130809992551804;
    private final int SET_FLOOR = 3;

    //set up an IndoorAtlas location manager
    IALocationManager mLocationManager;

    // set up an IndoorAtlas location listener
    IALocationListener mLocationListener = new IALocationListener() {

        // most actions happen when location is changed...
        @Override
        public void onLocationChanged(IALocation iaLocation) {

            // Change the message of the text view to give Lat and Long values, on location changed event
            TextView textLoc = (TextView) findViewById(R.id.textView);
            textLoc.setText("Location changed...");

            // check if close to set location and change the text
            // initialise distace - should find a better option really...
            float[] distance = new float[1];
            Location.distanceBetween(iaLocation.getLatitude(), iaLocation.getLongitude(), SET_LAT, SET_LONG,distance);
            // check distance
            if(distance[0] < 3.0){
                // if distance is accurate check floor
                if(iaLocation.getFloorLevel()==SET_FLOOR){
                    // make toast
                    textLoc.setText("You reached room 345!!! WELCOME!");
                }
            }

            textLoc.setText(String.valueOf(new StringBuilder().append("Lat: ").
                    append(iaLocation.getLatitude()).append(", Long: ").
                    append(iaLocation.getLongitude()).append(", Floor Level: ").
                    append(iaLocation.getFloorLevel()).append(", Accuracy: ").
                    append(iaLocation.getAccuracy()).append(", Distance to room 354: ").
                    append(distance[0]).toString()));


            // write log to the firebase database
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

    };


    // set proper permissions at run-time
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] neededPermissions={
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this,neededPermissions,CODE_PERMISSIONS);
        setContentView(R.layout.activity_main);

        // create a location mamager
        mLocationManager=IALocationManager.create(this);

        // make a long toast to show that location by IndoorAtlas has started
        Toast.makeText(this, "Locating you using IndoorAtlas...", Toast.LENGTH_LONG).show();
    }


    // request location updates on resume of activity
    protected void onResume(){
        super.onResume();
        mLocationManager.requestLocationUpdates(IALocationRequest.create(), mLocationListener);
        Toast.makeText(this, "Location re-started...", Toast.LENGTH_SHORT).show();
        TextView textLoc = (TextView) findViewById(R.id.textView);
        textLoc.setText("Location re-started...");
    }

    //stop receiving location updates when the app runs in the back-ground, to save battery
    protected void onPause(){

        //notify user though long toast
        Toast.makeText(this, "Location paused...", Toast.LENGTH_SHORT).show();
        mLocationManager.removeLocationUpdates(mLocationListener);
        TextView textLoc = (TextView) findViewById(R.id.textView);
        textLoc.setText("Location paused...");
        super.onPause();

    }

    // destroy the location manager when the app is killed
    protected void onDestroy(){
        mLocationManager.destroy();
        super.onDestroy();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Handle if any permissions are denied in grantResults above
    }

}
