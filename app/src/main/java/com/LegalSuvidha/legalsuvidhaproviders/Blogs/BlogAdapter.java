package com.LegalSuvidha.legalsuvidhaproviders.Blogs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.LegalSuvidha.legalsuvidhaproviders.R;

import java.text.ParseException;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder> {

    private static List<Blog> blogList;
    private Context context;
    private OnItemClickListener listener;

    public BlogAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        BlogAdapter.blogList = blogList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        holder.textTitle.setText(blog.getTitle());
        holder.textTitle.setText(blog.getTitle());
        String mHtmlString = blog.getDescription();
//        Log.i("html", String.valueOf(Html.fromHtml(Html.fromHtml(mHtmlString).toString())));
//        Spanned spanned = HtmlCompat.fromHtml(mHtmlString, HtmlCompat.FROM_HTML_MODE_COMPACT);
//        holder.textDescription.setText(spanned);
        holder.textDescription.setText(String.valueOf(Html.fromHtml(Html.fromHtml(mHtmlString).toString())),TextView.BufferType.SPANNABLE);
        String date = blog.getDate();
        try {
            holder.textDate.setText(date.substring(0,10)+" "+date.substring(11,19)+"  "+String.format(" • %s", Utils.DateToTimeFormat(date.substring(0, 19)+"Z")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        holder.textDate.setText(blog.getDate());
//        holder.textDate.setText(Utils.DateFormat(blog.getDate()));

        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
//        requestOptions.centerCrop();
//        requestOptions.optionalFitCenter();


        Glide.with(context).load(blog.getImageView()).apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDescription;
        TextView textDate;
        ImageView imageView;
        ProgressBar progressBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textViewBlogName);
            textDescription = itemView.findViewById(R.id.textViewBlogDescription);
            textDate = itemView.findViewById(R.id.textViewBlogDate);
            imageView=itemView.findViewById(R.id.blogImageView);
            progressBar = itemView.findViewById(R.id.progressBar);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= getAdapterPosition();

                if(position!=RecyclerView.NO_POSITION ) {
                    listener.onItemClick(position);
                }
            }
        });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }


}
