package com.LegalSuvidha.legalsuvidhaproviders.RentReceiptGenerator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.Blogs.CurrentBlogActivity;
import com.LegalSuvidha.legalsuvidhaproviders.BuildConfig;
import com.LegalSuvidha.legalsuvidhaproviders.GstVaidation.GstValidatorActivity;
import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.MyApplication;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.TypesOfServicesActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.LegalSuvidha.legalsuvidhaproviders.HomeScreen.GALLERY_REQUEST_CODE;

public class RentReceiptGenerator extends AppCompatActivity {

    TextInputLayout nameTextInputLayout, emailTextInputLayout, mobileNumberTextInputLayout, employeeIDTextInputLayout, landlordTextInputLayout, landlordPanTextInputLayout, periodToTextInputLayout, periodFromTextInputLayout, monthlyRentTextInputLayout, addressTextInputLayout;
    DatePickerDialog.OnDateSetListener dateSetToListener, dateSetFromListener;
    Calendar myCalendar;
    String name, email, mobile, employeeID, landlordName, landlordPan, monthlyRent, address;
    String pdfName;
//    String startMonth, endMonth;
    Date periodFrom, periodTo;
    int starMonthInt,endMonthInt,starYearInt,endYearInt;
//    int monthlyRentInNumber;
    Bitmap bitmap, scaledBitmap;
    int pageWidth = 1200;
    ProgressBar progressBar;
    int totalDaysInMonth;
    String chosenDateFrom, chosenDateTo;
    Integer arr31[] = { 1, 3, 5, 7, 8, 10, 12 };
    Integer arr28[] = { 2 };
    Integer arr30[] = { 4, 6, 9, 11 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_receipt_generator);


        nameTextInputLayout = findViewById(R.id.textInputLayoutName);
        emailTextInputLayout = findViewById(R.id.textInputLayoutEmailID);
        mobileNumberTextInputLayout = findViewById(R.id.textInputLayoutMobileNumber);
        employeeIDTextInputLayout = findViewById(R.id.textInputLayoutEmployeeID);
        landlordTextInputLayout = findViewById(R.id.textInputLayoutLandlord);
        landlordPanTextInputLayout = findViewById(R.id.textInputLayoutPanLandlord);
        periodToTextInputLayout = findViewById(R.id.textInputLayoutPeriodTo);
        periodFromTextInputLayout = findViewById(R.id.textInputLayoutPeriodFrom);
        monthlyRentTextInputLayout = findViewById(R.id.textInputLayoutMonthlyRent);
        addressTextInputLayout = findViewById(R.id.textInputLayoutAddress);
        progressBar = findViewById(R.id.progressBarHRA);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 400, false);

//        Hashtable<Integer, String> my_dict = new Hashtable<Integer, String>();
//        my_dict.put(1, "January");
//        my_dict.put(2, "February");
//        my_dict.put(3, "March");
//        my_dict.put(4, "April");
//        my_dict.put(5, "May");
//        my_dict.put(6, "June");
//        my_dict.put(7, "July");
//        my_dict.put(8, "August");
//        my_dict.put(9, "September");
//        my_dict.put(10, "October");
//        my_dict.put(11, "November");
//        my_dict.put(12, "December");


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        periodFromTextInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCalendar = Calendar.getInstance();
                final int year = myCalendar.get(Calendar.YEAR);
                final int month = myCalendar.get(Calendar.MONTH);
                final int day = myCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RentReceiptGenerator.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetFromListener, year, month,1);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        periodToTextInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCalendar = Calendar.getInstance();
                final int year = myCalendar.get(Calendar.YEAR);
                final int month = myCalendar.get(Calendar.MONTH);
                final int day = myCalendar.get(Calendar.DAY_OF_MONTH);

                if(Arrays.asList(arr31).contains(month)){
                    totalDaysInMonth=31;
                }else if(Arrays.asList(arr30).contains(month)) {
                    totalDaysInMonth = 30;
                }else if(Arrays.asList(arr28).contains(month)) {
                    totalDaysInMonth = 28;
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(RentReceiptGenerator.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetToListener, year, month,totalDaysInMonth);



                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                datePickerDialog.show();
            }
        });

        dateSetFromListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth
                                  ) {
                // TODO Auto-generated method stub

                monthOfYear = monthOfYear + 1;

                chosenDateFrom = dayOfMonth + "-" + monthOfYear + "-" + year;
//                chosenDateFrom =  monthOfYear + "-" + year;

                periodFromTextInputLayout.getEditText().setText(chosenDateFrom);

//                startMonth= my_dict.get(monthOfYear);
                starMonthInt=monthOfYear;
                starYearInt=year;
            }
        };

        dateSetToListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                monthOfYear = monthOfYear + 1;

                chosenDateTo = dayOfMonth + "-" + monthOfYear + "-" + year;
//                chosenDateTo =  monthOfYear + "-" + year;

                periodToTextInputLayout.getEditText().setText(chosenDateTo);

//                endMonth= my_dict.get(monthOfYear);
                endMonthInt=monthOfYear;
                endYearInt=year;
            }
        };

    }

    public void generateReceipt(View v) {

        if (ContextCompat.checkSelfPermission(RentReceiptGenerator.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {

            if (ContextCompat.checkSelfPermission(RentReceiptGenerator.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            } else {
                name = nameTextInputLayout.getEditText().getText().toString().trim();
                email = emailTextInputLayout.getEditText().getText().toString().trim();
//                mobile = mobileNumberTextInputLayout.getEditText().getText().toString().trim();
                landlordName = landlordTextInputLayout.getEditText().getText().toString().trim();
                landlordPan = landlordPanTextInputLayout.getEditText().getText().toString().trim();
                employeeID = employeeIDTextInputLayout.getEditText().getText().toString().trim();
                address = addressTextInputLayout.getEditText().getText().toString().trim();


                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    periodTo = df.parse(periodToTextInputLayout.getEditText().getText().toString().trim());
                    periodFrom = df.parse(periodFromTextInputLayout.getEditText().getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

//        String[] months = new DateFormatSymbols().getMonths();
//        for (int i = 0; i < months.length; i++) {
//            String month = months[i];
//            Log.i("date","month = " + month);
//        }

                monthlyRent = monthlyRentTextInputLayout.getEditText().getText().toString();
//        monthlyRentInNumber=Integer.parseInt(monthlyRent);

                generatePdf();
            }
        }

    }

    public void generatePdf() {

        if (!validateName() || !validateEmail()  || !validateEmployeeID() || !validateLandlord() || !validateLandlordPan() || !validateAddress() || !validateFrom() || !validateTo() || !validateRent()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        int x=0;
        int pageNumber=1;
        int diff = 0;
        int yearDiff=endYearInt-starYearInt;

        if(yearDiff==0){
            diff=endMonthInt-starMonthInt+1;

            if(diff<=0){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this,"Please select a valid period!",Toast.LENGTH_SHORT).show();
                return;
            }

        }else if(yearDiff>0){
            diff=12-starMonthInt+1+endMonthInt;
            if(yearDiff>1){
                for(int i=1;i<yearDiff;i++){
                    diff+=12;
                }
            }


        }else{

            progressBar.setVisibility(View.GONE);
            Toast.makeText(this,"Please select a valid period!",Toast.LENGTH_SHORT).show();
            return;
        }



        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(pageWidth, 2010, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();

//        canvas.drawBitmap(scaledBitmap,0,0,myPaint);

//        titlePaint.setTextAlign(Paint.Align.RIGHT);
//        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//        titlePaint.setTextSize(40);
//        titlePaint.setColor((Color.rgb(255, 153, 51)));
//        canvas.drawText("Legal ", pageWidth / 2, 50, titlePaint);

//        titlePaint.setTextAlign(Paint.Align.LEFT);
//        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//        titlePaint.setTextSize(40);
//        titlePaint.setColor((Color.rgb(0, 100, 0)));
//        canvas.drawText(" Suvidha", pageWidth / 2, 50, titlePaint);

//        myPaint.setColor((Color.rgb(0, 0, 0)));
//        myPaint.setTextSize(25);
//        myPaint.setTextAlign(Paint.Align.CENTER);
//        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//        canvas.drawText("Call:999999999  Email:legalsuvidha@gmail.com", pageWidth / 2, 85, myPaint);


        for(int i=1;i<=diff;i++){

            myPaint.setColor((Color.rgb(0, 100, 100)));
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(4);
            canvas.drawLine(20, x+105, pageWidth - 20, x+105, myPaint);

            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(2);
            canvas.drawLine(30, x+115, pageWidth - 30, x+115, myPaint);

            myPaint.setColor((Color.rgb(255, 0, 0)));
            myPaint.setTextAlign(Paint.Align.CENTER);
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            myPaint.setTextSize(65);
            canvas.drawText("Rent Receipt", pageWidth / 2, x+178, myPaint);

//            titlePaint.setColor((Color.rgb(0, 0, 0)));
//            titlePaint.setTextAlign(Paint.Align.LEFT);
//            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//            titlePaint.setTextSize(30);
//            canvas.drawText("Date:" + new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()), 40, x+230, titlePaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextSize(36);
            canvas.drawText("Receieved a sum of RS " + monthlyRent + " from " + name + " (" + employeeID + ") ", pageWidth/2, x+290, titlePaint);
            canvas.drawText("towards rent of the  property  situated at " + address + ".", pageWidth/2, x+340, titlePaint);


            if(Arrays.asList(arr31).contains(starMonthInt)){
                totalDaysInMonth=31;
            }else if(Arrays.asList(arr30).contains(starMonthInt)) {
                totalDaysInMonth = 30;
            }else if(Arrays.asList(arr28).contains(starMonthInt)) {

                if(starYearInt%4==0){
                    totalDaysInMonth = 29;
                }else{
                    totalDaysInMonth = 28;
                }
            }

            titlePaint.setTextAlign(Paint.Align.LEFT);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextSize(35);
            canvas.drawText("Period: " + "1-"+starMonthInt+"-" +starYearInt+ " to "+totalDaysInMonth+"-"+starMonthInt+"-" +starYearInt, 40, x+450, titlePaint);
//            canvas.drawText("Period: " + chosenDateFrom + "-" + chosenDateTo, 40, x+450, titlePaint);

            titlePaint.setTextAlign(Paint.Align.RIGHT);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextSize(35);
            canvas.drawText("Landlord: " + landlordName, pageWidth - 40, x+450, titlePaint);

            titlePaint.setTextAlign(Paint.Align.RIGHT);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            titlePaint.setTextSize(30);
            canvas.drawText("(" + landlordPan + ")", pageWidth - 60, x+481, titlePaint);

            myPaint.setColor((Color.rgb(0, 100, 100)));
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(2);
            canvas.drawLine(30, x+538, pageWidth - 30, x+538, myPaint);

            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(4);
            canvas.drawLine(20, x+548, pageWidth - 20, x+548, myPaint);

            x=x+550;

            if(starMonthInt%12==0){
                starMonthInt=1;
                starYearInt++;
            }else
            {
                starMonthInt++;
            }


            if(i%3==0 && i!=diff){

                myPdfDocument.finishPage(myPage1);
                pageNumber+=1;

                 myPageInfo1 = new PdfDocument.PageInfo.Builder(pageWidth, 2010, pageNumber).create();
                 myPage1 = myPdfDocument.startPage(myPageInfo1);
                 canvas = myPage1.getCanvas();
                x=0;

            }
        }



        myPdfDocument.finishPage(myPage1);

        pdfName = "rent_receipt" + new SimpleDateFormat("dd-MM-yyyyHH:mm:ss", Locale.getDefault()).format(new Date()) + ".pdf";

//        File folder = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
//
//        // Storing the data in file with name as geeksData.txt
//        File file = new File(folder, pdfName);

        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), pdfName);

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));

        } catch (IOException exception) {
            Toast.makeText(RentReceiptGenerator.this, exception.getMessage(), Toast.LENGTH_SHORT).show();

        }

        myPdfDocument.close();


        fromNotificationOpenPdfFromPhoneStorage();


    }

    public void fromNotificationOpenPdfFromPhoneStorage() {

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File file = new File(folder, pdfName);

        Uri path = FileProvider.getUriForFile(this,
                "com.LegalSuvidha.legalsuvidhaproviders",
                new File(file.getAbsolutePath()));

        Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
        pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pdfOpenIntent.setDataAndType(path, "application/pdf");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, pdfOpenIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.RENT_RECEIPT_CHANNEL_ID))
                .setColor(this.getResources().getColor(R.color.red))
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_baseline_download_for_offline_24))
                .setContentTitle("Download Complete")
                .setContentText("Download Complete")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(pdfName))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.RENT_RECEIPT_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());

        progressBar.setVisibility(View.GONE);
        Toast.makeText(this," Your Rent Receipt has been generated",Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(this, HomeScreen.class);
//        startActivity(intent);

    }


    private boolean validateName() {
        String val = nameTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            nameTextInputLayout.setError("Name cannot be null");
            return false;
        } else {
            nameTextInputLayout.setError(null);
            nameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLandlord() {
        String val = landlordTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            landlordTextInputLayout.setError("Field cannot be null");
            return false;
        } else {
            landlordTextInputLayout.setError(null);
            landlordTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLandlordPan() {
        String val = landlordPanTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            landlordPanTextInputLayout.setError("Field cannot be null");
            return false;
        } else {
            landlordPanTextInputLayout.setError(null);
            landlordPanTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateRent() {
        String val = monthlyRentTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            monthlyRentTextInputLayout.setError("Field cannot be null");
            return false;
        } else {
            monthlyRentTextInputLayout.setError(null);
            monthlyRentTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = emailTextInputLayout.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            emailTextInputLayout.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            emailTextInputLayout.setError("Invalid Email");
            return false;
        } else {
            emailTextInputLayout.setError(null);
            emailTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

//    public boolean validatePhoneNumber() {
//        String val = mobileNumberTextInputLayout.getEditText().getText().toString().trim();
//
//        if (val.isEmpty()) {
//            mobileNumberTextInputLayout.setError("Phone Number cannot be empty");
//            return false;
//        } else {
//            mobileNumberTextInputLayout.setError(null);
//            mobileNumberTextInputLayout.setErrorEnabled(false);
//            return true;
//        }
//    }

    private boolean validateEmployeeID() {
        String val = employeeIDTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            employeeIDTextInputLayout.setError("Field cannot be null");
            return false;
        } else {
            employeeIDTextInputLayout.setError(null);
            employeeIDTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress() {
        String val = addressTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            addressTextInputLayout.setError("Field cannot be null");
            return false;
        } else {
            addressTextInputLayout.setError(null);
            addressTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTo() {
        String val = periodToTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            periodToTextInputLayout.setError("Field cannot be null");
            return false;
        } else {
            periodToTextInputLayout.setError(null);
            periodToTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFrom() {
        String val = periodFromTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            periodFromTextInputLayout.setError("Field cannot be null");
            return false;
        } else {
            periodFromTextInputLayout.setError(null);
            periodFromTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }


}