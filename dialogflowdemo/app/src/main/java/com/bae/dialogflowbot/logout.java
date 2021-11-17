package com.bae.dialogflowbot;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class logout extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
//    private CardView cv5;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.menu.menu_main);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        cv5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });
//    }

    private void logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(logout.this, MainActivity.class));
    }
}
