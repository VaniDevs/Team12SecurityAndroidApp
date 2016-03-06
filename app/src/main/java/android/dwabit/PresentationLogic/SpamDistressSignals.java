package android.dwabit.PresentationLogic;

import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;

import java.util.Random;

/**
 * Created by Andy on 3/6/2016.
 * This function spams 5 different distress signals
 */
public class SpamDistressSignals {
    public SpamDistressSignals(Context context) {
        // Getting randums
        Random random = new Random();
        int randomInt10 = random.nextInt(10);
        double randomDouble = randomInt10 / 9990;
        String[] dwabitEmployees = {"Kevin Chua", "Godot Bian", "Kelvin Lau", "Andy Wong", "Sally Pang"};
        String currentEmployee = dwabitEmployees[random.nextInt(6)];
        Log.e("CurrentEmployee", currentEmployee);
        // Firebase stufff
        Firebase.setAndroidContext(context);
        Firebase rootRef = new Firebase("https://dwabit.firebaseio.com/");
        Firebase ref = rootRef.child("DistressSignals").child(currentEmployee);
        DistressSignalModel distressSignalModel = new DistressSignalModel(49 + randomDouble, -123 + randomDouble, currentEmployee);
        ref.setValue(distressSignalModel);
    }

    class DistressSignalModel {
        String name;
        double latitude, longitude;

        public DistressSignalModel(double latitude, double longitude, String name) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.name = name;
        }

        double getLatitude() {
            return latitude;
        }

        double getLongitude() {
            return longitude;
        }

        String getName() {
            return name;
        }
    }
}
