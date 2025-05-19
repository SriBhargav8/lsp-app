package com.LegalSuvidha.legalsuvidhaproviders.Blogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.LegalSuvidha.legalsuvidhaproviders.R;

public class BlogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerBlogs,new TypesOfBlogsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
    }
}