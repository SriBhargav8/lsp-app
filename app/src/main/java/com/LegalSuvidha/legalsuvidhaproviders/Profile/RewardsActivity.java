package com.LegalSuvidha.legalsuvidhaproviders.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.TypesOfServicesActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class RewardsActivity extends AppCompatActivity {

    TextView coinsTV;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseUser user;
    String uid;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    public static DocumentReference currentUserDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        coinsTV=findViewById(R.id.textView1);

        user = mAuth.getCurrentUser();
        uid= user.getUid();

        currentUserDocumentReference = db.collection("Users").document(uid);

        currentUserDocumentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            coinsTV.setText(String.valueOf(documentSnapshot.get("Coins")));

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RewardsActivity.this,"Couldn't load coins",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void goToReferralActivity(View v){

        Intent intent= new Intent(RewardsActivity.this, ReferralActivity.class);
        startActivity(intent);

    }

    public void goToServices(View v){
        Intent intent= new Intent(RewardsActivity.this, TypesOfServicesActivity.class);
        startActivity(intent);

    }
}