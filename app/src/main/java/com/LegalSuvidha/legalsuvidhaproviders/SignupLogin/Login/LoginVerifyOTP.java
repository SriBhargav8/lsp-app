package com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.SignupLogin.StartActivity;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class LoginVerifyOTP extends AppCompatActivity {
    private static final String TAG = "LoginVerifyOTP";
    private PinView pinView;
    private String systemOtp;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button verify;
    private TextView textViewNumber;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String fullName;
    private String emailID;
    private String username;
    private String newPassword;
    private String gender;
    private String dob;
    private String phoneNumber;
    private boolean isVerificationInProgress = false;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private boolean doubleBackToExitPressedOnce = false;
    private Handler doubleBackHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_otp_verificatio);

        if (savedInstanceState != null) {
            systemOtp = savedInstanceState.getString("systemOtp");
        }

        initializeViews();
        getIntentData();
        setupPhoneVerification();
    }

    private void initializeViews() {
        pinView = findViewById(R.id.pinView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        verify = findViewById(R.id.verifyOtp);
        mAuth = FirebaseAuth.getInstance();
        textViewNumber = findViewById(R.id.textViewNumber);
    }

    private void getIntentData() {
        phoneNumber = getIntent().getStringExtra("PhoneNumberLogin");
        fullName = getIntent().getStringExtra("Fullname");
        emailID = getIntent().getStringExtra("EmailID");
        username = getIntent().getStringExtra("Username");
        newPassword = getIntent().getStringExtra("Password");
        gender = getIntent().getStringExtra("Gender");
        dob = getIntent().getStringExtra("DOB");
    }

    private void setupPhoneVerification() {
        if (phoneNumber != null) {
            textViewNumber.setText("Enter One Time Password sent on \n   " + phoneNumber);
            if (!isVerificationInProgress) {
                sendVerificationCodeToUser(phoneNumber);
            }
        } else {
            Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("systemOtp", systemOtp);
    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        isVerificationInProgress = true;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
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
                        SignInUsingCredential(phoneAuthCredential);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    isVerificationInProgress = false;
                    hideProgressBar();
                    Log.e(TAG, "Verification failed", e);
                    Toast.makeText(LoginVerifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    isVerificationInProgress = false;
                    systemOtp = s;
                    resendToken = forceResendingToken;
                }
            };

    private void verifyCode(String codeByUser) {
        if (systemOtp == null) {
            Toast.makeText(this, "Verification code expired. Please try again.", Toast.LENGTH_SHORT).show();
            recreate();
            return;
        }

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(systemOtp, codeByUser);
            SignInUsingCredential(credential);
        } catch (Exception e) {
            Log.e(TAG, "Error verifying code", e);
            hideProgressBar();
            Toast.makeText(this, "Verification Code is wrong, try again", Toast.LENGTH_SHORT).show();
            recreate();
        }
    }

    private void SignInUsingCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = task.getResult().getUser();
                        if (user != null) {
                            long creationTimestamp = user.getMetadata().getCreationTimestamp();
                            long lastSignInTimestamp = user.getMetadata().getLastSignInTimestamp();
                            
                            if (creationTimestamp == lastSignInTimestamp) {
                                Log.i(TAG, "User does not exist");
                                hideProgressBar();
                                Toast.makeText(LoginVerifyOTP.this, "User does not exist", Toast.LENGTH_LONG).show();
                                user.delete()
                                        .addOnCompleteListener(deleteTask -> {
                                            if (deleteTask.isSuccessful()) {
                                                Log.d(TAG, "User account deleted.");
                                            }
                                        });
                            } else {
                                hideProgressBar();
                                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                    } else {
                        hideProgressBar();
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(LoginVerifyOTP.this, "The OTP entered was invalid", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginVerifyOTP.this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void verifyOtp(View view) {
        String code = pinView.getText().toString();
        if (code.isEmpty()) {
            Toast.makeText(this, "OTP cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressBar();
        verifyCode(code);
    }

    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideProgressBar() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgressBar();
        if (doubleBackHandler != null) {
            doubleBackHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            return;
        }

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirm Exit")
                .setMessage("All Information will be lost! Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> LoginVerifyOTP.super.onBackPressed())
                .setNegativeButton("No", null)
                .show();
    }
}