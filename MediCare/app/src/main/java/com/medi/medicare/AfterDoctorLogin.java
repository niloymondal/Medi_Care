package com.medi.medicare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.TimeZone;


public class AfterDoctorLogin extends AppCompatActivity implements View.OnClickListener {
    TextView doctorDetails,date,docNameID;
    String idFromOtherActivity, passFromOtherActivity, fnameFromOtherActivity, lnameFromOtherActivity,
            chamberFromOtherActivity, emailFromOtherActivity, specializedInFromOtherActivity, dgreeFromOtherActivity,useridFromOtherActivity,imageUrl;
    CardView serial, doctorProfile,doctorLogout,help;
int day,month,year,mHour,mMinute;
ImageView dctorImage;
    String fname,lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_doctor_login);

       // doctorDetails = findViewById(R.id.doctorDetailsID);
        serial = findViewById(R.id.serialID);
        doctorLogout = findViewById(R.id.doctorLogoutID);
        doctorProfile = findViewById(R.id.doctorProfilID);
        docNameID = findViewById(R.id.docNameID);
        help = findViewById(R.id.helpID);
         date= findViewById(R.id.dateID);
        dctorImage= findViewById(R.id.dctorImageID);

        doctorProfile.setOnClickListener(this);
        serial.setOnClickListener(this);
        doctorLogout.setOnClickListener(this);
        help.setOnClickListener(this);
        //data ana ek activity to another
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idFromOtherActivity = extras.getString("key7");
            passFromOtherActivity = extras.getString("key8");
            fnameFromOtherActivity = extras.getString("key4");
            lnameFromOtherActivity = extras.getString("key5");
            chamberFromOtherActivity = extras.getString("key1");
            emailFromOtherActivity = extras.getString("key3");
            specializedInFromOtherActivity = extras.getString("key6");
            dgreeFromOtherActivity = extras.getString("key2");
            imageUrl = extras.getString("key9");
           // useridFromOtherActivity = extras.getString("key9");
            //The key argument here must match that used in the other activity
        }
        TimeZone tz = TimeZone.getTimeZone("GMT+6");
        Calendar c = Calendar.getInstance(tz);
        day = c.get(Calendar.DAY_OF_MONTH);
         month = c.get(Calendar.MONTH)+1;
         year = c.get(Calendar.YEAR);
         mHour = c.get(Calendar.HOUR_OF_DAY);
         mMinute=c.get(Calendar.MINUTE);

        date.setText( ""+day +"/"+month+"/"+year);
        docNameID.setText(fnameFromOtherActivity + " "+lnameFromOtherActivity);

        Picasso.get().load(imageUrl).placeholder(R.drawable.patient).into(dctorImage);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.serialID:
                Intent intent_todays_patient_list_for_doctor= new Intent(getApplicationContext(), todays_patient_list_for_doctor.class);
                intent_todays_patient_list_for_doctor.putExtra("key1",idFromOtherActivity);
                intent_todays_patient_list_for_doctor.putExtra("key2",String.valueOf(day) );
                intent_todays_patient_list_for_doctor.putExtra("key3",String.valueOf(month) );
                startActivity(intent_todays_patient_list_for_doctor);
                break;
            case R.id.doctorProfilID:
                Intent intentDoctorSignin1 = new Intent(getApplicationContext(), doctorProfile.class);
                intentDoctorSignin1.putExtra("key1",idFromOtherActivity);
                intentDoctorSignin1.putExtra("key2",passFromOtherActivity);
                intentDoctorSignin1.putExtra("key3",fnameFromOtherActivity);
                intentDoctorSignin1.putExtra("key4",lnameFromOtherActivity);
                intentDoctorSignin1.putExtra("key5",chamberFromOtherActivity);
                intentDoctorSignin1.putExtra("key6",emailFromOtherActivity);
                intentDoctorSignin1.putExtra("key7",specializedInFromOtherActivity);
                intentDoctorSignin1.putExtra("key8",dgreeFromOtherActivity);
                //intentDoctorSignin1.putExtra("key9",useridFromOtherActivity);
                startActivity(intentDoctorSignin1);
                break;
            case R.id.doctorLogoutID:
                finish();
                SharedPreferences sharedPreferences
                        = getSharedPreferences("MySharedPref",
                        MODE_PRIVATE);
                SharedPreferences.Editor myEdit
                        = sharedPreferences.edit();
                myEdit.putString(
                        "idGiven",
                        "");
                myEdit.putString(
                        "passGiven",
                        "");
                myEdit.commit();
                Intent intent2 = new Intent(getApplicationContext(), DoctorLoginActivity.class);
                startActivity(intent2);
                break;
            case R.id.helpID:
                Toast.makeText(getApplicationContext(),"Code for help",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("doctor");
        Query checkuser = userRef.orderByChild("id").equalTo(idFromOtherActivity);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                         fname = dataSnapshot.child(idFromOtherActivity).child("fname").getValue(String.class);
                         lname = dataSnapshot.child(idFromOtherActivity).child("lname").getValue(String.class);
                }
                docNameID.setText( fname+ " "+lname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
