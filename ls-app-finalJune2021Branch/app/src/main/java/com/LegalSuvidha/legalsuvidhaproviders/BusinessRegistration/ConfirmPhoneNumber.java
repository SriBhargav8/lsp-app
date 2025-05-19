package com.LegalSuvidha.legalsuvidhaproviders.BusinessRegistration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;


public class ConfirmPhoneNumber extends Fragment {

    String name,companyName,message,phoneNumber,emailID,city,businessType,seviceType;
    MaterialButton nextbutton;
    TextInputLayout phoneNumberTV;
    CountryCodePicker countryCodePicker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_confirm_phone_number, container, false);

        assert getArguments() != null;
        message = getArguments().getString("message");
        name = getArguments().getString("FullName");
        companyName = getArguments().getString("CompanyName");
        city = getArguments().getString("City");
        phoneNumber = getArguments().getString("PhoneNumber");
        emailID = getArguments().getString("EmailID");
        businessType = getArguments().getString("BusinessType");
        seviceType=getArguments().getString("ServiceType");


        phoneNumberTV=root.findViewById(R.id.PhoneNo);
        phoneNumberTV.getEditText().setText(phoneNumber);
        countryCodePicker=root.findViewById(R.id.countryCodePicker);

        nextbutton=root.findViewById(R.id.nextButtonGoToOtpVerification);
        nextbutton.setOnClickListener(this::goToOtpVerificationFragment);

        return root;
    }

    public void  goToOtpVerificationFragment(View view){

        if(!validatePhoneNumber()){
            return;
        }

        String userPhoneNumber=phoneNumberTV.getEditText().getText().toString().trim();
        Log.i("Verify number ",userPhoneNumber);

        String ccp = countryCodePicker.getSelectedCountryCode();
        String verificationPhoneNumber="+"+ccp+userPhoneNumber;

        Log.i("Verify number",verificationPhoneNumber );

        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("FullName",name);
        bundle.putString("EmailID",emailID);
        bundle.putString("City",city);
        bundle.putString("PhoneNumber",verificationPhoneNumber);
        bundle.putString("CompanyName",companyName);
        bundle.putString("BusinessType",businessType);
        bundle.putString("ServiceType",seviceType);

        OtpVerificationFragment otpVerificationFragment= new OtpVerificationFragment();
        otpVerificationFragment.setArguments(bundle);

        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,otpVerificationFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

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
}