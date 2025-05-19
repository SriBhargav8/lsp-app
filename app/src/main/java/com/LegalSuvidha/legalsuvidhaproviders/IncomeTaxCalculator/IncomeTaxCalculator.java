package com.LegalSuvidha.legalsuvidhaproviders.IncomeTaxCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class IncomeTaxCalculator extends AppCompatActivity {

    TextInputLayout incomeTextInputLayout, investmentTextInputLayout, TDSTextInputLayout, ageTextInputLayout;
    AutoCompleteTextView autoCompleteTextViewAge;
    TextView oldSlabTaxableIncomeTV,newSlabTaxableIncomeTV,oldSlab5TV,newSlab5TV,newSlab10TV,oldSlab10TV, newSlab15TV,oldSlab15TV, newSlab20TV,oldSlab20TV,newSlab25TV, newSlab30TV, oldSlab30TV, cessOldSlabTV, cessNewSlabTV, rebateOldSlabTV, rebateNewSlabTV, incomeTaxNewSlabTV,incomeTaxOldSlabTV,incomeTaxRefundNewSlabTV,incomeTaxRefundOldSlabTV;
    float oldSlabTaxableIncome,newSlabTaxableIncome,oldSlab5,newSlab5,newSlab10,newSlab15, newSlab20,oldSlab20,newSlab25, newSlab30, oldSlab30,cessOldSlab, cessNewSlab, rebateOldSlab, rebateNewSlab,incomeTaxNewSlab,incomeTaxOldSlab,incomeTaxRefundNewSlab,incomeTaxRefundOldSlab;
    float income,investment,TDS;
    Button calculateIncomeTax;
    ConstraintLayout constraintLayoutITDetails;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_tax_calculator);

        progressBar=findViewById(R.id.progressBar);
        calculateIncomeTax=findViewById(R.id.incometaxButton);
        constraintLayoutITDetails=findViewById(R.id.constraintLayoutIncomeTaxDetail);
        incomeTextInputLayout=findViewById(R.id.textInputLayoutIncome);
        investmentTextInputLayout=findViewById(R.id.textInputLayoutInvestment);
        TDSTextInputLayout=findViewById(R.id.textInputLayoutTDS);
        ageTextInputLayout=findViewById(R.id.textInputLayoutAge);
        autoCompleteTextViewAge=findViewById(R.id.autocompleteTextViewAge);
        oldSlabTaxableIncomeTV=findViewById(R.id.taxableSalaryOldSlab);
        newSlabTaxableIncomeTV=findViewById(R.id.taxableSalaryNewSlab);
        oldSlab5TV=findViewById(R.id.percent5OldSlab);
        newSlab5TV=findViewById(R.id.percent5NewSlab);
        oldSlab10TV=findViewById(R.id.percent10OldSlab);
        newSlab10TV=findViewById(R.id.percent10NewSlab);
        oldSlab15TV=findViewById(R.id.percent15OldSlab);
        newSlab15TV=findViewById(R.id.percent15NewSlab);
        oldSlab20TV=findViewById(R.id.percent20OldSlab);
        newSlab20TV=findViewById(R.id.percent20NewSlab);
        newSlab25TV=findViewById(R.id.percent25NewSlab);
        oldSlab30TV=findViewById(R.id.percent30OldSlab);
        newSlab30TV=findViewById(R.id.percent30NewSlab);
        cessNewSlabTV=findViewById(R.id.cessNewSlab);
        cessOldSlabTV=findViewById(R.id.cessOldSlab);
        incomeTaxNewSlabTV=findViewById(R.id.IncomeTaxNewSlab);
        incomeTaxOldSlabTV=findViewById(R.id.IncomeTaxOldSlab);
        rebateNewSlabTV=findViewById(R.id.taxRebateNewSlab);
        rebateOldSlabTV=findViewById(R.id.taxRebateOldSlab);
        incomeTaxRefundNewSlabTV=findViewById(R.id.IncomeTaxRefundNewSlab);
        incomeTaxRefundOldSlabTV=findViewById(R.id.IncomeTaxRefundOldSlab);



        ArrayList<String> ageList = new ArrayList<>();
        ageList.add("0-60");
        ageList.add("60-80");
        ageList.add("80 & Above");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ageList);
        autoCompleteTextViewAge.setAdapter(adapter);
    }

    private boolean validateIncome(){
        String val= incomeTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            incomeTextInputLayout.setError("Income cannot be empty");
            return false;
        } else{
            incomeTextInputLayout.setError(null);
            incomeTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTDS(){
        String val= TDSTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            TDSTextInputLayout.getEditText().setText("0");
            return true;
//            TDSTextInputLayout.setError("TDS cannot be empty");
//            return false;
        } else{
            TDSTextInputLayout.setError(null);
            TDSTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateInvestment(){
        String val= investmentTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
              investmentTextInputLayout.getEditText().setText("0");
            //            investmentTextInputLayout.setError("Investment cannot be empty");
//            return false;
        }else{
            investmentTextInputLayout.setError(null);
            investmentTextInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateAge(){
        String val= ageTextInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            ageTextInputLayout.setError("Please select an Age group");
            return false;
        } else{
            ageTextInputLayout.setError(null);
            ageTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public void calculateIncomeTax(View v){

        if(!validateIncome()|  !validateAge()|!validateInvestment()|!validateTDS()){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        income=Float.parseFloat(Objects.requireNonNull(incomeTextInputLayout.getEditText()).getText().toString().trim());
        investment=Float.parseFloat(investmentTextInputLayout.getEditText().getText().toString().trim());
        TDS=Float.parseFloat(TDSTextInputLayout.getEditText().getText().toString().trim());

        newSlabTaxableIncome=income;
        oldSlabTaxableIncome=income-investment;

        if(income<=250000){

            Log.i("income","250000");

            newSlab5=0;
            newSlab10=0;
            newSlab15=0;
            newSlab20=0;
            newSlab25=0;
            newSlab30=0;
            rebateNewSlab=0;

        }else if(income<=500000 & income>250000){

            Log.i("income","500000");

            newSlab5=5*(newSlabTaxableIncome-250000)/100;
            newSlab10=0;
            newSlab15=0;
            newSlab20=0;
            newSlab25=0;
            newSlab30=0;
            rebateNewSlab=newSlab5+newSlab10+newSlab15+newSlab20+newSlab25+newSlab30;


        }else if(income>500000 & income<=750000){

            Log.i("income","750000");

            newSlab5=5*(500000-250000)/100;
            newSlab10=10*(newSlabTaxableIncome-500000)/100;
            newSlab15=0;
            newSlab20=0;
            newSlab25=0;
            newSlab30=0;
            rebateNewSlab=0;



        }else if(income>750000 & income<=1000000){

            Log.i("income","1000000");

            newSlab5=5*(500000-250000)/100;
            newSlab10=10*(750000-500000)/100;
            newSlab15=15*(newSlabTaxableIncome-750000)/100;
            newSlab20=0;
            newSlab25=0;
            newSlab30=0;
            rebateNewSlab=0;

        }else if(income>1000000 & income<=1250000){

            Log.i("income","1250000");

            newSlab5=5*(500000-250000)/100;
            newSlab10=10*(750000-500000)/100;
            newSlab15=15*(1000000-750000)/100;
            newSlab20=20*(newSlabTaxableIncome-1000000)/100;;
            newSlab25=0;
            newSlab30=0;
            rebateNewSlab=0;


        }else if(income>1250000 & income<=1500000){

            Log.i("income","1500000");

            newSlab5=5*(500000-250000)/100;
            newSlab10=10*(750000-500000)/100;
            newSlab15=15*(1000000-750000)/100;
            newSlab20=20*(1250000-1000000)/100;
            newSlab25=25*(newSlabTaxableIncome-1250000)/100;
            newSlab30=0;
            rebateNewSlab=0;


        }else if(income>1500000){

            Log.i("income","1500000");

            newSlab5=5*(500000-250000)/100;
            newSlab10=10*(750000-500000)/100;
            newSlab15=15*(1000000-750000)/100;
            newSlab20=20*(1250000-1000000)/100;
            newSlab25=25*(1500000-1250000)/100;
            newSlab30=30*(newSlabTaxableIncome-1500000)/100;
            rebateNewSlab=0;

        }


        if(autoCompleteTextViewAge.getText().toString().equals("0-60")){

            if(income<=250000){

                oldSlab5=0;
                oldSlab20=0;
                oldSlab30=0;
                rebateOldSlab=0;
                rebateNewSlab=0;


            }else if(income<=500000 & income>250000){

                Log.i("income","500000");
                oldSlab5=5*(oldSlabTaxableIncome-250000)/100;
                oldSlab20=0;
                oldSlab30=0;
                rebateOldSlab=oldSlab5+oldSlab20+oldSlab30;

            }else if(income>500000 & income<=750000){

                oldSlab20=20*(oldSlabTaxableIncome-500000)/100;
                oldSlab5=5*(500000-250000)/100;
                oldSlab30=0;
                rebateOldSlab=0;

            }else if(income>750000 & income<=1000000){

                oldSlab5=5*(500000-250000)/100;
                oldSlab20=20*(oldSlabTaxableIncome-500000)/100;
                oldSlab30=0;
                rebateOldSlab=0;


            }else if(income>1000000 & income<=1250000){

                oldSlab5=5*(500000-250000)/100;
                oldSlab20=20*(1000000-500000)/100;
                oldSlab30=30*(oldSlabTaxableIncome-1000000)/100;
                rebateOldSlab=0;

            }else if(income>1250000 & income<=1500000){

                oldSlab5=5*(500000-250000)/100;
                oldSlab20=20*(1000000-500000)/100;
                oldSlab30=30*(oldSlabTaxableIncome-1000000)/100;
                rebateOldSlab=0;



            }else if(income>1500000){

                oldSlab5=5*(500000-250000)/100;
                oldSlab20=20*(1000000-500000)/100;
                oldSlab30=30*(oldSlabTaxableIncome-1000000)/100;
                rebateOldSlab=0;

            }
        }else if(autoCompleteTextViewAge.getText().toString()=="60-80"){

            if(income<=300000){

                oldSlab5=0;
                oldSlab20=0;
                oldSlab30=0;
                rebateOldSlab=0;

            }else if(income<=500000 & income>300000){

                Log.i("income","500000");
                oldSlab5=5*(oldSlabTaxableIncome-300000)/100;
                oldSlab20=0;
                oldSlab30=0;
                rebateOldSlab=oldSlab5+oldSlab20+oldSlab30;

            }else if(income>500000 & income<=750000){

                oldSlab20=20*(oldSlabTaxableIncome-500000)/100;
                oldSlab5=5*(500000-300000)/100;
                oldSlab30=0;
                rebateOldSlab=0;

            }else if(income>750000 & income<=1000000){

                oldSlab5=5*(500000-300000)/100;
                oldSlab20=20*(oldSlabTaxableIncome-500000)/100;
                oldSlab30=0;
                rebateOldSlab=0;


            }else if(income>1000000 & income<=1250000){

                Log.i("income","1250000");

                oldSlab5=5*(500000-300000)/100;
                oldSlab20=20*(1000000-500000)/100;
                oldSlab30=30*(oldSlabTaxableIncome-1000000)/100;
                rebateOldSlab=0;

            }else if(income>1250000 & income<=1500000){

                Log.i("income","1500000");

                oldSlab5=5*(500000-300000)/100;
                oldSlab20=20*(1000000-500000)/100;
                oldSlab30=30*(oldSlabTaxableIncome-1000000)/100;
                rebateOldSlab=0;



            }else if(income>1500000){

                Log.i("income","1500000");

                oldSlab5=5*(500000-300000)/100;
                oldSlab20=20*(1000000-500000)/100;
                oldSlab30=30*(oldSlabTaxableIncome-1000000)/100;
                rebateOldSlab=0;

            }

        }else if(autoCompleteTextViewAge.getText().toString()=="80 & Above"){
            if(income<=500000 ){

                    oldSlab5=0;
                    oldSlab20=0;
                    oldSlab30=0;
                    rebateOldSlab=0;

                }else if(income>500000 & income<=750000){

                    oldSlab20=20*(oldSlabTaxableIncome-500000)/100;
                    oldSlab5=0;
                    oldSlab30=0;
                    rebateOldSlab=0;

                }else if(income>750000 & income<=1000000){

                    oldSlab5=0;
                    oldSlab20=20*(oldSlabTaxableIncome-500000)/100;
                    oldSlab30=0;
                    rebateOldSlab=0;


                }else if(income>1000000 & income<=1250000){

                    Log.i("income","1250000");

                    oldSlab5=0;
                    oldSlab20=20*(1000000-500000)/100;
                    oldSlab30=30*(oldSlabTaxableIncome-1000000)/100;
                    rebateOldSlab=0;

                }else if(income>1250000 & income<=1500000){

                    Log.i("income","1500000");

                    oldSlab5=0;
                    oldSlab20=20*(1000000-500000)/100;
                    oldSlab30=30*(oldSlabTaxableIncome-1000000)/100;
                    rebateOldSlab=0;

                }else if(income>1500000){

                    Log.i("income","1500000");

                    oldSlab5=0;
                    oldSlab20=20*(1000000-500000)/100;
                    oldSlab30=30*(oldSlabTaxableIncome-1000000)/100;
                    rebateOldSlab=0;

                }

        }

        cessNewSlab=4*(newSlab5+newSlab10+newSlab15+newSlab20+newSlab25+newSlab30)/100;
        cessOldSlab=4*(oldSlab5+oldSlab20+oldSlab30)/100;

        incomeTaxNewSlab=newSlab5+newSlab10+newSlab15+newSlab20+newSlab25+newSlab30+cessNewSlab;
        incomeTaxOldSlab=oldSlab5+oldSlab20+oldSlab30+cessOldSlab;

        incomeTaxRefundNewSlab=incomeTaxNewSlab-TDS;
        incomeTaxRefundOldSlab=incomeTaxOldSlab-TDS;

        incomeTaxRefundNewSlabTV.setText( String.valueOf(incomeTaxRefundNewSlab));
        incomeTaxRefundOldSlabTV.setText(String.valueOf(incomeTaxRefundOldSlab));

        newSlabTaxableIncomeTV.setText( String.valueOf(newSlabTaxableIncome));
        oldSlabTaxableIncomeTV.setText( String.valueOf(oldSlabTaxableIncome));

        newSlab5TV.setText( String.valueOf(newSlab5));
        oldSlab5TV.setText( String.valueOf(oldSlab5));
        newSlab10TV.setText( String.valueOf(newSlab10));
        newSlab15TV.setText( String.valueOf(newSlab15));
        newSlab20TV.setText( String.valueOf(newSlab20));
        oldSlab20TV.setText( String.valueOf(oldSlab20));
        newSlab25TV.setText( String.valueOf(newSlab25));
        newSlab30TV.setText( String.valueOf(newSlab30));
        oldSlab30TV.setText( String.valueOf(oldSlab30));

        rebateOldSlabTV.setText( String.valueOf(rebateOldSlab));
        rebateNewSlabTV.setText(String.valueOf(rebateNewSlab));
        incomeTaxNewSlabTV.setText(String.valueOf(incomeTaxNewSlab));
        incomeTaxOldSlabTV.setText(String.valueOf(incomeTaxOldSlab));

        cessNewSlabTV.setText(String.valueOf(cessNewSlab));
        cessOldSlabTV.setText(String.valueOf(cessOldSlab));

        progressBar.setVisibility(View.GONE);
        constraintLayoutITDetails.setVisibility(View.VISIBLE);

    }
}