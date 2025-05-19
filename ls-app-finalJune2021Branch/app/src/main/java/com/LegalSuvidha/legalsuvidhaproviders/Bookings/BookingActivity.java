package com.LegalSuvidha.legalsuvidhaproviders.Bookings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.LegalSuvidha.legalsuvidhaproviders.HomeScreen;
import com.LegalSuvidha.legalsuvidhaproviders.Profile.ProfileActivity;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BookingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BookingFirestoreAdapter bookingFirestoreAdapter;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user;
    String uid;

    public static DocumentReference currentUserDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        recyclerView =findViewById(R.id.bookingRecyclerView);

        user = mAuth.getCurrentUser();
        assert user != null;
        uid= user.getUid();
        currentUserDocumentReference = db.collection("Users").document(uid);
        Query query =currentUserDocumentReference.collection("Booking List").orderBy("TimestampBooking", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<BookingModelClass> options =new FirestoreRecyclerOptions.Builder<BookingModelClass>().setQuery(query,BookingModelClass.class).build();
        bookingFirestoreAdapter=new BookingFirestoreAdapter(options,this);
        recyclerView.setHasFixedSize(true);
        Log.i("booking","adapter");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookingFirestoreAdapter);
//        db_cnt = todoListFirestoreAdapter.getItemCount();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bookingFirestoreAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bookingFirestoreAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        Intent intent= new Intent(BookingActivity.this, HomeScreen.class);
        startActivity(intent);

    }
}