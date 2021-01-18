package com.example.d1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText _txtUserName,_txtUserEmail,_txtUserPassword;
    private Button _btnRegister;
    private TextView _txtInfo;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        _btnRegister = (Button)findViewById(R.id.btnRegister);
        _txtUserName = (EditText)findViewById(R.id.txtUserNamer);
        _txtUserEmail = (EditText)findViewById(R.id.txtUserEmail);
        _txtUserPassword = (EditText)findViewById(R.id.txtUserPassword);
        _txtInfo = (TextView) findViewById(R.id.txtInfo);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();



        _btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()) {
                    String userEmail = _txtUserEmail.getText().toString().trim();
                    String userPassword = _txtUserPassword.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(Registration.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String userName =  _txtUserName.getText().toString().trim();
                            String userEmail = _txtUserEmail.getText().toString().trim();
                            String userPassword = _txtUserPassword.getText().toString().trim();


                            if (task.isSuccessful()){

                                final User mUser = new User(userName,userEmail,userPassword);
                                mDatabase.getReference("User").child(firebaseAuth.getCurrentUser().getUid()).setValue(mUser).addOnCompleteListener(Registration.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Registration.this, Profile.class);

                                            startActivity(intent);
                                        }else Toast.makeText(Registration.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else {
                                Toast.makeText(Registration.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });


                }


            }
        });






    }

    private Boolean validate(){
        Boolean result = false;
        String name = _txtUserName.getText().toString();
        String password = _txtUserPassword.getText().toString();
        String email = _txtUserEmail.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(Registration.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

}
