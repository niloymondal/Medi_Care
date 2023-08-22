package com.medi.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class CallingActivityBySiham extends AppCompatActivity implements View.OnClickListener {
    private TextView nameContact;
    private ImageView profileImage;
    private ImageView cancelCallBtn,acceptCallBtn;
    private MediaPlayer mediaPlayer;

    String callingOrRingingCheck ;
    String patientIdFromDoctorButton="" ;
    int day,month,year,mHour,mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_by_siham);
        nameContact = findViewById(R.id.name_calling);
        profileImage = findViewById(R.id.profile_image_calling);
        cancelCallBtn = findViewById(R.id.cancel_call);
        acceptCallBtn = findViewById(R.id.make_call);

        acceptCallBtn.setOnClickListener(this);
        cancelCallBtn.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.ringing);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {  //ene error dite pare karon duita activity thika ek vabe data pathaisi
            callingOrRingingCheck = extras.getString("key1");
        }
        if(callingOrRingingCheck.equals("ring"))
        {
            ringActivity();
        }
        else   if(callingOrRingingCheck.equals("call"))
        {
            Bundle extras1 = getIntent().getExtras();
            if (extras != null) {
                patientIdFromDoctorButton = extras1.getString("key2");
            }
            callActivity();
        }

    }

    private void callActivity() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Patient").child(patientIdFromDoctorButton);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    // String pickCheckCheck = dataSnapshot.getValue(String.class);
                    PatientClass patient1 = dataSnapshot.getValue(PatientClass.class);
                    String pickCheckCheck = patient1.getPick();
                    if(pickCheckCheck.equals("yes")) {
                        finish();
                        Intent intent=new Intent(getApplicationContext(),VideoChatActivity.class);
                        intent.putExtra("key1",callingOrRingingCheck);
                        intent.putExtra("key2",patientIdFromDoctorButton);
                        startActivity(intent);
                    }
                    if(pickCheckCheck.equals("no_pick")) {
                        makeNo_pick_to_pick();
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void makeNo_pick_to_pick() {
        FirebaseDatabase.getInstance().getReference("Patient").child(patientIdFromDoctorButton)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                                        postValues.put("pick","no");
                                                        FirebaseDatabase.getInstance().getReference("Patient").child(patientIdFromDoctorButton)
                                                                .updateChildren(postValues);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {}
                                                }
                );
    }

    private void ringActivity() {
        mediaPlayer.start();
        acceptCallBtn.setVisibility(View.VISIBLE);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    // String pickCheckCheck = dataSnapshot.getValue(String.class);
                    PatientClass patient1 = dataSnapshot.getValue(PatientClass.class);
                    String pickCheckCheck = patient1.getPick();
                    if(pickCheckCheck.equals("yes")) {
                        finish();
                        Intent intent=new Intent(getApplicationContext(),VideoChatActivity.class);
                        intent.putExtra("key1",callingOrRingingCheck);
                        startActivity(intent);
                    }
                    if(pickCheckCheck.equals("no_pick")) {
                       finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(callingOrRingingCheck.equals("call"))
        {
            switch (v.getId())
            {
                case R.id.cancel_call:
                    /*setFatDatabase();
                    mediaPlayer.stop();
                    finish();*/
                    setFatDatabase();
                    FirebaseDatabase.getInstance().getReference("Patient").child(patientIdFromDoctorButton)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    Map<String, Object> postValues = new HashMap<String,Object>();
                                                                    postValues.put("pick","no");
                                                                    FirebaseDatabase.getInstance().getReference("Patient").child(patientIdFromDoctorButton)
                                                                            .updateChildren(postValues);
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {}
                                                            }
                            );
                    finish();

                    break;
            }
        }
        else {
            switch (v.getId())
            {
                case R.id.make_call:
                    mediaPlayer.stop();
                    makePickYes();
                    finish();

                    break;
                case R.id.cancel_call:
                    setFatDatabase();
                    mediaPlayer.stop();
                    finish();
                    break;

            }
        }

    }

    private void makePickYes() {
        finish();
        FirebaseDatabase.getInstance().getReference("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                                        postValues.put("pick","yes");
                                                        FirebaseDatabase.getInstance().getReference("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                .updateChildren(postValues);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {}
                                                }
                );
    }

    private void setFatDatabase() {
        if(patientIdFromDoctorButton.equals(""))
        {
            patientIdFromDoctorButton=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        FirebaseDatabase.getInstance().getReference("Patient").child(patientIdFromDoctorButton)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            postValues.put(snapshot.getKey(),snapshot.getValue());
                                                        }
                                                        postValues.put("calling", "f");
                                                        postValues.put("pick", "no_pick");
                                                        FirebaseDatabase.getInstance().getReference("Patient").child(patientIdFromDoctorButton)
                                                                .updateChildren(postValues);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {}
                                                }
                );
    }
    private void setTatDatabase() {
        FirebaseDatabase.getInstance().getReference("Patient").child(patientIdFromDoctorButton)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            postValues.put(snapshot.getKey(),snapshot.getValue());
                                                        }
                                                        postValues.put("calling", "t");
                                                        FirebaseDatabase.getInstance().getReference("Patient").child(patientIdFromDoctorButton)
                                                                .updateChildren(postValues);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {}
                                                }
                );
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(callingOrRingingCheck.equals("call"))
        {

        }
        if(callingOrRingingCheck.equals("ring"))
        {
            setFatDatabase();
            mediaPlayer.stop();
            finish();
        }
    }

}
