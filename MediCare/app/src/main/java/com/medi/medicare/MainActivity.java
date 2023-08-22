package com.medi.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    ImageButton doctorSignin,patientSignin_signOut,ambulance,primary_treatment,meditation,medicineID,mapID,todoID,mem_gameID;
    Button AdminSignin;
    TextView rateUs;
    ImageView imageView;
    AnimationDrawable animationDrawable;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doctorSignin=findViewById(R.id.doctorSigninID);
        patientSignin_signOut=findViewById(R.id.patientSignin_signOutID);
        imageView=findViewById(R.id.imageviewID);
        ambulance=findViewById(R.id.ambulanceID);
        meditation=findViewById(R.id.medicine_ID);
        primary_treatment=findViewById(R.id.primsarytreatmentID);
        medicineID=findViewById(R.id.medicineID);
        todoID=findViewById(R.id.todoID);
        mem_gameID=findViewById(R.id.mem_gameID);

        doctorSignin.setOnClickListener(this);
        patientSignin_signOut.setOnClickListener(this);
        ambulance.setOnClickListener(this);
        primary_treatment.setOnClickListener(this);
        medicineID.setOnClickListener(this);
        meditation.setOnClickListener(this);
        todoID.setOnClickListener(this);
        mem_gameID.setOnClickListener(this);
        imageView.setBackgroundResource(R.drawable.animation);
        animationDrawable= (AnimationDrawable) imageView.getBackground();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.doctorSigninID:
                //finish();
                Intent intentDoctorSignin=new Intent(getApplicationContext(),DoctorLoginActivity.class);
                startActivity(intentDoctorSignin);
                break;

            case R.id.patientSignin_signOutID:
                //finish();
                Intent intentPatientSignin_signOut=new Intent(getApplicationContext(),PatientSignInActivity.class);
                startActivity(intentPatientSignin_signOut); //patient signIn e jabe
                break;
            case R.id.ambulanceID:
                //finish();
               Intent intentAmbu=new Intent(getApplicationContext(),ambulance_activity.class);
                startActivity(intentAmbu); //patient signIn e jabe*/

                break;
            case R.id.primsarytreatmentID:
                //finish();
                Intent intentf=new Intent(getApplicationContext(),firstAid.class);
                startActivity(intentf);
                break;

            case R.id.medicine_ID:
                //finish();
                Intent intentFuad=new Intent(getApplicationContext(),Media_Activity_By_Niloy.class);
                startActivity(intentFuad);
                break;
            case R.id.medicineID:
                //finish();
               Intent intent10=new Intent(getApplicationContext(),medicine_by_niloy.class);
                startActivity(intent10);
                break;
            case R.id.todoID:

            Intent intent1000=new Intent(getApplicationContext(),todo_list.class);
            startActivity(intent1000);
            break;
            case R.id.mem_gameID:
                Intent intent10000=new Intent(getApplicationContext(),memory_game.class);
                startActivity(intent10000);
                break;
           /* case R.id.mapID:
                //finish();

                break;*/
        }
    }

    @Override
   public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(i==0)
            animationDrawable.start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                animationDrawable.stop();
            }
        }, 6000);

    }
}