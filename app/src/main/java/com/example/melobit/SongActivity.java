package com.example.melobit;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.melobit.data.Song;
import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDate;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SongActivity extends AppCompatActivity {

    private TextView tvSongName, tvArtist,tvPlayCount,tvReleaseDate;
    private ImageView btnPlay, ivCover;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        tvSongName = findViewById(R.id.tv_song_title);
        tvArtist = findViewById(R.id.tv_artist);
        btnPlay = findViewById(R.id.btn_play);
        ivCover = findViewById(R.id.iv_cover);
        seekBar = findViewById(R.id.seekBar);
        tvPlayCount = findViewById(R.id.tv_play_count);
        tvReleaseDate = findViewById(R.id.tv_release_date);

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
                    tvPlayCount.setText(response.getDownloadCount());
                    tvReleaseDate.setText(miladiToShamsi(response.getReleaseDate()));
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

    private String miladiToShamsi(String releaseDate) {
        String[] realeaseDate  = releaseDate.split("T");
        String[] date = realeaseDate[0].split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        DateConverter dateConverter = new DateConverter();
        JalaliDate jalaliDate = dateConverter.gregorianToJalali(year,month,day);
        return jalaliDate.format(new JalaliDateFormatter("yyyy- M dd", JalaliDateFormatter.FORMAT_IN_PERSIAN));
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
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition()/1000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }}, 0, 1000);

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
    public void onBackPressed() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onBackPressed();
    }
}