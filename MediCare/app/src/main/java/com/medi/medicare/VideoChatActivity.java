package com.medi.medicare;

import android.Manifest;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import java.util.HashMap;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class VideoChatActivity extends AppCompatActivity implements Session.SessionListener,
PublisherKit.PublisherListener{
    private  static String API_KEY= "46872194";
    private static String SESSION_ID="2_MX40Njg3MjE5NH5-MTYwMDA5ODg5MjM0M35yeWtpQUpORDlaOG1yenIwMGppUVI4UjZ-fg";
    //private static String TOKEN = "T1==cGFydG5lcl9pZD00Njg3MjAyNCZzaWc9OTdkOTA5NGE5ZTNkMTIyNDVmMWI0MjdiYTIxYzAyNGY2ODI3NzAwMTpzZXNzaW9uX2lkPTFfTVg0ME5qZzNNakF5Tkg1LU1UVTVOalExTXpJNU1qQXlNWDRyTWxaT1NXTnRhSFZXV1c1YVExTXlSVUpOZEZJeEsweC1mZyZjcmVhdGVfdGltZT0xNTk2NDUzMjkyJnJvbGU9bW9kZXJhdG9yJm5vbmNlPTE1OTY0NTMyOTIuMDI3NTE1ODI0MDk1ODg=";
   // private static String TOKEN = "T1==cGFydG5lcl9pZD00Njg3MjE5NCZzaWc9ZDBkNzc0NDk0ZTc4YTkwYjE4ZTU1NWE0ZDE5ZTY1NTVmMWM3ZDYzNjpzZXNzaW9uX2lkPTFfTVg0ME5qZzNNakU1Tkg1LU1UVTVOamczTXpZd01EUXdOWDR5WWxGNWN6QkphR2RJTUVjMWFFNXdiMWQxYWpkNWNGbC1mZyZjcmVhdGVfdGltZT0xNTk2ODczNjIwJm5vbmNlPTAuMjczNjE5NTgyNjY3Njc5NyZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTk5NDY1NjE5JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00Njg3MjE5NCZzaWc9YWM2OTQ0OTM3ODIzN2E5NTBhZmFjZDI2ZmQ3NTY4NGE3NTRhMDQ1YTpzZXNzaW9uX2lkPTJfTVg0ME5qZzNNakU1Tkg1LU1UWXdNREE1T0RnNU1qTTBNMzV5ZVd0cFFVcE9SRGxhT0cxeWVuSXdNR3BwVVZJNFVqWi1mZyZjcmVhdGVfdGltZT0xNjAwMDk4OTA1Jm5vbmNlPTAuNTA2MDQ4MDE1NDYyMDcwNiZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNjAyNjkwOTA0JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static final String LOG_TAG = VideoChatActivity.class.getSimpleName();
    private static final  int RC_VIDEO_APP_PERM=124;

    private ImageView closeVideoChatBtn;
    private DatabaseReference usersRef;
    private String user="";
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    FirebaseAuth fAuth;

    String patientId,callingOrRingingCheck,patientIdFromDoctorButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);
        fAuth = FirebaseAuth.getInstance();
     /*   user = fAuth.getCurrentUser().getUid();
        Toast.makeText(getApplicationContext(),""+user,Toast.LENGTH_SHORT).show();*/

        closeVideoChatBtn = findViewById(R.id.close_video_chat_btn);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {  //ene error dite pare karon duita activity thika ek vabe data pathaisi
            callingOrRingingCheck = extras.getString("key1");
        }
        if(callingOrRingingCheck.equals("ring"))
        {
            patientId=FirebaseAuth.getInstance().getCurrentUser().getUid();
            Toast.makeText(getApplicationContext(),""+patientId,Toast.LENGTH_SHORT).show();
        }
        if(callingOrRingingCheck.equals("call"))
        {
            Bundle extras1 = getIntent().getExtras();
            if (extras != null) {
                patientIdFromDoctorButton = extras1.getString("key2");
            }
            patientId=patientIdFromDoctorButton;
            Toast.makeText(getApplicationContext(),""+patientId,Toast.LENGTH_SHORT).show();
        }
        closeVideoChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Patient").child(patientId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Map<String, Object> postValues = new HashMap<String,Object>();
                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                    postValues.put(snapshot.getKey(),snapshot.getValue());
                                                                }
                                                                postValues.put("pick", "picked_cancle");
                                                                FirebaseDatabase.getInstance().getReference("Patient").child(patientId)
                                                                        .updateChildren(postValues);
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {}
                                                        }
                        );
            }
        });







        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Patient").child(patientId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    // String pickCheckCheck = dataSnapshot.getValue(String.class);
                    PatientClass patient1 = dataSnapshot.getValue(PatientClass.class);
                    String pickCheckCheck = patient1.getPick();
                    if(pickCheckCheck.equals("picked_cancle") ) {
                        Toast.makeText(getApplicationContext(),"picked_cancle",Toast.LENGTH_SHORT).show();
                        if(callingOrRingingCheck.equals("ring"))
                        {
                            Intent intent=new Intent(getApplicationContext(),doctor_review.class);
                            startActivity(intent);
                        }
                        finish();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        requestPermissions();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            mPublisherViewContainer = findViewById(R.id.publisher_container);
            mSubscriberViewContainer = findViewById(R.id.subscriber_container);
            // initialize view objects from your layout
            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(VideoChatActivity.this);
            mSession.connect(TOKEN);

            // initialize and connect to the session


        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }


    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());

        if (mPublisher.getView() instanceof GLSurfaceView){
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }

        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {

    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }
    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDatabase.getInstance().getReference("Patient").child(patientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            postValues.put(snapshot.getKey(),snapshot.getValue());
                                                        }
                                                        postValues.put("pick", "no");
                                                        FirebaseDatabase.getInstance().getReference("Patient").child(patientId)
                                                                .updateChildren(postValues);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {}
                                                }
                );

    }
}

