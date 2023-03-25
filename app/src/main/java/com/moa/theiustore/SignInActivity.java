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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button singUpLink;
    Button signIn;
    EditText emailInput;
    EditText passwordInput;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        emailInput = (EditText) findViewById(R.id.emailLoginInput);
        passwordInput = (EditText) findViewById(R.id.inputLoginPassword);
        signIn=(Button)findViewById(R.id.signInLoginButton);
        singUpLink =(Button)findViewById(R.id.singUpLink);
        userDao = new UserDao(this);

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
                }else if(userDao.getUserByEmail(email) == null){
                    Toast.makeText(this, "User don't exists", Toast.LENGTH_SHORT).show();
                }else if(userDao.loginUser(email, password)){
                    User user = userDao.getUserByEmail(email);
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent ( SignInActivity.this, UserPanelActivity.class);
                    intent.putExtra("user", user.getId());
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.singUpLink:
                Intent i = new Intent ( SignInActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
                break;
        }

    }
}