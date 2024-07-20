package com.EVERLIST.ever_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;
//Ryder Pridgen
public class Notes extends AppCompatActivity {
    private EditText editText;
    private Button saveButton;
    private LinearLayout noteSpace;
    private SharedPreferences sharedPreferences;
    private Set<String> notesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);

        editText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.saveButton);
        noteSpace = findViewById(R.id.noteSpace);
        sharedPreferences = getPreferences(MODE_PRIVATE);

        notesString = sharedPreferences.getStringSet("notes", new HashSet<>());
        for (String note : notesString) {
            addNoteToContainer(note);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = editText.getText().toString();
                if (!note.isEmpty()) {
                    notesString.add(note);
                    sharedPreferences.edit().putStringSet("notes", notesString).apply();
                    addNoteToContainer(note);
                    editText.setText("");
                }
            }
        });
        ImageView home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void addNoteToContainer(String note) {
        TextView textView = new TextView(this);
        textView.setText(note);
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(16);
        noteSpace.addView(textView);
    }
}