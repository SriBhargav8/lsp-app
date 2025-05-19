package com.LegalSuvidha.legalsuvidhaproviders.Blogs;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LegalSuvidha.legalsuvidhaproviders.R;

public class TypesOfBlogsFragment extends Fragment  {

    CardView cardViewGST,cardViewIncomeTax,cardViewStartUp,cardViewOther, getCardViewCorporateLaw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_types_of_blogs, container, false);

        cardViewGST=root.findViewById(R.id.cardViewGstBlog);
        cardViewIncomeTax=root.findViewById(R.id.cardViewIncomeTaxBlogs);
        cardViewStartUp=root.findViewById(R.id.cardViewStartUpBlogs);
        cardViewOther=root.findViewById(R.id.cardViewOtherBlog);
        getCardViewCorporateLaw=root.findViewById(R.id.cardViewCorporateLawBlog);

        cardViewGST.setOnClickListener(this::goToGSTBlogs);
        cardViewIncomeTax.setOnClickListener(this::goToIncomeBlogs);
        cardViewStartUp.setOnClickListener(this::goToStartUpBlogs);
        cardViewOther.setOnClickListener(this::goToOtherBlogs);
        getCardViewCorporateLaw.setOnClickListener(this::goToCorprateLawBlogs);

        return root;
    }

    private void goToCorprateLawBlogs(View view) {

        Intent intent = new Intent(getContext(), Blog_List_Activity.class);
        intent.putExtra("typeOfBlogs", "Corporate Law");
        startActivity(intent);
    }

    private void goToOtherBlogs(View view) {

        Intent intent = new Intent(getContext(), Blog_List_Activity.class);
        intent.putExtra("typeOfBlogs", "Other");
        startActivity(intent);
    }

    private void goToStartUpBlogs(View view) {

        Intent intent = new Intent(getContext(), Blog_List_Activity.class);
        intent.putExtra("typeOfBlogs", "Start-up");
        startActivity(intent);
    }

    private void goToIncomeBlogs(View view) {

        Intent intent = new Intent(getContext(), Blog_List_Activity.class);
        intent.putExtra("typeOfBlogs", "Income Tax");
        startActivity(intent);
    }

    private void goToGSTBlogs(View view) {
        Intent intent = new Intent(getContext(), Blog_List_Activity.class);
        intent.putExtra("typeOfBlogs", "GST");
        startActivity(intent);
    }

}