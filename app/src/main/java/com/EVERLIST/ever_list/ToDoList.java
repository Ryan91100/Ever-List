package com.EVERLIST.ever_list;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ToDoList extends AppCompatActivity {
//Ryan McCollum
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list);

        listView = findViewById(R.id.listView);
        Button addButton = findViewById(R.id.AddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);

            }
        });

        ImageView home = findViewById(R.id.Home);

       home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter((itemsAdapter));
        setUpListViewListener();

        loadContent();

    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void loadContent(){
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path,"ToDo.txt" );
        byte[] content = new byte[(int) readFrom.length()];

        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);

            String s = new String(content);

            s = s.substring(1, s.length() - 1);
            String split[] = s.split(",");
            items = new ArrayList<>(Arrays.asList(split));
            itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            listView.setAdapter(itemsAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path,"ToDo.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.todo_item);
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))) {
            itemsAdapter.add(itemText);
            input.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Item", Toast.LENGTH_LONG).show();
        }
    }
}