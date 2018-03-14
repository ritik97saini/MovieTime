package com.example.aniket.movietime;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity{

    EditText username,password;
    FirebaseAuth mauth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mauth=FirebaseAuth.getInstance();
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=mauth.getCurrentUser();
        if(user!=null)
        {
            Intent logged= new Intent(this,main.class);
            startActivity(logged);
        }

    }
    public void login(View view)
    {
        String name = username.getText().toString();
        String pass= password.getText().toString();


        mauth.signInWithEmailAndPassword(name, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mauth.getCurrentUser();
                            Intent logged= new Intent(login.this,main.class);
                            startActivity(logged);

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(login.this, "Authentication Failed..", Toast.LENGTH_SHORT).show();
                            password.setText("");

                        }

                        // ...
                    }
                });
    }
}
