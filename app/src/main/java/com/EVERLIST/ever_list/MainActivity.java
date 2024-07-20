package com.EVERLIST.ever_list;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Ryan McCollum
public class MainActivity extends AppCompatActivity {
        Button todoButton;
        Button Notes;
        Button ShopList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoButton = (Button) findViewById(R.id.todoButton);

        Notes = (Button) findViewById(R.id.Notes);

        ShopList = (Button) findViewById(R.id.ShopList);

        todoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListToDo();
            }
        });

        Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesList();
            }
        });

        ShopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shopping_List();
            }
        });
    }
    public void ListToDo(){
        Intent intent = new Intent(this,ToDoList.class);
        startActivity(intent);
    }
    public void NotesList(){
        Intent intent = new Intent(this,Notes.class);
        startActivity(intent);
    }
    public void Shopping_List(){
        Intent intent = new Intent(this,ShopList.class);
        startActivity(intent);
    }
}
