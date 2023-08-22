package com.medi.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class medicine_by_niloy extends AppCompatActivity {
    private Button btnf, btna,btnd,btnh,btng,btnc,btne,btnw,btnn,btnv,btno,btnk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_by_niloy);
        btnf= (Button) findViewById(R.id.btnf);
        btnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),FluActivity.class);
                startActivity(intent);

            }
        });

        btna= (Button) findViewById(R.id.btna);
        btna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),AlgActivity.class);
                startActivity(intent);

            }
        });
        btnd= (Button) findViewById(R.id.btnd);
        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),DiaActivity.class);
                startActivity(intent);

            }
        });
        btnh= (Button) findViewById(R.id.btnh);
        btnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),HeadActivity.class);
                startActivity(intent);

            }
        });
        btng= (Button) findViewById(R.id.btng);
        btng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),GasActivity.class);
                startActivity(intent);

            }
        });
        btnc= (Button) findViewById(R.id.btnc);
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),ConActivity.class);
                startActivity(intent);

            }
        });
        btne= (Button) findViewById(R.id.btne);
        btne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),FevActivity.class);
                startActivity(intent);

            }
        });
        btnw= (Button) findViewById(R.id.btnw);
        btnw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),WonActivity.class);
                startActivity(intent);

            }
        });
        btnn= (Button) findViewById(R.id.btnn);
        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),DecActivity.class);
                startActivity(intent);

            }
        });
        btnv= (Button) findViewById(R.id.btnv);
        btnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),VomActivity.class);
                startActivity(intent);

            }
        });
        btno= (Button) findViewById(R.id.btno);
        btno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),ConsActivity.class);
                startActivity(intent);

            }
        });

        btnk= (Button) findViewById(R.id.btnk);
        btnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });


    }
}