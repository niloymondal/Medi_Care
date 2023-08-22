package com.medi.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class doctor_review extends AppCompatActivity implements View.OnClickListener {
    TextView titlerate,resultrate;
    Button btnfeedback,skipbtn;
    ImageView charPlace,icSprite;
    RatingBar ratingBar;
    String answerValue;

    Animation charAnim,bgANIM,btn;
    FirebaseDatabase rootnode,rootnode1;
    DatabaseReference userRef,userRef1;






    String totalStr,rateStr;
    int totalInt;
    double rateInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_review);
        btnfeedback=findViewById(R.id.btnfeedback);
        btnfeedback.setOnClickListener(this);
        titlerate =findViewById(R.id.titlerate);
        resultrate =findViewById(R.id.resultrate);

        btnfeedback=findViewById(R.id.btnfeedback);
        charPlace=findViewById(R.id.charPlace);
        icSprite=findViewById(R.id.icSprite);
        ratingBar=findViewById(R.id.rateStars);

        //load anim
        charAnim = AnimationUtils.loadAnimation(this,R.anim.charanim);
        bgANIM = AnimationUtils.loadAnimation(this,R.anim.animbg);
        btn = AnimationUtils.loadAnimation(this,R.anim.btnanim);

        icSprite.startAnimation(charAnim);
        charPlace.startAnimation(bgANIM);
        btnfeedback.startAnimation(btn);

        RetriveRating();


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                answerValue = String.valueOf((int)(ratingBar.getRating()));
                if(answerValue.equals("1")){
                    icSprite.setImageResource(R.drawable.onestar);
                    //giveAnim
                    icSprite.startAnimation(charAnim);
                    charPlace.animate().alpha(0).setDuration(300).start();
                    btnfeedback.startAnimation(btn);
                    resultrate.setText("Just so so");
                }
                else if(answerValue.equals("2")){
                    icSprite.setImageResource(R.drawable.twostar);
                    //giveAnim
                    icSprite.startAnimation(charAnim);
                    charPlace.animate().alpha(0).setDuration(300).start();
                    btnfeedback.startAnimation(btn);
                    resultrate.setText("Just so so");
                }
                else if(answerValue.equals("3")){
                    icSprite.setImageResource(R.drawable.threestar);
                    //giveAnim
                    icSprite.startAnimation(charAnim);
                    charPlace.animate().alpha(1).setDuration(300).start();
                    btnfeedback.startAnimation(btn);
                    resultrate.setText("Good job");
                }
                else if(answerValue.equals("4")){
                    icSprite.setImageResource(R.drawable.fourstar);
                    //giveAnim
                    icSprite.startAnimation(charAnim);
                    charPlace.animate().alpha(1).setDuration(300).start();
                    charPlace.startAnimation(bgANIM);
                    btnfeedback.startAnimation(btn);
                    resultrate.setText("Good job");
                }
                else if(answerValue.equals("5")){
                    icSprite.setImageResource(R.drawable.fivestar);
                    //giveAnim
                    icSprite.startAnimation(charAnim);
                    charPlace.animate().alpha(1).setDuration(300).start();
                    charPlace.startAnimation(bgANIM);
                    btnfeedback.startAnimation(btn);
                    resultrate.setText("Awesome");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No point",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {//
            case R.id.btnfeedback:
                //get data

                int valFromPatient = (int) ratingBar.getRating();
                double actualRate =((totalInt*rateInt)+valFromPatient)/((totalInt+1));

          //set data
                rating_class rating = new rating_class(actualRate,totalInt+1);
                FirebaseDatabase.getInstance().getReference("rating")
                        .child("1002")
                        .setValue(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Thank You ", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent=new Intent(getApplicationContext(),Patient_User_Profile.class);
                startActivity(intent);

                break;

        }
    }
    private void RetriveRating() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("rating").child("1002");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    rating_class ratingclass = dataSnapshot.getValue(rating_class.class);
                    totalInt=ratingclass.getTotalReview();
                    rateInt=ratingclass.getRate();
                    Toast.makeText(getApplicationContext(),""+totalInt+"\t"+rateInt,Toast.LENGTH_SHORT).show();
                   //int totalInt,rateInt;

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
