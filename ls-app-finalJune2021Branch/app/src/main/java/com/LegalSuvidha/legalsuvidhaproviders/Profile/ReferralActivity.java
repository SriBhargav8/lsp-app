package com.LegalSuvidha.legalsuvidhaproviders.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReferralActivity extends AppCompatActivity {

    public static FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseUser user;
    String uid;
    TextView myCodeReferralTextView,shareReferralTextView;
    String myReferralCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid= user.getUid();

        myCodeReferralTextView = findViewById(R.id.myCodeReferralTextView);
        myReferralCode = "LSP" + uid;
        myCodeReferralTextView.setText(myReferralCode);
    }

    public void copyReferral(View v){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("myReferralCode", myReferralCode);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this,"Referral Code Copied",Toast.LENGTH_SHORT).show();

    }

    public void shareReferral(View v){
        createLink();

    }

    public void createLink(){
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
                            Toast.makeText(ReferralActivity.this,"Error! \n Try Again",Toast.LENGTH_SHORT).show();
//                            Uri shortLink=dynamicLinkUri;
                            // Error
                            // ...
                        }
                    }
                });

    }
}