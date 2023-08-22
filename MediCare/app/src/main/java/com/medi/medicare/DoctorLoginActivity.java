package com.medi.medicare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DoctorLoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button presshere, loginButton;
    CheckBox rememberMe;
    String s1,s2;
    ProgressBar progressBar;
   /** String[] idString = new String[100];
    String[] passString = new String[100];
    String[] fnameString = new String[100];
    String[] lnameString = new String[100];
    String[] dgreeString = new String[100];
    String[] chamberString = new String[100];
    String[] emailString = new String[100];
    String[] specializedInString = new String[100];
    String[] userId = new String[100];*/
    int i = 0, pos;
    EditText DoctorSignInPassword, DoctorSignInEmail;
    TextView trial;
    boolean login = false;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        DoctorSignInEmail = findViewById(R.id.DoctorSignInEmailID);
        DoctorSignInPassword = findViewById(R.id.DoctorSignInPasswordID);
        presshere = findViewById(R.id.PresshereID);
        loginButton = findViewById(R.id.loginButtonID);
        rememberMe = findViewById(R.id.rememberMeID);
        trial = findViewById(R.id.trialID);
        presshere.setOnClickListener(this);
        loginButton.setOnClickListener(this);


        progressBar=findViewById(R.id.progressBarID);
       // FirebaseFirestore db = FirebaseFirestore.getInstance();

        fAuth = FirebaseAuth.getInstance();

        SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_APPEND);
         s1 = sh.getString("idGiven", "");
         s2 = sh.getString("passGiven", "");
      //   Toast.makeText(getApplicationContext(),""+s1 +"\n"+s2 ,Toast.LENGTH_SHORT).show();
        if(!s1.equals("") && !s2.equals(""))
        {
            autoLogin();
        }


    }

    private void autoLogin() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("doctor");
        Query checkuser = userRef.orderByChild("id").equalTo(s1);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                       String chamber = dataSnapshot.child(s1).child("chamber").getValue(String.class);
                        String dgree = dataSnapshot.child(s1).child("dgree").getValue(String.class);
                        String email = dataSnapshot.child(s1).child("email").getValue(String.class);
                        String fname = dataSnapshot.child(s1).child("fname").getValue(String.class);
                        String lname = dataSnapshot.child(s1).child("lname").getValue(String.class);
                        String specialized= dataSnapshot.child(s1).child("specialized-In").getValue(String.class);
                        Intent intentDoctorSignin1 = new Intent(getApplicationContext(), AfterDoctorLogin.class);
                        intentDoctorSignin1.putExtra("key1",chamber);
                        intentDoctorSignin1.putExtra("key2",dgree);
                        intentDoctorSignin1.putExtra("key3",email);
                        intentDoctorSignin1.putExtra("key4",fname);
                        intentDoctorSignin1.putExtra("key5",lname);
                        intentDoctorSignin1.putExtra("key6",specialized);
                        intentDoctorSignin1.putExtra("key7",s1);
                        intentDoctorSignin1.putExtra("key8",s2);
                        startActivity(intentDoctorSignin1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PresshereID:
                Intent intentDoctorSignin = new Intent(getApplicationContext(), DoctorSignUpRequestForm.class);
                startActivity(intentDoctorSignin);
                break;
            case R.id.loginButtonID:
                progressBar.setVisibility( View.VISIBLE);
                login = false;
                final String idGiven = DoctorSignInEmail.getText().toString();
                final String passGiven = DoctorSignInPassword.getText().toString();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("doctor");
                Query checkuser = userRef.orderByChild("id").equalTo(idGiven);
                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            DoctorSignInEmail.setError(null);
                            String passwordFromDB= dataSnapshot.child(idGiven).child("pass").getValue(String.class);

                            if(passwordFromDB.equals(passGiven))
                            {
                                progressBar.setVisibility( View.GONE);
                                DoctorSignInPassword.setError(null);

                                String chamber = dataSnapshot.child(idGiven).child("chamber").getValue(String.class);
                                String dgree = dataSnapshot.child(idGiven).child("dgree").getValue(String.class);
                                String email = dataSnapshot.child(idGiven).child("email").getValue(String.class);
                                String fname = dataSnapshot.child(idGiven).child("fname").getValue(String.class);
                                String lname = dataSnapshot.child(idGiven).child("lname").getValue(String.class);
                                String specialized= dataSnapshot.child(idGiven).child("specialized").getValue(String.class);
                                String imageUrl= dataSnapshot.child(idGiven).child("imageUrl").getValue(String.class);


                                Intent intentDoctorSignin1 = new Intent(getApplicationContext(), AfterDoctorLogin.class);

                                intentDoctorSignin1.putExtra("key1",chamber);
                                intentDoctorSignin1.putExtra("key2",dgree);
                                intentDoctorSignin1.putExtra("key3",email);
                                intentDoctorSignin1.putExtra("key4",fname);
                                intentDoctorSignin1.putExtra("key5",lname);
                                intentDoctorSignin1.putExtra("key6",specialized);
                                intentDoctorSignin1.putExtra("key7",idGiven);
                                intentDoctorSignin1.putExtra("key8",passGiven);
                                intentDoctorSignin1.putExtra("key9",imageUrl);
                                startActivity(intentDoctorSignin1);
                                finish();
                            }
                            else{
                                progressBar.setVisibility( View.GONE);
                                DoctorSignInPassword.setError("Wrong password");progressBar.setVisibility( View.GONE);
                            }
                        }

                        else
                        {
                            progressBar.setVisibility( View.GONE);
                            DoctorSignInEmail.setError("No ID");

                        }
                        if(rememberMe.isChecked())
                        {
                            SharedPreferences sharedPreferences
                                    = getSharedPreferences("MySharedPref",
                                    MODE_PRIVATE);
                            SharedPreferences.Editor myEdit
                                    = sharedPreferences.edit();
                            myEdit.putString(
                                    "idGiven",
                                    idGiven);
                            myEdit.putString(
                                    "passGiven",
                                 passGiven);
                           /* myEdit.putString(
                                    "rememberCheck",
                                    "yes");*/
                            myEdit.commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                      break;

                    }
                }
        }



