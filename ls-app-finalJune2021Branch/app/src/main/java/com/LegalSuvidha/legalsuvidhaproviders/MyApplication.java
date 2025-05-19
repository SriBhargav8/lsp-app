package com.LegalSuvidha.legalsuvidhaproviders;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyApplication extends Application {

    MyAppsNotificationManager  myAppsNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        myAppsNotificationManager = MyAppsNotificationManager.getInstance(this);
        myAppsNotificationManager.registerNotificationChannelChannel(
                getString(R.string.BLOGS_CHANNEL_ID),
                getString(R.string.CHANNEL_BLOGS),
                getString(R.string.CHANNEL_DESCRIPTION));

        myAppsNotificationManager.registerNotificationChannelChannel(
                getString(R.string.RENT_RECEIPT_CHANNEL_ID),
                getString(R.string.CHANNEL_RENT_RECEIPT),
                getString(R.string.RENT_RECEIPT_CHANNEL_DESCRIPTION));

        myAppsNotificationManager.registerNotificationChannelChannel(
                getString(R.string.SHARE_CERTIFICATE_CHANNEL_ID),
                getString(R.string.CHANNEL_SHARE_CERTIFICATE),
                getString(R.string.SHARE_CERTIFICATE_CHANNEL_DESCRIPTION));

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()){
                    Log.i(getString(R.string.DEBUG_TAG), "Task Failed");
                    return;
                }
                Log.i(getString(R.string.DEBUG_TAG), "The result: "+task.getResult().getToken());
            }
        });
    }

    public void triggerNotification(Uri path, String channelId, String title, String text, String bigText, int priority, boolean autoCancel, int notificationId, int pendingIntentFlag){
        myAppsNotificationManager.triggerNotification(path,channelId,title,text, bigText, priority, autoCancel,notificationId, pendingIntentFlag);
    }

    public void triggerNotification(Uri path, String channelId, String title, String text, String bigText, int priority, boolean autoCancel, int notificationId){
        myAppsNotificationManager.triggerNotification(path,channelId,title,text, bigText, priority, autoCancel,notificationId);
    }

    public void triggerNotificationWithBackStack(Class targetNotificationActivity, String channelId, String title, String text, String bigText,String date,String image_url,String url, int priority, boolean autoCancel, int notificationId, int pendingIntentFlag){
        myAppsNotificationManager.triggerNotificationWithBackStack(targetNotificationActivity,channelId,title,text, bigText,date,image_url,url,priority, autoCancel,notificationId, pendingIntentFlag);
    }

    public void updateNotification(Class targetNotificationActivity,String title,String text, String channelId, int notificationId, String bigpictureString, int pendingIntentflag){
        myAppsNotificationManager.updateWithPicture(targetNotificationActivity, title, text, channelId, notificationId, bigpictureString, pendingIntentflag,R.id.imageView);
    }

    public void cancelNotification(int notificaitonId){
        myAppsNotificationManager.cancelNotification(notificaitonId);
    }

}
