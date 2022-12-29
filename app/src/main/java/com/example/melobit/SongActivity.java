package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.melobit.data.Song;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        TextView tv = findViewById(R.id.textView5);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("id");
            RequestManager manager = new RequestManager(this);
            SongRequestListener listener = new SongRequestListener() {
                @Override
                public void didFetch(Song response) {
                    //set up media player
                }
                @Override
                public void didError(String errorMessage) {
                    tv.setText(errorMessage);
                }
            };
            manager.getSongById(listener,value);
        }
    }
}