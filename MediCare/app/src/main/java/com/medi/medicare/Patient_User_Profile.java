package com.medi.medicare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class Patient_User_Profile extends AppCompatActivity {
    TextView userAgeID, userNameID, userDiseaseID;
    CardView userEditID, DoctorListID;
    EditText FnameFromDialog, ageFromDialog, diseaseFromDialog, LnameFromDialog;
    CheckBox highBp, LowBp, Diabetes, Asthma, heartDisease;
    Button updateFromDialog, cancleFromDialog;

    FirebaseAuth fAuth, mAuth;
    String user;
    String pin;
    Dialog dialog, dialog1;
    private String calledBy = "";
    String callingOrRingingCheck = "call";
    DatabaseReference mDatabase, usersRef;
    ImageView patienImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__user__profile);
        //userAgeID, userNameID,userDiseaseID;
        userNameID = findViewById(R.id.userNameID);
        userAgeID = findViewById(R.id.userAgeID);
        userDiseaseID = findViewById(R.id.userDiseaseID);
        patienImage = findViewById(R.id.patienImageID);
        // userEditID,DoctorListID
        userEditID = findViewById(R.id.userEditID);
        DoctorListID = findViewById(R.id.DoctorListID);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Patient");


        mAuth = FirebaseAuth.getInstance();

        RetriveData();
    }

    private void RetriveData() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PatientClass patient = dataSnapshot.getValue(PatientClass.class);
                userNameID.setText(patient.getPatientSignUpFirstName() + " " + patient.getPatientSignUpLastName());
                userAgeID.setText(patient.getPatientSignUpAge() + " years");
                userDiseaseID.setText(patient.getPrevDisease());
                Picasso.get().load(patient.getImageStr()).placeholder(R.drawable.patient).into(patienImage);
                // Toast.makeText(getApplicationContext(), "" + patient.getImageStr(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);

    }

    public void Logout(View view) {
        mAuth.signOut();
        finish();
        Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //back button e press korle logout hoye jabe
        startActivity(intent3);
    }

    public void updateDocument(View view) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.patient_name_update_alert);
        dialog.setTitle("SAVE");
        dialog.show();

    }


    private String PreviousDiseaseCheckbox() {
        StringBuilder stringBuilder = new StringBuilder();

        if (highBp.isChecked()) {
            String str = highBp.getText().toString();
            stringBuilder.append(str + ",\n");
        }
        if (LowBp.isChecked()) {
            String str = LowBp.getText().toString();
            stringBuilder.append(str + "\n");
        }
        if (Diabetes.isChecked()) {
            String str = Diabetes.getText().toString();
            stringBuilder.append(str + "\n");
        }
        if (Asthma.isChecked()) {
            String str = Asthma.getText().toString();
            stringBuilder.append(str + "\n");
        }
        if (heartDisease.isChecked()) {
            String str = heartDisease.getText().toString();
            stringBuilder.append(str + "\n");
        }
        String prev = stringBuilder.toString();
        return prev;
    }

    public void CancelButtonOnDialog(View view) {
        dialog.cancel();
    }

    public void updateFromAleartBox(View view) {
        FnameFromDialog = dialog.findViewById(R.id.PatientChangedFirstNameID);
        LnameFromDialog = dialog.findViewById(R.id.PatientChangedLastNameID);
        ageFromDialog = dialog.findViewById(R.id.PatientChangedAgeID);
        // CheckBox highBp,LowBp,Diabetes,Asthma,heartDisease;
        highBp = dialog.findViewById(R.id.highChangedBpID);
        LowBp = dialog.findViewById(R.id.LowBpChangedID);
        Diabetes = dialog.findViewById(R.id.DiabetesChangedID);
        Asthma = dialog.findViewById(R.id.AsthmaChangedID);
        heartDisease = dialog.findViewById(R.id.heartDiseaseChangedID);


        final String fname = FnameFromDialog.getText().toString();
        final String lname = LnameFromDialog.getText().toString();
        final String age = ageFromDialog.getText().toString();
        final String disease = PreviousDiseaseCheckbox();

       /* FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Patient").document(user);*/

        if (fname.equals("") || lname.equals("") || age.equals("") || disease.equals("")) {
            Toast.makeText(this, "please enter all the value", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase.getInstance().getReference("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            Map<String, Object> postValues = new HashMap<String, Object>();
                                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                postValues.put(snapshot.getKey(), snapshot.getValue());
                                                            }
                                                            postValues.put("patientSignUpFirstName", fname);
                                                            postValues.put("patientSignUpLastName", lname);
                                                            postValues.put("patientSignUpAge", age);
                                                            postValues.put("prevDisease", disease);
                                                            FirebaseDatabase.getInstance().getReference("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                    .updateChildren(postValues);
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {
                                                        }
                                                    }
                    );
            Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
            ageFromDialog.setText(age + "years");
            RetriveData();
            dialog.cancel();

        }

    }

    public void goToMySerialList(View view) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PatientClass patient = dataSnapshot.getValue(PatientClass.class);
                pin = patient.getPin();
                TextView textView = dialog1.findViewById(R.id.pinNumberID);   ///eikhane ekta error dite pare may be

                if (pin.equals("")) {
                    textView.setText(" You have no serail yet or verification is pending");
                } else {
                    textView.setText(" " + pin);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);

        dialog1 = new Dialog(this);
        dialog1.setContentView(R.layout.my_serial_sample_layout);
        dialog1.setTitle("Serial");
        dialog1.show();

    }

    public void goToDoctorList(View view) {
        Intent intent3 = new Intent(getApplicationContext(), Doctor_category_list.class);///eita change korte hobe
        //  intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //back button e press korle logout hoye jabe
        startActivity(intent3);
    }

    private void CountinousChecking() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    PatientClass patient1 = dataSnapshot.getValue(PatientClass.class);
                    String callingCheck = patient1.getCalling();
                    if (callingCheck.equals("t")) {
                        Intent intent = new Intent(getApplicationContext(), CallingActivityBySiham.class);
                        callingOrRingingCheck = "ring";
                        intent.putExtra("key1", callingOrRingingCheck);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        CountinousChecking();
    }

    public void gotoMyPrescription(View view) {
        Intent intent = new Intent(getApplicationContext(), MyPrescriptionActivity.class);
        startActivity(intent);
    }

    public void goToBMICalculator(View view) {
        Intent intent = new Intent(getApplicationContext(), BmiCalculation.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        finish();
        Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //back button e press korle logout hoye jabe
        startActivity(intent3);
    }
}
