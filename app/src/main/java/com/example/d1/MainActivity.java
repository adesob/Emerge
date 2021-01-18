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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText _txtUsername, _txtPassword;
    private TextView _txtSignUp;
    private Button _btnLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _txtPassword = (EditText)findViewById(R.id.txtUserPassword);
        _txtUsername= (EditText)findViewById(R.id.txtUsername);
        _btnLogin = (Button)findViewById(R.id.btnLogin);
        _txtSignUp = (TextView)findViewById(R.id.txtSignUp);


        firebaseAuth = firebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        /*if (user != null){
        *    finish();
        *}
        */

        _btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                authenticate(_txtUsername.getText().toString().trim(), _txtPassword.getText().toString().trim());
            }
        });

        _txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
            }
        });
    }

    private void authenticate(String userName, String userPassword){
        if (validate()){
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, Profile.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private Boolean validate(){
        Boolean result = false;
        String name = _txtUsername.getText().toString();
        String password = _txtPassword.getText().toString();

        if(name.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }
}
