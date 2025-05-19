package com.LegalSuvidha.legalsuvidhaproviders.FileITR;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.LegalSuvidha.legalsuvidhaproviders.BusinessRegistration.ConfirmPhoneNumber;
import com.LegalSuvidha.legalsuvidhaproviders.Payments.PaymentActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileITRRegistrationFragment extends Fragment {
    TextInputLayout nameTV;
    TextInputLayout emailIDTV;
    TextInputLayout phoneNumberTV;
    TextInputLayout TypeTV;
    TextInputLayout cityTV;
    TextInputLayout messageTV;
    MaterialButton nextButton;
    ProgressBar progressBar;
    String bookingDocID;
    int bookingAmount = 0;
    String typeOfRegistration;
//    AutoCompleteTextView autoCompleteTV, autoCompleteTVState;
    AutoCompleteTextView  autoCompleteTVState;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    String uid;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DocumentReference currentUserDocumentReference;

    String serviceType = "File ITR";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_file_itr, container, false);

        nameTV = root.findViewById(R.id.textInputLayoutName);
        emailIDTV = root.findViewById(R.id.textInputLayoutEmailAddress);
        cityTV = root.findViewById(R.id.textInputLayoutCity);
        phoneNumberTV = root.findViewById(R.id.textInputLayoutPhoneNumber);
//        companyNameTV=root.findViewById(R.id.textInputLayoutCompanyName);
        messageTV = root.findViewById(R.id.textInputLayoutmessage);
        nextButton = root.findViewById(R.id.nextButtongoToConfirmPhoneNumber);
        nextButton.setOnClickListener(this::goToConfirmPhoneNumber);
        progressBar = root.findViewById(R.id.progressBar);
//        TypeTV = root.findViewById(R.id.textInputLayoutType);
//        autoCompleteTV = root.findViewById(R.id.autocompleteTextView);
        autoCompleteTVState = root.findViewById(R.id.autocompleteTextViewState);

        phoneNumberTV.getEditText().setOnClickListener(this::changePhoneNumber);

        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.india_states));
        autoCompleteTVState.setAdapter(adapterState);

//        ArrayList<String> typeList = new ArrayList<>();
//        typeList.add("Salaried");
//        typeList.add("Business");
//        typeList.add("Director-Designated Partner");

        typeOfRegistration = getArguments().getString("typeOfRegistration");

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, typeList);
//        autoCompleteTV.setAdapter(adapter);

        TextView businessTypeTV = root.findViewById(R.id.textViewBusinessType);
        TextView businessDescTV = root.findViewById(R.id.textViewBusinessDescription);
        TextView title_on_appbar = root.findViewById(R.id.title_on_appbar);

        businessTypeTV.setText("ITR Registration- "+typeOfRegistration);
        businessDescTV.setText((R.string.ITR_Registration));

        title_on_appbar.setText("ITR Registration");



        user = mAuth.getCurrentUser();
        uid = user.getUid();

        currentUserDocumentReference = db.collection("Users").document(uid);

        currentUserDocumentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            nameTV.getEditText().setText(documentSnapshot.getString("Full Name"));
                            emailIDTV.getEditText().setText(documentSnapshot.getString("Email ID"));
                            phoneNumberTV.getEditText().setText(documentSnapshot.getString("Phone Number"));


                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Couldn't load user Info", Toast.LENGTH_SHORT).show();
                    }
                });

        if (typeOfRegistration == "Basic Plan") {
            bookingAmount = 499;
//
//
        }else if (typeOfRegistration == "Advanced Plan") {

            bookingAmount = 999;
//            TextView businessTypeTV=root.findViewById(R.id.textViewBusinessType);
//            TextView businessDescTV=root.findViewById(R.id.textViewBusinessDescription);
//            TextView title_on_appbar=root.findViewById(R.id.title_on_appbar);
//
//            businessTypeTV.setText("SGST");
//            businessDescTV.setText((R.string.GST_Registration));
//            title_on_appbar.setText("SGST");
//
        }
        else if (typeOfRegistration == "Professional/Freelancer") {

            bookingAmount = 999;
//            TextView businessTypeTV=root.findViewById(R.id.textViewBusinessType);
//            TextView businessDescTV=root.findViewById(R.id.textViewBusinessDescription);
//            TextView title_on_appbar=root.findViewById(R.id.title_on_appbar);
//
//            businessTypeTV.setText("IGST");
//            businessDescTV.setText((R.string.GST_Registration));
//            title_on_appbar.setText("IGST");
//
        }else if (typeOfRegistration == "Business Plan: No Balance Sheet") {

            bookingAmount =  1499;
//            TextView businessTypeTV=root.findViewById(R.id.textViewBusinessType);
//            TextView businessDescTV=root.findViewById(R.id.textViewBusinessDescription);
//            TextView title_on_appbar=root.findViewById(R.id.title_on_appbar);
//
//            businessTypeTV.setText("UGST");
//            businessDescTV.setText((R.string.GST_Registration));
//            title_on_appbar.setText("UGST");
//
        }else if (typeOfRegistration == "Business Plan: Balance Sheet") {

            bookingAmount = 1999;

//            TextView businessTypeTV=root.findViewById(R.id.textViewBusinessType);
//            TextView businessDescTV=root.findViewById(R.id.textViewBusinessDescription);
//            TextView title_on_appbar=root.findViewById(R.id.title_on_appbar);
//
//            businessTypeTV.setText("UGST");
//            businessDescTV.setText((R.string.GST_Registration));
//            title_on_appbar.setText("UGST");
//
        }else if (typeOfRegistration == "Directors of Company or Designated Partner of LLP") {

            bookingAmount = 1250;

//            TextView businessTypeTV=root.findViewById(R.id.textViewBusinessType);
//            TextView businessDescTV=root.findViewById(R.id.textViewBusinessDescription);
//            TextView title_on_appbar=root.findViewById(R.id.title_on_appbar);
//
//            businessTypeTV.setText("UGST");
//            businessDescTV.setText((R.string.GST_Registration));
//            title_on_appbar.setText("UGST");
//
        }
        else if (typeOfRegistration == "Capital Gain Plan") {

            bookingAmount = 1999;

        } else if (typeOfRegistration == "Capital Gain &amp; Business OR Profession") {

            bookingAmount = 2499;

        } else if (typeOfRegistration == "NRI Plan: Having House Property or Interest Income") {

            bookingAmount = 1999;

        } else if (typeOfRegistration == "Resident having Foreign Income") {

            bookingAmount = 3999;

        } else if (typeOfRegistration == "Future & Option Plan Without Audit") {

            bookingAmount = 2999;

        } else if (typeOfRegistration == "Future and Options Plan (With Audit)") {

            bookingAmount = 10000;
        }

        Chip chip=root.findViewById(R.id.chipBookingAmount);
        chip.setText(String.valueOf(bookingAmount));

        return root;
    }

    private void changePhoneNumber(View view) {
        Toast.makeText(getContext(), "Phone Number cannot be changed", Toast.LENGTH_SHORT).show();
    }

    private boolean validateFullName() {
        String val = nameTV.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            nameTV.setError("Field cannot be empty");
            return false;
        } else {
            nameTV.setError(null);
            nameTV.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = emailIDTV.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            emailIDTV.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            emailIDTV.setError("Invalid Email");
            return false;
        } else {
            emailIDTV.setError(null);
            emailIDTV.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatePhoneNumber() {
        String val = phoneNumberTV.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumberTV.setError("Phone Number cannot be empty");
            return false;
        } else {
            phoneNumberTV.setError(null);
            phoneNumberTV.setErrorEnabled(false);
            return true;
        }
    }

//    public boolean validateType() {
//        String val = autoCompleteTV.getText().toString().trim();
//
//        if (val.isEmpty()) {
//            TypeTV.setError("Type cannot be empty");
//            return false;
//        } else {
//            TypeTV.setError(null);
//            TypeTV.setErrorEnabled(false);
//            return true;
//        }
//    }

    public boolean validateCity() {
        String val = cityTV.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            cityTV.setError("Phone Number cannot be empty");
            return false;
        } else {
            cityTV.setError(null);
            cityTV.setErrorEnabled(false);
            return true;
        }
    }

    public void goToConfirmPhoneNumber(View view) {

        if (!validateFullName() | !validateEmail() | !validatePhoneNumber() ) {
            return;
        }

        String name = nameTV.getEditText().getText().toString().trim();
//        String type = autoCompleteTV.getText().toString().trim();
        String emailID = emailIDTV.getEditText().getText().toString().trim();
        String city = cityTV.getEditText().getText().toString().trim();
        String phoneNumber = phoneNumberTV.getEditText().getText().toString().trim();
        String message = messageTV.getEditText().getText().toString().trim();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

//        if(user != null){

        String uid = user.getUid();
        showProgressBar();

        DocumentReference user_booking_doc = db.collection("Users").document(uid).collection("Booking List").document();

        Map<String, Object> profile = new HashMap<>();
        profile.put("Full Name", name);
        profile.put("Company Name", " ");
        profile.put("Email ID", emailID);
        profile.put("Phone Number", phoneNumber);
        //profile.put("BusinessType",businessType);
        profile.put("State", city);
        profile.put("message", message);
        profile.put("PaymentStatus", "Unpaid");
        profile.put("ItemTotal", bookingAmount);
        profile.put("ServiceType", serviceType + ": " + typeOfRegistration);
        Calendar calendar = Calendar.getInstance();
        String dateStr = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String timeStr = sdf.format(calendar.getTime());
        profile.put("TimestampBooking", dateStr+" "+timeStr);


        user_booking_doc.set(profile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideProgressBar();

                        bookingDocID = user_booking_doc.getId();
                        Intent intent = new Intent(getContext(), PaymentActivity.class);
                        intent.putExtra("bookingDocID", bookingDocID);
//                            intent.putExtra("bookingAmount",bookingAmount);
//                            intent.putExtra("BusinessType", type);
                        startActivity(intent);

//                            String url ="https://script.google.com/macros/s/AKfycbzQHLP9heK-uOzGIOAPiebOlmomwMw_D8CCCjdnN9qFijamW5XvY35YJZ9mnDIYYRGX/exec";
////                            String url ="https://script.google.com/macros/s/AKfycbxDmpX8Lc-xCnhIPwjHjiXDLpRoaAZdHJBNU3C7iC5BSHv30dayW7Yjp2K5X0FrpJoY/exec";
//
//                            StringRequest stringRequest=new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
////                        TempDialog.dismiss();
//                                    hideProgressBar();
//                                    Toast.makeText(getContext(),"Booking Successful! \n We will contact you soon.",Toast.LENGTH_SHORT).show();
//
//                                    Intent intent= new Intent(getContext(), HomeScreen.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//                                }
//
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
//                                    hideProgressBar();
//                                    Log.e("statuscode", error.toString());
//                                }
//                            }
//                            ) {
//                                @Nullable
//                                @Override
//                                protected Map<String, String> getParams() throws AuthFailureError {
//
//                                    Map<String,String> params = new HashMap<>();
//                                    params.put("action","addItem");
//                                    params.put("name",name);
//                                    params.put("company","companyName");
//                                    params.put("email",emailID);
//                                    params.put("phone",phoneNumber);
//                                    params.put("type",type);
//                                    params.put("city",city);
//                                    params.put("message",message);
//                                    params.put("serviceType",serviceType);
//
//                                    return params;
//                                }
//
//                            };
//
//                            int socketTimeOut=500000;
//
//                            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//                            stringRequest.setRetryPolicy(retryPolicy);
//
//                            RequestQueue queue= Volley.newRequestQueue(getContext());
//                            queue.add(stringRequest);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Something went wrong /n Try again", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), HomeScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });

//        } else {
//            Bundle bundle = new Bundle();
//            bundle.putString("message", message);
//            bundle.putString("FullName", name);
//            bundle.putString("EmailID", emailID);
//            bundle.putString("City", city);
//            bundle.putString("PhoneNumber", phoneNumber);
//            bundle.putString("CompanyName", "");
//            bundle.putString("RegistrationType", type);
//            bundle.putString("ServiceType",serviceType);
//
//            ConfirmPhoneNumber confirmPhoneNumber = new ConfirmPhoneNumber();
//            confirmPhoneNumber.setArguments(bundle);
//
//            assert getFragmentManager() != null;
//            getFragmentManager().beginTransaction().replace(R.id.fragment_container, confirmPhoneNumber).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
//
//        }
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void hideProgressBar() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }



}
