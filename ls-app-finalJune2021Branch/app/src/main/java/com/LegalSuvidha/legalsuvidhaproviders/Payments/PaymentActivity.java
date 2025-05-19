package com.LegalSuvidha.legalsuvidhaproviders.Payments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.Bookings.BookingActivity;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {


    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseUser user;
    public static String uid ;
    public static DocumentReference currentUserDocumentReference ;
//    private Button payNow, payLater;
    private TextView itemTotalTV,coinDiscountTV,coinsEarnedTV,coinDiscountHeadingTV, serviceBookedTV,paymentTimeTextView,bookingTimeTV,gstTV, discountTV, totalTV,coinTV;
    private final String TAG = " main";
    long itemTotal, gst, discount, totalAmount, coins=0;
    int cashbackCoins;
    CheckBox checkBoxCoins;
    Button payLaterButton,payNowButton;
    LinearLayout paymentLinearLayout,coinLinearLayout,buttonLinearLayout,coinsEarnedLinearLayout,coinDiscountLinearLayout;
    String bookingDocID, bookingType;
    long bookingAmount, coinEarned,coinDiscountUsed;
    DocumentReference user_booking_doc;
    boolean coinsUsed=true,payLater=false;
    String name,companyName,emailID,phoneNumber,serviceType, state,message, paymentStatus, timeOfPayment,timeOfBooking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        currentUserDocumentReference = db.collection("Users").document(uid);

//        payNow = findViewById(R.id.paymentNow);
//        payLater = findViewById(R.id.paylater);
        itemTotalTV = findViewById(R.id.itemTotalTextView);
        gstTV = findViewById(R.id.gstTextView);
        discountTV = findViewById(R.id.discountTextView);
        totalTV = findViewById(R.id.totalTextView);
        coinTV=findViewById(R.id.coinTextView);
        checkBoxCoins=findViewById(R.id.coinCheckBox);
        serviceBookedTV=findViewById(R.id.serviceBookedTextView);
        paymentTimeTextView=findViewById(R.id.paymentTimeTextView);
        bookingTimeTV=findViewById(R.id.bookingTimeTV);
        coinDiscountTV=findViewById(R.id.coinDiscountTV);
        paymentLinearLayout=findViewById(R.id.paymentLinearLayout);
        coinLinearLayout=findViewById(R.id.coinLinearLayout);
        buttonLinearLayout=findViewById(R.id.buttonLinearLayout);
        coinDiscountHeadingTV=findViewById(R.id.coinDiscountHeadingTV);
        coinsEarnedLinearLayout=findViewById(R.id.coinsEarnedLinearLayout);
        coinDiscountLinearLayout=findViewById(R.id.coinDiscountLinearLayout);
        coinsEarnedTV=findViewById(R.id.coinsEarnedTV);

        bookingDocID = getIntent().getStringExtra("bookingDocID");
//        bookingAmount = getIntent().getIntExtra("bookingAmount", 0);
//        bookingType = getIntent().getStringExtra("BusinessType");


        user_booking_doc = currentUserDocumentReference.collection("Booking List").document(bookingDocID);

        user_booking_doc.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        bookingAmount= (long) documentSnapshot.get("ItemTotal");

                        name = (String) documentSnapshot.get("Full Name");
                        companyName =(String) documentSnapshot.get("Company Name");
                        emailID = (String) documentSnapshot.get("Email ID");
                        phoneNumber = (String) documentSnapshot.get("Phone Number");

//                            businessType = (String) documentSnapshot.get("BusinessType");
                        serviceType = (String) documentSnapshot.get("ServiceType");
                        serviceBookedTV.setText(serviceType);

                        state= (String) documentSnapshot.get("State");
                        message= (String) documentSnapshot.get("message");

                        timeOfBooking= (String) documentSnapshot.get("TimestampBooking");
                        bookingTimeTV.setText(timeOfBooking);
                        paymentStatus= (String) documentSnapshot.get("PaymentStatus");

                        itemTotal = bookingAmount;
                        itemTotalTV.setText(String.valueOf(itemTotal));

                        gst = (18 * itemTotal) / 100;
                        gstTV.setText(String.valueOf(gst));

                        discount = 0;
                        discountTV.setText("-"+String.valueOf(discount));

                        if(!(paymentStatus.equals("Payment Successful"))){

                            currentUserDocumentReference.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            DocumentSnapshot documentSnapshot=task.getResult();

                                            if (documentSnapshot.exists()) {
                                                coins = Integer.parseInt(documentSnapshot.get("Coins").toString());

                                                if(coins>itemTotal + gst - discount){
                                                    coins=itemTotal + gst - discount;
                                                }

                                                coinTV.setText("-"+String.valueOf(coins));
                                                checkBoxCoins.setChecked(true);

                                            }
                                        }
                                    });
                        }

                        if(paymentStatus.equals("Payment Successful")){

                            buttonLinearLayout.setVisibility(View.GONE);
                            coinLinearLayout.setVisibility(View.GONE);
                            coinDiscountLinearLayout.setVisibility(View.VISIBLE);

                            coinsUsed= (boolean) documentSnapshot.get("CoinsUsed");

//                            if(coinsUsed) {
                                coinDiscountUsed = (long) documentSnapshot.get("CoinDiscountUsed");
                                coinDiscountTV.setText("-" + String.valueOf(coinDiscountUsed));
//                            }


                            timeOfPayment= (String) documentSnapshot.get("TimestampPayment");
                            paymentTimeTextView.setText(timeOfPayment);
                            paymentLinearLayout.setVisibility(View.VISIBLE);

                            coinsEarnedLinearLayout.setVisibility(View.VISIBLE);
                            coinEarned=(long) documentSnapshot.get("CoinEarned");
                            coinsEarnedTV.setText(String.valueOf(coinEarned));



                        }else if (paymentStatus.equals("Pay Later")){

                            Button payLaterButton=findViewById(R.id.payLater);
                            payLaterButton.setVisibility(View.GONE);
                        }

                        if(paymentStatus.equals("Payment Successful")){

                            totalAmount = itemTotal + gst - discount - coinDiscountUsed;
                            totalTV.setText(String.valueOf(totalAmount));


                        }else{

                            totalAmount = itemTotal + gst - discount - coins;
                            totalTV.setText(String.valueOf(totalAmount));

                            cashbackCoins= (int) (totalAmount/10);
                        }

                        if(paymentStatus.equals("Payment Pending")){
                           TextView pendingTV=findViewById(R.id.pendingTextView);
                           pendingTV.setVisibility(View.VISIBLE);

                        }

                    }

                });

        Log.i("totalAmount last", String.valueOf(totalAmount));

        Checkout.preload(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();

//        if(!(paymentStatus.equals("Pay Later")) || !(paymentStatus.equals("Payment Successful"))){

            checkBoxCoins.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecked){
                        if(!(paymentStatus.equals("Payment Successful"))) {
                            coinTV.setText("0");
                            totalAmount = itemTotal + gst - discount;
                            cashbackCoins = (int) (totalAmount / 10);
                            coinsUsed = false;
                            Log.i("totalAmountcheckedfalse", String.valueOf(totalAmount));
                        }
                    }else {
                        if(!(paymentStatus.equals("Payment Successful"))) {
                            coinTV.setText("-" + String.valueOf(coins));
                            totalAmount = itemTotal + gst - discount - coins;
                            cashbackCoins = (int) (totalAmount / 10);
                            coinsUsed = true;
                            Log.i("totalAmountchecked", String.valueOf(totalAmount));
                        }
                    }
                    totalTV.setText(String.valueOf(totalAmount));

                }
            });


//        }
    }

    public void payNow(View v) {
        DocumentReference user_booking_doc = currentUserDocumentReference.collection("Booking List").document(bookingDocID);

        if (paymentStatus.equals("Pay Later")){
           cashbackCoins=0;
        }

        user_booking_doc.update("PaymentStatus", "Payment pending");
        startPayment();

    }

    public void payLater(View v) {
        payLaterMethod();
    }


    public void payLaterMethod() {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure you want to Pay Later?")
                    .setMessage("If you Pay now you will get coins worth Rs" + cashbackCoins)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            payLater = true;
                            Log.i("paylater","true");

                            DocumentReference user_booking_doc = currentUserDocumentReference.collection("Booking List").document(bookingDocID);

                            Log.i("Payment", "Pay Later");
                            user_booking_doc.update("PaymentStatus", "Pay Later")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.i("Payment", "on success db");
                                            String url = "https://script.google.com/macros/s/AKfycbzQHLP9heK-uOzGIOAPiebOlmomwMw_D8CCCjdnN9qFijamW5XvY35YJZ9mnDIYYRGX/exec";

                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
//                              TempDialog.dismiss();
//                                hideProgressBar();
                                                    Log.i("Payment", "on response");
                                                    Toast.makeText(PaymentActivity.this, "Booking Successful! \n We will contact you soon.", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(PaymentActivity.this, BookingActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                }

                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                    Toast.makeText(PaymentActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                                hideProgressBar();
                                                    Log.i("Payment", "on error");
                                                    Log.e("statuscode", error.toString());
                                                }
                                            }
                                            ) {
                                                @Nullable
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> params = new HashMap<>();

                                                    Log.i("Payment", "get params");

                                                    params.put("action", "addItem");
                                                    params.put("name", name);
                                                    params.put("company", companyName);
                                                    params.put("email", emailID);
                                                    params.put("phone", phoneNumber);
                                                    params.put("type", "Pay Later BookingId:"+ bookingDocID);
                                                    params.put("city", state);
                                                    params.put("message", message);
                                                    params.put("serviceType", serviceType);

                                                    return params;
                                                }

                                            };

                                            int socketTimeOut = 500000;

                                            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                                            stringRequest.setRetryPolicy(retryPolicy);

                                            RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
                                            queue.add(stringRequest);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("Payment", "on failure db");
                                            Toast.makeText(PaymentActivity.this, "Something went wrong /n Try again", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(PaymentActivity.this, HomeScreen.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
                                        }
                                    });

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            payLater = false;

                        }
                    })
                    .show();
//            Toast.makeText(PaymentActivity.this, "If you Pay now you will get coins worth Rs"+cashbackCoins, Toast.LENGTH_LONG).show();




//        if (payLater){
//
//
//
//        }
    }


    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout();

//        co.setKeyID("MHLxgQsoshKo6ozAKeiVuWjX");

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Legal Suvidha");

            options.put("description", "Payment for "+ bookingType);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
//            options.put("order_id", bookingDocID);
            options.put("currency", "INR");

            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = totalAmount * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", emailID);
            preFill.put("contact", phoneNumber);
            options.put("prefill", preFill);

//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);

            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    @Override
    public void onPaymentSuccess(String s) {
        // payment successfull pay_DGU19rDsInjcF2
        Log.e(TAG, "Payment Successful"+s);

        if(coinsUsed){
            currentUserDocumentReference.update("Coins",FieldValue.increment(cashbackCoins-coins));
        }else{
            currentUserDocumentReference.update("Coins",FieldValue.increment(cashbackCoins));
        }

        DocumentReference user_booking_doc = currentUserDocumentReference.collection("Booking List").document(bookingDocID);
        Map<String, Object> map = new HashMap<>();
        map.put("PaymentAmount", totalAmount);

        Calendar calendar = Calendar.getInstance();
        String dateStr = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String timeStr = sdf.format(calendar.getTime());
        map.put("TimestampPayment", dateStr+" "+timeStr);

        map.put("CoinsUsed",coinsUsed);
        map.put("CoinEarned",cashbackCoins);

        if(coinsUsed){
            map.put("CoinDiscountUsed",coins);
        }else{
            map.put("CoinDiscountUsed",0);
        }

        map.put("PaymentId",s);

        user_booking_doc.set(map, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        user_booking_doc.update("PaymentStatus", "Payment Successful");


                        String url = "https://script.google.com/macros/s/AKfycbzQHLP9heK-uOzGIOAPiebOlmomwMw_D8CCCjdnN9qFijamW5XvY35YJZ9mnDIYYRGX/exec";

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                              TempDialog.dismiss();
//                                hideProgressBar();
                                Toast.makeText(PaymentActivity.this, "Booking Successful! \n We will contact you soon.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PaymentActivity.this, BookingActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(PaymentActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                                hideProgressBar();
                                Log.e("statuscode", error.toString());
                            }
                        }
                        ) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                Log.i("Payment","get params");

                                params.put("action", "addItem");
                                params.put("name", name);
                                params.put("company", companyName);
                                params.put("email", emailID);
                                params.put("phone", phoneNumber);
                                params.put("type", "Paid BookingId:"+bookingDocID);
                                params.put("city", state);
                                params.put("message", message );
                                params.put("serviceType", serviceType);
                                return params;
                            }

                        };

                        int socketTimeOut = 500000;

                        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        stringRequest.setRetryPolicy(retryPolicy);

                        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
                        queue.add(stringRequest);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PaymentActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                    }
                });
        Toast.makeText(this, "Payment successfully done! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e(TAG, "error code " + String.valueOf(i) + " -- Payment failed " + s.toString());

        DocumentReference user_booking_doc = currentUserDocumentReference.collection("Booking List").document(bookingDocID);


        Log.i("Payment", "Payment Failed");
        Map<String, Object> map = new HashMap<>();
        map.put("PaymentId",s);
        map.put("PaymentStatus", "Payment Failed");

        user_booking_doc.set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                try {
                    Toast.makeText(PaymentActivity.this, "Payment failed please try again", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("OnPaymentError", "Exception in onPaymentError", e);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PaymentActivity.this, "Something went wrong /n Try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        Intent intent= new Intent(PaymentActivity.this, BookingActivity.class);
        startActivity(intent);

    }
}

