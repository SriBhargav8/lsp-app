package com.LegalSuvidha.legalsuvidhaproviders.SignupLogin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login.LoginActivity;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.SignUpp.SignUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        if (user!= null) {
            login();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user!= null) {
            login();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void goToLoginScreen(View v){
        Intent intent= new Intent(getApplicationContext(), LoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0]=new Pair<View,String>(findViewById(R.id.loginButton),"loginTransition");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(StartActivity.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void  callSignUpScreen(View view){
        Intent intent= new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public void login() {
        //move to next activity
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }
}