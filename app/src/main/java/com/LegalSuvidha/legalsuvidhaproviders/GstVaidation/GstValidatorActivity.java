package com.LegalSuvidha.legalsuvidhaproviders.GstVaidation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.LegalSuvidha.legalsuvidhaproviders.HomeScreen.CAMERA_PERM_CODE;
import static com.LegalSuvidha.legalsuvidhaproviders.HomeScreen.CAMERA_REQUEST_CODE;
import static com.LegalSuvidha.legalsuvidhaproviders.HomeScreen.GALLERY_REQUEST_CODE;

public class GstValidatorActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "Main Activity";
    String API_KEY;
//    public static String API_KEY = "cd0e06afaff1217ce85da9bf3fd45011";
    public static String GSTIN = "";
    Uri photoURI;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    public  DocumentReference apiKeyRef=db.collection("Gst Key").document("ApiKey");

    List<ReturnTypeModel> returnTypeList;

    private Bitmap myBitmap;
    private ImageView myImageView;
//    private TextView myTextViewInfo;
    private TextInputEditText myEditTextGst;
    int rotation=0;
    String GstPattern= "GST\\s*.*\\s*([0-9A-Z]{15})";
    Button checkGstButton;
    FloatingActionButton selectPhotoButton;
    TextInputLayout gstTextLayout;
    LinearLayout linearButtonLayout,linearlayoutdetails;
    JSONObject responseFromApi;
    private String tradeName, status, lastUpdate,addressText,registrationType,registrationDate;
    Chip statusChip;
    TextInputLayout textInputLayoutGSTIN,textInputLayoutTradeName,textInputLayoutLastUpdate, textInputLayoutAddress, textInputLayoutRegistrationType, textInputLayoutRegistrationDate;
    ProgressBar progressBar;
    List<FillingData> dataFilingList;
    RecyclerView recyclerView;
    Button getFilingHistoryButton, getTaxPayerDetailButton;
    View view;
    TextView textViewTitle;
    String currentPhotoPath;
    Button extractGstButton;

//     07AANFG0307M1ZC

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst_validate);

        getApiKeyFromFirestore();

//        if (Build.VERSION.SDK_INT >= 24) {
//            try {
//                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
//                m.invoke(null);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());

//        myTextViewInfo = findViewById(R.id.textViewBillInfo);

        myEditTextGst = findViewById(R.id.textViewGST);
        myImageView = findViewById(R.id.imageView);
        progressBar= findViewById(R.id.progressBarGSTValidate);
        gstTextLayout=findViewById(R.id.textInputLayoutGST);
        linearButtonLayout=findViewById(R.id.linearButtonLayout);
        checkGstButton=findViewById(R.id.checkGst);
        getTaxPayerDetailButton=findViewById(R.id.fetchDetails);
        linearlayoutdetails=findViewById(R.id.linearLayoutTaxPayerDetails);
        statusChip = findViewById(R.id.statusChip);
        textInputLayoutGSTIN= findViewById(R.id.textInputLayoutGSTIN);
        textInputLayoutTradeName=findViewById(R.id.textInputLayoutTradeName);
        textInputLayoutAddress=findViewById(R.id.textInputLayoutAddress);
        textInputLayoutRegistrationType= findViewById(R.id.textInputLayoutRegistrationType);
        textInputLayoutRegistrationDate = findViewById(R.id.textInputLayoutRegistrationDate);
        recyclerView=findViewById(R.id.recyclerViewFilling);
        getFilingHistoryButton=findViewById(R.id.filingButton);
        view= findViewById(R.id.view2);
        textViewTitle= findViewById(R.id.textViewFilingTitle);
        selectPhotoButton=findViewById(R.id.select_image);
        extractGstButton=findViewById(R.id.checkText);

        findViewById(R.id.checkText).setOnClickListener(this);
        findViewById(R.id.select_image).setOnClickListener(this);

        myEditTextGst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                gstTextLayout.setEndIconDrawable(null);

                if(checkGstButton.getVisibility()==View.GONE){
                    checkGstButton.setVisibility(View.VISIBLE);
                }

                if(linearButtonLayout.getVisibility()==View.VISIBLE){
                    linearButtonLayout.setVisibility(View.INVISIBLE);
                }
                if(linearlayoutdetails.getVisibility()==View.VISIBLE){
                    linearlayoutdetails.setVisibility(View.INVISIBLE);
                }
                recyclerView.setAdapter(null);

                if(getFilingHistoryButton.getVisibility()==View.VISIBLE){
                    getFilingHistoryButton.setVisibility(View.INVISIBLE);
                }
                if(view.getVisibility()==View.VISIBLE){
                    view.setVisibility(View.INVISIBLE);
                }

                if(textViewTitle.getVisibility()==View.VISIBLE){
                    textViewTitle.setVisibility(View.INVISIBLE);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getApiKeyFromFirestore() {
        apiKeyRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                API_KEY=documentSnapshot.get("ApiKey").toString();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkText:
                if (myBitmap != null) {
                    runTextRecognition();
                }else {
                    Toast.makeText(this, "Please insert a bill", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.select_image:
//                myTextViewInfo.setText("");

                myEditTextGst.setText("");
                gstTextLayout.setEndIconDrawable(null);
                GSTIN = "";
                myImageView.setImageResource(R.drawable.bill);

                if(selectPhotoButton.getVisibility()==View.INVISIBLE){
                    selectPhotoButton.setVisibility(View.VISIBLE);
                }

                if(checkGstButton.getVisibility()==View.INVISIBLE){
                    checkGstButton.setVisibility(View.VISIBLE);
                }

                if(linearButtonLayout.getVisibility()==View.VISIBLE){
                    linearButtonLayout.setVisibility(View.INVISIBLE);
                }

                if(linearlayoutdetails.getVisibility()==View.VISIBLE){
                    linearlayoutdetails.setVisibility(View.INVISIBLE);
                }

                if(getFilingHistoryButton.getVisibility()==View.VISIBLE){
                    getFilingHistoryButton.setVisibility(View.INVISIBLE);
                }

                if(view.getVisibility()==View.VISIBLE){
                    view.setVisibility(View.INVISIBLE);
                }

                if(textViewTitle.getVisibility()==View.VISIBLE){
                    textViewTitle.setVisibility(View.INVISIBLE);
                }

                recyclerView.setAdapter(null);
                selectPhoto();
                break;
        }
    }

    public void selectPhoto() {
        if(selectPhotoButton.getVisibility()==View.INVISIBLE){
            selectPhotoButton.setVisibility(View.VISIBLE);
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(GstValidatorActivity.this);
        builder.setIcon(R.drawable.addimage);
        builder.setMessage("Choose image source")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            cameraPermission();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                galleryPermissions();
            }
        });
        builder.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERM_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        dispatchTakePictureIntent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
                break;
            case GALLERY_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchToGalleryIntent();
                } else {
                }
                break;
        }
    }

    private void galleryPermissions() {
        if (ContextCompat.checkSelfPermission(GstValidatorActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
            }
        } else {
            dispatchToGalleryIntent();
        }
    }

    private void dispatchToGalleryIntent() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
    }

    public void cameraPermission() throws IOException {

        if (ContextCompat.checkSelfPermission(GstValidatorActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
            }
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent () throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();


        // Save a file: path for use with ACTION_VIEW intents


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = image;
            } catch (Exception e) {
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.LegalSuvidha.legalsuvidhaproviders",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(MainActivity.this, "Please Wait!", Toast.LENGTH_SHORT).show();

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Log.i("", "onActivityResult: ");

                    if(myImageView.getVisibility()!=View.VISIBLE){
                        myImageView.setVisibility(View.VISIBLE);
                    }
                    if(extractGstButton.getVisibility()!=View.VISIBLE){
                        extractGstButton.setVisibility(View.VISIBLE);
                    }
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                    myImageView.setImageBitmap(myBitmap);
                    hideProgressBar();

                } catch (Exception e) {
                    hideProgressBar();
                    e.printStackTrace();
                }

            }
        }

        if (requestCode == GALLERY_REQUEST_CODE&&data!=null&&data.getData()!=null) {
            if (resultCode == Activity.RESULT_OK) {

                Uri uri=data.getData();
                try {
                    if(myImageView.getVisibility()!=View.VISIBLE){
                        myImageView.setVisibility(View.VISIBLE);
                    }
                    if(extractGstButton.getVisibility()!=View.VISIBLE){
                        extractGstButton.setVisibility(View.VISIBLE);
                    }
                    myBitmap=MediaStore.Images.Media.getBitmap(GstValidatorActivity.this.getContentResolver(),uri);
                    myImageView.setImageBitmap(myBitmap);
                    hideProgressBar();


                } catch (IOException e) {
                    Toast.makeText(this, "Photo not uploaded", Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                    e.printStackTrace();
                }

            }
        }
    }

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // Get the device's sensor orientation.
        CameraManager cameraManager = (CameraManager) activity.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);

        if (isFrontFacing) {
            rotationCompensation = (sensorOrientation + rotationCompensation) % 360;
        } else { // back-facing
            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360;
        }
        return rotationCompensation;
    }

    private void runTextRecognition() {
        showProgressBar();

        if(linearButtonLayout.getVisibility()==View.VISIBLE) {
            linearButtonLayout.setVisibility(View.GONE);
        }
        InputImage image = InputImage.fromBitmap(myBitmap,rotation);
        TextRecognizer recognizer = TextRecognition.getClient();
        Task<Text> result =recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text texts) {
                processExtractedText(texts);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure
                    (@NonNull Exception exception) {
                Toast.makeText(GstValidatorActivity.this,
                        exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void processExtractedText(Text firebaseVisionText) {
        hideProgressBar();
//        myTextViewInfo.setText(null);
        if (firebaseVisionText.getText().length() == 0) {
//            myTextViewInfo.setText("No text found. Please try again");
            Toast.makeText(this,"No text found. Please try again", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Text.TextBlock block : firebaseVisionText.getTextBlocks()) {
            String blockText = block.getText();

            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();

//                myTextViewInfo.append(lineText+"\n");

                if(lineText.contains("GST")){
                    Pattern r = Pattern.compile(GstPattern);
                    Matcher m = r.matcher(lineText);

                    if (m.find( )) {
                        myEditTextGst.setText(m.group(1));
                    }
                }

//                for (Text.Element element : line.getElements()) {
//                    String elementText = element.getText();
//
////                    Pattern p = Pattern.compile(GstPattern);
////                    Matcher m = p.matcher(elementText);
////
////                    if (m.find( )) {
////                        myTextViewInfo.setText("GSTIN"+m.group(1));
////                    }
////                    myTextViewInfo.append(elementText+"\n");
////                    Log.i("ExtractedText-line:", lineText);
////                    Log.i("ExtractedText-block:", blockText);
////                    Log.i("ExtractedText-element:", elementText);
//                }
            }
        }

        if(myEditTextGst.getText().toString().trim().isEmpty()){
            Toast.makeText(GstValidatorActivity.this,"Couldn't find GSTIN",Toast.LENGTH_LONG).show();
            //alertbox
        }
    }

    public void validateGST(View v){
        showProgressBar();
//        String gst="(.{15})";
        String gst="([0-9A-Z]{15})";
        GSTIN= myEditTextGst.getText().toString().trim();
        Pattern p = Pattern.compile(gst);

        Matcher matcher = p.matcher(GSTIN);

        if (matcher.find()) {

            // Instantiate the RequestQueue.
//            queue = Volley.newRequestQueue(this);
            String url ="https://sheet.gstincheck.ml/check/"+ API_KEY +"/"+ GSTIN +"";

            JsonRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    responseFromApi=response;
//                        TempDialog.dismiss();
                    hideProgressBar();
                    try {
                        Boolean responseflag=response.getBoolean("flag");
                        Log.i("statusresponse","status:" +responseflag);

                        if(responseflag){

                            Toast.makeText(GstValidatorActivity.this, "GST Valid",Toast.LENGTH_SHORT).show();
                            checkGstButton.setVisibility(View.GONE);
                            gstTextLayout.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24);
                            linearButtonLayout.setVisibility(View.VISIBLE);
//                            fetchDataFromApi(response);

                        }else{
                            gstTextLayout.setEndIconDrawable(R.drawable.ic_baseline_close_24);
//                            gstinTextLayout.setError("GST Invalid");
                            Toast.makeText(GstValidatorActivity.this, "GST Invalid",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideProgressBar();
                    Log.e("statuscode",error.toString());
                }
            });

            MySingleton.getInstance(this).addToRequestQueue(objectRequest);
//            queue.add(objectRequest);

        } else {
            hideProgressBar();
            gstTextLayout.setError("GST must contain 15 characters");
//            Toast.makeText(this,"GST must contain 15 characters",Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchDetailButton(View v) throws JSONException {
        if(responseFromApi!=null){
            fetchDataFromApi(responseFromApi);
        }else {
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchDataFromApi(JSONObject response) throws JSONException {

        linearButtonLayout.setVisibility(View.INVISIBLE);
//        Toast.makeText(MainActivity.this, "GST Valid",Toast.LENGTH_SHORT).show();

        JSONObject data_from_api  = response.getJSONObject("data");
        Log.i(TAG, "onResponse data:"+data_from_api.toString());
        DataClass data= new DataClass();

        data.setTradeNam(data_from_api.getString("tradeNam"));
        data.setSts(data_from_api.getString("sts"));
        data.setLstupdt(data_from_api.getString("lstupdt"));
        data.setRgdt(data_from_api.getString("rgdt"));
        data.setDty(data_from_api.getString("dty"));

        JSONObject pradr_from_api= data_from_api.getJSONObject("pradr");

        JSONObject address_from_api= pradr_from_api.getJSONObject("addr");
        addressClass address = new addressClass();

        address.setBno(address_from_api.getString("bno"));
        address.setFlno(address_from_api.getString("flno"));
        address.setSt(address_from_api.getString("st")) ;
        address.setLoc(address_from_api.getString("loc"));
        address.setCity(address_from_api.getString("city"));
        address.setDst(address_from_api.getString("dst"));
        address.setStcd(address_from_api.getString("stcd"));
        address.setLoc(address_from_api.getString("pncd"));

        goToTaxPayerInfoActivity(data,address);
    }

    private void goToTaxPayerInfoActivity(DataClass data, addressClass address) {

//        linearlayoutdetails.setVisibility(View.GONE);
        linearlayoutdetails.setVisibility(View.VISIBLE);
        getFilingHistoryButton.setVisibility(View.VISIBLE);

        tradeName=data.getTradeNam();
        status=data.getSts();
        lastUpdate=data.getLstupdt();
        addressText=address.getBno()+" "+address.getFlno()+", "+address.getSt()+", "+address.getLoc()+", "+ address.getCity()+", "+address.getDst()+", "+address.getStcd()+", "+address.getPncd();
        registrationType=data.getDty();
        registrationDate=data.getRgdt();

        if(status.equals("Active")){
            statusChip.setChipBackgroundColorResource(R.color.green);
        }else{
            statusChip.setChipBackgroundColorResource(R.color.red);
        }

        statusChip.append(status);
        textInputLayoutGSTIN.getEditText().setText(GSTIN);
        textInputLayoutTradeName.getEditText().setText(tradeName);
        textInputLayoutAddress.getEditText().setText(addressText);
        textInputLayoutRegistrationType.getEditText().setText(registrationType);
        textInputLayoutRegistrationDate.getEditText().setText(registrationDate);
    }

    public void getFillingDetails(View view){
        showProgressBar();
        String url ="https://sheet.gstincheck.ml/check-return/"+ API_KEY +"/"+ GSTIN+"";

        JsonRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null,new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {
                hideProgressBar();
                try {

                    Boolean responseflag=response.getBoolean("flag");
                    Log.i("statusresponse","status:" +responseflag);

                    if(responseflag){

                        Toast.makeText(GstValidatorActivity.this, response.getString("message"),Toast.LENGTH_SHORT).show();
                        fetchFillingDetailsFromApi(response);
                    }else{

                        Toast.makeText(GstValidatorActivity.this, response.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();
                Toast.makeText(GstValidatorActivity.this, error.toString(),Toast.LENGTH_SHORT).show();
                Log.e("statuscode",error.toString());
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(objectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fetchFillingDetailsFromApi(JSONObject response) throws JSONException {

        JSONObject data_from_api  = response.getJSONObject("data");
        JSONArray filing_status_list = data_from_api.getJSONArray("filingStatus");
        JSONArray filing_status_inner_list= (JSONArray) filing_status_list.get(0);

        FillingData filing = new FillingData();

        dataFilingList=new ArrayList<>();
//        List<ReturnTypeModel> returnTypeList =new ArrayList<>();

        for (int i=0; i<filing_status_inner_list.length();i++) {

            JSONObject filing_status_from_api= (JSONObject) filing_status_inner_list.get(i);
            filing.setFy(filing_status_from_api.getString("fy"));
            filing.setTaxp(filing_status_from_api.getString("taxp"));
            filing.setMof(filing_status_from_api.getString("mof"));
            filing.setDof(filing_status_from_api.getString("dof"));
            filing.setRtntype(filing_status_from_api.getString("rtntype"));
            filing.setArn(filing_status_from_api.getString("arn"));
            filing.setStatus(filing_status_from_api.getString("status"));

            dataFilingList.add(new FillingData(filing.getFy(),filing.getTaxp(),filing.getMof(),filing.getDof(),filing.getRtntype(),filing.getArn(),filing.getStatus()));

            if(i==filing_status_inner_list.length()-1){
                Log.i(TAG,"i==length");
                returnTypeList(dataFilingList);
//                setRecyclerView();
//                getFilingHistoryButton.setVisibility(View.INVISIBLE);
//                view.setVisibility(View.VISIBLE);
//                textViewTitle.setVisibility(View.VISIBLE);
            }
        }

    }

    private void returnTypeList(List<FillingData> dataFilingList) {

        returnTypeList =new ArrayList<>();

        for(int i=0; i<dataFilingList.size();i++){

            Log.i("return datai",String.valueOf(i));

            Map<String, Boolean> returnTypeMap= new HashMap<String, Boolean>();
            returnTypeMap.put("GSTR1",false);
            returnTypeMap.put("GSTR3B",false);

            String GST1date="  -    ";
            String GST3date="  -     ";

            FillingData datai= dataFilingList.get(i);

            String monthSessioni=datai.getTaxp()+datai.getFy();
            Log.i("return datai",monthSessioni);

            if (datai.getRtntype().equals("GSTR1")) {
                Log.i("return","==gst1");
//                returnTypeMap.replace("GSTR1",true);
                returnTypeMap.put("GSTR1",true);
                GST1date= datai.getDof();

            }
            if(datai.getRtntype().equals("GSTR3B")){
                Log.i("return","==gst3");
//                returnTypeMap.replace("GSTR3B",true);
                returnTypeMap.put("GSTR3B",true);
                GST3date= datai.getDof();
            }

            for(int j=i+1; j<dataFilingList.size();j++){
                Log.i("return datai",String.valueOf(j));

                FillingData dataj= dataFilingList.get(j);

                String monthSessionj=dataj.getTaxp()+dataj.getFy();
                Log.i("return dataj",monthSessionj);

                if(monthSessioni.equals(monthSessionj)){
                    Log.i("return","i==j");

                    if (dataj.getRtntype().equals("GSTR1")) {
                        returnTypeMap.put("GSTR1", true);
                        Log.i("return", "==gst1");
                        GST1date = dataj.getDof();
                    }

                    if(dataj.getRtntype().equals("GSTR3B")){
                        Log.i("return","==gst3");
                        returnTypeMap.put("GSTR3B",true);
                        GST3date= dataj.getDof();
                    }

                    dataFilingList.remove(j);
                }

            }
            returnTypeList.add(new ReturnTypeModel(monthSessioni,returnTypeMap.get("GSTR1"),returnTypeMap.get("GSTR3B"),GST1date,GST3date));

            if(i==dataFilingList.size()-1){
                Log.i(TAG,"i==length");
                Log.i("return","return list" +returnTypeList.toString());
                setRecyclerView();
                getFilingHistoryButton.setVisibility(View.INVISIBLE);
                view.setVisibility(View.VISIBLE);
                textViewTitle.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setRecyclerView() {
        AdapterReturnType adapter= new AdapterReturnType(returnTypeList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public  void CreateExcelSheet(View v){
        ////
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static class MySingleton {
        private static MySingleton instance;
        private RequestQueue requestQueue;
        private static Context ctx;

        private MySingleton(Context context) {
            ctx = context;
            requestQueue = getRequestQueue();

        }

        public static synchronized MySingleton getInstance(Context context) {
            if (instance == null) {
                instance = new MySingleton(context);
            }
            return instance;
        }

        public RequestQueue getRequestQueue() {
            if (requestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
            }
            return requestQueue;
        }

        public <T> void addToRequestQueue(Request<T> req) {
            getRequestQueue().add(req);
        }

    }
}

