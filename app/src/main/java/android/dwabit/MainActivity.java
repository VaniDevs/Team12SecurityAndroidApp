package android.dwabit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.dwabit.CameraLogic.CameraTakePhoto;
import android.dwabit.FirebaseStuff.DeleteFireBase;
import android.dwabit.FirebaseStuff.LocationLogic;
import android.dwabit.FirebaseStuff.PopulateBeforeSave;
import android.dwabit.FloatingIcon.FloatingView;
import android.dwabit.LockScreen.ConfirmPinActivity;
import android.dwabit.PresentationLogic.SpamDistressSignals;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.manusunny.pinlock.PinListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static String username = "Godot Bian";
    int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0, FLOATING_CODE = 4000, CANCEL_CODE = 2000;
    public static int captureTime = 2000;
    TextView tv_longitude, tv_latitude;
    EditText name, et_switchRole;
    Button create, delete, cancel_request, switchRole, btn_adddistress;
    SurfaceView camera_surface_view;
    SurfaceHolder surfaceHolder;
    LinearLayout buttonstack, cheatTopPanel;
    Boolean floating = false, buttonStack_Visibility = false;

    private static boolean activityVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        tv_longitude = (TextView) findViewById(R.id.longitude);
        tv_latitude = (TextView) findViewById(R.id.latitude);
        create = (Button) findViewById(R.id.btn_create);
        delete = (Button) findViewById(R.id.btn_delete);
        cancel_request = (Button) findViewById(R.id.cancel_action);
        name = (EditText) findViewById(R.id.et_victimname);
        buttonstack = (LinearLayout) findViewById(R.id.buttonstack);
        camera_surface_view = (SurfaceView) findViewById(R.id.camera_surface_view);
        cheatTopPanel = (LinearLayout) findViewById(R.id.cheat_button);
        switchRole = (Button) findViewById(R.id.btn_switchname);
        et_switchRole = (EditText) findViewById(R.id.et_switchname);
        btn_adddistress = (Button) findViewById(R.id.btn_adddistress);

        FloatingView.floatingActive = false;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        }
        LocationLogic locationLogic = new LocationLogic(this);
        locationLogic.locationGetter(username, tv_longitude, tv_latitude);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PopulateBeforeSave(v.getContext(), name.getText().toString(), tv_longitude, tv_latitude);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteFireBase(v.getContext(), name.getText().toString(), false);
            }
        });

        cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelRequester();
            }
        });

        cheatTopPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonStack_Visibility) {
                    buttonStack_Visibility = true;
                    buttonstack.setVisibility(View.VISIBLE);
                } else {
                    buttonStack_Visibility = false;
                    buttonstack.setVisibility(View.GONE);
                }
            }
        });

        switchRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_switchRole.getText().toString();
            }
        });

        btn_adddistress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SpamDistressSignals(v.getContext());
            }
        });

        //Get a surface
        surfaceHolder = camera_surface_view.getHolder();
        CameraTakePhoto cameraTakePhoto = null;
        try {
            cameraTakePhoto = new CameraTakePhoto(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final CameraTakePhoto finalCameraTakePhoto = cameraTakePhoto;
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                finalCameraTakePhoto.CameraCreated(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                finalCameraTakePhoto.surfaceDestroyed(holder);
            }
        });
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityVisible = false;
        captureTime = 5000;
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityVisible = true;
        captureTime = 5000;
        // Get extras just in case
        // Start the floating window
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            floating = extras.getBoolean("floating");
            checkDrawOverlayPermission();
            if (!floating && !FloatingView.floatingActive) {
                Intent intent = new Intent(this, FloatingView.class);
                startService(intent);
                floating = false;
                FloatingView.floatingActive = true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                LocationLogic locationLogic = new LocationLogic(this);
                locationLogic.locationGetter(username, tv_longitude, tv_latitude);
            } else {
                // Permission denied
                Toast.makeText(this, "Need to grant location permission!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, FLOATING_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FLOATING_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // continue here - permission was granted
                    if (!FloatingView.floatingActive) {
                        this.startService(new Intent(this, FloatingView.class));
                    }
                }
            }
        } else if (requestCode == CANCEL_CODE) {
            if (resultCode == PinListener.SUCCESS) {
                this.finish();
                new DeleteFireBase(this, username, true);
                CameraTakePhoto.cameraTimer.cancel();
            } else if (resultCode == PinListener.CANCELLED) {
                Log.e("Pin", "FailedCancelled");
            } else {
                Log.e("Pin", "FailedAfter");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new DeleteFireBase(this, username, true);
    }

    public void cancelRequester() {
        Intent intent = new Intent(this, ConfirmPinActivity.class);
        intent.putExtra("username", username);
        startActivityForResult(intent, CANCEL_CODE);
    }

    public static void stopActivityAndExit(Activity activity, String username) {
        activity.finish();
        new DeleteFireBase(activity, username, true);
        CameraTakePhoto.cameraTimer.cancel();
    }
}
