package com.LegalSuvidha.legalsuvidhaproviders.SignupLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login.LoginActivity;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity  {

    public static FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseUser user;
    private static int SPLASH_SCREEN=3000;

    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo, version;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animatiom);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image=findViewById(R.id.imageViewlogo);
        logo=findViewById(R.id.textViewLogo);
        version=findViewById(R.id.textViewVersion);

//        version.setText("");

        topAnim.setDuration(1500);
        bottomAnim.setDuration(2000);


        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        version.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user = FirebaseAuth.getInstance().getCurrentUser();

                Intent intent;
                if (user!= null) {
                    intent = new Intent(MainActivity.this, HomeScreen.class);
                }else{
                    intent = new Intent(MainActivity.this, LoginActivity.class);

                }
                startActivity(intent);
            }
        },SPLASH_SCREEN);




    }

}