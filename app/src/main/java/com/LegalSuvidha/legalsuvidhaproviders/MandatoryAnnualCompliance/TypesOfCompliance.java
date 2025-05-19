package com.LegalSuvidha.legalsuvidhaproviders.MandatoryAnnualCompliance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.LegalSuvidha.legalsuvidhaproviders.R;

public class TypesOfCompliance extends Fragment implements View.OnClickListener {

    CardView cardViewAcPvtLtd,cardViewACLPP,cardViewACNidhiCompany,cardViewAcSection8NGO;
    String typeOfAC;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.fragment_types_of_compliance, container, false);

        cardViewAcPvtLtd=root.findViewById(R.id.cardViewACPvtLtd);
        cardViewACLPP=root.findViewById(R.id.cardViewACLPP);
        cardViewACNidhiCompany=root.findViewById(R.id.cardviewACNidhiCompany);
        cardViewAcSection8NGO=root.findViewById(R.id.cardViewAcSection8NGO);

        cardViewAcPvtLtd.setOnClickListener(this);
        cardViewACLPP.setOnClickListener(this);
        cardViewACNidhiCompany.setOnClickListener(this);
        cardViewAcSection8NGO.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.cardViewACPvtLtd){
            typeOfAC="Annual compliance for Pvt Co.";
//            Log.i("TypeOfBusiness","TypeOfBusiness "+ typeOfAC);
//            Intent intent = new Intent(getContext(), BusinessRegistrationActivity.class);
//            intent.putExtra("typeOfBusiness",typeOfAC);
//            startActivity(intent);

            AnnualComplianceRegistrationFragment annualComplianceRegistrationFragment = new AnnualComplianceRegistrationFragment();
            Bundle args = new Bundle();
            args.putString(" typeOfBusiness", typeOfAC);
            annualComplianceRegistrationFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,annualComplianceRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if(v.getId()==R.id.cardViewACLPP){
            typeOfAC="Annual compliance for LLP's";
//            Log.i("TypeOfBusiness","TypeOfBusiness "+ typeOfAC);
//            Intent intent = new Intent(getContext(),BusinessRegistrationActivity.class);
//            intent.putExtra("typeOfBusiness",typeOfAC);
//            startActivity(intent);

            AnnualComplianceRegistrationFragment annualComplianceRegistrationFragment = new AnnualComplianceRegistrationFragment();
            Bundle args = new Bundle();
            args.putString(" typeOfBusiness", typeOfAC);
            annualComplianceRegistrationFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,annualComplianceRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


        }else if(v.getId()==R.id.cardviewACNidhiCompany){
            typeOfAC="OPC Annual compliance";
//            Log.i("TypeOfBusiness","TypeOfBusiness "+ typeOfAC);
//            Intent intent = new Intent(getContext(),BusinessRegistrationActivity.class);
//            intent.putExtra("typeOfBusiness",typeOfAC);
//            startActivity(intent);

            AnnualComplianceRegistrationFragment annualComplianceRegistrationFragment = new AnnualComplianceRegistrationFragment();
            Bundle args = new Bundle();
            args.putString(" typeOfBusiness", typeOfAC);
            annualComplianceRegistrationFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,annualComplianceRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


        }else if(v.getId()==R.id.cardViewAcSection8NGO){
            typeOfAC="Annual compliance for Section 8";
//            Log.i("TypeOfBusiness","TypeOfBusiness "+ typeOfAC);
//            Intent intent = new Intent(getContext(),BusinessRegistrationActivity.class);
//            intent.putExtra("typeOfBusiness",typeOfAC);
//            startActivity(intent);

            AnnualComplianceRegistrationFragment annualComplianceRegistrationFragment = new AnnualComplianceRegistrationFragment();
            Bundle args = new Bundle();
            args.putString(" typeOfBusiness", typeOfAC);
            annualComplianceRegistrationFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,annualComplianceRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


        }

    }
}