package com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.SignUpp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.LegalSuvidha.legalsuvidhaproviders.BusinessRegistration.TypesOfBusinessRegistration;
import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login.LoginVerifyOTP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (user != null) {
            login();
        }

        if (savedInstanceState == null) {

            Bundle bundle = new Bundle();

            bundle.putString("deepLink", getIntent().getStringExtra("deepLink"));
//            Log.i("getDynamicLink", getIntent().getStringExtra("deepLink"));
            signUpWithUserInfoFragment signUpWithUserInfoFrag = new signUpWithUserInfoFragment();
            signUpWithUserInfoFrag.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, signUpWithUserInfoFrag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }

    }

    public void login() {
        //move to next activity
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

//            if(getSupportFragmentManager().findFragmentById(R.id.fragment_container).getParentFragment()==R.layout.fragment_profile){
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            return;
        }

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirm Exit")
                .setMessage("All Information will be lost! Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SignUp.super.onBackPressed();
//
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}