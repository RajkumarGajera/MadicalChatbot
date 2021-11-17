package com.bae.dialogflowbot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText userName,userEmail,userPassword,userPhone;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String name, email, password, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validate()){
                    //Upload to database
                    String user_email = userEmail.getText().toString();
                    String user_password = userPassword.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                sendUserData();
                                firebaseAuth.signOut();
                                Toast.makeText(Registration.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this,main.class));
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this,main.class));
            }
        });

    }

    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.etRegName);
        userEmail = (EditText)findViewById(R.id.etRegGmail);
        userPassword = (EditText)findViewById(R.id.etRegPassword);
        userPhone = (EditText)findViewById(R.id.etRegPhone);
        regButton = (Button)findViewById(R.id.btnRegLogin);
        userLogin = (TextView)findViewById(R.id.btnGotoLogin);
    }

    private Boolean Validate(){
        Boolean result = false;

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        phone = userName.getText().toString();


        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()){
            progressDialog.dismiss();
            Toast.makeText(this,"Please enter all the details.",Toast.LENGTH_SHORT).show();
        }else{
            result=true;
        }

        return result;
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid()).child("USER");
        UserProfile userProfile = new UserProfile(name, email);
        myRef.setValue(userProfile);
    }
}
