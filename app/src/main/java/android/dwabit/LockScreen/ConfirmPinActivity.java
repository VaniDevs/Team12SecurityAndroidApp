package android.dwabit.LockScreen;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.dwabit.MainActivity;
import android.dwabit.R;
import android.util.Log;
import android.widget.Toast;

import com.manusunny.pinlock.PinListener;

/**
 * Created by Andy on 3/6/2016.
 */
public class ConfirmPinActivity extends com.manusunny.pinlock.ConfirmPinActivity {
    int forgotPinRequest = 1000;
    Activity activity;
    String username;

    @Override
    public boolean isPinCorrect(String pin) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String currentPin = sharedPreferences.getString(getResources().getString(R.string.pin), "0000");
        Log.e("CurrentPin", currentPin);
        return pin.equals(currentPin);
    }

    @Override
    public void onForgotPin() {
        // Yeah good luck resetting that
        // We'll just reset it LOL
        Intent intent = new Intent(this, PinSet.class);
        startActivityForResult(intent, forgotPinRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.forgotPinRequest == requestCode) {
            if (resultCode == PinListener.SUCCESS) {
                username = getIntent().getStringExtra("username");
                activity = getParent();
                // Success
                Log.e("Pin", "Success");
                finish();
                MainActivity.stopActivityAndExit(activity, username);
            } else {
                Log.e("Pin", "Failure");
                finish();
            }
        }
    }
}
