package com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.SignUpp.SignUp;

import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hbb20.CountryCodePicker;

import java.time.temporal.Temporal;

public class LoginActivity extends AppCompatActivity {

    ImageView backButton;
    Button createUser,getOtpButton;
    TextInputLayout phoneNumber;
    CheckBox checkBox;
    CountryCodePicker countryCodePicker;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
//    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    FirebaseUser user;
    Uri deepLink = null;
//    Boolean validPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//        user = firebaseAuth.getCurrentUser();
//
//        if (user!= null) {
//            login();
//        }

        createUser=findViewById(R.id.loginCreateUser);
        getOtpButton=findViewById(R.id.loginGetOtpButton);
        phoneNumber=findViewById(R.id.loginPhoneNumberTextInputLayout);
        countryCodePicker=findViewById(R.id.loginCcp);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)

                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
//                            Log.i("getDynamicLink", pendingDynamicLinkData.toString());
//                            Log.i("getDynamicLink", pendingDynamicLinkData.getExtensions().toString());
//                            Log.i("getDynamicLink", deepLink.toString());
                        }
                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Log.w("getDynamicLink", "getDynamicLink:onFailure", e);
                    }
                });

        phoneNumber.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

//                String phoneNumb=phoneNumber.getEditText().getText().toString().trim();
//                String cc = countryCodePicker.getSelectedCountryCode();
//                String phnNumber="+"+cc+phoneNumb;
//
//                validPhone=ifPhoneNumberAlreadyExist(phnNumber);
//
//                if(!validPhone){
//                    phoneNumber.setError("Phone Number doesn't exists.Please create a new account.");
//
//                }else{
//                    phoneNumber.setError(null);
//                    phoneNumber.setErrorEnabled(false);
//                }

            }
        });
    }

    public void login() {
        //move to next activity
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void  callSignUpScreen(View view){

        Intent intent2= new Intent(getApplicationContext(),SignUp.class);

        if(deepLink!=null){
            intent2.putExtra("deepLink",deepLink.toString());
        }
        startActivity(intent2);

    }


    public void getOtp(View view){

        validateFields();

    }

    public void validateFields(){
        String val=phoneNumber.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            phoneNumber.setError("Phone Number cannot be empty");
            Log.i("valid", "empty");
            return;

        } else {
            ifPhoneNumberAlreadyExist(val);
        }

    }

    public void ifPhoneNumberAlreadyExist(String val){
        Log.i("valid", "function");

        String ccp = countryCodePicker.getSelectedCountryCode();
        String verificationPhoneNumber="+"+ccp+val;

        final boolean[] verify = {false};

        CollectionReference usersCollectionReference = db.collection("Users");
        usersCollectionReference
                .whereEqualTo("Phone Number",verificationPhoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.i("valid", "oncomplete");

                        verify[0] = task.getResult().size() == 0;

                        if(verify[0]){
                            phoneNumber.setError("Phone Number doesn't exists.Please create a new account.");


                        }else {
                            phoneNumber.setError(null);
                            phoneNumber.setErrorEnabled(false);

                            Intent intent = new Intent(LoginActivity.this, LoginVerifyOTP.class);
                            intent.putExtra("PhoneNumberLogin",verificationPhoneNumber);
                            startActivity(intent);
                        }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,"Error!",Toast.LENGTH_SHORT).show();

                    }
                });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }



}