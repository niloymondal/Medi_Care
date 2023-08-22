package com.medi.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Doctor_category_list extends AppCompatActivity implements View.OnClickListener {
CardView Allergists,Anesthesiologists,Cardiologists,ColonandRectalSurgeons,CriticalCareMedicineSpecialists,
    Endocrinologists,MedicineSpecialists,FamilyPhysicians,Gynecologist,Dermatologists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_category_list);

        Allergists=findViewById(R.id.AllergistsID);
        Anesthesiologists=findViewById(R.id.AnesthesiologistsID);
        Cardiologists=findViewById(R.id.CardiologistsID);
        ColonandRectalSurgeons=findViewById(R.id.ColonandRectalSurgeonsID);
        CriticalCareMedicineSpecialists=findViewById(R.id.CriticalCareMedicineSpecialistsID);
        Endocrinologists=findViewById(R.id.EndocrinologistsID);
        MedicineSpecialists=findViewById(R.id.MedicineSpecialistsID);
        FamilyPhysicians=findViewById(R.id.FamilyPhysiciansID);
        Gynecologist=findViewById(R.id.GynecologistlID);
        Dermatologists=findViewById(R.id.DermatologistsID);

        Allergists.setOnClickListener(this);
        Anesthesiologists.setOnClickListener(this);
        Cardiologists.setOnClickListener(this);
        ColonandRectalSurgeons.setOnClickListener(this);
        CriticalCareMedicineSpecialists.setOnClickListener(this);
        Endocrinologists.setOnClickListener(this);
        MedicineSpecialists.setOnClickListener(this);
        FamilyPhysicians.setOnClickListener(this);
        Gynecologist.setOnClickListener(this);
        Dermatologists.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.AllergistsID:
                Intent intent1 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
                intent1.putExtra("key1","Allergists");
                startActivity(intent1);
                break;

            case R.id.AnesthesiologistsID:
                Intent intent2 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
                intent2.putExtra("key1","Anesthesiologists");
                startActivity(intent2);
            case R.id.CardiologistsID:
                Intent intent3 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
                intent3.putExtra("key1","Cardiologists");
                startActivity(intent3);
                break;
            case R.id.ColonandRectalSurgeonsID:
                Intent intent4 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
                intent4.putExtra("key1","ColonandRectalSurgeons");
                startActivity(intent4);
                break;
            case R.id.CriticalCareMedicineSpecialistsID:
                //finish();
                Intent intent5 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
                intent5.putExtra("key1","CriticalCareMedicineSpecialists");
                startActivity(intent5);
                break;
            case R.id.EndocrinologistsID:
                //finish();
                Intent intent6 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
                intent6.putExtra("key1","Endocrinologists");
                startActivity(intent6);
                break;
            case R.id.MedicineSpecialistsID:
                //finish();
                Intent intent7 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
                intent7.putExtra("key1","MedicineSpecialists");
                startActivity(intent7);
                break;
            case R.id.FamilyPhysiciansID:
                //finish();
                Intent intent8 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
                intent8.putExtra("key1","FamilyPhysicians");
                startActivity(intent8);
                break; case R.id.GynecologistlID:
            //finish();
            Intent intent9 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
            intent9.putExtra("key1","gynecologist");
            startActivity(intent9);
            break; case R.id.DermatologistsID:
            //finish();
            Intent intent10 = new Intent(getApplicationContext(), AfterPatientSignInORsignUP.class);
            intent10.putExtra("key1","Others");
            startActivity(intent10);
            break;

        }
    }
}
