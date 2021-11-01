package com.example.tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChosenTask extends AppCompatActivity {

    Intent intent;
    TextView chosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_task);

        intent = getIntent();

        chosenTask = (TextView) findViewById(R.id.chosenTask);
        chosenTask.setText(intent.getExtras().get("chosenTask").toString());
    }
}