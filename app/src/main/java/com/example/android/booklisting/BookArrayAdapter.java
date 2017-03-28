package com.example.android.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jimmy on 1/9/2017.
 */

public class BookArrayAdapter extends ArrayAdapter<Book> {

    public BookArrayAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the object located at this position in the list
        Book books = getItem(position);

        // Find the TextView in the activity_main.xml layout with the ID version_name
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(books.getmTitle());

        // Find the TextView in the activity_main.xml layout with the ID version_name
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        authorTextView.setText(books.getmAuthor());

        return listItemView;
    }
}
