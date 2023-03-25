package com.moa.theiustore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moa.theiustore.dao.UserDao;
import com.moa.theiustore.model.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText emailInput;
    EditText nameInput;
    EditText phoneInput;
    EditText passwordInput;

    UserDao userDao;

    Button signInLink;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        emailInput = (EditText) findViewById(R.id.emailInput);
        nameInput = (EditText) findViewById(R.id.emailLoginInput);
        phoneInput = (EditText) findViewById(R.id.phoneInput);
        passwordInput = (EditText) findViewById(R.id.inputLoginPassword);

        userDao = new UserDao(this);

        signUpButton=(Button)findViewById(R.id.signUpButton);

        signInLink =(Button)findViewById(R.id.signInButton);

        signInLink.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpButton:
                User user = new User();
                user.setEmail(emailInput.getText().toString());
                user.setName(nameInput.getText().toString());
                user.setPhone(phoneInput.getText().toString());
                user.setPassword(passwordInput.getText().toString());
                if (!user.isNull()){
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else if(!(userDao.getUserByEmail(user.getEmail()) == null)){
                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                }else if(userDao.addUser(user)){
                    Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.signInButton:
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}