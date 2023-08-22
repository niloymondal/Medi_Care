package com.medi.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyPrescriptionActivity extends AppCompatActivity {
TextView docname , patientname ,prescription;
    PatientClass patient;
    prescriptionClass prescriptionClass;
    DoctorClass doctorClass;
    String pres="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prescription);
        docname= findViewById(R.id.docname);
        patientname= findViewById(R.id.patientnameId);
        prescription= findViewById(R.id.prescriptionId);
        RetriveData();
        RetrivePrescripton();
      //  RetriveDoctor(prescriptionClass.getDocid());
//        docname.setText(doctorClass.getFname() +" "+doctorClass.getLname());
       // patientname.setText(patient.getPatientSignUpFirstName() +" "+patient.getPatientSignUpLastName());
      //  prescription.setText(prescriptionClass.getPrescription());
    }
    private void RetriveData() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 patient = dataSnapshot.getValue(PatientClass.class);
                patientname.setText(patient.getPatientSignUpFirstName() +" "+patient.getPatientSignUpLastName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);

    }
    private void RetrivePrescripton() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("prescription").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    prescriptionClass = dataSnapshot.getValue(prescriptionClass.class);
                    pres=prescriptionClass.getPrescription();
                    prescription.setText(pres +"");
                    RetriveDoctor(prescriptionClass.getDocid());
                }

                else {
                    prescription.setError("You have no prescription");
                    docname.setError("You have no prescription");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);

    }
    private void RetriveDoctor(String docid) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("doctor").child(docid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 doctorClass = dataSnapshot.getValue(DoctorClass.class);
                docname.setText(doctorClass.getFname() +" "+doctorClass.getLname());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);

    }
}
