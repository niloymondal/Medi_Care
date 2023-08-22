package com.medi.medicare;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;


public class doctorProfile extends AppCompatActivity {
    TextView idtext,fnametext,lnametext,chambertext,emailtext,specializedIntext,dgreetext;
String id,pass,fname,lname,chamber,email,specializedIn,dgree,user;
Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        idtext=findViewById(R.id.idID);
        fnametext=findViewById(R.id.fnameID);
        lnametext=findViewById(R.id.lnameID);
        chambertext=findViewById(R.id.chamberID);
        emailtext=findViewById(R.id.emailID);
        specializedIntext=findViewById(R.id.specializedInID);
        dgreetext=findViewById(R.id.dgreeID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("key1");
            pass= extras.getString("key2");
            fname = extras.getString("key3");
            lname = extras.getString("key4");
            chamber = extras.getString("key5");
            email = extras.getString("key6");
            specializedIn = extras.getString("key7");
            dgree = extras.getString("key8");
            user = extras.getString("key9");
            //The key argument here must match that used in the other activity
        }
        idtext.setText(id);
        fnametext.setText(fname);
        lnametext.setText(lname);
        chambertext.setText(chamber);
        emailtext.setText(email);
        specializedIntext.setText(specializedIn);
        dgreetext.setText(dgree);

    }

    public void updateDocumentDoctor(View view) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.doctor_profile_update);
        dialog.setTitle("SAVE");
        dialog.show();
    }
     TextView FnameFromDialog,LnameFromDialog,EmailFromDialog,Chamber;
    public void updateFromAleartBox(View view) {
        FnameFromDialog = dialog.findViewById(R.id.doctorChangedFirstNameID);
        LnameFromDialog = dialog.findViewById(R.id.doctorChangedLastNameID);
        EmailFromDialog = dialog.findViewById(R.id.doctorChangedEmailID);
        Chamber = dialog.findViewById(R.id.doctorChangedChamberID);



        String fname = FnameFromDialog.getText().toString();
        String lname = LnameFromDialog.getText().toString();
        String email = EmailFromDialog.getText().toString();
        String chamber = Chamber.getText().toString();

        FirebaseDatabase.getInstance().getReference("doctor")
                .child(id).child("chamber")
                .setValue(chamber);
        FirebaseDatabase.getInstance().getReference("doctor")
                .child(id).child("email")
                .setValue(email);
        FirebaseDatabase.getInstance().getReference("doctor")
                .child(id).child("fname")
                .setValue(fname);
        FirebaseDatabase.getInstance().getReference("doctor")
                .child(id).child("lname")
                .setValue(lname);
        Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
        fnametext.setText(fname);
        lnametext.setText(lname);
        emailtext.setText(email);
        chambertext.setText(chamber);
        specializedIntext.setText(specializedIn);
        dialog.cancel();
    }

    public void CancelButtonOnDialog(View view) {
        dialog.cancel();
    }
}
