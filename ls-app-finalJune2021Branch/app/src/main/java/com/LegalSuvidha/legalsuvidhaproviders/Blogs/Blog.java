package com.LegalSuvidha.legalsuvidhaproviders.Blogs;

public class Blog {

//    private int imageView;
    private String imageView;
    private String title;
    private String description;
    private String date;

    private Blog() {

    }

    Blog(String imageView,String title, String description, String date) {
        this.imageView=imageView;
        this.title=title;
        this.description=description;
        this.date=date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

}
