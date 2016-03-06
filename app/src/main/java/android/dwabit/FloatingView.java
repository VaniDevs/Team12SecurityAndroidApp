package android.dwabit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import jp.co.recruit_lifestyle.android.floatingview.FloatingViewListener;

/**
 * Created by Andy on 3/6/2016.
 */
public class FloatingView extends Service implements FloatingViewListener{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onFinishFloatingView() {

    }
}
