package com.LegalSuvidha.legalsuvidhaproviders.Blogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LegalSuvidha.legalsuvidhaproviders.GstVaidation.GstValidatorActivity;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.LegalSuvidha.legalsuvidhaproviders.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrentBlogActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private ImageView imageView;
    private TextView appBarTitle, date, time, title, blogContentTV;
    WebView webViewContent;
    private Boolean isHideTolBarView = false;
    private FrameLayout dateBehavior;
    private LinearLayout titleAppbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String currentUrl, currentContent, currentTitle, currentDate;
    boolean notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_blog);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(CurrentBlogActivity.this);
        dateBehavior = findViewById(R.id.date_behavior);
        titleAppbar = findViewById(R.id.title_appbar);
        imageView = findViewById(R.id.backdrop);
        appBarTitle=findViewById(R.id.title_on_appbar);
//        appBarSubtitle= findViewById(R.id.subtitle_on_appbar);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        title = findViewById(R.id.title);
//        blogContentTV=findViewById(R.id.TextViewBlogContent);
        webViewContent=findViewById(R.id.webViewBlogContent);

        Intent intent=getIntent();
        notification=intent.getBooleanExtra("notification",false);
        Log.i("notification", String.valueOf(notification));
        currentUrl=intent.getStringExtra("imageURL");
        Log.i("notification",currentUrl);
        currentTitle=intent.getStringExtra("title");
        Log.i("notification", currentTitle);
        currentDate=intent.getStringExtra("date");
        Log.i("notification", currentDate);

        if(notification){

            String url=intent.getStringExtra("URL");

//            String  url="https://legalsuvidha.com/blog/api/detail/amendment-of-form-12ba-under-income-tax-act";
            JsonRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
//                Log.i("Ns","on response");
                    try {
////                        JSONArray blog_list =response.getJSONArray("Income Tax");
////
//                        BlogClass blogClass = new BlogClass();
//
//                        JSONObject blog_from_api = (JSONObject) blog_list.get(0);
//                        blogClass.setImage(blog_from_api.getString("image"));
//                        blogClass.setHeader(blog_from_api.getString("header"));
//                        blogClass.setTimestamp(blog_from_api.getString("timestamp"));
//                        blogClass.setContent(blog_from_api.getString("content"));
////
                        BlogClass blogClass = new BlogClass();


                        blogClass.setImage(response.getString("image"));
                        blogClass.setHeader(response.getString("header"));
                        blogClass.setTimestamp(response.getString("timestamp"));
                        blogClass.setContent(response.getString("content"));

//                        Log.i("content",response.getString("content"));

                        Blog blog=new Blog(blogClass.getImage(),blogClass.getHeader(),blogClass.getContent(),blogClass.getTimestamp());

                        currentContent=blog.getDescription();
                        Log.i("notificationcurrentblog", currentContent);
                        webViewContent.loadDataWithBaseURL("", currentContent, "text/html", "UTF-8", "");
                        Log.i("notification","webview");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    hideProgressBar();
//                Log.i("Ns","on error" );
                    Log.e("statuscode",error.toString());
                }
            });

            objectRequest.setRetryPolicy(new DefaultRetryPolicy(5*DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
            objectRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
            objectRequest.setTag("blogRequest");

            GstValidatorActivity.MySingleton.getInstance(this).addToRequestQueue(objectRequest);
        }else{
            currentContent=intent.getStringExtra("content");
            webViewContent.loadDataWithBaseURL("", currentContent, "text/html", "UTF-8", "");
            Log.i("notification","webview");
        }

//        Intent intent=getIntent();
//        currentUrl=intent.getStringExtra("imageURL");
//        currentTitle=intent.getStringExtra("title");
//        currentContent=intent.getStringExtra("content");
//        currentDate=intent.getStringExtra("date");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());

        Glide.with(this).load(currentUrl).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);

        appBarTitle.setText(currentTitle);
//        appBarSubtitle.setText(currentUrl);
//        appBarSubtitle.setText(" ");
        try {

            date.setText(Utils.DateFormat(currentDate.substring(0,10)));
//            date.setText(Utils.DateFormat(currentDate.substring(0,10))+" "+String.format(" • %s", Utils.DateToTimeFormat(currentDate.substring(0, 19)+"Z")));
        }catch(Exception e){}

        title.setText(currentTitle);

//        String author = null;
//
//        if(currentAuthor != null || currentAuthor != ""){
//            currentAuthor=" \u2022 "+ currentAuthor;
//        }else{
//            author= "";
//        }
//        try {
//            Log.i("date",currentDate.substring(0, 19)+"Z"+String.format(" • %s", Utils.DateToTimeFormat(currentDate.substring(0, 19)+"Z")));
//            Log.i("date",Utils.DateToTimeFormat(currentDate.substring(0, 19)+"Z"));
////            time.setText(String.format(" • %s", Utils.DateToTimeFormat(currentDate.substring(0, 19)+"Z")));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        blogContentTV.setText(String.valueOf(Html.fromHtml(Html.fromHtml(currentContent).toString())),TextView.BufferType.SPANNABLE);
//        Spanned spanned = HtmlCompat.fromHtml(currentContent, HtmlCompat.FROM_HTML_MODE_COMPACT);
//        blogContentTV.setText(spanned);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset)/ (float) maxScroll;


        if(percentage == 1f  && isHideTolBarView){
            dateBehavior.setVisibility(View.GONE);
            titleAppbar.setVisibility(View.VISIBLE);
            isHideTolBarView = !isHideTolBarView;
        }else if (percentage < 1f && isHideTolBarView){
            dateBehavior.setVisibility(View.VISIBLE);
            titleAppbar.setVisibility(View.GONE);
            isHideTolBarView = !isHideTolBarView;
        }
    }
}