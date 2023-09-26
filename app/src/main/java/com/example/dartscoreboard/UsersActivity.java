package com.example.dartscoreboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity implements OnClickListener{

    private ArrayList<User> usersList;
    private RecyclerView recyclerView;


    private Button addNewUserButton;

    private EditText editText;

    private recyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        setupUI();
    }

    private void setAdapter() {
        adapter = new recyclerAdapter(usersList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_new_user_button){
            Log.d("dom test","onAddPlayerButtonclick");
            onAddNewUserButtonClick(editText.getText().toString());
        }
    }

    private void onAddNewUserButtonClick(String nameToAdd) {
        if (!nameToAdd.isEmpty()){
            usersList.add(new User(nameToAdd));
            adapter.notifyDataSetChanged();
        }
    }

    private void setupUI(){
        editText = findViewById(R.id.inputUserNameEditText);
        addNewUserButton = findViewById(R.id.add_new_user_button);
        addNewUserButton.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view_username_list);
        usersList = new ArrayList<>();
        setAdapter();

    }

}