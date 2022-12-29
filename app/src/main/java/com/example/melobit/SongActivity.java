package com.example.melobit;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.melobit.data.Song;

import java.io.IOException;

public class SongActivity extends AppCompatActivity {

    private TextView tvSongName, tvArtist;
    private ImageView btnPlay, ivCover;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        tvSongName = findViewById(R.id.tv_song_title);
        tvArtist = findViewById(R.id.tv_artist);
        btnPlay = findViewById(R.id.btn_play);
        ivCover = findViewById(R.id.iv_cover);
        seekBar = findViewById(R.id.seekBar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("id");
            RequestManager manager = new RequestManager(this);
            SongRequestListener listener = new SongRequestListener() {
                @Override
                public void didFetch(Song response) {
                    playAudio(response.getAudio().getMedium().getUrl());
                    tvSongName.setText(response.getTitle());
                    tvArtist.setText(response.getArtists().get(0).getFullName());
                    Glide.with(SongActivity.this)
                            .load(response.getImage().getCover().getUrl())
                            .into(ivCover);
                }
                @Override
                public void didError(String errorMessage) {
                    tvSongName.setText(errorMessage);
                }
            };
            manager.getSongById(listener, value);
            seekBar.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }
    private void playAudio(String url) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Handler mHandler = new Handler();
        SongActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlay.setImageResource(R.drawable.ic_baseline_play_circle_24);
            } else {
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.ic_baseline_pause_circle_24);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btnPlay.setImageResource(R.drawable.ic_baseline_play_circle_24);
            }
        });
    }
    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}