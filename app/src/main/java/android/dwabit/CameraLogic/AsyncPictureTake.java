package android.dwabit.CameraLogic;

import android.dwabit.MainActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy on 3/6/2016.
 */
public class AsyncPictureTake extends AsyncTask<byte[], Void, Void> {

    @Override
    protected Void doInBackground(byte[]... params) {
        CameraTakePhoto.isRunning = true;
        byte[] data = params[0];
        // anti-clockwise by 90 degrees
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap scaledBmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 8, bmp.getHeight() / 8, true);
        Bitmap rotatedBmp = Bitmap.createBitmap(scaledBmp, 0, 0, scaledBmp.getWidth(), scaledBmp.getHeight(), matrix, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        rotatedBmp.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] resizedData = byteArrayOutputStream.toByteArray();

        // Convert bytedata to String
        String base64data = Base64.encodeToString(resizedData, Base64.DEFAULT);
        Firebase rootRef = new Firebase("https://dwabit.firebaseio.com/");
        Firebase pushRef = rootRef.child("DistressImages").child(MainActivity.username);

        Map<String, String> map = new HashMap<>();
        map.put("image", base64data);
        pushRef.push().setValue(map);
        Log.e("AsyncUpload", "Success");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            CameraTakePhoto.camera.startPreview();
        } catch (Exception ignored) {
        }
        CameraTakePhoto.isRunning = false;
    }
}
