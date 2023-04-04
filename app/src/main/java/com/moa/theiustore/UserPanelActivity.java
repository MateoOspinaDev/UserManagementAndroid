package com.moa.theiustore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class UserPanelActivity extends AppCompatActivity implements View.OnClickListener{

    EditText phoneInput;
    EditText emailInput;
    EditText passwordInput;
    EditText nameInput;
    Button update;
    Button delete;
    Button goToPoke;
    Button logout;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference("User");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        nameInput = (EditText) findViewById(R.id.nameFieldUpdate);
        emailInput = (EditText) findViewById(R.id.emailFieldUpdate);
        phoneInput = (EditText) findViewById(R.id.phoneFieldUpdate);
        passwordInput = (EditText) findViewById(R.id.passwordFieldUpdate);
        update = (Button) findViewById(R.id.updateButton);
        delete = (Button) findViewById(R.id.deleteButton);
        goToPoke = (Button) findViewById(R.id.GoToUserList);
        logout = (Button) findViewById(R.id.signOff);

        String emailFromLogin = getIntent().getStringExtra("email");
        System.out.println(emailFromLogin);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user.getEmail().equals(emailFromLogin)) {
                        emailInput.setText(user.getEmail());
                        nameInput.setText(user.getName());
                        phoneInput.setText(user.getPhone());
                        passwordInput.setText(user.getPassword());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        logout.setOnClickListener(this);
        goToPoke.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String emailFromLogin = getIntent().getStringExtra("email");
        switch (v.getId()) {
            case R.id.deleteButton:
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.child("email").getValue().equals(emailFromLogin)){
                                //print user
                                User user = snapshot.getValue(User.class);
                                System.out.println(user);
                                snapshot.getRef().removeValue();
                                Toast.makeText(UserPanelActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserPanelActivity.this, SignInActivity.class);
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle error
                    }
                });
            case R.id.updateButton:
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (name.equals("") || email.equals("") || password.equals("")) {
                    Toast.makeText(this, "Please fill name, email or password fields, they are required", Toast.LENGTH_SHORT).show();
                } else {
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if(snapshot.child("email").getValue().equals(emailFromLogin)){
                                    snapshot.getRef().child("name").setValue(name);
                                    snapshot.getRef().child("email").setValue(email);
                                    snapshot.getRef().child("phone").setValue(phone);
                                    snapshot.getRef().child("password").setValue(password);
                                    Toast.makeText(UserPanelActivity.this, "User updated", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                }
                break;
            case R.id.GoToUserList:
                Intent intent = new Intent(UserPanelActivity.this, MainActivity.class);
                intent.putExtra("email", emailFromLogin);
                startActivity(intent);
                finish();
                break;
            case R.id.signOff:
                Intent intent2 = new Intent(UserPanelActivity.this, SignInActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}
