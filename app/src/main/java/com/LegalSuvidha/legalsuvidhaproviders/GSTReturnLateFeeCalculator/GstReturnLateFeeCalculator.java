package com.LegalSuvidha.legalsuvidhaproviders.GSTReturnLateFeeCalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.LegalSuvidha.legalsuvidhaproviders.RentReceiptGenerator.RentReceiptGenerator;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class GstReturnLateFeeCalculator extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTVTurnover, autoCompleteTVState,autoCompleteTVPendingFrom;
    GstLateFeeAdapter gstLateFeeAdapter;
    RecyclerView recyclerView;
    TextInputLayout stateTextInputLayout, textInputLayoutDOF,textInputLayoutTurnover,textInputLayoutPendingFrom;
    public static TextView totalPenaltyAmountTV,totalPenaltyUnderSGSTAmountTV,totalPenaltyUnderCGSTAmountTV;
    public static  int TOTAL_PENALTY;
    View viewHeading;
    public static String stateCategory;
    DatePickerDialog.OnDateSetListener dateSetListener;
    public static List<GstLateFeeRecyclerModelClass> monthList;
    int pendingFrom;

    public static FirebaseFirestore db= FirebaseFirestore.getInstance();
    CardView cardViewHeadingLateFee,cardViewTotalLateFee;
    HashMap<String,Integer > hashMap = new HashMap<>();
    int days = -1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Calendar c;
    String formattedDate ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst_return_late_fee_calculator);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        autoCompleteTVPendingFrom=findViewById(R.id.autocompleteTextViewReturnPendingFrom);
        textInputLayoutDOF=findViewById(R.id.textInputLayoutDOF);
        autoCompleteTVState=findViewById(R.id.autocompleteTextViewState);
        autoCompleteTVTurnover=findViewById(R.id.autocompleteTextViewTurnOver);
        recyclerView=findViewById(R.id.recyclerViewLateFee);
        stateTextInputLayout=findViewById(R.id.stateTextInputLayout);
        cardViewHeadingLateFee=findViewById(R.id.cardViewHeadingLateFee);
        cardViewTotalLateFee=findViewById(R.id.cardViewTotalLateFee);
        totalPenaltyAmountTV=findViewById(R.id.totalPenaltyAmountTV);
        viewHeading=findViewById(R.id.viewHeading);
        textInputLayoutTurnover=findViewById(R.id.textInputLayoutTurnover);
        textInputLayoutPendingFrom=findViewById(R.id.textInputLayoutPendingFrom);
        totalPenaltyUnderSGSTAmountTV=findViewById(R.id.totalPenaltyUnderSGSTAmountTV);
        totalPenaltyUnderCGSTAmountTV=findViewById(R.id.totalPenaltyUnderCGSTAmountTV);

        monthList=new ArrayList<>();
        gstLateFeeAdapter =new GstLateFeeAdapter(GstReturnLateFeeCalculator.this,monthList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GstReturnLateFeeCalculator.this));
        recyclerView.setAdapter(gstLateFeeAdapter);

        ArrayList<String> turnoverList = new ArrayList<>();
        turnoverList.add("below 5 Crore");
        turnoverList.add("Equal to or above 5 Crore");
        ArrayAdapter<String> adapterTurnover = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, turnoverList);
        autoCompleteTVTurnover.setAdapter(adapterTurnover);


        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.india_states));
        autoCompleteTVState.setAdapter(adapterState);

        ArrayAdapter<String> adapterPendingFrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Return_Pending_From));
        autoCompleteTVPendingFrom.setAdapter(adapterPendingFrom);

       c = Calendar.getInstance();
       formattedDate = sdf.format(c.getTime());
       textInputLayoutDOF.getEditText().setText(formattedDate);

        textInputLayoutDOF.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                final int year = c.get(Calendar.YEAR);
                final int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(GstReturnLateFeeCalculator.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener, year, month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth
            ) {
                // TODO Auto-generated method stub

                monthOfYear = monthOfYear + 1;

                formattedDate = dayOfMonth + "-" + monthOfYear + "-" + year;
                textInputLayoutDOF.getEditText().setText(formattedDate);

            }
        };

        autoCompleteTVTurnover.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("5crore", "onItemSelected: ");
                if(position==0 || parent.getItemAtPosition(position).toString().equals("below 5 Crore")){
                    stateTextInputLayout.setVisibility(View.VISIBLE);
                }
                if(position==1 || parent.getItemAtPosition(position).toString().equals("Equal to or above 5 Crore")){
                    stateTextInputLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hashMap.put("July-17",1701);
        hashMap.put("August-17",1702);
        hashMap.put("September-17",1703);
        hashMap.put("October-17",1704);
        hashMap.put("November-17",1705);
        hashMap.put("December-17",1706);
        hashMap.put("January-18",1801);
        hashMap.put("February-18",1802);
        hashMap.put("March-18",1803);
        hashMap.put("April-18",1804);
        hashMap.put("May-18",1805);
        hashMap.put("June-18",1806);
        hashMap.put("July-18",1807);
        hashMap.put("August-18",1808);
        hashMap.put("September-18",1809);
        hashMap.put("October-18",1810);
        hashMap.put("November-18",1811);
        hashMap.put("December-18",1812);
        hashMap.put("January-19",1901);
        hashMap.put("February-19",1902);
        hashMap.put("March-19",1903);
        hashMap.put("April-19",1904);
        hashMap.put("May-19",1905);
        hashMap.put("June-19",1906);
        hashMap.put("July-19",1907);
        hashMap.put("August-19",1908);
        hashMap.put("September-19",1909);
        hashMap.put("October-19",1910);
        hashMap.put("November-19",1911);
        hashMap.put("December-19",1912);
        hashMap.put("January-20",2001);
        hashMap.put("February-20",2002);
        hashMap.put("March-20",2003);
        hashMap.put("April-20",2004);
        hashMap.put("May-20",2005);
        hashMap.put("June-20",2006);
        hashMap.put("July-20",2007);
        hashMap.put("August-20",2008);
        hashMap.put("September-20",2009);
        hashMap.put("October-20",2010);
        hashMap.put("November-20",2011);
        hashMap.put("December-20",2012);
    }

    private boolean validateTurnover() {
        String val = textInputLayoutTurnover.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayoutTurnover.setError("Field cannot be null");
            return false;
        } else {
            textInputLayoutTurnover.setError(null);
            textInputLayoutTurnover.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateState() {
        String val = stateTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            stateTextInputLayout.setError("Field cannot be null");
            return false;
        } else {
            stateTextInputLayout.setError(null);
            stateTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }


    public void CalculateLateFee(View v){

        if(!validateTurnover()){
            return;
        }
        TOTAL_PENALTY=0;
        pendingFrom = 1701;

        if(autoCompleteTVPendingFrom.getText()==null){
//            autoCompleteTVPendingFrom.setListSelection(0);
//            textInputLayoutPendingFrom.getEditText().setText("July-17");

        }else {
            for(Map.Entry<String,Integer> entry : hashMap.entrySet()){

                if(autoCompleteTVPendingFrom.getText().toString().trim().equals(entry.getKey())){
                    pendingFrom=entry.getValue();
                    Log.i("pending", String.valueOf(pendingFrom));
                    break;
                }
            }

        }

        if(autoCompleteTVTurnover.getText().toString().equals("Equal to or above 5 Crore")){
            stateTextInputLayout.setVisibility(View.GONE);
            Log.i("Days","equal to 5 Crore");
            setRecyclerView();


        }else if(autoCompleteTVTurnover.getText().toString().equals("below 5 Crore")) {
            stateCategory="";
            if (stateTextInputLayout.getVisibility() == View.GONE) {
                stateTextInputLayout.setVisibility(View.VISIBLE);
            }else{
                if(!validateState()){
                    return;
                }

                String state=stateTextInputLayout.getEditText().getText().toString().trim();

                String[] AState= {"Chhattisgarh", "Madhya Pradesh", "Gujarat", "Maharashtra", "Karnataka", "Goa", "Kerala", "Tamil Nadu", "Telangana", "Andhra Pradesh", "Daman and Diu" , "Dadra and Nagar Haveli", "Puducherry", "Andaman and Nicobar Islands","Lakshadweep"};
                String[] BState={ "Himachal Pradesh", "Punjab", "Uttarakhand", "Haryana", "Rajasthan", "Uttar Pradesh", "Bihar", "Sikkim", "Arunachal Pradesh", "Nagaland", "Manipur", "Mizoram", "Tripura", "Meghalaya", "Assam", "West Bengal", "Jharkhand", "Odisha", "Jammu and Kashmir", "Ladakh", "Chandigarh" ,"Delhi"};

                if(Arrays.asList(AState).contains(state)){
                    stateCategory="A";

                }else if(Arrays.asList(BState).contains(state)){
                    stateCategory="B";

                }
                setStateRecyclerView();
            }
        }
    }

    private void setStateRecyclerView() {

        monthList=new ArrayList<>();

        db.collection("Gst Return Penalty")
                .orderBy("id")
                .whereGreaterThanOrEqualTo("id",pendingFrom)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            GstLateFeeRecyclerModelClass month = documentSnapshot.toObject(GstLateFeeRecyclerModelClass.class);
                            month.setDocumentID(documentSnapshot.getId());

                            Log.i("Duedate B1", month.getReturnMonth());


                            if(stateCategory.equals("A")){
                                if(!month.getDueDateA().isEmpty()){
                                    month.setDueDate(month.getDueDateA());
                                }

                            }else if(stateCategory.equals("B")){
                                Log.i("Duedate B1", month.getDueDateB());
                                if(!month.getDueDateB().isEmpty()){
                                    month.setDueDate(month.getDueDateB());
                                    Log.i("Duedate B", month.getDueDate());
                                }

                            }

                            month.setNilReturn("Yes");
                            month.setDateOfFiling(formattedDate);

                            if(month.getPenalty()==-1){
                                try {
                                    Date dueDate = sdf.parse(month.getDueDate());
                                    Log.i("Duedate penalty", month.getDueDate());
                                    Date endDate = sdf.parse(month.getDateOfFiling());

                                    long diff = endDate.getTime() - dueDate.getTime();
                                    TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                    Log.i("Days time unit: ", String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                                    days= (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                                    if(days>=-1){

                                        //20-500days
                                        if (days >= 500) {
                                            month.setPenalty(10000);
//                                        clicked.setPenalty(10000);

                                            Log.i("Days", "1000");
                                        } else {
                                            int penalty = days * 20;
                                            month.setPenalty(penalty);
                                            Log.i("Days", String.valueOf(penalty));
                                        }
                                    }

                                }catch (Exception e){
                                    Log.i("Days",e.getMessage());
                                }

                            }

                            if(month.getPenalty()>0){
                               TOTAL_PENALTY+=month.getPenalty();

                            }

                            monthList.add(month);

                        }


                        try {
                            totalPenaltyAmountTV.setText(String.valueOf(TOTAL_PENALTY));
                            int CGST=TOTAL_PENALTY/2;
                            totalPenaltyUnderCGSTAmountTV.setText(String.valueOf(CGST));
                            totalPenaltyUnderSGSTAmountTV.setText(String.valueOf(CGST));
                            cardViewHeadingLateFee.setVisibility(View.VISIBLE);
                            viewHeading.setVisibility(View.VISIBLE);
                        }catch (Exception e){
                            Log.e("TOTAL_PENALTY",e.getMessage());
                        }



                        recyclerView.setAdapter(null);
                        gstLateFeeAdapter =new GstLateFeeAdapter(GstReturnLateFeeCalculator.this,monthList);
                        gstLateFeeAdapter.setHasStableIds(true);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(GstReturnLateFeeCalculator.this));
                        recyclerView.setAdapter(gstLateFeeAdapter);

                        cardViewTotalLateFee.setVisibility(View.VISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GstReturnLateFeeCalculator.this,"Something went wrong\n"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("Error",e.toString());
                    }
                });

    }




    private void setRecyclerView() {

        monthList=new ArrayList<>();

        db.collection("Gst Return Penalty")
                .orderBy("id")
                .whereGreaterThanOrEqualTo("id",pendingFrom)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            GstLateFeeRecyclerModelClass month = documentSnapshot.toObject(GstLateFeeRecyclerModelClass.class);
                            month.setDocumentID(documentSnapshot.getId());
                            month.setNilReturn("Yes");
                            month.setDateOfFiling(formattedDate);



                            if(month.getPenalty()==-1){
                                try {
                                    Date dueDate = sdf.parse(month.getDueDate());
                                    Date endDate = sdf.parse(month.getDateOfFiling());

                                    long diff = endDate.getTime() - dueDate.getTime();
                                    TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                    Log.i("Days time unit: ", String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                                    days= (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                                    if(days>=-1){

                                        //20-500days
                                        if (days >= 500) {
                                            month.setPenalty(10000);
//                                        clicked.setPenalty(10000);

                                            Log.i("Days", "1000");
                                        } else {
                                            int penalty = days * 20;
                                            month.setPenalty(penalty);
                                            Log.i("Days", String.valueOf(penalty));
                                        }
                                    }

                                }catch (Exception e){
                                    Log.i("Days",e.getMessage());
                                }



                            }

                            if(month.getPenalty()>0){
                                TOTAL_PENALTY+=month.getPenalty();
                            }

                            monthList.add(month);

                        }

                        try {
                            totalPenaltyAmountTV.setText(String.valueOf(TOTAL_PENALTY));
                            int CGST=TOTAL_PENALTY/2;
                            totalPenaltyUnderCGSTAmountTV.setText(String.valueOf(CGST));
                            totalPenaltyUnderSGSTAmountTV.setText(String.valueOf(CGST));
                            cardViewHeadingLateFee.setVisibility(View.VISIBLE);
                            viewHeading.setVisibility(View.VISIBLE);
                        }catch (Exception e){
                            Log.e("TOTAL_PENALTY",e.getMessage());
                        }

                        recyclerView.setAdapter(null);
                        gstLateFeeAdapter =new GstLateFeeAdapter(GstReturnLateFeeCalculator.this,monthList);
                        gstLateFeeAdapter.setHasStableIds(true);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(GstReturnLateFeeCalculator.this));
                        recyclerView.setAdapter(gstLateFeeAdapter);
                        cardViewTotalLateFee.setVisibility(View.VISIBLE);


//                        gstLateFeeAdapter.setOnItemSelectedListener(new GstLateFeeAdapter.OnItemSelectedListener() {
//                            @Override
//                            public void onNilReturnChange(int spinnerPosition, int adapterPosition) {
//
//
//
//                                for(int i=adapterPosition; i<monthList.size();i++){
//
//
//                                    GstLateFeeRecyclerModelClass clicked=monthList.get(i);
//                                    if(list[spinnerPosition]==clicked.getNilReturn()){
//
//                                        try {
//                                            Date dueDate = sdf.parse(clicked.getDueDate());
//                                            Date endDate = sdf.parse(clicked.getDateOfFiling());
//
//                                            long diff = endDate.getTime() - dueDate.getTime();
//                                            TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//                                            Log.i("Days time unit: ", String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
//                                            days= (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//
//                                        }catch (Exception e){
//                                            Log.i("Days",e.getMessage());
//                                        }
//
////                                      Log.i("Days",parent.getItemAtPosition(position).toString());
//
//                                        if(days!=-1 && clicked.getPenalty()!=0){
//                                            if(spinnerPosition==0){
//                                                Log.i("Days","Y");
//                                                clicked.setNilReturn("Yes");
//
//                                                //20-500days
//                                                if(days>=500){
//                                                    clicked.setPenalty(1);
////                                        clicked.setPenalty(10000);
//
//                                                    Log.i("Days","1000");
//                                                }else{
//                                                    int penalty= days*20;
//                                                    clicked.setPenalty(penalty);
//                                                    Log.i("Days",String.valueOf(penalty));
//                                                }
//
//                                            } else if (spinnerPosition==1) {
//                                                Log.i("Days","N");
////                                          holder.spinnerNilReturn.setSelection(1);
//                                                clicked.setNilReturn("No");
//
//                                                //50-200days
//                                                if(days>=200){
//                                                    clicked.setPenalty(10000);
//                                                    Log.i("Days","1000");
//                                                }else{
//                                                    int penalty= days*50;
//                                                    clicked.setPenalty(penalty);
//                                                    Log.i("Days",String.valueOf(penalty));
//                                                }
//                                            }
//                                        }
//
//                                        monthList.add(i,clicked);
//
//                                    }
//
//
//
//                                }
//                                gstLateFeeAdapter.notifyDataSetChanged();
//                                gstLateFeeAdapter.notifyItemRangeChanged(adapterPosition,monthList.size()-adapterPosition);
//                            }
//                        });
//                        gstLateFeeAdapter.setOnItemClickListener(new GstLateFeeAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(int position) {
//                                Log.i("Days",monthList.toString());
//
////                                gstLateFeeAdapter.notifyDataSetChanged();
////                                gstLateFeeAdapter.notify();
////                                gstLateFeeAdapter.notifyItemRangeChanged(position,monthList.size());
////                                gstLateFeeAdapter.notifyAll();
//
////                                recyclerView.setAdapter(null);
////                                gstLateFeeAdapter = new GstLateFeeAdapter(GstReturnLateFeeCalculator.this, monthList);
////                                recyclerView.setHasFixedSize(true);
////                                recyclerView.setLayoutManager(new LinearLayoutManager(GstReturnLateFeeCalculator.this));
////                                recyclerView.setAdapter(gstLateFeeAdapter);
//
//                            }
//
//                            @Override
//                            public void onDateSelected(String date, Date dateOfFiling, int adapterPosition) {
//
//                                for (int i = adapterPosition; i < monthList.size(); i++) {
//                                    Log.i("days"," i" +i);
//
//                                    GstLateFeeRecyclerModelClass clickedMonth = monthList.get(i);
//                                    clickedMonth.setDateOfFiling(date);
//
////                                  holder.dateOfFillingTV.setText(clickedMonth.getDateOfFiling());
//                                    Log.i("Days",clickedMonth.getDateOfFiling());
//
//                                    Calendar c = Calendar.getInstance();
//                                    Date dueDate=c.getTime();
//
//                                    try {
//                                        dueDate = sdf.parse(clickedMonth.getDueDate());
//
//                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                                            long daysBetween = ChronoUnit.DAYS.between( dueDate.toInstant(),dateOfFiling.toInstant());
//                                            Log.i("Days chrono: ", String.valueOf(daysBetween));
//                                        }
//                                    } catch (Exception e) {
//                                    }
//
//                                    long diff = dateOfFiling.getTime() - dueDate.getTime();
//                                    TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//                                    Log.i("Days time unit: ", String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
//                                    days= (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//
//                                    if (days != -1 && clickedMonth.getPenalty() != 0) {
//                                        if (clickedMonth.getNilReturn().equals("Yes")) {
//                                            //20-500days
//                                            if (days >= 500) {
////                                        holder.penaltyTV.setText("10000");
//                                                clickedMonth.setPenalty(10000);
//                                                Log.i("Days", "1000");
//                                            } else {
//                                                int penalty = days * 20;
//                                                clickedMonth.setPenalty(penalty);
////                                        holder.penaltyTV.setText(penalty);
//                                                Log.i("Days", String.valueOf(penalty));
//                                            }
//
//
//                                        } else if (clickedMonth.getNilReturn().equals("No")) {
//                                            //50-200days
//                                            if (days >= 200) {
//                                                clickedMonth.setPenalty(10000);
////                                        holder.penaltyTV.setText("10000");
//                                                Log.i("Days", "1000");
//
//
//                                            } else {
//                                                int penalty = days * 50;
//                                                clickedMonth.setPenalty(penalty);
////                                        holder.penaltyTV.setText(penalty);
//                                                Log.i("Days", String.valueOf(penalty));
//
//                                            }
//                                        }
//                                    }
//
//                                    monthList.add(i,clickedMonth);
//                                }
//                            }
//
//                        });

//                            @Override
//                            public void onDateClicked(int position) {
//                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                                GstLateFeeRecyclerModelClass clickedMonth=monthList.get(position);
//
//                                 DatePickerDialog.OnDateSetListener dateListener;
//
//                                dateListener = new DatePickerDialog.OnDateSetListener() {
//
//                                    @Override
//                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth
//                                    ) {
//                                        // TODO Auto-generated method stub
//
//                                        monthOfYear = monthOfYear + 1;
//
//                                        String chosenDate = dayOfMonth + "/" + monthOfYear + "/" + year;
//                                        calendar = Calendar.getInstance();
//                                        Date endDate = calendar.getTime();
//                                        try {
//                                            endDate = sdf.parse(chosenDate);
//                                        }catch (Exception e){
//                                            e.printStackTrace();
//                                        }
//
//
//                                        for(int i=position; i<monthList.size();i++){
//
////                                            String selectedDocID=clickedMonth.getDocumentID();
//                                            clickedMonth.setDateOfFiling(chosenDate);
//
//                                            try {
//                                                Date dueDate = sdf.parse(clickedMonth.getDueDate());
//
//                                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                                                    long daysBetween = ChronoUnit.DAYS.between(endDate.toInstant(), dueDate.toInstant());
//                                                    Log.i("Days chrono: ", String.valueOf(daysBetween));
//                                                }
//
//
//                                                long diff =endDate.getTime() - dueDate.getTime();
//                                                TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//                                                Log.i("Days time unit: ", String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
//
//
//                                                int days = (int)( endDate.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
//                                                Log.i("Days",String.valueOf(days));
//
//
//                                                if(days!=-1 && clickedMonth.getPenalty()!=0){
//                                                    if(clickedMonth.getNilReturn()=="Yes"){
//                                                        //20-500days
//                                                        if(days>=500){
//                                                            clickedMonth.setPenalty(10000);
//                                                            Log.i("Days","1000");
//                                                        }else{
//                                                            int penalty= days*20;
//                                                            clickedMonth.setPenalty(penalty);
//                                                            Log.i("Days",String.valueOf(penalty));
//                                                        }
//
//
//                                                    } else if (clickedMonth.getNilReturn()=="No") {
//                                                        //50-200days
//                                                        if(days>=200){
//                                                            clickedMonth.setPenalty(10000);
//                                                            Log.i("Days","1000");
//                                                        }else{
//                                                            int penalty= days*50;
//                                                            clickedMonth.setPenalty(penalty);
//                                                            Log.i("Days",String.valueOf(penalty));
//                                                        }
//                                                    }
//                                                }

//                                            }catch (Exception e){ }



//                                        recyclerView.setAdapter(null);
//                                        gstLateFeeAdapter =new GstLateFeeAdapter(GstReturnLateFeeCalculator.this,monthList);
//                                        recyclerView.setHasFixedSize(true);
//                                        recyclerView.setLayoutManager(new LinearLayoutManager(GstReturnLateFeeCalculator.this));
//                                        recyclerView.setAdapter(gstLateFeeAdapter);

//
//                                    }
//                                };
//                            }
//
//                            @Override
//                            public void onItemSelected(int positionAdapter, int position) {
//
//                                GstLateFeeRecyclerModelClass month=monthList.get(positionAdapter);
//                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//                                for(int i=positionAdapter; i<monthList.size();i++){
//                                    try {
//                                        Date dueDate = sdf.parse(month.getDueDate());
//                                        Date endDate = sdf.parse(month.getDateOfFiling());
//
//                                        int days = (int)( endDate.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
//                                        Log.i("Days",String.valueOf(days));
//
//
//                                        if(days!=-1 && month.getPenalty()!=0){
//                                            if(position==0||list[position]=="Yes"){
//                                                month.setNilReturn("Yes");
//                                                //20-500days
//                                                if(days>=500){
//                                                    month.setPenalty(10000);
//                                                    Log.i("Days","1000");
//                                                }else{
//                                                    int penalty= days*20;
//                                                    month.setPenalty(penalty);
//                                                    Log.i("Days",String.valueOf(penalty));
//                                                }
//
//
//                                            } else if (position==1||list[1].equals("No")) {
//                                                month.setNilReturn("No");
//                                                //50-200days
//                                                if(days>=200){
//                                                    month.setPenalty(10000);
//                                                    Log.i("Days","1000");
//                                                }else{
//                                                    int penalty= days*50;
//                                                    month.setPenalty(penalty);
//                                                    Log.i("Days",String.valueOf(penalty));
//                                                }
//                                            }
//                                        }
//
//                                    }catch (Exception e){ }
//                                }
//
//                                recyclerView.setAdapter(null);
//                                gstLateFeeAdapter =new GstLateFeeAdapter(GstReturnLateFeeCalculator.this,monthList);
//                                recyclerView.setHasFixedSize(true);
//                                recyclerView.setLayoutManager(new LinearLayoutManager(GstReturnLateFeeCalculator.this));
//                                recyclerView.setAdapter(gstLateFeeAdapter);
//
//                            }
//                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GstReturnLateFeeCalculator.this,"Something went wrong\n"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("Error",e.toString());
                    }
                });

    }

//    @Override
//    public void onStart() {
//        super.onStart();
////        if(gstLateFeeFirestoreAdapter!=null){
//        Log.d("Errorr","On Start");
//            gstLateFeeFirestoreAdapter.startListening();
////        }
//
//    }
//
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.d("Errorr","On Stop");
//        gstLateFeeFirestoreAdapter.stopListening();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Errorr","On Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Errorr","On Resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Errorr","On Restart");
    }
}