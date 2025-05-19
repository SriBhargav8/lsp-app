package com.LegalSuvidha.legalsuvidhaproviders.TrademarkRegistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.LegalSuvidha.legalsuvidhaproviders.R;

public class TrademarkRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trademark_registration);

        if(savedInstanceState==null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TrademarkRegistrationFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
    }
}