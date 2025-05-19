package com.LegalSuvidha.legalsuvidhaproviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.Blogs.BlogsActivity;
import com.LegalSuvidha.legalsuvidhaproviders.BusinessRegistration.TypesOfBusinessRegistration;
import com.LegalSuvidha.legalsuvidhaproviders.GstRegistration.TypesOfGstRegistrationFragment;
import com.LegalSuvidha.legalsuvidhaproviders.GstVaidation.GstValidatorActivity;
import com.LegalSuvidha.legalsuvidhaproviders.MandatoryAnnualCompliance.MandatoryComplianceActivity;
import com.LegalSuvidha.legalsuvidhaproviders.getFree21Agreements.Registration21AgreementsFragment;
import com.google.android.material.navigation.NavigationView;

public class FrameActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationview;
    ImageView imageView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        imageView=findViewById(R.id.frameActivityImageView);
        textView=findViewById(R.id.frameActivityTextView);

//        imageView.setImageResource();
//        textView.setText();

        toolbar = findViewById(R.id.toolbarFrameActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        drawer= findViewById(R.id.drawer_layoutFrameActivity);

        navigationview = findViewById(R.id.navigationFrameActivity);
        navigationview.setNavigationItemSelectedListener(this);
        navigationview.setItemIconTintList(null);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
//        toggle.syncState();


        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            navigationview.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                navigationview.setCheckedItem(R.id.nav_home);
                break;
            case R.id.gst_registration:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfGstRegistrationFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                navigationview.setCheckedItem(R.id.gst_registration);
                break;
            case R.id.gst_validator:
                navigationview.setCheckedItem(R.id.gst_validator);
                Intent intent= new Intent(this, GstValidatorActivity.class);
                startActivity(intent);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyPlacesMapsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
//                Intent intent= new Intent(this, MyPlacesClass.class);
//                startActivity(intent);
                break;
            case R.id.get_21_Free_Agreement:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Registration21AgreementsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
////                Intent intent= new Intent(this, MyPlacesClass.class);
//                startActivity(intent);
                navigationview.setCheckedItem(R.id.get_21_Free_Agreement);
                break;
            case R.id.nav_email_us:
                Intent emailIntent= new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                //mail to protocol that lets you mail using client installed on your device
                String[] to={"neetibisht919@gmail.com"};
                emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Legal Suvidha Providers");
                emailIntent.setType("message/rfc822");
                //specification for email
                Intent chooser=Intent.createChooser(emailIntent,"Send Email");
                startActivity(chooser);
                break;
            case R.id.nav_share:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new music_library()).commit();
                break;
            case R.id.rate_the_app:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                break;

            case R.id.business_registration:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfBusinessRegistration()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                navigationview.setCheckedItem(R.id.business_registration);
                break;

            case R.id.trademark_registration:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfBusinessRegistration()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                navigationview.setCheckedItem(R.id.trademark_registration);
                break;


            case R.id.gst_return:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfBusinessRegistration()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                navigationview.setCheckedItem(R.id.gst_return);
                break;


            case R.id.mandatory_annual_compliance:
                Intent intentM= new Intent(FrameActivity.this, MandatoryComplianceActivity.class);
                startActivity(intentM);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfBusinessRegistration()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                navigationview.setCheckedItem(R.id.mandatory_annual_compliance);
                break;

            case R.id.blogs:
                Intent intentBlog= new Intent(FrameActivity.this, BlogsActivity.class);
                startActivity(intentBlog);
                navigationview.setCheckedItem(R.id.blogs);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
//            if(getSupportFragmentManager().findFragmentById(R.id.fragment_container).getParentFragment()==R.layout.fragment_profile){
            if (doubleBackToExitPressedOnce) {
                this.finishAffinity();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(mRunnable, 2000);

        }
    }
}