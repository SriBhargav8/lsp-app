package com.LegalSuvidha.legalsuvidhaproviders.StartupIndiaRegistration;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.TrademarkRegistration.TrademarkRegistrationFragment;

public class StartupIndiaRegistrationActivity extends AppCompatActivity {
    ImageView appBarImageView;
    TextView appBarTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trademark_registration);

        appBarImageView=findViewById(R.id.appBarImageView);
        appBarTV=findViewById(R.id.appBarTV);

        appBarTV.setText("Startup India Registration");
        appBarImageView.setImageResource(R.drawable.businessman);

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new StartupIndiaRegistrationFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
    }
}