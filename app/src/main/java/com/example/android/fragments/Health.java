package com.example.android.fragments;

public class Health {

        String Author;
        String Title;
        String Date;
        String ImageURL;
        String url;


        public Health(String mAuthor, String mTitle, String mDate, String imageURL, String murl)

        {
            Author=mAuthor;
            Title=mTitle;
            Date=mDate;
            ImageURL=imageURL;
            url=murl;
        }
        public String Getname()
        {
            return Author;

        }
        public String GetTitle()
        {
            return Title;
        }
        public String GetDate()
        {
            return Date;
        }
        public String GetImage()
        {
            return ImageURL;

        }
        public String Geturl()
        {
            return url;
        }

    }


