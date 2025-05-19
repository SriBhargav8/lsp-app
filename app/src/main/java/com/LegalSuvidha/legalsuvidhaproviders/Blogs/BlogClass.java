package com.LegalSuvidha.legalsuvidhaproviders.Blogs;

public class BlogClass {


    public BlogClass() {
    }

    String id;
    String header;
    String content;
    String timestamp;
    String image;


    public BlogClass(String id, String header, String content, String timestamp, String image) {
        this.id = id;
        this.header = header;
        this.content = content;
        this.timestamp = timestamp;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
