package com.LegalSuvidha.legalsuvidhaproviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.LegalSuvidha.legalsuvidhaproviders.config.AppConfig;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class RazorpayPaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = "RazorpayPayment";
    private static final int RAZORPAY_PAYMENT_REQUEST_CODE = 100;
    
    private String orderId;
    private String amount;
    private String description;
    private AppConfig appConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        appConfig = AppConfig.getInstance(this);
        
        // Get payment details from intent
        orderId = getIntent().getStringExtra("order_id");
        amount = getIntent().getStringExtra("amount");
        description = getIntent().getStringExtra("description");

        if (orderId == null || amount == null) {
            Toast.makeText(this, "Invalid payment details", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        startPayment();
    }

    private void startPayment() {
        try {
            Checkout checkout = new Checkout();
            checkout.setKeyID(appConfig.getRazorpayKey());

            JSONObject options = new JSONObject();
            options.put("name", "Legal Suvidha");
            options.put("description", description);
            options.put("order_id", orderId);
            options.put("currency", "INR");
            options.put("amount", amount);
            options.put("prefill", new JSONObject()
                    .put("email", "")
                    .put("contact", ""));

            checkout.open(this, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting payment", e);
            Toast.makeText(this, "Error in starting payment", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("payment_id", razorpayPaymentID);
            resultIntent.putExtra("order_id", orderId);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error in payment success", e);
            Toast.makeText(this, "Error in processing payment", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("error_code", code);
            resultIntent.putExtra("error_message", response);
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error in payment failure", e);
            Toast.makeText(this, "Error in processing payment", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up any resources if needed
    }
} 