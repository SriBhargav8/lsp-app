package com.LegalSuvidha.legalsuvidhaproviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.Blogs.BlogsActivity;
import com.LegalSuvidha.legalsuvidhaproviders.BusinessRegistration.BusinessRegistrationActivity;
import com.LegalSuvidha.legalsuvidhaproviders.FileITR.FileITRActivity;
import com.LegalSuvidha.legalsuvidhaproviders.GSTReturnLateFeeCalculator.GstReturnLateFeeCalculator;
import com.LegalSuvidha.legalsuvidhaproviders.GstRegistration.GstRegistrationActivity;
import com.LegalSuvidha.legalsuvidhaproviders.GstReturn.GstReturnActivity;
import com.LegalSuvidha.legalsuvidhaproviders.GstVaidation.GstValidatorActivity;
import com.LegalSuvidha.legalsuvidhaproviders.IncomeTaxCalculator.IncomeTaxCalculator;
import com.LegalSuvidha.legalsuvidhaproviders.MandatoryAnnualCompliance.MandatoryComplianceActivity;
import com.LegalSuvidha.legalsuvidhaproviders.RentReceiptGenerator.RentReceiptGenerator;
import com.LegalSuvidha.legalsuvidhaproviders.ShareCertificate.ShareCertificateGeneratorActivity;
import com.LegalSuvidha.legalsuvidhaproviders.TrademarkRegistration.TrademarkRegistrationActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.LegalSuvidha.legalsuvidhaproviders.HomeScreen.navigationview;

public class HomeFragment extends Fragment {
    CardView cardView1,cardView2,cardView3,cardView4,cardView5,cardView6,cardView7,cardView8;
    Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        cardView1=root.findViewById(R.id.cardView1);
//        cardView2=root.findViewById(R.id.cardview2);
        cardView3=root.findViewById(R.id.cardView3);
        cardView4=root.findViewById(R.id.cardView4);
        cardView5=root.findViewById(R.id.cardView5);
        cardView6=root.findViewById(R.id.cardView6);
        cardView7=root.findViewById(R.id.cardView7);
        cardView8=root.findViewById(R.id.cardView8);
        button=root.findViewById(R.id.ibuttonVisitWebsite);

        cardView1.setOnClickListener(this::goToBlogs);
//        cardView2.setOnClickListener(this::goToFileITR);
        cardView3.setOnClickListener(this::goToValidateGSTActivity);
        cardView4.setOnClickListener(this::goToIncomeTaxCalculatorActivity);
        cardView5.setOnClickListener(this::goToServices);
        cardView6.setOnClickListener(this::goToRentReceiptGenerator);
        cardView7.setOnClickListener(this::goToGstLateFeecalc);
        cardView8.setOnClickListener(this::goToShareCertificateGenerator);
        button.setOnClickListener(this::visitWebsite);

        return  root;
    }

    private void goToIncomeTaxCalculatorActivity(View view) {

        Intent intent= new Intent(getContext(), IncomeTaxCalculator.class);
        startActivity(intent);
        navigationview.setCheckedItem(R.id.IncomeTaxCalculator);
    }

    private void goToServices(View view) {
        Intent intent = new Intent(getContext(), TypesOfServicesActivity.class);
        startActivity(intent);
    }



    public void  goToValidateGSTActivity(View v){
        Intent intent= new Intent(getContext(), GstValidatorActivity.class);
        startActivity(intent);

        navigationview.setCheckedItem(R.id.gst_validator);
    }


    public void  goToBlogs(View v){
        Intent intent= new Intent(getContext(), BlogsActivity.class);
        startActivity(intent);
        navigationview.setCheckedItem(R.id.blogs);
    }

    public void  goToGstLateFeecalc(View v){
        Intent intent= new Intent(getContext(), GstReturnLateFeeCalculator.class);
        startActivity(intent);
        navigationview.setCheckedItem(R.id.gstlatefeeCalculator);
    }

    public void  goToShareCertificateGenerator(View v){
        Intent intent= new Intent(getContext(), ShareCertificateGeneratorActivity.class);
        startActivity(intent);
        navigationview.setCheckedItem(R.id.share_certificate_generator);
    }

    public void  goToRentReceiptGenerator(View v){
        Intent intent= new Intent(getContext(), RentReceiptGenerator.class);
        startActivity(intent);

        navigationview.setCheckedItem(R.id.rent_receipt_generator);
    }

    public void  visitWebsite(View v){
        String url = "https://legalsuvidha.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


}