package android.dwabit.LockScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.dwabit.R;

import com.manusunny.pinlock.SetPinActivity;

/**
 * Created by Andy on 3/6/2016.
 */
public class PinSet extends SetPinActivity {
    @Override
    public void onPinSet(String pin) {
        // Set pin as sharedpreference
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.pin), pin);
        editor.apply();
    }
}
