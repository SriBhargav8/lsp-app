package com.LegalSuvidha.legalsuvidhaproviders;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.LegalSuvidha.legalsuvidhaproviders.Blogs.CurrentBlogActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFireBaseMessagingService.class.getSimpleName();
    public FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i(getString(R.string.DEBUG_TAG), "New Token: "+s);

//        DocumentReference user_profileReference = db.collection("Token").document();
//
//        Map<String,Object> profile = new HashMap<>();
//        profile.put("token",s);



//        user_profileReference.set(profile)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });



    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("notification","Message received");
//        Log.i("notification",remoteMessage.toString());
//        Log.i("notification",remoteMessage.getData().toString());
//        Log.i("notification",remoteMessage.getNotification().toString());
//        Log.i("notification",remoteMessage.getData().get("url"));


        ((MyApplication)getApplication()).triggerNotificationWithBackStack(CurrentBlogActivity.class,
                getString(R.string.BLOGS_CHANNEL_ID),
                "Important Update",
//                remoteMessage.getNotification().getBody(),
                remoteMessage.getData().get("not_blog_title"),
                remoteMessage.getData().get("not_blog_title"),
                remoteMessage.getData().get("date"),
                remoteMessage.getData().get("image_url"),
                remoteMessage.getData().get("url"),
                NotificationCompat.PRIORITY_HIGH,
                true,
                (int) System.currentTimeMillis(),
                PendingIntent.FLAG_UPDATE_CURRENT);
//                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
