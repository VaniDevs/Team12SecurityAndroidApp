package android.dwabit.FirebaseStuff;

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
        DistressSignalModel distressSignalModel = new DistressSignalModel(name, longitude, latitude);
        ref.setValue(distressSignalModel);
    }

    class DistressSignalModel {
        private double longitude, latitude;
        String name;

        public DistressSignalModel(String name, double distress_longitude, double distress_latitude) {
            this.longitude = distress_longitude;
            this.latitude = distress_latitude;
            this.name = name;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public String getName(){
            return name;
        }
    }
}
