package android.dwabit.CameraLogic;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy on 3/5/2016.
 */
public class CameraTakePhoto {
    Camera camera;
    Camera.Parameters parameters;

    public CameraTakePhoto(SurfaceHolder holder, final String name) throws IOException {
        camera = Camera.open();
        parameters = camera.getParameters();
        camera.setParameters(parameters);
        /*SurfaceTexture surfaceTexture = new SurfaceTexture(10);
        camera.setPreviewTexture(surfaceTexture);*/
        camera.setPreviewDisplay(holder);
        camera.startPreview();
        camera.setDisplayOrientation(90);
        Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap scaledBmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 8, bmp.getHeight() / 8, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                scaledBmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] resizedData = byteArrayOutputStream.toByteArray();

                // Convert bytedata to String
                String base64data = Base64.encodeToString(resizedData, Base64.DEFAULT);
                Firebase rootRef = new Firebase("https://dwabit.firebaseio.com/");
                Firebase pushRef = rootRef.child("DistressImages").child(name);

                Map<String, String> map = new HashMap<>();
                map.put("image", base64data);
                pushRef.push().setValue(map);

                Log.e("Camera", "Photo taking successful");
                camera.startPreview();
                camera.setDisplayOrientation(90);
            }
        };
        try {
            camera.takePicture(null, null, null, pictureCallback);
        } catch (Exception e) {
            Log.e("Camera Error", e.toString());
        }
    }

    public void CameraCreated(SurfaceHolder holder) {
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(holder);
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
