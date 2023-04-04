package com.moa.theiustore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moa.theiustore.model.User;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button singUpLink;
    Button signIn;
    EditText emailInput;
    EditText passwordInput;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference("User");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        emailInput = (EditText) findViewById(R.id.emailLoginInput);
        passwordInput = (EditText) findViewById(R.id.inputLoginPassword);
        signIn=(Button)findViewById(R.id.signInLoginButton);
        singUpLink =(Button)findViewById(R.id.singUpLink);
        singUpLink.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInLoginButton:
                String email;
                String password;
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean exists = false;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                if (user.getEmail().equals(emailInput.getText().toString())) {
                                    exists = true;
                                    if (user.getPassword().equals(passwordInput.getText().toString())) {
                                        Intent intent = new Intent(SignInActivity.this, UserPanelActivity.class);
                                        Toast.makeText(SignInActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                                        intent.putExtra("email", user.getEmail());
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                }
                            }
                            if (!exists) {
                                Toast.makeText(SignInActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                }
            case R.id.singUpLink:
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}