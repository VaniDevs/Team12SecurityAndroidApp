package android.dwabit;

import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by Andy on 3/5/2016.
 */
public class SaveFireBase {

    public SaveFireBase(Context context, String name, double longitude, double latitude) {
        Firebase.setAndroidContext(context);
        Firebase rootRef = new Firebase("https://dwabit.firebaseio.com/");
        Firebase ref = rootRef.child("DistressSignals").child(name);
        DistressSignalModel distressSignalModel = new DistressSignalModel(longitude, latitude);
        ref.setValue(distressSignalModel);
    }

    class DistressSignalModel {
        private double longitude, latitude;

        public DistressSignalModel(double distress_longitude, double distress_latitude) {
            this.longitude = distress_longitude;
            this.latitude = distress_latitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
