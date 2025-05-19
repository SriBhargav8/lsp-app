package com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.SignUpp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login.LoginActivity;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.SignUpp.SignUpWithPhoneNumberFragment;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class signUpWithUserInfoFragment extends Fragment {
    MaterialButton nextbutton;
    Button login;
    TextView titleText;
    RadioGroup radioGroup;
    RadioButton radioButtonGender;
    TextInputLayout emailIDTextInputLayout,fullNameTextInputLayout;
    View v;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_sign_up_with_user_info, container, false);

//        if (user!= null) {
//            login();
//        }


        nextbutton=v.findViewById(R.id.signUpNextButton);
        login=v.findViewById(R.id.signUpLoginButton);
        titleText=v.findViewById(R.id.signUpTitle);
        fullNameTextInputLayout=v.findViewById(R.id.signUpFullNameTextView);
        emailIDTextInputLayout=v.findViewById(R.id.signUpEmailTextView);
        radioGroup=v.findViewById(R.id.radioGroup);

        nextbutton.setOnClickListener(this::callSignUpWithPhoneNumber);
        login.setOnClickListener(this::callLogin);

        return v;
    }

    public void  callLogin(View view){

        Intent intentLogin= new Intent(getContext(), LoginActivity.class);

        startActivity(intentLogin);

    }

//    public void login() {
//        //move to next activity
//        Intent intent = new Intent(getContext(), HomeScreen.class);
//        startActivity(intent);
//    }

    private boolean validateFullName(){
        String val= fullNameTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            fullNameTextInputLayout.setError("Field cannot be empty");
            return false;
        }else{
            fullNameTextInputLayout.setError(null);
            fullNameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender(){
        if(radioGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(getContext(),"Please select a gender",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private boolean validateEmail(){
        String val= emailIDTextInputLayout.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            emailIDTextInputLayout.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(checkEmail)){
            emailIDTextInputLayout.setError("Invalid Email");
            return false;
        } else{
            emailIDTextInputLayout.setError(null);
            emailIDTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }



    public void  callSignUpWithPhoneNumber(View view) {

        if(!validateFullName()| !validateEmail()|!validateGender()){
            return;
        }

        String name=fullNameTextInputLayout.getEditText().getText().toString().trim();
        String emailID=emailIDTextInputLayout.getEditText().getText().toString().trim();
        radioButtonGender=v.findViewById(radioGroup.getCheckedRadioButtonId());
        String gender=radioButtonGender.getText().toString();


        Bundle bundle = new Bundle();
        bundle.putString("FullName", name);
        bundle.putString("EmailID", emailID);
        bundle.putString("Gender", gender);
        bundle.putString("deepLink",getArguments().getString("deepLink"));
//        Log.i("getDynamicLink", getArguments().getString("deepLink"));

        SignUpWithPhoneNumberFragment signUpWithPhoneNumberFragment = new SignUpWithPhoneNumberFragment();
        signUpWithPhoneNumberFragment.setArguments(bundle);

        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, signUpWithPhoneNumberFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

}