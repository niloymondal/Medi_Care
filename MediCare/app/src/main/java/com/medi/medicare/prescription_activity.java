package com.medi.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;

public class prescription_activity extends AppCompatActivity implements View.OnClickListener {
    String Patientid, prescriptiontext, docid, key;
    EditText prescriptionEdittext;
    Button SendButton;
    int day, month, year, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Patientid = extras.getString("key2");
            docid = extras.getString("key3");
        }
        Toast.makeText(getApplicationContext(), "" + docid, Toast.LENGTH_SHORT).show();
        prescriptionEdittext = findViewById(R.id.prescriptionEdittextId);
        SendButton = findViewById(R.id.SendButtonID);
        SendButton.setOnClickListener(this);
        TimeZone tz = TimeZone.getTimeZone("GMT+6");
        Calendar c = Calendar.getInstance(tz);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {//
            case R.id.SendButtonID:
                prescriptiontext = prescriptionEdittext.getText().toString();
                prescriptionClass prescriptionclass = new prescriptionClass(Patientid, docid, prescriptiontext);
                FirebaseDatabase.getInstance().getReference("prescription")
                        .child(Patientid + "")
                        .setValue(prescriptionclass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "send successfully", Toast.LENGTH_SHORT).show();
                    }
                });

                //todays patient delete
                /*DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                        .child("schedule").child("Day-"+day+"-"+month).child(docid).child(Patientid);
                mPostReference.removeValue();*/
                //
               /* DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = rootRef.child("schedule").child("Day-"+day+"-"+month).child(docid);*/

                DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference();
                mDatabase1.child("schedule").child("Day-" + day + "-" + month).child(docid)
                        .child(Patientid).removeValue();
//for making patient pin = null
                DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference();
                mDatabase2.child("Patient").child(Patientid)
                        .child("pin").setValue("Used");
                finish();
                Intent intent = new Intent(getApplicationContext(), todays_patient_list_for_doctor.class);
                intent.putExtra("key1", docid);
                intent.putExtra("key2", String.valueOf(day));
                intent.putExtra("key3", String.valueOf(month));
                startActivity(intent);
                break;
        }
    }
}
