package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.melobit.adapters.SearchAdapter;
import com.example.melobit.adapters.SongAdapter;
import com.example.melobit.data.SearchResultItem;
import com.example.melobit.data.Song;
import com.example.melobit.data.SongResponse;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rvResults;
    private TextView tvError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rvResults = findViewById(R.id.rv_results);
        tvError = findViewById(R.id.tv_error);
        rvResults.setLayoutManager(new GridLayoutManager(this,2));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String query = extras.getString("query");
            RequestManager manager = new RequestManager(SearchActivity.this);
            SearchResultsListener listener = new SearchResultsListener() {
                @Override
                public void didFetch(List<SearchResultItem> response) {
                    rvResults.setAdapter(new SearchAdapter(getApplicationContext(), response, (position, v, id) -> {
                        Intent intent = new Intent(SearchActivity.this,SongActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }));
                }

                @Override
                public void didError(String errorMessage) {
                    tvError.setText(errorMessage);
                }
            };
            manager.search(listener,query);
        }
    }
}