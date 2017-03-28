package com.example.android.booklisting;

/**
 * Created by Jimmy on 1/9/2017.
 */

public class Book {
    private String mAuthor;
    private String mTitle;

    public Book(String author, String title) {
        mAuthor = author;
        mTitle = title;
    }

    public String getmAuthor() {
        return mAuthor;
    }
    public String getmTitle() {
        return mTitle;
    }
}
