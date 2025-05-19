package com.LegalSuvidha.legalsuvidhaproviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeScreen<Public> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    private DrawerLayout drawer;
    Toolbar toolbar;
    public static NavigationView navigationview;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseMessaging.getInstance().subscribeToTopic("all");
//        createNotificationChannel();


//        toolbar= findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");

        int Permission_All=1;

        String[] Permissions = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE };
        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        drawer= findViewById(R.id.drawer_layout);

        navigationview = findViewById(R.id.navigation);
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


    public static boolean hasPermissions(Context context, String... permissions){
        Log.i("testingpermission","reached");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M&& context!=null&&permissions!=null){
            for(String permission: permissions){
                Log.i("testingpermission","reached3");
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){

                    Log.i("testingpermission","reached5");
                    return false;
                }
            }
        }
        return true;
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

                case R.id.nav_bookings:
                    Intent intentBooking= new Intent(HomeScreen.this, BookingActivity.class);
                    startActivity(intentBooking);
                    navigationview.setCheckedItem(R.id.nav_bookings);
                    break;

                case R.id.get_21_Free_Agreement:
//                  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Registration21AgreementsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                     Intent intentITR= new Intent(this, FileITRActivity.class);
                     startActivity(intentITR);
                    navigationview.setCheckedItem(R.id.get_21_Free_Agreement);
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
                    navigationview.setCheckedItem(R.id.nav_email_us);
                    break;
//                case R.id.nav_refer:
//                    Intent intentRefer= new Intent(this, ReferralActivity.class);
//                    startActivity(intentRefer);
//                    navigationview.setCheckedItem(R.id.nav_share);
//                  break;
                case R.id.nav_rewards:
                    Intent intentRefer= new Intent(this, RewardsActivity.class);
                    startActivity(intentRefer);
                    navigationview.setCheckedItem(R.id.nav_rewards);
                    break;
                case R.id.nav_share:

                    navigationview.setCheckedItem(R.id.nav_share);
                    String uid;
                    uid= user.getUid();

                    DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                            .setLink(Uri.parse("https://legalsuvidha.com/"))
                            .setDynamicLinkDomain("https://legalsuvidhaproviders.page.link")
                            // Open links with this app on Android
                            .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                            // Open links with com.example.ios on iOS
                            .buildDynamicLink();
                    Log.i("link di", String.valueOf(Uri.parse(dynamicLink.toString())));

//otherUserid="+uid
                    String sharelinktext  = "https://legalsuvidhaproviders.page.link?"+
                            "link=https://legalsuvidha.com/otherUserid="+uid+
//                "link=https://play.google.com/store/apps/details?id=com.LegalSuvidha.legalsuvidhaproviders"+
                            "&apn="+ getPackageName()+
                            "&st="+"Legal Suvidha-My Refer Link"+
                            "&sd="+"Earn 250 coins"+
                            "&si="+"https://legalsuvidha.com/media/FORM_12BA_CANVA_YSPDQ0o.jpg";

                    Log.i("link share",sharelinktext);

                    Uri dynamicLinkUri = Uri.parse(sharelinktext);

                    Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                            .setLongLink(dynamicLinkUri)
                            .buildShortDynamicLink()
                            .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                                @Override
                                public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                    if (task.isSuccessful()) {
                                        // Short link created
                                        Uri shortLink = task.getResult().getShortLink();
                                        Uri flowchartLink = task.getResult().getPreviewLink();
                                        Log.i("link",sharelinktext);
                                        Intent intent=new Intent();
                                        intent.setAction(Intent.ACTION_SEND);
                                        assert shortLink != null;
                                        intent.putExtra(Intent.EXTRA_TEXT,"Hey, click on this link " + shortLink.toString()+ " to download Legal Suvidha Application, \"One-stop for all legal services " +
                                                "for Indian businesses\" and Sign Up or use my refer code: LSP"+uid+" and both of us will earn 250 coins " +
                                                "each.");
                                        intent.setType("text/plain");
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(HomeScreen.this,"Error! \n Try Again",Toast.LENGTH_SHORT).show();
//                            Uri shortLink=dynamicLinkUri;
                                        // Error
                                        // ...
                                    }
                                }
                            });
//                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                    sharingIntent.setType("text/plain");
//                    String shareBody = "Application Link : https://play.google.com/store/apps/details?id=com.LegalSuvidha.legalsuvidhaproviders";
//                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
//                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                 break;
                case R.id.rate_the_app:
                    rateApp();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    break;

                case R.id.startup_india_registration:
//                  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Registration21AgreementsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    Intent intentStartup= new Intent(this, StartupIndiaRegistrationActivity.class);
                    startActivity(intentStartup);
                    navigationview.setCheckedItem(R.id.startup_india_registration);
                    break;

                case R.id.business_registration:
                    Intent intentBr= new Intent(HomeScreen.this, BusinessRegistrationActivity.class);
                    startActivity(intentBr);
                    navigationview.setCheckedItem(R.id.business_registration);
                    break;

                case R.id.trademark_registration:
                    Intent intentT= new Intent(HomeScreen.this, TrademarkRegistrationActivity.class);
                    startActivity(intentT);
                     navigationview.setCheckedItem(R.id.trademark_registration);
                    break;


                case R.id.gst_return:
                    Intent intentGR= new Intent(HomeScreen.this, GstReturnActivity.class);
                    startActivity(intentGR);
                      navigationview.setCheckedItem(R.id.gst_return);
                    break;


                case R.id.mandatory_annual_compliance:
                    Intent intentM= new Intent(HomeScreen.this, MandatoryComplianceActivity.class);
                    startActivity(intentM);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TypesOfBusinessRegistration()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    navigationview.setCheckedItem(R.id.mandatory_annual_compliance);
                    break;

                case R.id.blogs:
                    Intent intentBlog= new Intent(HomeScreen.this, BlogsActivity.class);
                    startActivity(intentBlog);
                    navigationview.setCheckedItem(R.id.blogs);
                    break;

                case R.id.IncomeTaxCalculator:
                    Intent intentITCalc= new Intent(HomeScreen.this, IncomeTaxCalculator.class);
                    startActivity(intentITCalc);
                    navigationview.setCheckedItem(R.id.blogs);
                    break;

                case R.id.rent_receipt_generator:
                    Intent intentRent= new Intent(HomeScreen.this, RentReceiptGenerator.class);
                    startActivity(intentRent);
                    navigationview.setCheckedItem(R.id.rent_receipt_generator);
                    break;

                case R.id.gstlatefeeCalculator:
                    Intent GstLateFeeCalc= new Intent(HomeScreen.this, GstReturnLateFeeCalculator.class);
                    startActivity(GstLateFeeCalc);
                    navigationview.setCheckedItem(R.id.gstlatefeeCalculator);
                    break;

                case R.id.share_certificate_generator:
                    Intent intentShareCert= new Intent(HomeScreen.this, ShareCertificateGeneratorActivity.class);
                    startActivity(intentShareCert);
                    navigationview.setCheckedItem(R.id.share_certificate_generator);
                    break;
                case R.id.nav_profile:
                    Intent intentProfile= new Intent(HomeScreen.this, ProfileActivity.class);
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
                                    Toast.makeText(HomeScreen.this," Successfully Logged Out",Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent= new Intent(HomeScreen.this, LoginActivity.class);
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


    public void goToProfile(View v){
        Intent intentProfile= new Intent(HomeScreen.this, ProfileActivity.class);
        startActivity(intentProfile);
        navigationview.setCheckedItem(R.id.share_certificate_generator);
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


