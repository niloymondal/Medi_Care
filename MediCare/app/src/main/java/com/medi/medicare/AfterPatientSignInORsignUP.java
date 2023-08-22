package com.medi.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AfterPatientSignInORsignUP extends AppCompatActivity {
    ListView list;
    ArrayList<DoctorClass> stdArrayList;
    ArrayAdapter<DoctorClass> adapter;
    String doctorType;

    // String for listedDoctorDetails

 /*   String [] docDetails = new String[50];*/
    String [] docIds = new String[50];
    String [] names = new String[50];
    String [] dgree = new String[50];
    String [] specialized = new String[50];
    String [] time_table  = new String[50];
    String [] picture  = new String[50];
String checkPurpose ;
    int loopControl = 0;
    int listviewController = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_patient_sign_in_orsign_up);
        list = findViewById(R.id.listID);
        stdArrayList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doctorType = extras.getString("key1");
        }

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("doctor");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    DoctorClass doctorClass = ds.getValue(DoctorClass.class);
                    if(doctorClass.getSpecialized().equals(doctorType))
                    {
                         names[loopControl]=doctorClass.getFname() + " "+doctorClass.getLname();
                          dgree[loopControl]=doctorClass.getDgree();
                         specialized[loopControl]=doctorClass.getSpecialized();
                        docIds[loopControl]= doctorClass.getId() ;
                        time_table[loopControl] = doctorClass.getConsultan_hour();
                        picture[loopControl]=doctorClass.getImageUrl();
                        loopControl++;

                    }
                  //Toast.makeText(getApplicationContext()," "+doctorClass.getSpecialized(),Toast.LENGTH_SHORT).show();
                }
                listviewController = loopControl;
                CustomAdapter adapter=new CustomAdapter(listviewController,docIds,names,dgree,specialized,time_table,picture,AfterPatientSignInORsignUP.this);
                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent1 = new Intent(getApplicationContext(), ListedDoctorDetails.class);
                        intent1.putExtra("key1",docIds[position]);
                        intent1.putExtra("key2",names[position]);
                        intent1.putExtra("key3",dgree[position]);
                        intent1.putExtra("key4",specialized[position]);
                        intent1.putExtra("key5",time_table[position]);
                        intent1.putExtra("key6",picture[position]);
                      //  Toast.makeText(getApplicationContext(),""+checkPurpose,Toast.LENGTH_SHORT).show();
                        startActivity(intent1);
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }
}