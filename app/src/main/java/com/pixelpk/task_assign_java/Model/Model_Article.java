package com.pixelpk.task_assign_java.Model;

public class Model_Article
{
    String img;
    String title;
    String details;
    String date;
    String url;

    public Model_Article(String img, String title, String details, String date, String url)
    {
        this.img = img;
        this.title = title;
        this.details = details;
        this.date = date;
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
