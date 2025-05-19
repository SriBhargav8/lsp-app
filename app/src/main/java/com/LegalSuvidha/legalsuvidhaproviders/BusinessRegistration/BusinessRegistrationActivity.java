package com.LegalSuvidha.legalsuvidhaproviders.BusinessRegistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.LegalSuvidha.legalsuvidhaproviders.R;

public class BusinessRegistrationActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_registration);

        if(savedInstanceState==null) {

//            String registrationType= getIntent().getStringExtra("typeOfRegistration");
//            Log.i("typeOfRegistration","typeOfRegistration "+ registrationType);
//
//            Bundle bundle = new Bundle();
//            bundle.putString("RegistrationType",registrationType);
//
//            BusinessRegistrationFragment businessRegistrationFragment= new BusinessRegistrationFragment();
//            businessRegistrationFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfBusinessRegistration()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }

    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        }
        else {
            super.onBackPressed();
        }
    }

}