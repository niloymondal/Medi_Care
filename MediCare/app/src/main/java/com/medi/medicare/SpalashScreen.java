package com.medi.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SpalashScreen extends AppCompatActivity {
    ImageView imageView1;
    ProgressBar progressBar;
    int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash_screen);imageView1=findViewById(R.id.imageID);
        progressBar=findViewById(R.id.progressID);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        });
        thread.start();
        imageView1.animate().alpha(1f).setDuration(4000);
        //imageView1.animate().alpha(0f).setDuration(7000);
        //   setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SpalashScreen.this, MainActivity.class);
                SpalashScreen.this.startActivity(intent);
                SpalashScreen.this.finish();
            }
        }, 6000);

    }
    public  void doWork()
    {
        for(progress=30 ; progress<=100 ; progress+=10)
        {
            try {
                Thread.sleep(500);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
