package com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.SignUpp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.Profile.ProfileActivity;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login.LoginActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignUpReferralActivity extends AppCompatActivity {
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user ;
    String uid;
    LottieAnimationView referralImage;
    TextInputLayout referralCodeTextInputLayout;
    DocumentReference currentUserDocumentReference;
    DocumentReference otherUserDocumentReference;
    String otherUID,deeplink,referralCode;
    int CoinsAdded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_referral);

        referralCodeTextInputLayout=findViewById(R.id.referralCodeTextInputLayout);
        referralImage=findViewById(R.id.referralrImage);

        user = mAuth.getCurrentUser();
        uid= user.getUid();
        currentUserDocumentReference = db.collection("Users").document(uid);

        deeplink=getIntent().getStringExtra("deepLink");
//        Log.i("getDynamicLink", deeplink);
        if(deeplink!=null){
            otherUID=deeplink.substring(37);
            referralCode="LSP"+otherUID;
            referralCodeTextInputLayout.getEditText().setText(referralCode);
        }


    }

    public void validateReferralCode(){
        String val=referralCodeTextInputLayout.getEditText().getText().toString().trim();
        otherUID=val.substring(3);

        if (val.isEmpty()) {
            referralCodeTextInputLayout.setError("Referral Code cannot be empty");
        }
        else{
            isUserReferralPinValid(otherUID);

        }

    }

    public void isUserReferralPinValid(String otherUID) {
        try {
            DocumentReference otherUserDocumentReference = db.collection("Users").document(otherUID);


            otherUserDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {

                        currentUserDocumentReference.update("Coins", FieldValue.increment(250)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                otherUserDocumentReference.update("Coins", FieldValue.increment(250)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        referralImage.playAnimation();

                                        Toast.makeText(SignUpReferralActivity.this, "Congratulations! \n 250 coins have been successfully credited to your account!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpReferralActivity.this, ProfileActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(SignUpReferralActivity.this, "Sorry did not work", Toast.LENGTH_SHORT).show();
                                    }
                                });
//                Toast.makeText(SignUpReferralActivity.this,"Yayyyyyy! \n 250 coins have been successfully credited to your account!",Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        referralCodeTextInputLayout.setError("Referral code Invalid");

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void  claimReferral(View v){
        validateReferralCode();
    }

    public void  skipReferral(View v){
        Intent intent= new Intent(this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}