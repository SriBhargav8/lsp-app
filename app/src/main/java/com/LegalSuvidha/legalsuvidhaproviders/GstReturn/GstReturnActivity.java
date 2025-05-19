package com.LegalSuvidha.legalsuvidhaproviders.GstReturn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.LegalSuvidha.legalsuvidhaproviders.GstRegistration.TypesOfGstRegistrationFragment;
import com.LegalSuvidha.legalsuvidhaproviders.R;

public class GstReturnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst_return);

        if(savedInstanceState==null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfGstReturnFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
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