package com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.SignUpp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login.LoginActivity;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login.LoginVerifyOTP;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.SignUpp.OtpVerificationFragmentSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hbb20.CountryCodePicker;

import static android.view.View.VISIBLE;


public class SignUpWithPhoneNumberFragment extends Fragment {
    ImageView backButton;
    Button login;
    MaterialButton nextbutton;
    TextView titleText;
    TextInputLayout phoneNumberTV;
    CountryCodePicker countryCodePicker;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String name,phoneNumber,emailID,gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_sign_up_with_phone_number, container, false);

        nextbutton=v.findViewById(R.id.signUpNextButton);
        login=v.findViewById(R.id.signUpLoginButton);
        titleText=v.findViewById(R.id.signUpTitle);
        phoneNumberTV=v.findViewById(R.id.signUpPhoneNo);
        countryCodePicker=v.findViewById(R.id.countryCodePicker);

        assert getArguments() != null;

        nextbutton.setOnClickListener(this::callOtpVerificationScreen);
        login.setOnClickListener(this::callLogin);

        return v;
    }

    public void  callOtpVerificationScreen(View view) {
        validatePhoneNumber();

    }



    public void validatePhoneNumber(){
        String val=phoneNumberTV.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            phoneNumberTV.setError("Phone Number cannot be empty");
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

                            phoneNumberTV.setError(null);
                            phoneNumberTV.setErrorEnabled(false);

                            String userPhoneNumber=phoneNumberTV.getEditText().getText().toString().trim();
                            String ccp = countryCodePicker.getSelectedCountryCode();
                            String verificationPhoneNumber="+"+ccp+userPhoneNumber;
                            Log.i("Verify number",verificationPhoneNumber );


                            Bundle bundle = new Bundle();
                            bundle.putString("FullName",  getArguments().getString("FullName"));
                            bundle.putString("EmailID", getArguments().getString("EmailID"));
                            bundle.putString("Gender", getArguments().getString("Gender"));
                            bundle.putString("PhoneNumber", verificationPhoneNumber);
                            bundle.putString("deepLink",getArguments().getString("deepLink"));


                            OtpVerificationFragmentSignUp otpVerificationFragment= new OtpVerificationFragmentSignUp();
                            otpVerificationFragment.setArguments(bundle);

                            assert getFragmentManager() != null;
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,otpVerificationFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


                        }else {
                            phoneNumberTV.setError("Phone Number already exists.Please Login instead.");

                        }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Error!",Toast.LENGTH_SHORT).show();

                    }
                });


    }

    public void  callLogin(View view){

        Intent intentLogin= new Intent(getContext(), LoginActivity.class);
        startActivity(intentLogin);

    }



}