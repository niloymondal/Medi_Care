package com.medi.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class call_button_activity_for_doctor extends AppCompatActivity implements View.OnClickListener {
String Patientid,docid;
ImageButton PatinetCallButton,prescriptionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_button_for_doctor);
        Bundle extras = getIntent().getExtras();
        PatinetCallButton=findViewById(R.id.PatinetCallButtonID);
        prescriptionButton=findViewById(R.id.PatinetPrescriptionButtonID);
        PatinetCallButton.setOnClickListener(this);
        prescriptionButton.setOnClickListener(this);
        if (extras != null) {
            Patientid = extras.getString("key1");
            docid = extras.getString("key2");
        }
        Toast.makeText(getApplicationContext(),""+Patientid,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.PatinetCallButtonID:
                FirebaseDatabase.getInstance().getReference("Patient").child(Patientid)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Map<String, Object> postValues = new HashMap<String,Object>();
                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                    postValues.put(snapshot.getKey(),snapshot.getValue());
                                                                }
                                                                postValues.put("calling", "t");
                                                                FirebaseDatabase.getInstance().getReference("Patient").child(Patientid)
                                                                        .updateChildren(postValues);
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {}
                                                        }
                        );
               Intent intent=new Intent(getApplicationContext(),CallingActivityBySiham.class);
                intent.putExtra("key1","call");
                intent.putExtra("key2",Patientid);
                startActivity(intent);
                break;
            case R.id.PatinetPrescriptionButtonID:
                Intent intent1=new Intent(getApplicationContext(),prescription_activity.class);
                intent1.putExtra("key2",Patientid);
                intent1.putExtra("key3",docid);
                startActivity(intent1);
                break;

        }
    }
}
