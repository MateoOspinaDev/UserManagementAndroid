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

public class UserPanelActivity extends AppCompatActivity implements View.OnClickListener{

    EditText phoneInput;
    EditText emailInput;
    EditText passwordInput;
    EditText nameInput;
    Button update;
    Button delete;
    Button goToPoke;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        UserDao userDao = new UserDao(this);

        nameInput = (EditText) findViewById(R.id.nameFieldUpdate);
        emailInput = (EditText) findViewById(R.id.emailFieldUpdate);
        phoneInput = (EditText) findViewById(R.id.phoneFieldUpdate);
        passwordInput = (EditText) findViewById(R.id.passwordFieldUpdate);
        update = (Button) findViewById(R.id.updateButton);
        delete = (Button) findViewById(R.id.deleteButton);
        goToPoke = (Button) findViewById(R.id.goToListPokemonsbutton);

        int userId = getIntent().getIntExtra("user", -1);

        User user = userDao.getUserById(userId);

        emailInput.setText(user.getEmail());
        nameInput.setText(user.getName());
        phoneInput.setText(user.getPhone());
        passwordInput.setText(user.getPassword());

        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        goToPoke.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UserDao userDao = new UserDao(this);
        switch (v.getId()) {
            case R.id.deleteButton:
                userDao.deleteUser(getIntent().getIntExtra("user", -1));
                Intent i = new Intent(UserPanelActivity.this, SignInActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.updateButton:
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (name.equals("") || email.equals("") || password.equals("")) {
                    Toast.makeText(this, "Please fill name, email or password fields, they are required", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setId(getIntent().getIntExtra("user", -1));
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setPassword(password);
                    userDao.updateUser(user);
                    Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.goToListPokemonsbutton:
                Intent intent = new Intent(UserPanelActivity.this, ContactListActivity.class);
                intent.putExtra("user", getIntent().getIntExtra("user", -1));
                startActivity(intent);
                finish();
                break;
        }
    }
}
