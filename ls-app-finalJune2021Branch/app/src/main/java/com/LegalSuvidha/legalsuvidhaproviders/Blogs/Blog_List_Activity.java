package com.LegalSuvidha.legalsuvidhaproviders.Blogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.LegalSuvidha.legalsuvidhaproviders.R;

public class Blog_List_Activity extends AppCompatActivity {

    ImageView imageView;
    TextView blogTypeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog__list_);

        imageView=findViewById(R.id.typesOfBlogImageView);
        blogTypeTV=findViewById(R.id.typesOfBlogTextView);

        String blogType = getIntent().getStringExtra("typeOfBlogs");
        blogTypeTV.setText(blogType+" "+"Blogs");

        switch (blogType) {
            case "GST":
                imageView.setImageResource(R.drawable.income);
                break;
            case "Income Tax":
                imageView.setImageResource(R.drawable.tax);
                break;
            case "Start-up":
                imageView.setImageResource(R.drawable.businessman);
                break;
            case "Other":
                imageView.setImageResource(R.drawable.content);
                break;
            case "Corporate Law":
                imageView.setImageResource(R.drawable.law);
                break;
        }

        if(savedInstanceState==null) {
            GSTBlogListFragment gstBlogsFragment = new GSTBlogListFragment();
            Bundle args = new Bundle();
            args.putString("typeOfBlogs",blogType );
            gstBlogsFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerBlogList,gstBlogsFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
         }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}