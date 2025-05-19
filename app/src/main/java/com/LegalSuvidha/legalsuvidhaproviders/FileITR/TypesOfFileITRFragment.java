package com.LegalSuvidha.legalsuvidhaproviders.FileITR;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.LegalSuvidha.legalsuvidhaproviders.GstRegistration.GstRegistrationFragment;
import com.LegalSuvidha.legalsuvidhaproviders.R;


public class TypesOfFileITRFragment extends Fragment implements View.OnClickListener{

    CardView cardViewBasicPlan,cardviewAdvancvedPlan,cardviewProfessionalFreelancer,cardviewBusinessNoPlanBalanceSheet,cardviewBusinessPlanBalanceSheet,cardviewDirector,cardviewCapitalGainPlan,cardViewCapitalGainBusinessProfession,cardviewNRIPlan,cardviewResidentForeignIncome,cardviewFutureOptionPlanWithoutAudit,cardViewFutureOptionsPlanWithAudit;
    String typeOfRegistration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_types_of_itr_registration, container, false);

        cardViewBasicPlan=root.findViewById(R.id.cardViewBasicPlan);
        cardviewAdvancvedPlan=root.findViewById(R.id.cardviewAdvancvedPlan);
        cardviewProfessionalFreelancer=root.findViewById(R.id.cardviewProfessionalFreelancer);
        cardviewBusinessNoPlanBalanceSheet=root.findViewById(R.id.cardviewBusinessNoPlanBalanceSheet);
        cardviewDirector=root.findViewById(R.id.cardviewDirector);
        cardviewBusinessPlanBalanceSheet=root.findViewById(R.id.cardviewBusinessPlanBalanceSheet);
        cardviewCapitalGainPlan=root.findViewById(R.id.cardviewCapitalGainPlan);
        cardViewCapitalGainBusinessProfession=root.findViewById(R.id.cardViewCapitalGainBusinessProfession);
        cardviewNRIPlan=root.findViewById(R.id.cardviewNRIPlan);
        cardviewResidentForeignIncome=root.findViewById(R.id.cardviewResidentForeignIncome);
        cardviewFutureOptionPlanWithoutAudit=root.findViewById(R.id.cardviewFutureOptionPlanWithoutAudit);
        cardViewFutureOptionsPlanWithAudit=root.findViewById(R.id.cardViewFutureOptionsPlanWithAudit);

        cardviewNRIPlan.setVisibility(View.GONE);
        cardviewResidentForeignIncome.setVisibility(View.GONE);
        cardviewFutureOptionPlanWithoutAudit.setVisibility(View.GONE);
        cardViewFutureOptionsPlanWithAudit.setVisibility(View.GONE);

        cardViewBasicPlan.setOnClickListener(this);
        cardviewAdvancvedPlan.setOnClickListener(this);
        cardviewProfessionalFreelancer.setOnClickListener(this);
        cardviewBusinessNoPlanBalanceSheet.setOnClickListener(this);
        cardviewDirector.setOnClickListener(this);
        cardviewBusinessPlanBalanceSheet.setOnClickListener(this);
        cardviewCapitalGainPlan.setOnClickListener(this);
        cardViewCapitalGainBusinessProfession.setOnClickListener(this);
//        cardviewNRIPlan.setOnClickListener(this);
//        cardviewResidentForeignIncome.setOnClickListener(this);
//        cardviewFutureOptionPlanWithoutAudit.setOnClickListener(this);
//        cardViewFutureOptionsPlanWithAudit.setOnClickListener(this);


        return root;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cardViewBasicPlan) {
            typeOfRegistration = "Basic Plan";
//            Log.i("TypeOfRegistration", "TypeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        } else if (v.getId() == R.id.cardviewAdvancvedPlan) {
            typeOfRegistration = "Advanced Plan";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        } else if (v.getId() == R.id.cardviewProfessionalFreelancer) {
            typeOfRegistration = "Professional/Freelancer";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            GstRegistrationFragment gstRegistrationFragment= new GstRegistrationFragment();
            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if (v.getId() == R.id.cardviewBusinessNoPlanBalanceSheet) {
            typeOfRegistration = "Business Plan: No Balance Sheet";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if (v.getId() == R.id.cardviewBusinessPlanBalanceSheet) {
            typeOfRegistration = "Business Plan: Balance Sheet";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if (v.getId() == R.id.cardviewDirector) {
            typeOfRegistration = "Directors of Company or Designated Partner of LLP";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if (v.getId() == R.id.cardviewCapitalGainPlan) {
            typeOfRegistration = "Capital Gain Plan";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if (v.getId() == R.id.cardViewCapitalGainBusinessProfession) {
            typeOfRegistration = "Capital Gain & Business OR Profession";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration", typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment = new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container, fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }if (v.getId() == R.id.cardviewNRIPlan) {
            typeOfRegistration = "NRI Plan: Having House Property or Interest Income";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if (v.getId() == R.id.cardviewFutureOptionPlanWithoutAudit) {
            typeOfRegistration = "Future &amp; Option Plan Without Audit";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if (v.getId() == R.id.cardViewFutureOptionsPlanWithAudit) {
            typeOfRegistration = "Future and Options Plan (With Audit)";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if (v.getId() == R.id.cardviewResidentForeignIncome) {
            typeOfRegistration = "Resident having Foreign Income";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            FileITRRegistrationFragment fileITRRegistrationFragment= new FileITRRegistrationFragment();
            fileITRRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,fileITRRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }

    }
}