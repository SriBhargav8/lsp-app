package com.LegalSuvidha.legalsuvidhaproviders.MandatoryAnnualCompliance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.LegalSuvidha.legalsuvidhaproviders.R;

public class MandatoryComplianceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandatory_compliance);

        if(savedInstanceState==null) {

//            String registrationType= getIntent().getStringExtra("typeOfRegistration");
//            Log.i("typeOfRegistration","typeOfRegistration "+ registrationType);
//
//            Bundle bundle = new Bundle();
//            bundle.putString("RegistrationType",registrationType);
//
//            BusinessRegistrationFragment businessRegistrationFragment= new BusinessRegistrationFragment();
//            businessRegistrationFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfCompliance()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
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