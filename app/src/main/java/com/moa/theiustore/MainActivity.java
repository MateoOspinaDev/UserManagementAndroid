package com.moa.theiustore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moa.theiustore.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button buttonBackToUserPanel;
    private ListView lvDatos;
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference("User");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        String registerEmail = getIntent().getStringExtra("email");
        lvDatos = (ListView) findViewById(R.id.lvDatos);
        buttonBackToUserPanel = (Button) findViewById(R.id.buttonBackToUserPanel);

        buttonBackToUserPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserPanelActivity.class);
                intent.putExtra("email", registerEmail);
                startActivity(intent);
                finish();
            }
        });
        listarUsuarios ();

    }
    private void listarUsuarios () {
        ArrayList<User> list = new ArrayList<>();
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, list);
        lvDatos.setAdapter(adapter);
        database.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                list.add(user);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
