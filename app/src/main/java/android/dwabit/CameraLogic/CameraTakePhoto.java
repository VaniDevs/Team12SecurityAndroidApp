package android.dwabit.CameraLogic;


import android.dwabit.MainActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Andy on 3/5/2016.
 */
public class CameraTakePhoto {
    public static Camera camera;
    public static CountDownTimer cameraTimer;
    Camera.Parameters parameters;
    public static String name;
    public static Boolean isRunning = false;

    public CameraTakePhoto(SurfaceHolder holder) throws IOException {
        camera = Camera.open();
        parameters = camera.getParameters();
        camera.setParameters(parameters);
        /*SurfaceTexture surfaceTexture = new SurfaceTexture(10);
        camera.setPreviewTexture(surfaceTexture);*/
        camera.setPreviewDisplay(holder);
        camera.setDisplayOrientation(90);
        camera.startPreview();
        final Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                AsyncPictureTake asyncPictureTake = new AsyncPictureTake();
                asyncPictureTake.execute(data);
            }
        };
        try {
            cameraTimer = new CountDownTimer(MainActivity.captureTime, 500) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Log.e("Camera", "Prepping Capture!");
                    try {
                        camera.startPreview();
                        if (!isRunning) camera.takePicture(null, null, null, pictureCallback);
                    } catch (Exception ignored) {
                        Log.e("Camera", "Capture failed");
                    }
                    Log.e("Camera", "Capture!");
                    start();
                }
            }.start();
        } catch (Exception e) {
            Log.e("Camera Error", e.toString());
        }
    }

    public void CameraCreated(SurfaceHolder holder) {
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            camera.release();
            camera = null;
            Log.e("Camera", String.valueOf(e));
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        /*//stop the preview
        camera.stopPreview();
        //release the camera
        camera.release();
        //unbind the camera from this object
        camera = null; */
    }
}
