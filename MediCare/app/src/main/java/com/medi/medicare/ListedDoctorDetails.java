package com.medi.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ListedDoctorDetails extends AppCompatActivity implements View.OnClickListener {

    TextView textView1, textView2, textView3,textView4;
    Button serialForPatient;
    String docDetailsFromAfterPatientSignInORsignUP, docIdFromAfterPatientSignInORsignUP;
    String id, name, dgree, specialized, time_table,pic;
ImageView imageView;
double rateInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_doctor_details);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        imageView = findViewById(R.id.dctorImageID);
        serialForPatient = findViewById(R.id.serialForPatient);
        serialForPatient.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           /* docDetailsFromAfterPatientSignInORsignUP = extras.getString("key1");
            docIdFromAfterPatientSignInORsignUP = extras.getString("key2");*/
            id = extras.getString("key1");
            name = extras.getString("key2");
            dgree = extras.getString("key3");
            specialized = extras.getString("key4");
            time_table = extras.getString("key5");
            pic = extras.getString("key6");

        }

        RetriveRating();
        textView4.setText(Double.toString(rateInt));
        textView1.setText(id);
        textView2.setText(name+"\n"+dgree+"\nSpecialized In : "+specialized);
        textView3.setText(time_table);
        Picasso.get().load(pic).placeholder(R.drawable.doctor).into(imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.serialForPatient:
                Intent intent1 = new Intent(getApplicationContext(),PaymentActivity.class);
                intent1.putExtra("key3", id);
                startActivity(intent1);
                break;

        }
    }
    private void RetriveRating() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("rating").child(id);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    rating_class ratingclass = dataSnapshot.getValue(rating_class.class);
                    rateInt=ratingclass.getRate();
                    textView4.setText((String.format("%.2f", rateInt)));
                  /*  totalInt=ratingclass.getTotalReview();
                    rateInt=ratingclass.getRate();*/
                    // Toast.makeText(getApplicationContext(),""+totalInt+"\t"+rateInt,Toast.LENGTH_SHORT).show();
                    //int totalInt,rateInt;
                 //   Toast.makeText(getApplicationContext()," "+rateInt,Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(getApplicationContext(),"Not Found ",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);

    }
}
