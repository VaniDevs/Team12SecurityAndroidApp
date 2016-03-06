package android.dwabit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Andy on 3/5/2016.
 */
public class LocationLogic {
    Activity activity;
    double longitude = 0, latitude = 0;
    TextView mtv_longitude, mtv_latitude;
    String username;

    public LocationLogic(Activity mactivity) {
        activity = mactivity;
    }

    public void locationGetter(String username, TextView tv_longitude, TextView tv_latitude) {
        this.username = username;
        mtv_longitude = tv_longitude;
        mtv_latitude = tv_latitude;
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If not granted yet
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
            }
            locationUpdater();
        }
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            locationUpdater();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void locationUpdater(){
        Log.e("longitude", String.valueOf(longitude));
        Log.e("latitude", String.valueOf(latitude));
        mtv_longitude.setText(String.valueOf(longitude));
        mtv_latitude.setText(String.valueOf(latitude));
        new SaveFireBase(activity, username, longitude, latitude);
    }
}
