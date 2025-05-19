package com.LegalSuvidha.legalsuvidhaproviders.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.Bookings.BookingActivity;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {

    public static FirebaseFirestore db= FirebaseFirestore.getInstance();
    public static FirebaseUser user;
    public static String uid;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    public static DocumentReference currentUserDocumentReference;

    Button update;
    TextView fullNameProfile,bookingLabel,coinsLabel;
    TextInputLayout emailID,fullName,phoneNumber;
    CardView bookingCard,CoinsCard;

    private Boolean fullNameChange,emailChange,phoneNoChange;

    private int coinsCounter=0;
    private int bookingsCounter=0;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        update=findViewById(R.id.updateProfile);
        fullNameProfile=findViewById(R.id.fullNameProfile);
        bookingLabel=findViewById(R.id.bookingLabel);
        coinsLabel=findViewById(R.id.coinsLabel);
        fullName=findViewById(R.id.fullNameProf);
        emailID=findViewById(R.id.emailIDProfile);
        phoneNumber=findViewById(R.id.phoneNumberProfile);
        bookingCard=findViewById(R.id.BookingCard);
        CoinsCard=findViewById(R.id.coinCard);

         user = mAuth.getCurrentUser();
         uid= user.getUid();

        currentUserDocumentReference = db.collection("Users").document(uid);

        currentUserDocumentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){


                            coinsLabel.setText(String.valueOf(documentSnapshot.get("Coins")));

                            fullNameProfile.setText(documentSnapshot.getString("Full Name"));
                            fullName.getEditText().setText(documentSnapshot.getString("Full Name"));

//                            usernameProfile.setText(documentSnapshot.getString("Username"));

                            emailID.getEditText().setText(documentSnapshot.getString("Email ID"));
                            phoneNumber.getEditText().setText(documentSnapshot.getString("Phone Number"));
//                            password.getEditText().setText(documentSnapshot.getString("Password"));
                            fullNameChange=false;
                            emailChange=false;
                            phoneNoChange=false;

                            currentUserDocumentReference.collection("Booking List").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    bookingsCounter=queryDocumentSnapshots.size();
                                    bookingLabel.setText(String.valueOf(bookingsCounter));
                                }
                            });

                            fullNameProfile.setText(documentSnapshot.getString("Full Name"));
                            fullName.getEditText().setText(documentSnapshot.getString("Full Name"));
                            emailID.getEditText().setText(documentSnapshot.getString("Email ID"));
                            phoneNumber.getEditText().setText(documentSnapshot.getString("Phone Number"));

                            fullNameChange=false;
                            emailChange=false;
                            phoneNoChange=false;

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this,"profile couldn't load",Toast.LENGTH_SHORT).show();
                    }
                });

        bookingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ProfileActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });

         CoinsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ProfileActivity.this, RewardsActivity.class);
                startActivity(intent); }
        });

        fullName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fullNameChange=true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailID.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailChange=true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        phoneNumber.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNoChange=true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//        password.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                passwordChange=true;
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !emailChange && !phoneNoChange && !fullNameChange){
                    Toast.makeText(ProfileActivity.this, "Already up to date!", Toast.LENGTH_SHORT).show();
                }
//                if(passwordChange){
//                    user_profileReference.update("Password",password.getEditText().getText().toString().trim());
//                    Toast.makeText(getActivity(), "Successfully updated!", Toast.LENGTH_SHORT).show();
//                    fullNameChange=false;
//                    emailChange=false;
//                    phoneNoChange=false;
//                    passwordChange=false;
//                }
                if(emailChange){
                    currentUserDocumentReference.update("Email ID",emailID.getEditText().getText().toString().trim());
                    Toast.makeText(ProfileActivity.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
                    fullNameChange=false;
                    emailChange=false;
                    phoneNoChange=false;
//                    passwordChange=false;
                }
                if(phoneNoChange){
//                    user_profileReference.update("Phone Number",phoneNumber.getEditText().getText().toString());
                    Toast.makeText(ProfileActivity.this, "Cannot Update Phone Number", Toast.LENGTH_SHORT).show();

                    currentUserDocumentReference.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        phoneNumber.getEditText().setText(documentSnapshot.getString("Phone Number"));
                                        fullNameChange=false;
                                        emailChange=false;
                                        phoneNoChange=false;
//                                        passwordChange=false;
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfileActivity.this,"profile couldn't load",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                if(fullNameChange){
                    currentUserDocumentReference.update("Full Name",fullName.getEditText().getText().toString().trim());
                    Toast.makeText(ProfileActivity.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
                    currentUserDocumentReference.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    fullNameProfile.setText(documentSnapshot.getString("Full Name"));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                    fullNameChange=false;
                    emailChange=false;
                    phoneNoChange=false;
//                    passwordChange=false;
                }
            }
        });
    }
}