package com.medi.medicare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
/*import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;*/

import java.util.HashMap;
import java.util.Map;

public class PatientSignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    TextView SignUpThekeSignIn;
    EditText patientFirstName,patientLastName,patientAge,patientSignUpEmail,patientSignUpPassword;
    Button PatientsignUp;

    CheckBox highBp,LowBp,Diabetes,Asthma,heartDisease;
    RadioGroup radioGroupPatientGender;
    RadioButton radioButtonPatientGender;

    String PatientID;
    String calling;



    private StorageReference userProfileImageRef;
    private String downloadUrl;
   ImageView doctor_signup_image;
    private static int GalleyPick = 1;
    private Uri ImageUri;
boolean imageset = false;



   /* private FirebaseAuth mAuth;

    FirebaseFirestore fstore;*/
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

     String PatientSignUpFirstName;
     String PatientSignUpLastName;
     String PatientSignUpEmail;
     String PatientSignUpPassword;
       String PatientSignUpAge;
     String PrevDisease;
      String gender;
     String pin = "";
    String pick="no";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);
       /* mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();*/

        mAuth = FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.progressBarID);

        databaseReference = FirebaseDatabase.getInstance().getReference("Patient");

//image
        userProfileImageRef= FirebaseStorage.getInstance().getReference().child("Profile Images");
        doctor_signup_image = findViewById(R.id.doctor_signup_image);
        doctor_signup_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleyPick);
            }
        });

//image_
        SignUpThekeSignIn=findViewById(R.id.SignUpThekeSignInID);
        patientFirstName=findViewById(R.id.patientFirstNameID);
        patientLastName=findViewById(R.id.patientLastNameID);
        patientAge=findViewById(R.id.patientAgeID);
        patientSignUpEmail=findViewById(R.id.patientSignUpEmailID1);
        patientSignUpPassword=findViewById(R.id.pSignUpPasswordID);
        PatientsignUp=findViewById(R.id.patientSignUpButtonID);
//checkbox
        highBp=findViewById(R.id.highBpID);
        LowBp=findViewById(R.id.LowBpID);
        Diabetes=findViewById(R.id.DiabetesID);
        Asthma=findViewById(R.id.AsthmaID);
        heartDisease=findViewById(R.id.heartDiseaseID);
        //radioButton
        radioGroupPatientGender=findViewById(R.id.radioGroupPatientGenderID);

        SignUpThekeSignIn.setOnClickListener(this);
        patientFirstName.setOnClickListener(this);
        patientLastName.setOnClickListener(this);
        patientAge.setOnClickListener(this);
        patientSignUpEmail.setOnClickListener(this);
        patientSignUpPassword.setOnClickListener(this);
        PatientsignUp.setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleyPick && resultCode == RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            doctor_signup_image.setImageURI(ImageUri);
            imageset=true;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.patientSignUpButtonID :
            {
                //Toast.makeText(getApplicationContext(),"Working",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility( View.VISIBLE);
                PatientRegistration();
                //  Intent intentSignUpThekeSignIn=new Intent(getApplicationContext(),PatientSignIn.class);
                //  startActivity(intentSignUpThekeSignIn);
                break;
            }
            case R.id.SignUpThekeSignInID:
            {
                Intent intentSignUpThekeSignIn=new Intent(getApplicationContext(),PatientSignInActivity.class);
                startActivity(intentSignUpThekeSignIn);
                break;
            }


        }
    }

    private void PatientRegistration() {
        PatientSignUpFirstName = patientFirstName.getText().toString().trim();
        PatientSignUpLastName = patientLastName.getText().toString().trim();
        PatientSignUpEmail = patientSignUpEmail.getText().toString().trim(); //
        PatientSignUpPassword = patientSignUpPassword.getText().toString().trim();
        PatientSignUpAge = patientAge.getText().toString().trim();
        PrevDisease = PreviousDiseaseCheckbox();
        gender = PatientGenderMeth();
        pin = "";
        calling = "f";

        if (PatientSignUpEmail.isEmpty()) {
            progressBar.setVisibility( View.GONE);
            patientSignUpEmail.setError("Enter an email address");
            patientSignUpEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(PatientSignUpEmail).matches()) {
            progressBar.setVisibility( View.GONE);
            patientSignUpEmail.setError("Enter a valid email address");
            patientSignUpEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if (PatientSignUpPassword.isEmpty()) {
            progressBar.setVisibility( View.GONE);
            patientSignUpPassword.setError("Enter a password");
            patientSignUpPassword.requestFocus();
            return;
        }
        if (PatientSignUpPassword.length() < 6) {
            progressBar.setVisibility( View.GONE);
            patientSignUpPassword.setError("Minimum length of password should be 6");
            patientSignUpPassword.requestFocus();
            return;
        }
        if (!imageset) {
            progressBar.setVisibility( View.GONE);
            patientSignUpEmail.setError("Select a profile picture first");
            patientSignUpEmail.requestFocus();
            patientSignUpPassword.setError("Select a profile picture first");
            patientSignUpPassword.requestFocus();
            return;
        }


        //database

        mAuth.createUserWithEmailAndPassword(PatientSignUpEmail, PatientSignUpPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //image
                            final StorageReference filepath = userProfileImageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            final UploadTask uploadTask = filepath.putFile(ImageUri);

                            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();

                                    }
                                    downloadUrl = filepath.getDownloadUrl().toString();
                                    Toast.makeText(getApplicationContext(), "" + downloadUrl, Toast.LENGTH_SHORT).show();
                                    return filepath.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    downloadUrl = task.getResult().toString();
                                    PatientClass patient = new PatientClass(PatientSignUpFirstName, PatientSignUpLastName,
                                            PatientSignUpEmail, PatientSignUpPassword, PatientSignUpAge, PrevDisease, gender, pin, calling,
                                            pick, downloadUrl);
                                    FirebaseDatabase.getInstance().getReference("Patient")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(patient).
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Intent intentSignUpThekeSignIn = new Intent(getApplicationContext(), Patient_User_Profile.class);
                                                    startActivity(intentSignUpThekeSignIn);
                                                }
                                            });
                                }
                            });
                        }

                    }
                });
    }
                    private String PatientGenderMeth() {

                        int val = radioGroupPatientGender.getCheckedRadioButtonId();
                        radioButtonPatientGender = findViewById(val);
                        String gen = radioButtonPatientGender.getText().toString();


                        return gen;
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

                }
