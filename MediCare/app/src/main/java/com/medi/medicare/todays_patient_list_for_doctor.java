package com.medi.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class todays_patient_list_for_doctor extends AppCompatActivity {
    TextView textView;
    String idFromOtherActivity;
    String day,month,check;
    ListView listView;
    int i=0,j=0;
    DatabaseReference scheduleRef;
    ArrayList<String> list,list1;
    public static ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_patient_list_for_doctor);
        //textView = findViewById(R.id.tex1);
        listView = findViewById(R.id.listID);

        list=new ArrayList<String>();
        list1=new ArrayList<String>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idFromOtherActivity = extras.getString("key1");
            day = extras.getString("key2");
            month = extras.getString("key3");
        }


        // String TripIDD = intent.getStringExtra(AvailableRides.EXTRA_MESSAGE9);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("schedule").child("Day-"+day+"-"+month).child(idFromOtherActivity);
        //DatabaseReference ref = rootRef.child("schedule").child("Day-7-8").child("1001");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String passenger = ds.getValue(String.class);
                    list.add(i,passenger);
                    i++;
                    //  Log.d("TAG1", passenger);
                }
                for (i = 0 ;i<list.size();i++)
                {
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference ref = rootRef.child("Patient").child(list.get(i));
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override //
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            PatientClass patient = dataSnapshot.getValue(PatientClass.class);
                           String temp ="Patient No : "+(j+1)+"\nName : "+ patient.getPatientSignUpFirstName()+" "+patient.getPatientSignUpLastName()+
                                  "\nAge : " + patient.getPatientSignUpAge() + " years" + "\nPrevious Diseases : "+
                                   patient.getPrevDisease() + "Payment Varification : " + patient.getPin();
                           list1.add(j,temp);
                           j++;
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(todays_patient_list_for_doctor.this,android.R.layout.simple_list_item_1,list1);
                            listView.setAdapter(arrayAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    ///eitar vitte sob lekhte hoibo
                                    Toast.makeText(getApplicationContext(),""+list.get(position).toString(),Toast.LENGTH_SHORT).show();
                                    Intent intent_call_button_activity_for_doctor= new Intent(getApplicationContext(), call_button_activity_for_doctor.class);
                                    intent_call_button_activity_for_doctor.putExtra("key1",list.get(position).toString());
                                    intent_call_button_activity_for_doctor.putExtra("key2",idFromOtherActivity);
                                    startActivity(intent_call_button_activity_for_doctor);
                                }
                            });
                           // Toast.makeText(getApplicationContext(),""+temp,Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    };
                    ref.addListenerForSingleValueEvent(valueEventListener);
                }


               /*
               **/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }
}
