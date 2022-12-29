package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        TextView tv = findViewById(R.id.textView5);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("id");
            tv.setText(value);
        }
    }
}