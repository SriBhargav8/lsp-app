package com.LegalSuvidha.legalsuvidhaproviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.LegalSuvidha.legalsuvidhaproviders.config.AppConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GstValidatorActivity extends AppCompatActivity {
    private static final String TAG = "GstValidator";
    private static final String GST_VALIDATION_URL = "https://api.example.com/gst/validate"; // Replace with actual API endpoint

    private EditText gstNumberInput;
    private Button validateButton;
    private TextView resultText;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;
    private AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst_validator);

        appConfig = AppConfig.getInstance(this);
        requestQueue = Volley.newRequestQueue(this);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        gstNumberInput = findViewById(R.id.gst_number_input);
        validateButton = findViewById(R.id.validate_button);
        resultText = findViewById(R.id.result_text);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setupListeners() {
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateGstNumber();
            }
        });
    }

    private void validateGstNumber() {
        String gstNumber = gstNumberInput.getText().toString().trim();
        
        if (!isValidGstFormat(gstNumber)) {
            showError("Please enter a valid GST number");
            return;
        }

        showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GST_VALIDATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgress();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            handleValidationResponse(jsonResponse);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing response", e);
                            showError("Error processing response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgress();
                        Log.e(TAG, "Network error", error);
                        showError("Network error. Please try again.");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("gst_number", gstNumber);
                params.put("api_key", appConfig.getGstApiKey());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private boolean isValidGstFormat(String gstNumber) {
        // Basic GST number format validation
        return gstNumber != null && gstNumber.matches("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$");
    }

    private void handleValidationResponse(JSONObject response) {
        try {
            boolean isValid = response.getBoolean("is_valid");
            String message = response.getString("message");
            
            if (isValid) {
                showSuccess(message);
            } else {
                showError(message);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing validation response", e);
            showError("Error processing validation result");
        }
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        validateButton.setEnabled(false);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        validateButton.setEnabled(true);
    }

    private void showSuccess(String message) {
        resultText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        resultText.setText(message);
    }

    private void showError(String message) {
        resultText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        resultText.setText(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
} 