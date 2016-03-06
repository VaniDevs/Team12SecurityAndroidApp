package android.dwabit;

import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by Andy on 3/5/2016.
 */
public class DeleteFireBase {
    public DeleteFireBase(Context context, String name, boolean exit) {
        Firebase.setAndroidContext(context);
        Firebase rootRef = new Firebase("https://dwabit.firebaseio.com/");
        Firebase ref = rootRef.child("DistressSignals").child(name);
        ref.removeValue();
        if (!exit) {
            ref = rootRef.child("UserInfo").child(name);
            ref.removeValue();
            ref = rootRef.child("DistressImages").child(name);
            ref.removeValue();
        }
    }
}
