package com.LegalSuvidha.legalsuvidhaproviders.Blogs;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.LegalSuvidha.legalsuvidhaproviders.GstVaidation.GstValidatorActivity;
import com.LegalSuvidha.legalsuvidhaproviders.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GSTBlogListFragment extends Fragment {

    private RecyclerView recyclerView;
    BlogAdapter adapter;
    List<Blog> blogList;
    JSONObject responseFromApi;
    ProgressBar progressBar,paginationProgressbar;
    String typeOfBlog;
    NestedScrollView nestedScrollView;
    int offset=0,limit=10;
    RequestQueue queue;
    Boolean list_end=false, requestSent=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                // Handle the back button even
//                if (requestSent) {
//                   getActivity().onBackPressed();
//                }else {
//                    return;
//                }
//
//            }
//        };

//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.fragment_g_s_t_blogs, container, false);

        recyclerView=root.findViewById(R.id.recyclerViewBlogs);
        progressBar= root.findViewById(R.id.progressBar);
        paginationProgressbar=root.findViewById(R.id.progress_bar_pagination);
        nestedScrollView=root.findViewById(R.id.nestedScrollView);

        queue = Volley.newRequestQueue(getContext());

        blogList = new ArrayList<>();

        assert getArguments() != null;
        typeOfBlog = getArguments().getString("typeOfBlogs");

        adapter= new BlogAdapter(getContext(),blogList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getBlogData(offset,limit);



        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                    if (!list_end) {
//                        Log.i("Ns", "scroll end");
                        offset = offset + 10;
                        limit = limit + 10;
                        showProgressBar();
                        getBlogData(offset, limit);
                    }
                }
            }
        });

//        root.setFocusableInTouchMode(true);
//        root.requestFocus();
//        root.setOnKeyListener( new View.OnKeyListener()
//        {
//
//            @Override
//            public boolean onKey( View v, int keyCode, KeyEvent event )
//            {
//                if( keyCode == KeyEvent.KEYCODE_BACK )
//                {
//                    if (requestSent == true) {
//                        return false;
//                    }else {
//                        return true;
//                    }
//
//                }
//                return false;
//            }
//        } );

//        String mHtmlString ="<p style=\"text-align:center\"><span style=\"font-size:22px\"><strong><span style=\"font-family:Verdana,Geneva,sans-serif\">AMENDMENT OF FORM 12BA UNDER INCOME TAX RULE,1962</span></strong></span></p>\r\n\r\n<p><span style=\"font-size:18px\"><strong><span style=\"font-family:Verdana,Geneva,sans-serif\">WHAT IS FORM 12BA?</span></strong></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Form 12BA is a detailed statement which shows the particulars of perquisites, other fringe benefits and profits in lieu of salary. This is issued by the employer to the employee.</span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><strong><span style=\"font-family:Verdana,Geneva,sans-serif\">Perquisites and other benefits here means :</span></strong></span></p>\r\n\r\n<ul>\r\n\t<li><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Accomodation facility</span></span></li>\r\n\t<li><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Interest free or concessional loan</span></span></li>\r\n\t<li><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Free meals</span></span></li>\r\n\t<li><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Free education</span></span></li>\r\n\t<li><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Use of movable assets by employees</span></span></li>\r\n\t<li><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Gifts, vouchers etc</span></span></li>\r\n</ul>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p><span style=\"font-size:16px\"><strong><span style=\"font-family:Verdana,Geneva,sans-serif\">Who shall issue Form 12BA and by which date?</span></strong></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Employers issue Form 12 BA along with&nbsp;Form 16&nbsp;(a tax deducted at source certificate) and hence follows due date applicable for issuance of form 16 i.e., 15th June of immediately following the end of the financial year.</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">When Form 12BA is required to be issued by the employer?</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Form 12BA is required to be issued when the amount of salary is more than Rs. 1,50,000/-. Salary here means</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Includes:<br />\r\n- Basic Pay<br />\r\n- Bonus<br />\r\n- Commission<br />\r\n- Allowances<br />\r\n- Any other monetary payment</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Excludes :<br />\r\n-&nbsp;Dearness Allowance<br />\r\n- Exempt Allowances<br />\r\n- Employer contribution to PF<br />\r\n- Value of perquisites u/s 17(2)<br />\r\n- Payment which are not part of perquisites<br />\r\n- Superannuation<br />\r\n- Voluntary retirement benefits<br />\r\n- Commutation of pension<br />\r\n- Lump-sum payment for cessation of service</span></span></p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p><span style=\"font-size:18px\"><strong><span style=\"font-family:Verdana,Geneva,sans-serif\">What is the use of Form 12BA?</span></strong></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">The income from salary head consists of three parts:</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">- Salary/wages/pension/gratuity,</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">- Perquisites and</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">- Profits in lieu of salary</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Perquisites, commonly known as perks, is the benefit that an employee gets in addition to remuneration on account of his/her job or position. There are various types of perks given by the companies / employer monetary like education expenses, driver salary, medical expenses and non-monetary benefits like rent free accommodation, stock options etc.</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">Income tax has set out rules for calculation of the taxable value of each perk. According to such rules, the employer deducts tax on perks. Now, looking at the complicated taxation treatment of perks, income tax rules have mandated employers to give a separate form to employees to help them understand their tax deduction alongwith Form 16.</span></span></p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p><span style=\"font-size:18px\"><strong><span style=\"font-family:Verdana,Geneva,sans-serif\">AMENDMENT OF FORM 12BA:</span></strong></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">The&nbsp;Income Tax Department&nbsp;is a&nbsp;government agency&nbsp;undertaking&nbsp;direct tax&nbsp;collection of the&nbsp;Government of India. It functions under the Department of Revenue of the&nbsp;Ministry of Finance. Income Tax Department is headed by the apex body of&nbsp;Central Board of Direct Taxes&nbsp;(CBDT).&nbsp;Its functions include formulation of policies, dealing with natters relating to levy and collection of direct taxes, and supervision of the functioning of the entire Income Tax Department. CBDT also proposes legislative changes in direct tax enactments and changes in rates and structure of taxation in tune with the policies of the Government.</span></span></p>\r\n\r\n<p><span style=\"font-size:14px\"><span style=\"font-family:Verdana,Geneva,sans-serif\">The CBDT has amended Form No. 12BA (Statement showing particulars of perquisites, other fringe benefits or amenities and profits in lieu of salary with value thereof), Part B to Form 16 (Details of Salary Paid and any other income and tax deducted) and Annexure II to Form No. 24Q vide Notification No. 15/2021-Income Tax. Form 12BA, Form 16 and Form 24Q vide Income-tax (3rd Amendment) Rules, 2021.</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">Form 12BA has been revised to include additional reporting by the employer which included the details of:</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">Contribution by employer to recognized provident fund, pension scheme of Central Government or approved superannuation fund in respect of the assessee, to the extent it exceeds Rs 7,50,000 under section 17(2)(vii) of Income Tax Act and also annual accretion by way of interest, dividend etc. to the balance at the credit of fund and scheme;</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">Stock options (ESOP) allotted or transferred by an employer being an eligible start-up;</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">Stock options (non-qualified options) other than ESOP</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">Permanent Account Number of landlords shall be mandatorily furnished where the aggregate rent paid during the previous year exceeds one lakh rupees.</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">Permanent Account Number of lenders shall be mandatorily furnished where the housing loan, on which interest is paid, is taken from a person other than a Financial Institution or the Employer.</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">The Government deductors to fill information in item I of Part A if tax is paid without production of an income-tax challan and in item II of Part A if tax is paid accompanied by an income-tax challan. Non-Government deductors to fill information in item II of Part A. The deductor shall furnish the address of the Commissioner of Income-tax (TDS) having jurisdiction as regards TDS statements of the assessee.</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">If an assessee is employed under more than one employer during the year, each of the employers shall issue Part A of the certificate in Form No. 16 pertaining to the period for which such assessee was employed with each of the employers. &nbsp;Part B (Annexure) of the certificate in Form No.16 may be issued by each of the employers or the last employer at the option of the assessee. In Part A, in items I and II, in the column for tax deposited in respect of deductee, furnish total amount of tax, surcharge and health and education cess.</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">The Deductor shall duly fill details, where available, in item numbers 2(f) and 10(k) before furnishing of Part B (Annexure) to the employee. Part B of Form No.16 has been revised to include details as to whether the option to pay tax at lower rate for an Individuals or HUF&rsquo;s u/s 115BAC is exercised or not.</span></span></p>\\r\\n\\r\\n<p><span style=\\\"font-size:14px\\\"><span style=\\\"font-family:Verdana,Geneva,sans-serif\\\">Annexure II of Form 24Q has been revised in order to insert a column where the employer would be required to answer that whether the employee is opting for taxation u/s 115BAC or not.</span></span></p>";
//        Log.i("without html", String.valueOf(Html.fromHtml(Html.fromHtml(mHtmlString).toString())));
//        Spanned spanned = HtmlCompat.fromHtml(mHtmlString, HtmlCompat.FROM_HTML_MODE_COMPACT);
//        Log.i("spanned html", String.valueOf(spanned));
//        textView.setText(Html.fromHtml(Html.fromHtml(mHtmlString).toString()));

        return root;
    }

    private void getBlogData(int offset, int limit) {
        requestSent=true;

//        String url ="https://legalsuvidha.com/blog/api/category/"+ typeOfBlog ;

        String url="https://legalsuvidha.com/blog/api/category/"+ typeOfBlog+"/"+offset+"/"+limit;

        JsonRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.i("Ns","on response");
                try {
                    requestSent=false;
                responseFromApi=response;

                if (progressBar.getVisibility()==View.VISIBLE){
                    progressBar.setVisibility(View.INVISIBLE);
                }

                hideProgressBar();


                    JSONArray blog_list =response.getJSONArray(typeOfBlog);

                    BlogClass blogClass = new BlogClass();

                    if(blog_list.length()<=0){
                       paginationProgressbar.setVisibility(View.GONE);
                       list_end=true;
                    }

                    for (int i=0; i<blog_list.length();i++) {

                        JSONObject blog_from_api = (JSONObject) blog_list.get(i);
                        blogClass.setImage(blog_from_api.getString("image"));
                        blogClass.setHeader(blog_from_api.getString("header"));
                        blogClass.setTimestamp(blog_from_api.getString("timestamp"));
                        blogClass.setContent(blog_from_api.getString("content"));

                        blogList.add(new Blog(blogClass.getImage(),blogClass.getHeader(),blogClass.getContent(),blogClass.getTimestamp()));

                        if (i == blog_list.length() - 1) {
//                            Log.i("TAG:", "i==length");
                            adapter.notifyDataSetChanged();

                            adapter.setOnItemClickListener(new BlogAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick( int position) {
                                    Blog currentBlog =blogList.get(position);

                                    Intent intent = new Intent(getContext(), CurrentBlogActivity.class);
                                    intent.putExtra("imageURL",currentBlog.getImageView());
                                    intent.putExtra("title",currentBlog.getTitle());
                                    intent.putExtra("date",currentBlog.getDate());
                                    intent.putExtra("content",currentBlog.getDescription());
                                    intent.putExtra("notification",false);
                                    startActivity(intent);
                                }
                            });
//
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestSent=false;
                hideProgressBar();
//                Log.i("Ns","on error" );
                Log.e("statuscode",error.toString());
            }
        });

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(5*DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
        objectRequest.setTag("blogRequest");

        GstValidatorActivity.MySingleton.getInstance(getContext()).addToRequestQueue(objectRequest);
//        queue.add(objectRequest);

    }

    private void showProgressBar(){
//        your_view.setEnabled(false);
        paginationProgressbar.setVisibility(View.VISIBLE);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void hideProgressBar(){
        paginationProgressbar.setVisibility(View.INVISIBLE);
        Objects.requireNonNull(getActivity()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        GstValidatorActivity.MySingleton.getInstance(getContext()).getRequestQueue().cancelAll("blogRequest");
    }

    @Override
    public void onPause() {
        super.onPause();
        super.onStop();
        GstValidatorActivity.MySingleton.getInstance(getContext()).getRequestQueue().cancelAll("blogRequest");
    }
}