package com.LegalSuvidha.legalsuvidhaproviders.Bookings;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.LegalSuvidha.legalsuvidhaproviders.Payments.PaymentActivity;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class BookingFirestoreAdapter extends FirestoreRecyclerAdapter<BookingModelClass, BookingFirestoreAdapter.BookingVH> {

//    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
//    private FirebaseUser user = mAuth.getCurrentUser();
//    String userID=user.getUid();
    Context context;
//    private FirebaseFirestore db=FirebaseFirestore.getInstance();
//    private CollectionReference usersColRef=db.collection("Users");

    public BookingFirestoreAdapter(@NonNull FirestoreRecyclerOptions<BookingModelClass> options,Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public BookingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent,false);
        return new BookingVH(v);
    }




    @Override
    protected void onBindViewHolder(@NonNull BookingVH holder, int position, @NonNull BookingModelClass booking) {

//        holder.textViewBookingType.setText(booking.getServiceType()+":"+booking.getBusinessType());
        holder.textViewBookingType.setText(booking.getServiceType());
        holder.textViewTimestamp.setText(booking.getTimestampBooking());
//        holder.textViewTimestamp.setText("Booking Timestamp:"+booking.getTimestampBooking()+"\nPayment Timestamp:"+booking.getTimestampPayment());

        String paymentStatus= (String)booking.getPaymentStatus();
        holder.textViewPaymentStatus.setText(paymentStatus);

        if(paymentStatus.equals("Payment Successful")){
            holder.textViewBookingAmount.setText(String.valueOf("₹"+booking.getPaymentAmount()));
            holder.textViewBookingAmount.setVisibility(View.VISIBLE);
        }

        int magnitudeColor = ContextCompat.getColor(context, R.color.black);

        switch (booking.getPaymentStatus()) {
            case "Payment Failed":
                magnitudeColor = ContextCompat.getColor(context, R.color.red);

                break;
            case "Payment Successful":
                magnitudeColor = ContextCompat.getColor(context, R.color.green);


                break;
            case "Pay Later":
                magnitudeColor = ContextCompat.getColor(context, R.color.teal_700);


                break;
            case "Payment Pending":
                magnitudeColor = ContextCompat.getColor(context, R.color.pending);

                break;
            case "Unpaid":
                magnitudeColor = ContextCompat.getColor(context, R.color.purple_500);

                break;
        }

        holder.textViewPaymentStatus.setTextColor(magnitudeColor);
//        holder.customCheckbox.setChecked(model.getStatus().equals("Completed"));


        holder.cardBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                DocumentSnapshot documentSnapshot=getSnapshots().getSnapshot(position);
                booking.setDocID(documentSnapshot.getId());

                String doc_id = String.valueOf(booking.getDocID());

                Intent intent = new Intent(v.getContext(), PaymentActivity.class);
                intent.putExtra("bookingDocID", booking.getDocID());
                v.getContext().startActivity(intent);

            }
        });

    }


//    @Override
//    public void onDataChanged() {
//        super.onDataChanged();
//        if(getItemCount()<=2){
//            showPlaceHolder();
//        } else{
//            hidePlaceHolder();
//        }
//    }


    class BookingVH extends RecyclerView.ViewHolder {
        TextView textViewBookingType, textViewTimestamp, textViewBookingAmount, textViewPaymentStatus;
        CardView cardBooking;

        public BookingVH(@NonNull View itemView) {
            super(itemView);
            textViewBookingType = itemView.findViewById(R.id.textViewBookingType);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            textViewBookingAmount = itemView.findViewById(R.id.textViewBookingAmount);
            textViewPaymentStatus = itemView.findViewById(R.id.textViewPaymentStatus);
            cardBooking = itemView.findViewById(R.id.cardBooking);



        }

    }

}