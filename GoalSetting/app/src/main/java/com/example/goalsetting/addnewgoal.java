package com.example.goalsetting;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class addnewgoal extends AppCompatActivity {

    public AppDatabase db;
    public List<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewgoal);
        initData();

        setupToolBar();
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.addgoal);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);


        Spinner spinner = findViewById(R.id.categorySpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> goalAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);

// Specify the layout to use when the list of choices appears
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(goalAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tick_button, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    public void initData() {
        if(getApplicationContext() != null) {
            db = Room.databaseBuilder(
                    getApplicationContext(),
                    AppDatabase.class, "GoalSettingDB"
            ).allowMainThreadQueries().build();
        }

        categories = db.categoryDao().getAllTitles();
    }
}
