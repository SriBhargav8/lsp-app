package com.LegalSuvidha.legalsuvidhaproviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.Blogs.BlogsActivity;
import com.LegalSuvidha.legalsuvidhaproviders.Bookings.BookingActivity;
import com.LegalSuvidha.legalsuvidhaproviders.BusinessRegistration.BusinessRegistrationActivity;
import com.LegalSuvidha.legalsuvidhaproviders.FileITR.FileITRActivity;
import com.LegalSuvidha.legalsuvidhaproviders.GSTReturnLateFeeCalculator.GstReturnLateFeeCalculator;
import com.LegalSuvidha.legalsuvidhaproviders.GstRegistration.GstRegistrationActivity;
import com.LegalSuvidha.legalsuvidhaproviders.GstReturn.GstReturnActivity;
import com.LegalSuvidha.legalsuvidhaproviders.GstVaidation.GstValidatorActivity;
import com.LegalSuvidha.legalsuvidhaproviders.IncomeTaxCalculator.IncomeTaxCalculator;
import com.LegalSuvidha.legalsuvidhaproviders.MandatoryAnnualCompliance.MandatoryComplianceActivity;
import com.LegalSuvidha.legalsuvidhaproviders.Profile.ProfileActivity;
import com.LegalSuvidha.legalsuvidhaproviders.Profile.ReferralActivity;
import com.LegalSuvidha.legalsuvidhaproviders.Profile.RewardsActivity;
import com.LegalSuvidha.legalsuvidhaproviders.RentReceiptGenerator.RentReceiptGenerator;
import com.LegalSuvidha.legalsuvidhaproviders.ShareCertificate.ShareCertificateGeneratorActivity;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login.LoginActivity;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.StartActivity;
import com.LegalSuvidha.legalsuvidhaproviders.StartupIndiaRegistration.StartupIndiaRegistrationActivity;
import com.LegalSuvidha.legalsuvidhaproviders.TrademarkRegistration.TrademarkRegistrationActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class TypesOfServicesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    Toolbar toolbar;
    public static NavigationView navigationview;

    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_of_services);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        drawer= findViewById(R.id.drawer_layout);

        navigationview = findViewById(R.id.navigation);
        navigationview.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        navigationview.setItemIconTintList(null);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);


    }

    public void goToProfile(View v){
        Intent intentProfile= new Intent(TypesOfServicesActivity.this, ProfileActivity.class);
        startActivity(intentProfile);
        navigationview.setCheckedItem(R.id.share_certificate_generator);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                navigationview.setCheckedItem(R.id.nav_home);
                break;
            case R.id.gst_registration:

//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfGstRegistrationFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                Intent intentGstReg= new Intent(this, GstRegistrationActivity.class);
                startActivity(intentGstReg);
                navigationview.setCheckedItem(R.id.gst_registration);
                break;
            case R.id.gst_validator:
                navigationview.setCheckedItem(R.id.gst_validator);
                Intent intentVal= new Intent(this, GstValidatorActivity.class);
                startActivity(intentVal);
//                  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyPlacesMapsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
//                  Intent intent= new Intent(this, MyPlacesClass.class);
//                  startActivity(intent);
                break;
            case R.id.get_21_Free_Agreement:
//                  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Registration21AgreementsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                Intent intentITR= new Intent(this, FileITRActivity.class);
                startActivity(intentITR);
                navigationview.setCheckedItem(R.id.get_21_Free_Agreement);

            case R.id.startup_india_registration:
//                  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Registration21AgreementsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                Intent intentStartup= new Intent(this, StartupIndiaRegistrationActivity.class);
                startActivity(intentStartup);
                navigationview.setCheckedItem(R.id.startup_india_registration);
                break;

            case R.id.nav_email_us:
                Intent emailIntent= new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                //mail to protocol that lets you mail using client installed on your device
                String[] to={"neetibisht919@gmail.com","support@legalsuvidha"};
                emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Legal Suvidha Providers");
                emailIntent.setType("message/rfc822");
                //specification for email
                Intent chooser=Intent.createChooser(emailIntent,"Send Email");
                startActivity(chooser);
                break;
            case R.id.nav_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Application Link : https://play.google.com/store/apps/details?id=com.LegalSuvidha.legalsuvidhaproviders";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new music_library()).commit();
                break;
            case R.id.nav_rewards:
                Intent intentRefer= new Intent(this, RewardsActivity.class);
                startActivity(intentRefer);
                navigationview.setCheckedItem(R.id.nav_rewards);
                break;
            case R.id.rate_the_app:
                rateApp();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                break;

            case R.id.business_registration:
                Intent intentBr= new Intent(TypesOfServicesActivity.this, BusinessRegistrationActivity.class);
                startActivity(intentBr);
                navigationview.setCheckedItem(R.id.business_registration);
                break;

            case R.id.trademark_registration:
                Intent intentT= new Intent(TypesOfServicesActivity.this, TrademarkRegistrationActivity.class);
                startActivity(intentT);
                navigationview.setCheckedItem(R.id.trademark_registration);
                break;


            case R.id.gst_return:
                Intent intentGR= new Intent(TypesOfServicesActivity.this, GstReturnActivity.class);
                startActivity(intentGR);
                navigationview.setCheckedItem(R.id.gst_return);
                break;



            case R.id.mandatory_annual_compliance:
                Intent intentM= new Intent(TypesOfServicesActivity.this, MandatoryComplianceActivity.class);
                startActivity(intentM);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfBusinessRegistration()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                navigationview.setCheckedItem(R.id.mandatory_annual_compliance);
                break;

            case R.id.blogs:
                Intent intentBlog= new Intent(TypesOfServicesActivity.this, BlogsActivity.class);
                startActivity(intentBlog);
                navigationview.setCheckedItem(R.id.blogs);
                break;

            case R.id.IncomeTaxCalculator:
                Intent intentITCalc= new Intent(TypesOfServicesActivity.this, IncomeTaxCalculator.class);
                startActivity(intentITCalc);
                navigationview.setCheckedItem(R.id.blogs);
                break;

            case R.id.rent_receipt_generator:
                Intent intentRent= new Intent(TypesOfServicesActivity.this, RentReceiptGenerator.class);
                startActivity(intentRent);
                navigationview.setCheckedItem(R.id.rent_receipt_generator);
                break;

            case R.id.nav_bookings:
                Intent intentBooking= new Intent(TypesOfServicesActivity.this, BookingActivity.class);
                startActivity(intentBooking);
                navigationview.setCheckedItem(R.id.nav_bookings);
                break;

            case R.id.gstlatefeeCalculator:
                Intent GstLateFeeCalc= new Intent(TypesOfServicesActivity.this, GstReturnLateFeeCalculator.class);
                startActivity(GstLateFeeCalc);
                navigationview.setCheckedItem(R.id.gstlatefeeCalculator);
                break;

            case R.id.share_certificate_generator:
                Intent intentShareCert= new Intent(TypesOfServicesActivity.this, ShareCertificateGeneratorActivity.class);
                startActivity(intentShareCert);
                navigationview.setCheckedItem(R.id.share_certificate_generator);
                break;

            case R.id.nav_profile:
            Intent intentProfile= new Intent(TypesOfServicesActivity.this, ProfileActivity.class);
            startActivity(intentProfile);
            navigationview.setCheckedItem(R.id.share_certificate_generator);  break;
            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.logout)
                        .setTitle("Confirm Log out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(TypesOfServicesActivity.this," Successfully Logged Out",Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent= new Intent(TypesOfServicesActivity.this, LoginActivity.class);
                                Log.i("Item selected","Log out");

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                user=null;
//                                    uid=null;
//                                    currentUserDocumentReference=null;
                                startActivity(intent);

//                                finishAffinity();
//                                onDestroy();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                break;
}
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details?id=com.LegalSuvidha.legalsuvidhaproviders");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    public void  goToFileITR(View v){
        assert getFragmentManager() != null;
        Intent intent = new Intent(this, FileITRActivity.class);
        startActivity(intent);

        navigationview.setCheckedItem(R.id.get_21_Free_Agreement);
    }

    public void goToTrademarkRegistrationActivity(View view) {
//        assert getFragmentManager() != null;
////        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new Registration21AgreementsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
//        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new TrademarkRegistrationFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        Intent intentT= new Intent(this, TrademarkRegistrationActivity.class);
        startActivity(intentT);

        navigationview.setCheckedItem(R.id.trademark_registration);
    }

    public void goToGstReturnActivity(View view) {

        Intent intentG= new Intent(this, GstReturnActivity.class);
        startActivity(intentG);
        navigationview.setCheckedItem(R.id.gst_return);
    }

    public void goToMandatoryAnnualCompliance(View v) {

        Intent intentM = new Intent(this, MandatoryComplianceActivity.class);
        startActivity(intentM);

        navigationview.setCheckedItem(R.id.mandatory_annual_compliance);

    }

//    public void  goToGet21FreeAgreementsActivity(View v){
//        assert getFragmentManager() != null;
//        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new Registration21AgreementsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

//        Intent intent = new Intent(this, FileITRActivity.class);
//        startActivity(intent);
//
//        navigationview.setCheckedItem(R.id.get_21_Free_Agreement);
//    }


    public void  goToGstRegistrationActivity(View v){
        Intent intent= new Intent(this, GstRegistrationActivity.class);
        startActivity(intent);
        navigationview.setCheckedItem(R.id.gst_registration);

    }

    public void  goToBusinessRegistrationActivity(View v){
        Intent intent= new Intent(this, BusinessRegistrationActivity.class);
        startActivity(intent);
        navigationview.setCheckedItem(R.id.business_registration);
    }

    public void  goToStartupIndiaRegistrationActivity(View v){
        Intent intent= new Intent(this, StartupIndiaRegistrationActivity.class);
        startActivity(intent);
        navigationview.setCheckedItem(R.id.business_registration);
    }


}