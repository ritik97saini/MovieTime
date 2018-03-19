package com.example.aniket.movietime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;


public class register extends AppCompatActivity {
    EditText username,password,cpassword;
    FirebaseAuth mauth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mauth=FirebaseAuth.getInstance();
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        cpassword=(EditText)findViewById(R.id.cpassword);

    }
    public void register(View view)
    {
        String name,pass,cpass;
        name=username.getText().toString();
        pass=password.getText().toString();
        cpass=cpassword.getText().toString();

        if(name==""||pass==""||cpass=="")
        {
            Toast.makeText(this,"One or more fields are empty!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.equals(cpass))
        {
            mauth.createUserWithEmailAndPassword(name, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                mauth.signOut();
                                Intent i= new Intent(register.this,login.class);
                                startActivity(i);


                            } else {
                                // If sign in fails, display a message to the user.
                                FirebaseAuthException e= (FirebaseAuthException)task.getException();

                                Toast.makeText(register.this, "Authentication failed.  "+e.getMessage(),
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
            return;

        }


        Toast.makeText(this,"passwords do not match"+pass+"   "+cpass,Toast.LENGTH_SHORT).show();
        return;

    }
}
