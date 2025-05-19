package com.LegalSuvidha.legalsuvidhaproviders.GstRegistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.LegalSuvidha.legalsuvidhaproviders.R;

public class GstRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst_registration);

        if(savedInstanceState==null) {

//            String typeOfRegistration= getIntent().getStringExtra("typeOfRegistration");
//            Log.i("TypeOfBusinessIntent","typeOfRegistration "+ typeOfRegistration);
//
//            Bundle bundle = new Bundle();
//            bundle.putString("typeOfRegistration",typeOfRegistration);
//
//            GstRegistrationFragment gstRegistrationFragment= new GstRegistrationFragment();
//            gstRegistrationFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfGstRegistrationFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
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