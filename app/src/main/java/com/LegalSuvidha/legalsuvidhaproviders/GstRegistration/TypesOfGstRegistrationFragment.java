package com.LegalSuvidha.legalsuvidhaproviders.GstRegistration;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LegalSuvidha.legalsuvidhaproviders.R;


public class TypesOfGstRegistrationFragment extends Fragment implements View.OnClickListener{

    CardView cardViewSoleProprietor,cardviewLLPPartnershipFirm,cardviewCompany;
    String typeOfRegistration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_types_of_gst_registration, container, false);

        cardViewSoleProprietor=root.findViewById(R.id.cardViewSoleProprietor);
        cardviewLLPPartnershipFirm=root.findViewById(R.id.cardviewLLPPartnershipFirm);
        cardviewCompany=root.findViewById(R.id.cardviewCompany);

        cardViewSoleProprietor.setOnClickListener(this);
        cardviewLLPPartnershipFirm.setOnClickListener(this);
        cardviewCompany.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cardViewSoleProprietor) {
            typeOfRegistration = "Sole Proprietor";
//            Log.i("TypeOfRegistration", "TypeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            GstRegistrationFragment gstRegistrationFragment= new GstRegistrationFragment();
            gstRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,gstRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        } else if (v.getId() == R.id.cardviewLLPPartnershipFirm) {
            typeOfRegistration = "LLP/Partnership Firm";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            GstRegistrationFragment gstRegistrationFragment= new GstRegistrationFragment();
            gstRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,gstRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        } else if (v.getId() == R.id.cardviewCompany) {
            typeOfRegistration = "Company";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            GstRegistrationFragment gstRegistrationFragment= new GstRegistrationFragment();
            gstRegistrationFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,gstRegistrationFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }
    }
}