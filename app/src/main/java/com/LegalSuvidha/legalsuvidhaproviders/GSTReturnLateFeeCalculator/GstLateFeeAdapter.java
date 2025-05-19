package com.LegalSuvidha.legalsuvidhaproviders.GSTReturnLateFeeCalculator;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.LegalSuvidha.legalsuvidhaproviders.GSTReturnLateFeeCalculator.GstReturnLateFeeCalculator.TOTAL_PENALTY;
import static com.LegalSuvidha.legalsuvidhaproviders.GSTReturnLateFeeCalculator.GstReturnLateFeeCalculator.totalPenaltyAmountTV;

public class GstLateFeeAdapter extends RecyclerView.Adapter<GstLateFeeAdapter.GstLateFeeVH>{


    Context context;
    private static List<GstLateFeeRecyclerModelClass> monthList;

//    private OnItemClickListener listener;
//    private OnItemSelectedListener selectedListener;

    ArrayAdapter adapterS;
    String formattedDate;
//    int itemSpinner=0;

//    boolean b=false;
    String[] list = { "Yes", "No"};
    int days = -1;
    int adapterp;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    DatePickerDialog.OnDateSetListener dateListener;
    Calendar calendar;



    public GstLateFeeAdapter(Context context, List<GstLateFeeRecyclerModelClass> monthList) {
        GstLateFeeAdapter.monthList =monthList;
        this.context = context;
        Calendar c = Calendar.getInstance();
        formattedDate = sdf.format(c.getTime());
        adapterS = new ArrayAdapter(context,android.R.layout.simple_spinner_item,list);
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }


    @NonNull
    @Override
    public GstLateFeeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_gst_return_late_fee, parent, false);

        return new GstLateFeeVH(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull GstLateFeeVH holder, int positionAdapter) {


        GstLateFeeRecyclerModelClass month = monthList.get(positionAdapter);
        holder.dateOfFillingTV.setText(month.getDateOfFiling());
        holder.returnMonthTV.setText(month.getReturnMonth());
        holder.penaltyTV.setText(String.valueOf(month.getPenalty()));
        holder.dueDateTV.setText(month.getDueDate());
        holder.spinnerNilReturn.setAdapter(adapterS);
        holder.setIsRecyclable(false);

        Log.i("spinner", String.valueOf(month.getNilReturn()));

        if(month.getNilReturn().equals("No")){
            holder.spinnerNilReturn.setSelection(1);
        }else if(month.getNilReturn().equals("Yes" )) {
            holder.spinnerNilReturn.setSelection(0);
        }




//        holder.dateOfFillingTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                calendar = Calendar.getInstance();
//                final int year = calendar.get(Calendar.YEAR);
//                final int month = calendar.get(Calendar.MONTH);
//                final int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener, year, month,day);
//
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                datePickerDialog.show();
//            }
//        });

//        dateListener = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth
//            ) {
//                // TODO Auto-generated method stub
//
//                if(  RecyclerView.NO_POSITION != holder.getAdapterPosition()) {
//
//                    Log.i("days"," date change");
//
//                    monthOfYear = monthOfYear + 1;
//
//                    formattedDate = dayOfMonth + "-" + monthOfYear + "-" + year;
//                    calendar = Calendar.getInstance();
//                    Date endDate = calendar.getTime();
//                    try {
//                        endDate = sdf.parse(formattedDate);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
////                    int i=positionAdapter;
////
//
//                    for (int i = positionAdapter; i < monthList.size(); i++) {
//                        Log.i("days"," i" +i);
//
//                        GstLateFeeRecyclerModelClass clickedMonth = monthList.get(i);
//                        clickedMonth.setDateOfFiling(formattedDate);
//
////                        holder.dateOfFillingTV.setText(clickedMonth.getDateOfFiling());
//                        Log.i("Days",month.getDateOfFiling());
//
//                        Date dueDate=calendar.getTime();
//
//                        try {
//                            dueDate = sdf.parse(clickedMonth.getDueDate());
//
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                                long daysBetween = ChronoUnit.DAYS.between( dueDate.toInstant(),endDate.toInstant());
//                                Log.i("Days chrono: ", String.valueOf(daysBetween));
//                            }
//                        } catch (Exception e) {
//                        }
//
//                            long diff = endDate.getTime() - dueDate.getTime();
//                            TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//                            Log.i("Days time unit: ", String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
//                            days= (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//
//                            if (days != -1 && clickedMonth.getPenalty() != 0) {
//                                if (clickedMonth.getNilReturn().equals("Yes")) {
//                                    //20-500days
//                                    if (days >= 500) {
////                                        holder.penaltyTV.setText("10000");
//                                        clickedMonth.setPenalty(10000);
//                                        Log.i("Days", "1000");
//                                    } else {
//                                        int penalty = days * 20;
//                                        clickedMonth.setPenalty(penalty);
////                                        holder.penaltyTV.setText(penalty);
//                                        Log.i("Days", String.valueOf(penalty));
//                                    }
//
//
//                                } else if (clickedMonth.getNilReturn().equals("No")) {
//                                    //50-200days
//                                    if (days >= 200) {
//                                        clickedMonth.setPenalty(10000);
////                                        holder.penaltyTV.setText("10000");
//                                        Log.i("Days", "1000");
//
//
//                                    } else {
//                                        int penalty = days * 50;
//                                        clickedMonth.setPenalty(penalty);
////                                        holder.penaltyTV.setText(penalty);
//                                        Log.i("Days", String.valueOf(penalty));
//
//                                    }
//                                }
//                            }
//
//                        monthList.add(i,clickedMonth);
//                    }
////                    notifyDataSetChanged();
//
//
//                }
//
//            }
//        };


//        holder.spinnerNilReturn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////
////                    itemSpinner = position;
//
//                    if(RecyclerView.NO_POSITION != holder.getAdapterPosition()) {
//
//                        int i=positionAdapter;
//
////                        for(int i=positionAdapter; i<monthList.size();i++){
//
//                            GstLateFeeRecyclerModelClass clicked=monthList.get(i);
//
//                            if(!list[position].equals(clicked.getNilReturn())){
//                                try {
//                                    Date dueDate = sdf.parse(clicked.getDueDate());
//                                    Date endDate = sdf.parse(clicked.getDateOfFiling());
//
//                                    long diff = endDate.getTime() - dueDate.getTime();
//                                    TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//                                    Log.i("Days time unit: ", String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
//                                    days= (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//
//                                }catch (Exception e){
//                                    Log.i("Days",e.getMessage());
//                                }
//
//                                Log.i("Days",parent.getItemAtPosition(position).toString());
//
//                                if(days!=-1 && clicked.getPenalty()!=0){
//                                    if(position==0|| parent.getItemAtPosition(position).toString().equals("Yes")){
//                                        Log.i("Days","Y");
//                                        clicked.setNilReturn("Yes");
//
//                                        //20-500days
//                                        if(days>=500){
//                                            clicked.setPenalty(1);
////                                        clicked.setPenalty(10000);
//
//                                            Log.i("Days","1000");
//                                        }else{
//                                            int penalty= days*20;
//                                            clicked.setPenalty(penalty);
//                                            Log.i("Days",String.valueOf(penalty));
//                                        }
//
//
//                                    } else if (position==1|| parent.getItemAtPosition(position).toString().equals("No")) {
//                                        Log.i("Days","N");
////                                holder.spinnerNilReturn.setSelection(1);
//                                        clicked.setNilReturn("No");
//                                        //50-200days
//                                        if(days>=200){
//                                            clicked.setPenalty(10000);
//                                            Log.i("Days","1000");
//                                        }else{
//                                            int penalty= days*50;
//                                            clicked.setPenalty(penalty);
//                                            Log.i("Days",String.valueOf(penalty));
//                                        }
//                                    }
//                                }
//
////                                monthList.add(i,clicked);
//                            }
////                        }
//                    }
//                }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }



    public class GstLateFeeVH extends RecyclerView.ViewHolder{
        TextView returnMonthTV,dueDateTV,dateOfFillingTV,penaltyTV;
        Spinner spinnerNilReturn;



        public GstLateFeeVH(@NonNull View itemView) {
            super(itemView);
            returnMonthTV=itemView.findViewById(R.id.returnMonthTV);
            dueDateTV=itemView.findViewById(R.id.dueDateTV);
            dateOfFillingTV=itemView.findViewById(R.id.dateOfFilingReturnTV);
            penaltyTV=itemView.findViewById(R.id.penaltyAmountTV);
            spinnerNilReturn=itemView.findViewById(R.id.nilReturnSpinner);




            dateOfFillingTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    calendar = Calendar.getInstance();
                    final int year = calendar.get(Calendar.YEAR);
                    final int month = calendar.get(Calendar.MONTH);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    adapterp=getAdapterPosition();
                    Log.i("date", String.valueOf(adapterp));

                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener, year, month,day);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            });

            dateListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // TODO Auto-generated method stub

                    if(  RecyclerView.NO_POSITION != getAdapterPosition()) {
                        Log.i("days"," date change");
                        monthOfYear = monthOfYear + 1;
                        formattedDate = dayOfMonth + "-" + monthOfYear + "-" + year;
                        calendar = Calendar.getInstance();
                        Date endDate = calendar.getTime();
                        try {
                            endDate = sdf.parse(formattedDate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                        int position= getAdapterPosition();
                        int position= adapterp;
                        Log.i("date", String.valueOf(position));

                        if (position != RecyclerView.NO_POSITION && !monthList.get(position).getDateOfFiling().equals(formattedDate)) {
//                            dateOfFillingTV.setText(formattedDate);

                            GstLateFeeRecyclerModelClass clickedMonth = monthList.get(position);
                            clickedMonth.setDateOfFiling(formattedDate);

                            Date dueDate;
                            try {
                            dueDate = sdf.parse(clickedMonth.getDueDate());

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                long daysBetween = ChronoUnit.DAYS.between( dueDate.toInstant(),endDate.toInstant());
                                Log.i("Days chrono: ", String.valueOf(daysBetween));
                                long diff = endDate.getTime() - dueDate.getTime();
                                TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                Log.i("Days time unit", String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                                days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                            }
                        } catch (Exception e) {
                        }

                           int oldPenalty= clickedMonth.getPenalty();

                            if (days != -1 && clickedMonth.getPenalty() != 0) {
                                if (clickedMonth.getNilReturn().equals("Yes")) {
                                    //20-500days
                                    if (days >= 500) {
                                        clickedMonth.setPenalty(10000);
                                        Log.i("Days", "1000");
                                    } else {
                                        int penalty = days * 20;
                                        clickedMonth.setPenalty(penalty);
                                        Log.i("Days", String.valueOf(penalty));
                                    }

                                } else if (clickedMonth.getNilReturn().equals("No")) {
                                    //50-200days
                                    if (days >= 200) {
                                        clickedMonth.setPenalty(10000);
                                        Log.i("Days", "1000");
                                    } else {
                                        int penalty = days * 50;
                                        clickedMonth.setPenalty(penalty);
                                        Log.i("Days", String.valueOf(penalty));

                                    }
                                }
                                int newPenalty=clickedMonth.getPenalty();

                                if(newPenalty!=oldPenalty){

                                    try {
                                        TOTAL_PENALTY=TOTAL_PENALTY+newPenalty-oldPenalty;
                                        totalPenaltyAmountTV.setText(String.valueOf(TOTAL_PENALTY));
                                    }catch (Exception e){
                                        Log.e("TOTAL_PENALTY",e.getMessage());
                                    }
                                }

//                                penaltyTV.setText( String.valueOf(clickedMonth.getPenalty()));
                                notifyDataSetChanged();
                            }

                        }
                    }
//
                    }


            };

//            dateOfFillingTV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position= getAdapterPosition();
//
//                    if(position!=RecyclerView.NO_POSITION ) {
//                        listener.onItemClick(position);
//                    }
//                }
//            });

            spinnerNilReturn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String item = parent.getItemAtPosition(position).toString();
                    Log.i("spinner", item);
                    int adapterPosition = getAdapterPosition();
                    Log.i("spinner", String.valueOf(adapterPosition));
                    Log.i("spinner", monthList.get(adapterPosition).getNilReturn());

                    if (getAdapterPosition() != RecyclerView.NO_POSITION && !monthList.get(adapterPosition).getNilReturn().equals(item)) {
                        GstLateFeeRecyclerModelClass clickedMonth = monthList.get(adapterPosition);

                        clickedMonth.setNilReturn(item);
                        Log.i("spinner", monthList.get(adapterPosition).getNilReturn());

                        try {
                            Date dueDate = sdf.parse(clickedMonth.getDueDate());
                            Date endDate = sdf.parse(clickedMonth.getDateOfFiling());

                            long diff = endDate.getTime() - dueDate.getTime();
                            TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                            Log.i("Days time unit", String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                            days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                        } catch (Exception e) {
                            Log.i("Days", e.getMessage());
                        }

                        int oldPenalty=clickedMonth.getPenalty();

                        if (days >= -1 && clickedMonth.getPenalty() != 0) {

                            if (position == 0 || item.equals("Yes")) {

                                //20-500days
                                if (days >= 500) {
                                        clickedMonth.setPenalty(10000);

                                    Log.i("Days", "1000");
                                } else {
                                    int penalty = days * 20;
                                    clickedMonth.setPenalty(penalty);
                                    Log.i("Days", String.valueOf(penalty));
                                }


                            } else if (position == 1 || item.equals("No")) {
                                Log.i("Days", "N");

                                //50-200days
                                if (days >= 200) {
                                    clickedMonth.setPenalty(10000);
                                    Log.i("Days", "1000");
                                } else {
                                    int penalty = days * 50;
                                    clickedMonth.setPenalty(penalty);
                                    Log.i("Days", String.valueOf(penalty));
                                }
                            }

                            int newPenalty=clickedMonth.getPenalty();

                            if(newPenalty!=oldPenalty){

                                try {
                                    TOTAL_PENALTY=TOTAL_PENALTY+newPenalty-oldPenalty;
                                    totalPenaltyAmountTV.setText(String.valueOf(TOTAL_PENALTY));
                                }catch (Exception e){
                                    Log.e("TOTAL_PENALTY",e.getMessage());
                                }
                            }

//                            penaltyTV.setText( String.valueOf(clickedMonth.getPenalty()));
                            notifyDataSetChanged();
                        }
                    }

//

//                    if(getAdapterPosition()!=RecyclerView.NO_POSITION  && monthList.contains(getAdapterPosition())) {
////                        selectedListener.onNilReturnChange(position, getAdapterPosition());
//                    }

            }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
//
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    int position= getAdapterPosition();
//
//                    if(position!=RecyclerView.NO_POSITION  && monthList.contains(position)) {
//                        listener.onItemClick(position);
//                    }
//                }
//            });

        }
    }





//    public interface OnItemClickListener{
//        void onItemClick(int position);
//        void onDateSelected(String date, Date dateOfFiling, int adapterPosition);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.listener=listener;
//    }
//
//    public interface OnItemSelectedListener{
//        void onNilReturnChange(int spinnerPosition, int adapterPosition);
//    }
//
//    public void setOnItemSelectedListener(OnItemSelectedListener selectedListener){
//        this.selectedListener=selectedListener;
//    }

}