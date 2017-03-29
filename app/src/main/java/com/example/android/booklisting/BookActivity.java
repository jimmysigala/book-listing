package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {


    private BookArrayAdapter adapter;
    private View progress;
    private TextView mEmptyStateTextView;
    private String bookSearchKeyword;
    private String bookUrl;
    private EditText keyword;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyword = (EditText) findViewById(R.id.search_keyword);
        searchButton = (Button) findViewById(R.id.ok_button);


        progress = findViewById(R.id.loading_spinner);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_text_view);


        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                ConnectivityManager connManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

                //If there is a network connection fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Change url depending on keywords entered in search, restart loader
                    bookSearchKeyword = keyword.getText().toString();
                    bookUrl = "https://www.googleapis.com/books/v1/volumes?q=" + bookSearchKeyword;
                    View loadingIndicator = findViewById(R.id.loading_spinner);
                    loadingIndicator.setVisibility(View.VISIBLE);
                    getLoaderManager().restartLoader(1, null, BookActivity.this);
                } else {
                    // Display Error
                    mEmptyStateTextView.setText(R.string.no_connection);
                }
            }
        });

        ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setEmptyView(mEmptyStateTextView);

        // Create a new {ArrayAdapter} of books
        adapter = new BookArrayAdapter(this, new ArrayList<Book>());

        bookListView.setAdapter(adapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter.
            // (this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(1, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            // Display Error
            mEmptyStateTextView.setText(R.string.no_connection);
        }
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, bookUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        progress.setVisibility(View.GONE);

        // Display no data found if data is null
        mEmptyStateTextView.setText(R.string.no_data);
        adapter.clear();

        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }
}
