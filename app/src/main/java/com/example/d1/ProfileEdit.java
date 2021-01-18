package com.example.d1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEdit extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    String idneeded,name,email,password,number, cntN, cnt, medN;
    //private TextView introMessage;
    private EditText cntNum,emergCnt,cntNum2,medNote,name1;
    private TextView adsTxt, phTxt;
    private ImageButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //introMessage = (TextView) findViewById(R.id.introMessage);

        name1 = (EditText) findViewById(R.id.username);
        cntNum = (EditText) findViewById(R.id.cntNum);
        emergCnt = (EditText) findViewById(R.id.emergCnt);
        cntNum2 = (EditText) findViewById(R.id.cntNum2);
        medNote = (EditText) findViewById(R.id.medNote);


        //adsTxt = (TextView) findViewById(R.id.textView13);
        //phTxt = (TextView) findViewById(R.id.textView14);



        btnSave = (ImageButton) findViewById(R.id.btnSave);

        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else {
            mDatabase.child("User").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User curUser = dataSnapshot.getValue(User.class);
                    idneeded = dataSnapshot.getKey();

                    name = curUser.getFirstName();
                    email = curUser.getEmail();
                    password = curUser.getPassword();
                    number = curUser.getNumber();
                    cnt = curUser.getEmergCnt();
                    cntN = curUser .getEmergCntNum();
                    medN = curUser.getMedNote();


                    /*curemployee.setAddress(address);
                    curemployee.setNumber(number);
                    curemployee.setClinic(clinic);
                    curemployee.setInsurance(insurance);
                    curemployee.setPayment(payment);*/
                    //String display = "Welcome " + name + "! You are logged in";
                    //introMessage.setText(display);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        name1.setText(name);
        cntNum.setText(number);
        emergCnt.setText(cnt);
        cntNum2.setText(cntN);
        medNote.setText(medN);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res = updateEmployee(idneeded,name,email,password);
                if(res == true) {
                    startActivity(new Intent(ProfileEdit.this, Profile.class));
                }
            }
        });
    }

    private boolean updateEmployee(String id, String name, String email, String password){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(id);

        //cntNum,emergCnt,cntNum2,medNote,username

        String username = name1.getText().toString().trim();
        String number = cntNum.getText().toString().trim();
        String emc = emergCnt.getText().toString().trim();
        String nm2 = cntNum2.getText().toString().trim();
        String med = medNote.getText().toString().trim();

        boolean res = true;

        if(username.isEmpty() || number.isEmpty() || emc.isEmpty() || nm2.isEmpty() || med.isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            res = false;
        }

        if(res == true) {
            User curUser = new User(name, email, password, number, emc, nm2, med);
            databaseReference.setValue(curUser);
            Toast.makeText(this, "Account successfully updated", Toast.LENGTH_SHORT).show();
        }
        return res;
    }
}
