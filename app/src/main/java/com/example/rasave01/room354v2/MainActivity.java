package com.example.rasave01.room354v2;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;

public class MainActivity extends AppCompatActivity {

    private final int CODE_PERMISSIONS=1;

    IALocationManager mLocationManager;

    IALocationListener mLocationListener = new IALocationListener() {
        @Override
        public void onLocationChanged(IALocation iaLocation) {
            TextView textLoc = (TextView) findViewById(R.id.textView);
            textLoc.setText(String.valueOf("Lat: "+iaLocation.getLatitude()+", Long: "+iaLocation.getLongitude()));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }
    };

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

        mLocationManager=IALocationManager.create(this);
    }
    protected void onResume(){
        super.onResume();
        mLocationManager.requestLocationUpdates(IALocationRequest.create(),mLocationListener);
    }

    protected void onPause(){
        mLocationManager.removeLocationUpdates(mLocationListener);
        super.onPause();
    }

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
