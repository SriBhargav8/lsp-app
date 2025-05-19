package com.LegalSuvidha.legalsuvidhaproviders.GstVaidation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LegalSuvidha.legalsuvidhaproviders.R;

import java.util.List;

public class AdapterReturnType extends RecyclerView.Adapter<AdapterReturnType.FilingViewHolder> {

    List<ReturnTypeModel> returnTypeModelList;

    public AdapterReturnType(List<ReturnTypeModel> returnTypeModelList) {
        this.returnTypeModelList = returnTypeModelList;
    }


    @NonNull
    @Override
    public FilingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_type_card_layout,parent,false);
        return new FilingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReturnType.FilingViewHolder holder, int position) {

        ReturnTypeModel returns = returnTypeModelList.get(position);

        holder.monthSession.setText(returns.getMonthSession());
        holder.dofGst1.setText(returns.getGSTR1date());
        holder.dofGst3B.setText(returns.getGSTR3Bdate());

        if(returns.getGSTR1()){
            holder.gst1Image.setImageResource(R.drawable.ic_baseline_check_circle_24);
        }

        if(returns.getGSTR3B()){
            holder.gst3Image.setImageResource(R.drawable.ic_baseline_check_circle_24);
        }
    }

    @Override
    public int getItemCount() {
        return returnTypeModelList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }


    public class FilingViewHolder extends RecyclerView.ViewHolder {

        TextView dofGst1,dofGst3B, monthSession;
        ImageView gst1Image,gst3Image;

        public FilingViewHolder(@NonNull View itemView) {
            super(itemView);

            monthSession= itemView.findViewById(R.id.monthSessionTV);
            dofGst1= itemView.findViewById(R.id.dateOfFilingGST1);
            dofGst3B=itemView.findViewById(R.id.dateOfFilingGST3);
            gst1Image=itemView.findViewById(R.id.GSTR1IV);
            gst3Image= itemView.findViewById(R.id.GSTR3BIV);
        }


    }
}
