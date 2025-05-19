package com.LegalSuvidha.legalsuvidhaproviders.GstReturn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.LegalSuvidha.legalsuvidhaproviders.GstRegistration.GstRegistrationFragment;
import com.LegalSuvidha.legalsuvidhaproviders.R;


public class TypesOfGstReturnFragment extends Fragment implements View.OnClickListener{

    CardView cardViewNilReturn,cardviewAbove50,cardviewUpto50,cardviewECommerce;
    String typeOfRegistration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_types_of_gst_return, container, false);

        cardViewNilReturn=root.findViewById(R.id.cardViewNilReturn);
        cardviewAbove50=root.findViewById(R.id.cardviewTransactionsAboveB2B50);
        cardviewUpto50=root.findViewById(R.id.cardviewTransactionsUptoB2B50);
        cardviewECommerce=root.findViewById(R.id.cardviewECommerce);

        cardViewNilReturn.setOnClickListener(this);
        cardviewAbove50.setOnClickListener(this);
        cardviewUpto50.setOnClickListener(this);
        cardviewECommerce.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cardViewNilReturn) {
            typeOfRegistration = "Nil return (yearly package)";
//            Log.i("TypeOfRegistration", "TypeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            GstReturnRegistrationFragment gstReturnFragment= new GstReturnRegistrationFragment();
            gstReturnFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,gstReturnFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        } else if (v.getId() == R.id.cardviewTransactionsUptoB2B50) {
            typeOfRegistration = "Transactions upto 50 B2B per month (yearly package)";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            GstReturnRegistrationFragment gstReturnFragment= new GstReturnRegistrationFragment();
            gstReturnFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,gstReturnFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        } else if (v.getId() == R.id.cardviewTransactionsAboveB2B50) {
            typeOfRegistration = "Transactions above 50 B2B per month (yearly package)";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            GstReturnRegistrationFragment gstReturnFragment= new GstReturnRegistrationFragment();
            gstReturnFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,gstReturnFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else if (v.getId() == R.id.cardviewECommerce) {
            typeOfRegistration = "E-commerce (monthly package)";
//            Log.i("typeOfRegistration", "typeOfRegistration " + typeOfRegistration);
//            Intent intent = new Intent(getContext(), GstRegistrationActivity.class);
//            intent.putExtra("typeOfRegistration", typeOfRegistration);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("typeOfRegistration",typeOfRegistration);

            GstReturnRegistrationFragment gstReturnFragment= new GstReturnRegistrationFragment();
            gstReturnFragment.setArguments(bundle);

            getFragmentManager().beginTransaction().add(R.id.fragment_container,gstReturnFragment).addToBackStack("types").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }

    }
}