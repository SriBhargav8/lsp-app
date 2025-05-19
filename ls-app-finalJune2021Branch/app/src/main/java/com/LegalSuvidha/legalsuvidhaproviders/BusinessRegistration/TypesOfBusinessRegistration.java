package com.LegalSuvidha.legalsuvidhaproviders.BusinessRegistration;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LegalSuvidha.legalsuvidhaproviders.R;

public class TypesOfBusinessRegistration extends Fragment implements View.OnClickListener {

    CardView cardViewPvtLtd,cardViewOnePersonCompany,cardViewLimitedLiabilityPartnership,cardViewPublicLimitedCompany,cardViewNGO;
    String typeOfBusiness;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.fragment_types_of_business_registration, container, false);

        cardViewPvtLtd=root.findViewById(R.id.cardViewPvtLtd);
        cardViewOnePersonCompany=root.findViewById(R.id.cardviewOnePersonCompany);
        cardViewLimitedLiabilityPartnership=root.findViewById(R.id.cardviewLimitedLiabilityPartnership);
        cardViewPublicLimitedCompany=root.findViewById(R.id.cardviewPublicLimitedCompany);
        cardViewNGO=root.findViewById(R.id.cardViewSection8NGO);

        cardViewNGO.setOnClickListener(this);
        cardViewPvtLtd.setOnClickListener(this);
        cardViewOnePersonCompany.setOnClickListener(this);
        cardViewLimitedLiabilityPartnership.setOnClickListener(this);
        cardViewPublicLimitedCompany.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.cardViewSection8NGO){
            typeOfBusiness="Section 8 NGO";
//            Log.i("TypeOfBusiness","TypeOfBusiness "+ typeOfBusiness);
//            Intent intent = new Intent(getContext(),BusinessRegistrationFrameActivity.class);
//            intent.putExtra("typeOfBusiness",typeOfBusiness);
//            startActivity(intent);

            BusinessRegistrationFragment businessRegistrationFragment = new BusinessRegistrationFragment();
            Bundle args = new Bundle();
            args.putString(" typeOfBusiness", typeOfBusiness);
            businessRegistrationFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,businessRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if(v.getId()==R.id.cardViewPvtLtd){
            typeOfBusiness="Pvt. Limited Company";
//            Log.i("TypeOfBusiness","TypeOfBusiness "+ typeOfBusiness);
//            Intent intent = new Intent(getContext(), BusinessRegistrationFrameActivity.class);
//            intent.putExtra("typeOfBusiness",typeOfBusiness);
//            startActivity(intent);

            BusinessRegistrationFragment businessRegistrationFragment = new BusinessRegistrationFragment();
            Bundle args = new Bundle();
            args.putString(" typeOfBusiness", typeOfBusiness);
            businessRegistrationFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,businessRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


        }else if(v.getId()==R.id.cardviewLimitedLiabilityPartnership){
            typeOfBusiness="Limited Liability Partnership";

//            Log.i("TypeOfBusiness","TypeOfBusiness "+ typeOfBusiness);
//            Intent intent = new Intent(getContext(),BusinessRegistrationFrameActivity.class);
//            intent.putExtra("typeOfBusiness",typeOfBusiness);
//            startActivity(intent);

            BusinessRegistrationFragment businessRegistrationFragment = new BusinessRegistrationFragment();
            Bundle args = new Bundle();
            args.putString(" typeOfBusiness", typeOfBusiness);
            businessRegistrationFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,businessRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


        }else if(v.getId()==R.id.cardviewPublicLimitedCompany){
            typeOfBusiness="Public Limited Company";
//            Log.i("TypeOfBusiness","TypeOfBusiness "+ typeOfBusiness);
//            Intent intent = new Intent(getContext(),BusinessRegistrationFrameActivity.class);
//            intent.putExtra("typeOfBusiness",typeOfBusiness);
//            startActivity(intent);

            BusinessRegistrationFragment businessRegistrationFragment = new BusinessRegistrationFragment();
            Bundle args = new Bundle();
            args.putString(" typeOfBusiness", typeOfBusiness);
            businessRegistrationFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,businessRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


        }else if(v.getId()==R.id.cardviewOnePersonCompany){
            typeOfBusiness="One Person Company";
//            Log.i("TypeOfBusiness","TypeOfBusiness "+ typeOfBusiness);
//            Intent intent = new Intent(getContext(),BusinessRegistrationFrameActivity.class);
//            intent.putExtra("typeOfBusiness",typeOfBusiness);
//            startActivity(intent);

            BusinessRegistrationFragment businessRegistrationFragment = new BusinessRegistrationFragment();
            Bundle args = new Bundle();
            args.putString(" typeOfBusiness", typeOfBusiness);
            businessRegistrationFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,businessRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }

    }
}