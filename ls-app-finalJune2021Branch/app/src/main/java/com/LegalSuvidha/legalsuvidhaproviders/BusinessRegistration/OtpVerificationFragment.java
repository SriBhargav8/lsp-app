package com.LegalSuvidha.legalsuvidhaproviders.BusinessRegistration;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.api.services.sheets.v4.Sheets;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OtpVerificationFragment extends Fragment {
    PinView pinView;
    String systemOtp;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button verifyOtpBttn;
    TextView textViewNumber;
    String name,companyName,message,phoneNumber,emailID,city,businessType,seviceType;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();

//    private Sheets service;
//    String title="LegalSuvidhaProviders";
//    String spreadsheetId="1AbbkqS4RPardGBVCAfujOHxaggib-NThwochQpslApc";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.fragment_otp_verificatio, container, false);

        mAuth=FirebaseAuth.getInstance();

        pinView=root.findViewById(R.id.pinView);
        progressBar=root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        verifyOtpBttn=root.findViewById(R.id.verifyOtp);
        verifyOtpBttn.setOnClickListener(this::verifyOtp);
        textViewNumber=root.findViewById(R.id.textViewNumber);

        assert getArguments() != null;
        message = getArguments().getString("message");
        name = getArguments().getString("FullName");
        companyName = getArguments().getString("CompanyName");
        city = getArguments().getString("City");
        phoneNumber = getArguments().getString("PhoneNumber");
        emailID = getArguments().getString("EmailID");
        businessType = getArguments().getString("BusinessType");
        seviceType=getArguments().getString("ServiceType");

        textViewNumber.setText("Enter One Time Password sent on \n   "+phoneNumber);

        sendVerificationCodeToUser(phoneNumber);
        return root;
    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code=phoneAuthCredential.getSmsCode();
                    if(code!=null) {
                        pinView.setText(code);
                        showProgressBar();
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    systemOtp=s;
                }
            };

    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(systemOtp,codeByUser);
        SignInUsingCredential(credential);
    }

    private void SignInUsingCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Otp", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            assert user != null;
                            String uid = user.getUid();
                            DocumentReference user_profileReference = db.collection("Users").document(uid).collection(seviceType).document().collection(businessType).document();

                            Map<String,Object> profile = new HashMap<>();
                            profile.put("Full Name",name);
                            profile.put("Company Name",companyName);
                            profile.put("Email ID",emailID);
                            profile.put("Phone Number",phoneNumber);
                            profile.put("BusinessType",businessType);
                            profile.put("city",city);
                            profile.put("message",message);
                            profile.put("ServiceType",seviceType);
                            String timeStamp = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss'Z'").format(new Timestamp(System.currentTimeMillis()));
                            profile.put("Timestamp",timeStamp);


                            user_profileReference.set(profile)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            addItemToSheet();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(),"Something went wrong /n Try again",Toast.LENGTH_SHORT).show();
                                            Intent intent= new Intent(getContext(),HomeScreen.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    });


                        } else {
                            Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            // Sign in failed, display a message and update the UI
                            Log.w("Otp", "signInWithCredential:failure", task.getException());


                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getContext(), "The Otp entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void addItemToSheet() {

//        String url ="https://script.google.com/macros/s/AKfycbxXMjEUgFxNvwmfFKI1lIedz19iJv3CW-VvHFwstEnYuSVttv8CZI1LQWFWv5bvKd_z/exec";

        String url ="https://script.google.com/macros/s/AKfycbz-n07Ti0l2xEsttVuWzfeNDCfgwSUqdVDKJrEqtInQ03t1dmMUAYDRJnNFwA0E-ce_/exec";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                        TempDialog.dismiss();
                hideProgressBar();
                Toast.makeText(getContext(),"Booking Successful",Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(getContext(), HomeScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                hideProgressBar();
                Log.e("statuscode", error.toString());
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("action","addItem");
                params.put("name",name);
                params.put("company",companyName);
                params.put("email",emailID);
                params.put("phone",phoneNumber);
                params.put("type",businessType);
                params.put("city",city);
                params.put("message",message);
                params.put("serviceType",seviceType);

                return params;
            }

        };

        int socketTimeOut=500000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(stringRequest);

    }

    public void verifyOtp(View view){
        String code=pinView.getText().toString();

        if(code.isEmpty()){
            Toast.makeText(getContext(), "Otp cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!code.isEmpty()){
           showProgressBar();
            verifyCode(code);
        }

    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void hideProgressBar(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

}