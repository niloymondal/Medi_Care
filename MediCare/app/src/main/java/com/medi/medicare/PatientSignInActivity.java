package com.medi.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PatientSignInActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    TextView PatientSignInFromSignUp;
    Button PatientloginButton;
    EditText PatientsignInEmail,PatientsignInPassword;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_in);
        PatientSignInFromSignUp=findViewById(R.id.PatientSignInFromSignUpID);
        PatientloginButton=findViewById(R.id.PatinetloginButtonID);

        PatientsignInEmail=findViewById(R.id.PatinetsignInEmailID);
        PatientsignInPassword=findViewById(R.id.PatinetsignInPasswordID);

        progressBar=findViewById(R.id.progressBarID);


        mAuth = FirebaseAuth.getInstance();

        PatientSignInFromSignUp.setOnClickListener(this);
        PatientsignInEmail.setOnClickListener(this);
        PatientsignInPassword.setOnClickListener(this);
        PatientloginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.PatientSignInFromSignUpID:
                Intent intentPatientSignInFromSignUp=new Intent(getApplicationContext(),PatientSignUpActivity.class);
                startActivity(intentPatientSignInFromSignUp);
                break;

            case R.id.PatinetloginButtonID:
                progressBar.setVisibility( View.VISIBLE);
                PatienLogInVarification();
                break;

          /*  case R.id. ---------:

                break;*/

        }
    }

    private void PatienLogInVarification() {
        String PatientSignInEmailVar=PatientsignInEmail.getText().toString().trim(); //trim() space thakle oigula bad dibe
        String PatientSignInPasswordVar=PatientsignInPassword.getText().toString().trim();


        /*PatientsignInEmail=findViewById(R.id.PatinetsignInEmailID);
        PatientsignInPassword=findViewById(R.id.PatinetsignInPasswordID);
*/
        //checking the validity of the email
        if(PatientSignInEmailVar.isEmpty())
        {
            PatientsignInEmail.setError("Enter an email address");
            PatientsignInEmail.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(PatientSignInEmailVar).matches())
        {
            PatientsignInEmail.setError("Enter a valid email address");
            PatientsignInEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if(PatientSignInPasswordVar.isEmpty())
        {
            PatientsignInPassword.setError("Enter a password");
            PatientsignInPassword.requestFocus();
            return;
        }
        if(PatientSignInPasswordVar.length()<6)
        {
            PatientsignInPassword .setError("Minimum length of password should be 6");
            PatientsignInPassword.requestFocus();
            return;
        }
      /*  mAuth.signInWithEmailAndPassword(PatientSignInEmailVar,PatientSignInPasswordVar).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    Intent intent3=new Intent(getApplicationContext(),Patient_User_Profile.class);///eita change korte hobe
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //back button e press korle logout hoye jabe
                    startActivity(intent3);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });*/
        mAuth.signInWithEmailAndPassword(PatientSignInEmailVar, PatientSignInPasswordVar)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                           Intent intent3=new Intent(getApplicationContext(),Patient_User_Profile.class);///eita change korte hobe
                            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //back button e press korle logout hoye jabe
                            startActivity(intent3);
                            progressBar.setVisibility(View.INVISIBLE);

                        } else {
                            Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        // ...
                    }
                });

    }
}
