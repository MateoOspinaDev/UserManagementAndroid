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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText emailInput;
    EditText nameInput;
    EditText phoneInput;
    EditText passwordInput;
    Button signInLink;
    Button signUpButton;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        emailInput = (EditText) findViewById(R.id.emailInput);
        nameInput = (EditText) findViewById(R.id.emailLoginInput);
        phoneInput = (EditText) findViewById(R.id.phoneInput);
        passwordInput = (EditText) findViewById(R.id.inputLoginPassword);

        signUpButton=(Button)findViewById(R.id.signUpButton);
        signInLink =(Button)findViewById(R.id.signInButton);

        signInLink.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpButton:
                String email = (emailInput.getText().toString());
                String name = (nameInput.getText().toString());
                String phone = (phoneInput.getText().toString());
                String password = (passwordInput.getText().toString());
                User user = new User(email, name, phone, password);
                if (!user.isNull()) {
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
                                    Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if (!exists) {
                                database.push().setValue(user);
                                ocultarTeclado();
                                Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                }
            case R.id.signInButton:
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

        private void ocultarTeclado(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}