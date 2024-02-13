// This is the main page of this application

package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button newOrderButton, memberButton, historyButton;
    private long datetimeLabel = System.currentTimeMillis();
    private SimpleDateFormat dateTimeFormat;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateTime();

        newOrderButton = findViewById(R.id.newOrderButton);
        newOrderButton.setOnClickListener(MainActivity.this);

        memberButton = findViewById(R.id.memberButton);
        memberButton.setOnClickListener(MainActivity.this);

        historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(MainActivity.this);
    }

    // Convert SystemMillis into date and time
    public void dateTime() {
        TextView dateTimeLabel = findViewById(R.id.dateTimeLabel);
        dateTimeFormat = new SimpleDateFormat("EEE, dd-MM-yyyy HH:mm:ss" );
        currentDate = dateTimeFormat.format(datetimeLabel);
        dateTimeLabel.setText(currentDate);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.newOrderButton) {
            // Create an intent to launch the NewOrderActivity
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
            return;
        }
        else if (view.getId() == R.id.memberButton) {
            // Create an intent to launch the MemberActivity
            Intent intent = new Intent(MainActivity.this, MemberAdd.class);
            startActivity(intent);
            return;
        }
        else if (view.getId() == R.id.historyButton) {
            // Create an intent to launch the OrderRecordHistory
            Intent intent = new Intent(MainActivity.this, OrderRecordHistory.class);
            startActivity(intent);
            return;
        }
    }
}