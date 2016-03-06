package android.dwabit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.dwabit.CameraLogic.CameraTakePhoto;
import android.graphics.SurfaceTexture;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String username = "Daryl";
    int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;
    TextView tv_longitude, tv_latitude;
    EditText name;
    Button create, delete, cancel_request;
    SurfaceView camera_surface_view;
    SurfaceHolder surfaceHolder;
    LinearLayout buttonstack;

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
                cancel_requester();
            }
        });

        //Get a surface
        surfaceHolder = camera_surface_view.getHolder();
        CameraTakePhoto cameraTakePhoto = null;
        try {
            cameraTakePhoto = new CameraTakePhoto(surfaceHolder, username);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add the callback interface methods defined below as the Surface View callbacks
        final CameraTakePhoto finalCameraTakePhoto = cameraTakePhoto;
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                finalCameraTakePhoto.CameraCreated(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                try {
                    new CameraTakePhoto(surfaceHolder, username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                finalCameraTakePhoto.surfaceDestroyed(holder);
            }
        });

        //tells Android that this surface will have its data constantly replaced
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new DeleteFireBase(this, username, true);
    }

    public void cancel_requester() {
        this.finish();
        new DeleteFireBase(this, username, true);
    }
}
