package com.LegalSuvidha.legalsuvidhaproviders.ShareCertificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.RentReceiptGenerator.RentReceiptGenerator;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class ShareCertificateGeneratorActivity extends AppCompatActivity {

//    AutoCompleteTextView autoCompleteTextViewAuthorizedBy,autoCompleteTVState;
    AutoCompleteTextView autoCompleteTVState;
    String companyName,shareholderName, address,citytown,state,country,postcode,noOfShare,nominalValue,amountPaidPerShare;
//    String certificateNumber, shareLedgerFolio, fartherOrHusbandName,occupation,day, month,year;
    String certificateNumber, shareLedgerFolio, fartherOrHusbandName,occupation;
    int noOfShareInt,nominalValueInt,amountPaidPerShareInt;
    String pdfName;
    ProgressBar progressBar;
    int pageWidth = 2400;
    TextInputLayout textInputLayoutCertificateNo,textInputLayoutShareLedgerFolio,textInputLayoutFartherOrHusbandName,textInputLayoutOccupation,textInputLayoutCompanyName, textInputLayoutShareholderName,textInputLayoutShareholderAddress, textInputLayoutShareholderCityTown,textInputLayoutShareholderState,textInputLayoutShareholderCountry,textInputLayoutShareholderPostcode, textInputLayoutShareholderNoOfShare, textInputLayoutShareholderNominalValue, textInputLayoutShareholderAmountPaidPerShare;

//    TextInputLayout textInputLayoutCompanyName, textInputLayoutCompanyNumber, textInputLayoutShareholderName,textInputLayoutShareholderAddress, textInputLayoutShareholderCityTown, textInputLayoutShareholderTown,textInputLayoutShareholderState,textInputLayoutShareholderCountry,textInputLayoutShareholderPostcode, textInputLayoutShareholderNoOfShare, textInputLayoutShareholderNominalValue, textInputLayoutShareholderAmountPaidPerShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_certificate_generator);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        textInputLayoutCompanyName=findViewById(R.id.textInputLayoutCompanyName);
//        textInputLayoutCompanyNumber=findViewById(R.id.textInputLayoutMobileNumberCompany);
        textInputLayoutShareholderName=findViewById(R.id.textInputLayoutShareholderName);
        textInputLayoutShareholderAddress=findViewById(R.id.textInputLayoutShareholderAddress);
        textInputLayoutShareholderCityTown=findViewById(R.id.textInputLayoutShareholderCityTown);
//        textInputLayoutShareholderTown=findViewById(R.id.textInputLayoutShareholderTown);
        textInputLayoutShareholderState=findViewById(R.id.textInputLayoutShareholderState);
        textInputLayoutShareholderCountry=findViewById(R.id.textInputLayoutShareholderCountry);
        textInputLayoutShareholderPostcode=findViewById(R.id.textInputLayouShareholdertPostCode);
        textInputLayoutShareholderNoOfShare=findViewById(R.id.textInputLayoutNoOfShare);
        textInputLayoutShareholderNominalValue=findViewById(R.id.textInputLayoutNominalValue);
        textInputLayoutShareholderAmountPaidPerShare=findViewById(R.id.textInputLayoutAmountPaidPerShare);
//        autoCompleteTextViewAuthorizedBy=findViewById(R.id.autocompleteTextViewAuthorizedBy);
        autoCompleteTVState=findViewById(R.id.autocompleteTextViewState);
        textInputLayoutCertificateNo=findViewById(R.id.textInputLayoutCertificateNumber);
        textInputLayoutShareLedgerFolio=findViewById(R.id.textInputLayoutShareLedgerFolio);
        textInputLayoutFartherOrHusbandName=findViewById(R.id.textInputLayoutShareholderFartherHusbandName);
        textInputLayoutOccupation=findViewById(R.id.textInputLayoutShareholderOccupation);

        progressBar=findViewById(R.id.progressBarShareCertificate);

//        ArrayList<String> typeList = new ArrayList<>();
//        typeList.add("A Director");
//        typeList.add("A Secretary");
//        typeList.add("A Director and secretary");
//        typeList.add("Common Seal");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);
//        autoCompleteTextViewAuthorizedBy.setAdapter(adapter);

        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.india_states));
        autoCompleteTVState.setAdapter(adapterState);

    }

    public void generateShareCertificate(View v) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            } else {
                certificateNumber=textInputLayoutCertificateNo.getEditText().getText().toString().trim();
                shareLedgerFolio=textInputLayoutShareLedgerFolio.getEditText().getText().toString().trim();
                fartherOrHusbandName=textInputLayoutFartherOrHusbandName.getEditText().getText().toString().trim();

                companyName = textInputLayoutCompanyName.getEditText().getText().toString().trim();
                shareholderName = textInputLayoutShareholderName.getEditText().getText().toString().trim();
//                companyMobile = textInputLayoutCompanyNumber.getEditText().getText().toString().trim();
                address = textInputLayoutShareholderAddress.getEditText().getText().toString().trim();
//                town = textInputLayoutShareholderTown.getEditText().getText().toString().trim();
                citytown = textInputLayoutShareholderCityTown.getEditText().getText().toString().trim();
                state = autoCompleteTVState.getText().toString().trim();
                country = textInputLayoutShareholderCountry.getEditText().getText().toString().trim();
                postcode=textInputLayoutShareholderPostcode.getEditText().getText().toString().trim();
                noOfShare = textInputLayoutShareholderNoOfShare.getEditText().getText().toString().trim();
                occupation=textInputLayoutOccupation.getEditText().getText().toString().trim();
//                noOfShareInt = Integer.parseInt(noOfShare);
                nominalValue = textInputLayoutShareholderNominalValue.getEditText().getText().toString().trim();
//                nominalValueInt = Integer.parseInt(nominalValue);
                amountPaidPerShare = textInputLayoutShareholderAmountPaidPerShare.getEditText().getText().toString().trim();
//                amountPaidPerShareInt = Integer.parseInt(amountPaidPerShare);

//                authorizationBy=autoCompleteTextViewAuthorizedBy.getText().toString().trim();



                generatePdf();
            }
        }

    }

    public void generatePdf() {

//        if (!validateCompanyName() || !validateCompanyMobile() || !validateShareholderName() || !validateAddress() || !validateCity() || !validateState() || !validateShareholderNominalValue() || !validateCountry() || !validateNoOfShare() || !validateAmount() || !validatePostalCode()) {
//
//            return;
//        }

        progressBar.setVisibility(View.VISIBLE);

        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(pageWidth, 1200, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();

        myPaint.setColor((Color.rgb(0, 0, 150)));
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(4);
        canvas.drawRect(20, 20, pageWidth/3, 1200-20, myPaint);
        canvas.drawRect(pageWidth/3 + 20, 20, pageWidth - 20, 1200-20, myPaint);

        myPaint.setColor((Color.rgb(0, 0, 100)));
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(20);
        canvas.drawRect(pageWidth/3 +40, 40, pageWidth - 40, 1200-40, myPaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(30);
        titlePaint.setColor((Color.rgb(0, 0, 0)));
        canvas.drawText("Form No. SH-1  ",  2*pageWidth / 3, 110, titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(50);
        myPaint.setColor((Color.rgb(0, 0, 0)));
        canvas.drawText("S H A R E    C E R T I F I C A T E",  2*pageWidth / 3, 160, titlePaint);

        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(20);
        canvas.drawText("[Persuant to sub-section(3) of section 46 of the Companies Act 2013 and rule 5(2) of the Companies(Share Capital and Debentures) Rules 2014]", 2*pageWidth / 3, 185, titlePaint);


        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(55);
        canvas.drawText(companyName, 2*pageWidth / 3, 260, titlePaint);


        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
        titlePaint.setTextSize(30);
//                            proprietor of "+noOfShare +" Shares of Rs "+nominalValue+" each in the above
        canvas.drawText("THIS IS TO CERTIFY that the person(s) names in this Certificate is/are the Registered Holder(s) of the within "  , 2*pageWidth / 3, 350, titlePaint);
        canvas.drawText("mentioned share(s) bearing the distinctive number(s) herein specified in the above named Company subject " , 2*pageWidth / 3, 390, titlePaint);
        canvas.drawText("to the Memorandum and Articles of Association of the company and the amount endorsed herein has been " , 2*pageWidth / 3, 430, titlePaint);
        canvas.drawText("paid up on each such share.", 2*pageWidth / 3, 460, titlePaint);


        myPaint.setStrokeWidth(5);
        canvas.drawRect(pageWidth/3 +80, 510, pageWidth - 80, 590, myPaint);

        titlePaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titlePaint.setTextSize(35);
        canvas.drawText("EQUITY                    SHARES              EACH                OF           RUPEES                  "+nominalValue+" ", pageWidth /3+90, 550, titlePaint);
        canvas.drawText("AMOUNT                 PAID-UP             PER                 SHARE      RUPEES                   "+amountPaidPerShare+" ", pageWidth /3+90, 580, titlePaint);


        myPaint.setStrokeWidth(5);
        canvas.drawRect(pageWidth/3 +80, 615, pageWidth - 80, 800, myPaint);

        titlePaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(30);

        canvas.drawText("  Regd. Folio No.                                                                                                "+shareLedgerFolio+"                     Certificate No.:"+certificateNumber, pageWidth /3+90, 650, titlePaint);
        canvas.drawText("  Name(s) of Holder(s)", pageWidth /3+90, 680, titlePaint);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        canvas.drawText("                                                                                                                              "+shareholderName+" ", pageWidth /3+90, 690, titlePaint);

        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        canvas.drawText("  N0. of Share(s) held(In figure):-                                                                             "+noOfShare+" ", pageWidth /3+90, 720, titlePaint);
        canvas.drawText("  N0. of Share(s) held(In words):-                                                                      ", pageWidth /3+90, 750, titlePaint);
        canvas.drawText("  Distinctive No.(s) From                                             To                                                          (Both Inclusive)      ", pageWidth /3+90, 780, titlePaint);


//        canvas.drawText("This is to certify "+shareholderName+" " , 2*pageWidth / 3, 370, titlePaint);
//        canvas.drawText("residing at "+address+" "+town+" "+city+" "+state , 2*pageWidth / 3, 410, titlePaint);
//        canvas.drawText(" "+postcode+" "+country+" " , 2*pageWidth / 3, 450, titlePaint);
//        canvas.drawText("is registered proprietor of "+noOfShare +" Shares of Rs "+nominalValue+" each in the above-named company,", 2*pageWidth / 3, 490, titlePaint);
//        canvas.drawText("subject to the Memorandum and Articles of the company, and that upon each of such", 2*pageWidth / 3, 530, titlePaint);
//        canvas.drawText("shares the sum of Rs " + amountPaidPerShare + " has been paid.", 2*pageWidth / 3, 570, titlePaint);

        titlePaint.setColor((Color.rgb(0, 0, 0)));
        titlePaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(30);
        canvas.drawText(" Given under the company seal.  of the company this                   Day of                         ", pageWidth /3+80, 850, titlePaint);

        titlePaint.setColor((Color.rgb(0, 0, 0)));
        titlePaint.setTextAlign(Paint.Align.RIGHT);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(25);

        canvas.drawText("Director                              Director    ", pageWidth -160, 950, titlePaint);
        canvas.drawText("Secretary / Authorized Signatory", pageWidth -100, 1060, titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTextSize(22);
        canvas.drawText("Note: No transfer of Share(s) comprised in the certificate can be registered unless accompanied by this certificate.", 2*pageWidth/3, 1120, titlePaint);


        titlePaint.setColor((Color.rgb(0, 0, 0)));
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titlePaint.setTextSize(35);
        canvas.drawText("SHARE CERTIFICATE", pageWidth / 6, 100, titlePaint);

        titlePaint.setColor((Color.rgb(0, 0, 0)));
        titlePaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(27);

        canvas.drawText("Certificate No. "+certificateNumber+"   Share ledger Folio " +shareLedgerFolio+" "  , 80 ,200, titlePaint);
        canvas.drawText("No. of Shares "+noOfShare+" from........................................." , 80, 240, titlePaint);
        canvas.drawText("to .....................................................(both inclusive) " , 80, 280, titlePaint);
        canvas.drawText("Name "+shareholderName+" " , 80, 320, titlePaint);
        canvas.drawText("Father's/Husband's Name "+fartherOrHusbandName+" " , 80, 360, titlePaint);
        canvas.drawText("Occupation "+occupation+" " , 80, 400, titlePaint);
        canvas.drawText("Address "+address+" "+citytown+" " , 80, 430, titlePaint);
        canvas.drawText(state+" "+postcode+" "+country+" " , 80, 460, titlePaint);
        canvas.drawText("Given under the Common Seal of the Company" , 80, 500, titlePaint);
        canvas.drawText("This ...................... day of .........................  .............." , 80, 540, titlePaint);
//        canvas.drawText("This "+day+" of "+month+" "+year+" " , 80, 540, titlePaint);

        titlePaint.setTextSize(23);
        canvas.drawText("Secretary/Authorized Signatory              Director            Director" ,  80 , 620+80, titlePaint);

        myPaint.setStrokeWidth(5);
        RectF rect = new RectF(60, 640+90, pageWidth/3-30, 930+90);
        canvas.drawRoundRect(rect,15,15,myPaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titlePaint.setTextSize(25);
        canvas.drawText("MEMORANDUM OF CALLS PAID" , pageWidth / 6, 670+90, titlePaint);


        myPaint.setStrokeWidth(5);
        canvas.drawRect(60, 675+90, pageWidth/3 - 30, 735+90, myPaint);

        titlePaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(20);
        canvas.drawText("Date of                     Particulars                           Per-        Amount      Signature" , 80, 700+90, titlePaint);
        canvas.drawText("Receipt                                                                  Share                                " , 80, 720+90, titlePaint);

        myPaint.setStrokeWidth(5);
        canvas.drawLine(160,675+90,160,930+90,myPaint);
        canvas.drawLine(450,675+90,450,930+90,myPaint);
        canvas.drawLine(550,675+90,550,930+90,myPaint);
        canvas.drawLine(650,675+90,650,930+90,myPaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(20);
        canvas.drawText("Application ............." , 270, 760+90, titlePaint);
        canvas.drawText("Allotment ..............." , 270, 800+90, titlePaint);
        canvas.drawText("1st Call ................" , 270, 840+90, titlePaint);
        canvas.drawText("2nd Call ................" , 270, 880+90, titlePaint);
        canvas.drawText("3rd Call ................" , 270, 920+90, titlePaint);


//        if(authorizationBy.equals("A Director")){
//
//            titlePaint.setTextAlign(Paint.Align.LEFT);
//            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//            titlePaint.setTextSize(30);
//            canvas.drawText("Given under the signature of a company director in accordance with ", 60, 700, titlePaint);
//            canvas.drawText("the Companies Act 2006, there being no requirement ", 60, 730, titlePaint);
//            canvas.drawText("for a common seal.", 60, 760, titlePaint);
//
//            titlePaint.setTextSize(40);
//            canvas.drawText("Director: ______________________" , 60,900 , titlePaint);
//            canvas.drawText("Witness :_______________________" , 60, 970, titlePaint);
//
//        }else if(authorizationBy.equals("A Director and secretary")){
//
//            titlePaint.setTextAlign(Paint.Align.LEFT);
//            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//            titlePaint.setTextSize(30);
//            canvas.drawText("Given under the signature of a company director and secretary in accordance with ", 60, 700, titlePaint);
//            canvas.drawText("the Companies Act 2006, there being no requirement ", 60, 730, titlePaint);
//            canvas.drawText("for a common seal.", 60, 760, titlePaint);
//
//            titlePaint.setTextSize(40);
//            canvas.drawText("Director : ______________________" , 60,900 , titlePaint);
//            canvas.drawText("Secretary: ______________________" , 60, 970, titlePaint);
//
//        } else if(authorizationBy.equals("A Secretary")){
//
//            titlePaint.setTextAlign(Paint.Align.LEFT);
//            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//            titlePaint.setTextSize(30);
//            canvas.drawText("Given under the signature of a company secretary in accordance with ", 60, 700, titlePaint);
//            canvas.drawText("the Companies Act 2006, there being no requirement ", 60, 730, titlePaint);
//            canvas.drawText("for a common seal.", 60, 760, titlePaint);
//
//            titlePaint.setTextSize(40);
//            canvas.drawText("Secretary: ______________________" , 60,900 , titlePaint);
//            canvas.drawText("Witness  :_______________________" , 60, 970, titlePaint);
//
//        }else if(authorizationBy.equals("Common Seal")){
//
//            titlePaint.setTextAlign(Paint.Align.LEFT);
//            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//            titlePaint.setTextSize(30);
//            canvas.drawText("Given under the authority of the company seal.", 60, 700, titlePaint);
//
//        }

        myPdfDocument.finishPage(myPage1);

        pdfName = "Share_certificate" + new SimpleDateFormat("dd-MM-yyyyHH:mm:ss", Locale.getDefault()).format(new Date()) + ".pdf";


        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), pdfName);

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));

        } catch (IOException exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();

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


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.SHARE_CERTIFICATE_CHANNEL_ID))
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
                .setChannelId(getString(R.string.SHARE_CERTIFICATE_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());

        progressBar.setVisibility(View.GONE);
        Toast.makeText(this," Your Share Certificate has been generated",Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(this, HomeScreen.class);
//        startActivity(intent);

    }


    private boolean validateCompanyName() {
        String val = textInputLayoutCompanyName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutCompanyName.setError("Company Name cannot be null");
            return false;
        } else {
            textInputLayoutCompanyName.setError(null);
            textInputLayoutCompanyName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFartherHusbandName() {
        String val = textInputLayoutFartherOrHusbandName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutFartherOrHusbandName.setError("Field cannot be null");
            return false;
        } else {
            textInputLayoutFartherOrHusbandName.setError(null);
            textInputLayoutFartherOrHusbandName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateShareholderName() {
        String val = textInputLayoutShareholderName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutShareholderName.setError("Shareholder Name cannot be null");
            return false;
        } else {
            textInputLayoutShareholderName.setError(null);
            textInputLayoutShareholderName.setErrorEnabled(false);
            return true;
        }
    }

//    private boolean validateCompanyMobile() {
//        String val = textInputLayoutCompanyNumber.getEditText().getText().toString().trim();
//
//        if (val.isEmpty()) {
//            textInputLayoutCompanyNumber.setError("Company Number cannot be null");
//            return false;
//        } else {
//            textInputLayoutCompanyNumber.setError(null);
//            textInputLayoutCompanyNumber.setErrorEnabled(false);
//            return true;
//        }
//    }

    private boolean validateOccupation() {
        String val = textInputLayoutOccupation.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutOccupation.setError("Occupation cannot be null");
            return false;
        } else {
            textInputLayoutOccupation.setError(null);
            textInputLayoutOccupation.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress() {
        String val = textInputLayoutShareholderAddress.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutShareholderAddress.setError("Field cannot be null");
            return false;
        } else {
            textInputLayoutShareholderAddress.setError(null);
            textInputLayoutShareholderAddress.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateState() {
        String val = textInputLayoutShareholderState.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutShareholderState.setError("Field cannot be null");
            return false;
        } else {
            textInputLayoutShareholderState.setError(null);
            textInputLayoutShareholderState.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCityTown() {
        String val = textInputLayoutShareholderCityTown.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutShareholderCityTown.setError("Field cannot be null");
            return false;
        } else {
            textInputLayoutShareholderCityTown.setError(null);
            textInputLayoutShareholderCityTown.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCountry() {
        String val = textInputLayoutShareholderCountry.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutShareholderCountry.setError("Field cannot be null");
            return false;
        } else {
            textInputLayoutShareholderCountry.setError(null);
            textInputLayoutShareholderCountry.setErrorEnabled(false);
            return true;
        }
    }


    public boolean validatePostalCode() {
        String val = textInputLayoutShareholderPostcode.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutShareholderPostcode.setError("Phone Number cannot be empty");
            return false;
        } else {
            textInputLayoutShareholderPostcode.setError(null);
            textInputLayoutShareholderPostcode.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNoOfShare() {
        String val = textInputLayoutShareholderNoOfShare.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutShareholderNoOfShare.setError("Field cannot be null");
            return false;
        } else {
            textInputLayoutShareholderNoOfShare.setError(null);
            textInputLayoutShareholderNoOfShare.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateAmount() {
        String val = textInputLayoutShareholderAmountPaidPerShare.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutShareholderAmountPaidPerShare.setError("Amount paid per share cannot be null");
            return false;
        } else {
            textInputLayoutShareholderAmountPaidPerShare.setError(null);
            textInputLayoutShareholderAmountPaidPerShare.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateShareholderNominalValue() {
        String val = textInputLayoutShareholderNominalValue.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutShareholderNominalValue.setError("Shareholder Nominal Value cannot be null");
            return false;
        } else {
            textInputLayoutShareholderNominalValue.setError(null);
            textInputLayoutShareholderNominalValue.setErrorEnabled(false);
            return true;
        }
    }
}