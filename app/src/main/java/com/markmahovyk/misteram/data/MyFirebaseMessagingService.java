package com.markmahovyk.misteram.data;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.data.newtwork.App;
import com.markmahovyk.misteram.model.ResponseRegister;
import com.markmahovyk.misteram.ui.main.MainActivity;
import com.markmahovyk.misteram.ui.main.OrdersFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (SharePreference.getAppAuthToken(this) != null) {
            OrdersFragment.getInstance().updateData();
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.e("newToken", "onNewToken: "+ token );

        SharePreference.setTokenNotification(this, token);

        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        App.getApi()
                .register(SharePreference.getTokenApp(this), "1.0", "android",
                        deviceId, SharePreference.getTokenNotification(this), "FCM")
                .enqueue(new Callback<ResponseRegister>() {
                    @Override
                    public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                        if (response != null && response.body() != null) {
                            String token = response.body().getToken();
                            SharePreference.setTokenApp(MyFirebaseMessagingService.this, token);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRegister> call, Throwable t) {
                        //TODO
                    }
                });


    }
}
