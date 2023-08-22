package com.medi.medicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class Media_Activity_By_Niloy extends AppCompatActivity implements View.OnClickListener{
    Button play, pause, stop,bck;
    MediaPlayer mediaPlayer;
    int pauseCurrentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media___by__niloy);

        play= (Button) findViewById(R.id.btn_play);
        pause= (Button) findViewById(R.id.btn_pause);
        stop= (Button) findViewById(R.id.btn_stop);
        bck= (Button) findViewById(R.id.btn17);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        bck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_play:
                if(mediaPlayer==null){
                    mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.fly);
                    mediaPlayer.start();}
                else if(!mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(pauseCurrentPosition);
                    mediaPlayer.start();
                }
                break;
            case R.id.btn_pause:
                if(mediaPlayer!=null) {
                    mediaPlayer.pause();
                    pauseCurrentPosition=mediaPlayer.getCurrentPosition();

                }
                break;
            case R.id.btn_stop:
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                    mediaPlayer=null;
                }
                break;
            case R.id.btn17:
                finish();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }


    }
}