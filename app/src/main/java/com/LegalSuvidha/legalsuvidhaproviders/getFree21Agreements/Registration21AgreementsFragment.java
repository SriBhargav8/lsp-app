package com.LegalSuvidha.legalsuvidhaproviders.getFree21Agreements;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration21AgreementsFragment extends Fragment {
    TextInputLayout nameTV;
    TextInputLayout emailIDTV;
    TextInputLayout phoneNumberTV;
    TextInputLayout companyNameTV;
    TextInputLayout cityTV;
    TextInputLayout messageTV;
    MaterialButton nextButton;
    ProgressBar progressBar;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    AutoCompleteTextView autoCompleteTVState;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.fragment_registeration21_agreementsfragment, container, false);

        nameTV=root.findViewById(R.id.textInputLayoutName);
        emailIDTV=root.findViewById(R.id.textInputLayoutEmailAddress);
        cityTV=root.findViewById(R.id.textInputLayoutCity);
        phoneNumberTV=root.findViewById(R.id.textInputLayoutPhoneNumber);
        companyNameTV=root.findViewById(R.id.textInputLayoutCompanyName);
        messageTV=root.findViewById(R.id.textInputLayoutmessage);
        nextButton=root.findViewById(R.id.nextButtongoToConfirmPhoneNumber);
        nextButton.setOnClickListener(this::goToConfirmPhoneNumber);
        progressBar=root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        autoCompleteTVState=root.findViewById(R.id.autocompleteTextViewState);

        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.india_states));
        autoCompleteTVState.setAdapter(adapterState);

        return root;
    }

    private boolean validateFullName(){
        String val= nameTV.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            nameTV.setError("Field cannot be empty");
            return false;
        }else{
            nameTV.setError(null);
            nameTV.setErrorEnabled(false);
            return true;
        }
    }



    private boolean validateEmail(){
        String val= emailIDTV.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            emailIDTV.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(checkEmail)){
            emailIDTV.setError("Invalid Email");
            return false;
        } else{
            emailIDTV.setError(null);
            emailIDTV.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatePhoneNumber(){
        String val=phoneNumberTV.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            phoneNumberTV.setError("Phone Number cannot be empty");
            return false;
        } else{
            phoneNumberTV.setError(null);
            phoneNumberTV.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateCompanyName(){
        String val=companyNameTV.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            companyNameTV.setError("Phone Number cannot be empty");
            return false;
        } else{
            companyNameTV.setError(null);
            companyNameTV.setErrorEnabled(false);
            return true;
        }

    }

    public boolean validateCity(){
        String val=cityTV.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            cityTV.setError("Phone Number cannot be empty");
            return false;
        } else{
            cityTV.setError(null);
            cityTV.setErrorEnabled(false);
            return true;
        }

    }



    public void  goToConfirmPhoneNumber(View view){

    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void hideProgressBar(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }
}