package com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.SignUpp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OtpVerificationFragmentSignUp extends Fragment {
    private static final String TAG = "OtpVerificationFragment";
    private PinView pinView;
    private String systemOtp;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button verifyOtpBttn;
    private TextView textViewNumber;
    private String name, phoneNumber, emailID, gender;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean isVerificationInProgress = false;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            systemOtp = savedInstanceState.getString("systemOtp");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("systemOtp", systemOtp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_otp_verificatio, container, false);

        mAuth = FirebaseAuth.getInstance();

        pinView = root.findViewById(R.id.pinView);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        verifyOtpBttn = root.findViewById(R.id.verifyOtp);
        verifyOtpBttn.setOnClickListener(this::verifyOtp);
        textViewNumber = root.findViewById(R.id.textViewNumber);

        if (getArguments() != null) {
            name = getArguments().getString("FullName");
            phoneNumber = getArguments().getString("PhoneNumber");
            emailID = getArguments().getString("EmailID");
            gender = getArguments().getString("Gender");
        }

        if (phoneNumber != null) {
            textViewNumber.setText("Enter One Time Password sent on \n   " + phoneNumber);
            if (!isVerificationInProgress) {
                sendVerificationCodeToUser(phoneNumber);
            }
        } else {
            Toast.makeText(getContext(), "Phone number is required", Toast.LENGTH_SHORT).show();
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        }
        return root;
    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        if (getActivity() == null) return;
        
        isVerificationInProgress = true;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(getActivity())
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    isVerificationInProgress = false;
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinView.setText(code);
                        showProgressBar();
                        verifyCode(code);
                    } else {
                        try {
                            SignInUsingCredential(phoneAuthCredential);
                        } catch (Exception e) {
                            Log.e(TAG, "Error in verification completion", e);
                            hideProgressBar();
                            Toast.makeText(getContext(), "Something went wrong. Please try Again", Toast.LENGTH_SHORT).show();
                            if (getActivity() != null) {
                                getActivity().recreate();
                            }
                        }
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    isVerificationInProgress = false;
                    hideProgressBar();
                    Log.e(TAG, "Verification failed", e);
                    try {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception exp) {
                        Log.e(TAG, "Error showing toast", exp);
                    }
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    isVerificationInProgress = false;
                    try {
                        systemOtp = s;
                        resendToken = forceResendingToken;
                    } catch (Exception e) {
                        Log.e(TAG, "Error in code sent", e);
                        if (s == null) {
                            Toast.makeText(getContext(), "Something went wrong. Please try Again", Toast.LENGTH_SHORT).show();
                            if (getActivity() != null) {
                                getActivity().recreate();
                            }
                        }
                    }
                }
            };

    private void verifyCode(String codeByUser) {
        if (systemOtp == null) {
            Toast.makeText(getContext(), "Verification code expired. Please try again.", Toast.LENGTH_SHORT).show();
            if (getActivity() != null) {
                getActivity().recreate();
            }
            return;
        }

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(systemOtp, codeByUser);
            SignInUsingCredential(credential);
        } catch (Exception e) {
            Log.e(TAG, "Error verifying code", e);
            hideProgressBar();
            Toast.makeText(getContext(), "Verification Code is wrong, try again", Toast.LENGTH_SHORT).show();
            if (getActivity() != null) {
                getActivity().recreate();
            }
        }
    }

    private void SignInUsingCredential(PhoneAuthCredential credential) {
        if (getActivity() == null) return;

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = task.getResult().getUser();
                        if (user != null) {
                            String uid = user.getUid();
                            DocumentReference user_profileReference = db.collection("Users").document(uid);

                            Map<String, Object> profile = new HashMap<>();
                            profile.put("Full Name", name);
                            profile.put("Email ID", emailID);
                            profile.put("Phone Number", phoneNumber);
                            profile.put("Coins", 0);
                            profile.put("Gender", gender);

                            user_profileReference.set(profile)
                                    .addOnSuccessListener(aVoid -> {
                                        hideProgressBar();
                                        Intent intent = new Intent(getContext(), HomeScreen.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        hideProgressBar();
                                        Log.e(TAG, "Error saving user profile", e);
                                        Toast.makeText(getContext(), "Error saving profile. Please try again.", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        hideProgressBar();
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(getContext(), "The OTP entered was invalid", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void verifyOtp(View view) {
        String code = pinView.getText().toString();
        if (code.isEmpty()) {
            Toast.makeText(getContext(), "OTP cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressBar();
        verifyCode(code);
    }

    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
        }
        if (getActivity() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressBar();
    }
}