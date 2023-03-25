package com.moa.theiustore;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moa.theiustore.dao.UserDao;
import com.moa.theiustore.model.CustomAdapter;
import com.moa.theiustore.model.User;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    ListView listViewContact;
    List<User> usersList;
    UserDao userDao;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDao = new UserDao(this);

        listViewContact = findViewById(R.id.listViewContact);



        CustomAdapter customAdapter = new CustomAdapter(this, GetData());
        listViewContact.setAdapter(customAdapter);

        listViewContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                User user = usersList.get(position);
                Toast.makeText(getBaseContext(), user.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<User> GetData() {
        usersList = new ArrayList<>();
        usersList = userDao.getUsers();
        return usersList;
    }
}
