package com.example.alexk.inclass5;

/**
 * Created by AlexK on 2/19/2018.
 */

public class Article {

    String author, title, url, urlToimage, publishedAt;

    public Article(String author, String title, String url, String urlToimage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.url = url;
        this.urlToimage = urlToimage;
        this.publishedAt = publishedAt;
    }

    public Article() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToimage() {
        return urlToimage;
    }

    public void setUrlToimage(String urlToimage) {
        this.urlToimage = urlToimage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToimage='" + urlToimage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
