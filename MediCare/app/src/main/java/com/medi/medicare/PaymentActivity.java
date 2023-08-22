package com.medi.medicare;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    String Docid;
    TextView textView;
    EditText trx;
    Button sendForVarification,copynumber;
    int day,month,year,mHour,mMinute;
    FirebaseDatabase rootnode,rootnode1;
    DatabaseReference userRef,userRef1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        textView = findViewById(R.id.textview1);
        sendForVarification = findViewById(R.id.sendForVarification);
        copynumber = findViewById(R.id.copynumber);
        sendForVarification.setOnClickListener(this);
        copynumber.setOnClickListener(this);
        trx = findViewById(R.id.trxID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Docid = extras.getString("key3");
        }
        textView.setText("Doctor IDs : " + Docid);

        TimeZone tz = TimeZone.getTimeZone("GMT+6");
        Calendar c = Calendar.getInstance(tz);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH)+1;
        year = c.get(Calendar.YEAR);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute=c.get(Calendar.MINUTE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {//
            case R.id.sendForVarification:
                rootnode = FirebaseDatabase.getInstance();
                userRef = rootnode.getReference("paymentVarification");

                userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("TRX : "+trx.getText().toString()+ "\n"+"for doctor ID : "+Docid);
                Toast.makeText(getApplicationContext(),"Wait for Varification and after varification you will get  a  id from you profile my serial "
                        ,Toast.LENGTH_SHORT).show();

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("schedule").child("Day-"+day+"-"+month).child(Docid).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

                Intent intent1 = new Intent(getApplicationContext(), Patient_User_Profile.class);
                startActivity(intent1);
                break;
            case R.id.copynumber:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", "017*******");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Number Copied ",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
