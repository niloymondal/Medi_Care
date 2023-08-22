package com.medi.medicare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorSignUpRequestForm extends AppCompatActivity implements View.OnClickListener {
EditText DoctorFirstNameID,
    DoctorLastNameID,
            DoctorSignUpEmailID,
    DoctorMedicalCollegeID,
            DgreeID,
    DoctorPasswordID,
            ChamberID;
RadioGroup Specialized;
    RadioButton radioButtonSpecialized;
    Button DoctorSignUpButtonID;
    ImageView image;
    private static int GalleyPick = 1;
    private Uri ImageUri;
    boolean imageset = false;
String fname,lname,email,college,dgree,pass,chamber,special;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up_request_form);

        DoctorFirstNameID=findViewById(R.id.DoctorFirstNameID);
        DoctorLastNameID=findViewById(R.id.DoctorLastNameID);
        DoctorSignUpEmailID=findViewById(R.id.DoctorSignUpEmailID);
        DoctorMedicalCollegeID=findViewById(R.id.DoctorMedicalCollegeID);
        DgreeID=findViewById(R.id.DgreeID);
        DoctorPasswordID=findViewById(R.id.DoctorPasswordID);
        ChamberID=findViewById(R.id.ChamberID);
        Specialized=findViewById(R.id.SpecializedID);
        DoctorSignUpButtonID=findViewById(R.id.DoctorSignUpButtonID);

        DoctorSignUpButtonID.setOnClickListener(this);

        image = findViewById(R.id.imageviewID);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleyPick);
            }
        });

    }
    @Override
    public void onClick(View v) {
        final String fname=DoctorFirstNameID.getText().toString().trim();
        final String lname=DoctorLastNameID.getText().toString().trim();
        final String email=DoctorSignUpEmailID.getText().toString().trim();
        final String college=DoctorMedicalCollegeID.getText().toString().trim();
        final String dgree=DgreeID.getText().toString().trim();
        final String pass=DoctorPasswordID.getText().toString().trim();
        final String chamber=ChamberID.getText().toString().trim();
        final String special=getFromRadioGroup();
        if(v.getId()==R.id.DoctorSignUpButtonID)
        {
            Intent intent =new Intent(Intent.ACTION_SEND);
            intent.setType("text/email");

            intent.putExtra(Intent.EXTRA_EMAIL,new String[] {"fuadsiham139@gmail.com"});

            intent.putExtra(Intent.EXTRA_SUBJECT,"Request For Sign UP");
           intent.putExtra(Intent.EXTRA_TEXT,"First Name : " +fname + "\nLast Name : "+lname+
                   "\nEmail Address : "+email+"\nMedical College : "+college+
                           "\nDgree : "+dgree+"\nPassword : "+pass+"\nChamber : "+chamber+
                           "\nSpecialized In : "+special);
            startActivity(Intent.createChooser(intent,"Request For"));
        }
    }
    private String getFromRadioGroup() {
        int val=Specialized.getCheckedRadioButtonId();
        radioButtonSpecialized=findViewById(val);
        String specialIn = radioButtonSpecialized.getText().toString();
        return specialIn;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleyPick && resultCode == RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            image.setImageURI(ImageUri);
            imageset=true;
        }
    }
}
