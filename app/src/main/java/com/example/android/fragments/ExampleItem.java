package com.example.android.fragments;

public class ExampleItem {
    private String mImageUrl;
    private String mCreator;
    private String url;
    private  String murl;

    public ExampleItem(String imageUrl, String creator, String likes ,String url1) {
        mImageUrl = imageUrl;
        mCreator = creator;
        url = likes;
        murl=url1;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public String getCreator() {
        return mCreator;
    }
    public String getLikeCount() {
        return url;
    }
    public String getUrl(){return murl;}
}
