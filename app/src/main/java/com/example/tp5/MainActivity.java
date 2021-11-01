package com.example.tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TimePicker.OnTimeChangedListener {

    String formatted_time = "";

    EditText task;
    Button add;
    ListView todoList;
    Spinner daySpinner;
    TimePicker timer;

    String [] days = {
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    // liste des taches simple
    ArrayList<String> items = new ArrayList<String>(){
        {
            add("todo 1");
            add("todo 2");
            add("todo 3");
            add("todo 4");
            add("todo 5");
        }
    };

    ArrayList<HashMap<String,String>> values= new ArrayList<HashMap<String,String>>(){
        {
            add( new HashMap<String, String>(){{
                put("day", "samedi");
                put("time", "08:00AM");
                put("tache", "faire du sport");
            }} );
        }
    };

    ArrayAdapter simpleItemAdapter;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = findViewById(R.id.todoList);
        task = (EditText) findViewById(R.id.task);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        timer = (TimePicker) findViewById(R.id.timer);
        add = (Button) findViewById(R.id.add);

        timer.setOnTimeChangedListener(this);

        daySpinner.setOnItemSelectedListener(this);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                days
        );

        daySpinner.setAdapter(spinnerAdapter);



        // Liste des taches simple

        simpleItemAdapter = new ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                items
        );

        simpleAdapter = new SimpleAdapter(
                this,
                values,
                R.layout.todo_item,
                new String [] {"day", "time", "tache"},
                new int [] {R.id.day, R.id.time, R.id.tache}
        );

        //SET ONE OF TWO ADAPTERS

        //todoList.setAdapter(simpleItemAdapter); //simple items adapter
        todoList.setAdapter(simpleAdapter); //listed hashmap items

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG)
                        .show();

                Intent intent = new Intent(getApplicationContext(), ChosenTask.class);
                intent.putExtra("chosenTask", parent.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });

        add.setOnClickListener(this);
    }


    //IMPLEMENTED LISTENERS

    @Override
    public void onClick(View v) {

        //simple items
        /*items.add(task.getText().toString());
        simpleItemAdapter.notifyDataSetChanged();*/

        //listed hashmap items
        values.add(new HashMap<String, String>(){{
            put("day", daySpinner.getSelectedItem().toString());
            put("time", formatted_time);
            put("tache", task.getText().toString());
        }});
        simpleAdapter.notifyDataSetChanged();

        task.setText("");
    /*
        Toast.makeText(getApplicationContext(),
                task.getText().toString(),
                Toast.LENGTH_LONG)
                .show();


        Toast.makeText(getApplicationContext(),
                formatted_time,
                Toast.LENGTH_LONG)
                .show();

    */
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),
                days[position],
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        if(hourOfDay > 11)
            formatted_time = (hourOfDay-12) + ":" + minute + "PM";
        else
            formatted_time = hourOfDay + ":" + minute + "AM";
    }
}