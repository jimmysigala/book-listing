package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Jimmy on 1/10/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String eURL;


    public BookLoader(Context context, String url) {
        super(context);
        eURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (eURL == null) {
            return null;
        }

        List<Book> result = QueryUtils.extractBooks(eURL);
        return result;
    }
}
