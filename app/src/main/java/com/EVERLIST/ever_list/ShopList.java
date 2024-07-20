package com.EVERLIST.ever_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
//Akilah Parks
public class ShopList extends AppCompatActivity{
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private EditText editTextItem;
    private EditText editTextQuantity;
    private EditText editTextUnit;
    private Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);

        listView = findViewById(R.id.listView);
        editTextItem = findViewById(R.id.editTextItem);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextUnit = findViewById(R.id.editTextUnit);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonShare = findViewById(R.id.buttonShare);

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareList();
            }
        });
//Ryan McCollum
        ImageView home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loadContent();
        setUpListViewListener();
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
        File readFrom = new File(path,"ShopList.txt" );
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
            FileOutputStream writer = new FileOutputStream(new File(path,"ShopList.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
//Akilah Parks
    private void addItem() {
        String item = editTextItem.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();
        String quantity = editTextQuantity.getText().toString();
        String unit = editTextUnit.getText().toString();

        if (!item.isEmpty()) {
            String itemEntry = category + ": " + item;
            if (!quantity.isEmpty() && !unit.isEmpty()) {
                itemEntry += " (" + quantity + " " + unit + ")";
            }
            itemsAdapter.add(itemEntry);
            editTextItem.setText("");
            editTextQuantity.setText("");
            editTextUnit.setText("");
        }
    }

    private void shareList() {
        StringBuilder listContent = new StringBuilder();
        for (String item : items) {
            listContent.append(item).append("\n");
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, listContent.toString());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

}
