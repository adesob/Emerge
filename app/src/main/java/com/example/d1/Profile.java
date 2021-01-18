package com.example.d1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView nameTxt, cntNumTxt, cntNum2Txt, medNoteTxt,emergCntTxt ;
    private ImageButton btnEdit;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTxt = (TextView) findViewById(R.id.nameTxt);
        cntNumTxt = (TextView) findViewById(R.id.cntNumTxt);
        cntNum2Txt = (TextView) findViewById(R.id.cntNum2Txt);
        medNoteTxt = (TextView) findViewById(R.id.medNoteTxt);
        emergCntTxt = (TextView) findViewById(R.id.emergCntTxt);


        btnEdit = (ImageButton) findViewById(R.id.btnEdit);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mUser == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            mDatabase.child("User").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User curUser = dataSnapshot.getValue(User.class);

                    String name = curUser.getFirstName();
                    String email = curUser.getEmail();
                    String number = curUser.getNumber();
                    String medNote = curUser.getMedNote();
                    String cntNum = curUser.getEmergCntNum();
                    String emergCnt = curUser.getEmergCnt();

                    //String display = "Welcome " + name + "! You are logged in";
                    nameTxt.setText(name);
                    cntNumTxt.setText(number);
                    emergCntTxt.setText(emergCnt); ;
                    cntNum2Txt.setText(cntNum);
                    medNoteTxt.setText(medNote);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, ProfileEdit.class));
            }
        });
    }
}
