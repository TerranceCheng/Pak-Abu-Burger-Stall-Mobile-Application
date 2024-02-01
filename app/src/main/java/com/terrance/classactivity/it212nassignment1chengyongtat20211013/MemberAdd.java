package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MemberAdd extends AppCompatActivity implements View.OnClickListener {

    private Button addButton, showButton;
    private TextView homeTextView;
    private EditText fullNameEdit, phoneEdit;
    private Spinner genderSpinner;
    private String[] GENDER = {"Female", "Male"};

    private long datetimeLabel = System.currentTimeMillis();
    private SimpleDateFormat dateTimeFormat;
    private String currentDate;

    // Student database object
    private MemberDB memberDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add);

        dateTime();

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(MemberAdd.this, android.R.layout.simple_list_item_1, GENDER);
        genderSpinner = findViewById(R.id.genderSpinner);
        genderSpinner.setSelection(1, false);
        genderSpinner.setAdapter(genderAdapter);

        fullNameEdit = findViewById(R.id.fullNameUpdateEdit);
        phoneEdit = findViewById(R.id.phoneUpdateEdit);

        homeTextView = findViewById(R.id.homeTextView);
        homeTextView.setOnClickListener(MemberAdd.this);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(MemberAdd.this);

        showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(MemberAdd.this);

        memberDB = new MemberDB(MemberAdd.this);
    }

    public void dateTime() {
        TextView dateTimeLabel = findViewById(R.id.dateTimeLabel);
        dateTimeFormat = new SimpleDateFormat("EEE, dd-MM-yyyy HH:mm:ss" );
        currentDate = dateTimeFormat.format(datetimeLabel);
        dateTimeLabel.setText(currentDate);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.addButton) {
            String name = fullNameEdit.getText().toString().trim();
            String phone = phoneEdit.getText().toString().trim();

            if (name.equals("") || phone.equals("")) {
                Toast.makeText(MemberAdd.this, "All inputs are required", Toast.LENGTH_LONG).show();
                return;
            }

            Member member = new Member(
                    name,
                    phone,
                    genderSpinner.getSelectedItemPosition() == 1
            );
            if (memberDB.addNewMember(member)) {
                Toast.makeText(MemberAdd.this, "Member added.", Toast.LENGTH_LONG).show();
                // Clear existing field and prepare for next entry
                genderSpinner.setSelection(0);
                phoneEdit.setText("");
                fullNameEdit.setText("");
                fullNameEdit.requestFocus();
            }

        }
        else if(view.getId() == R.id.showButton) {
            // Create an intent to launch the MemberActivity
            Intent intent = new Intent(MemberAdd.this, MemberActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.homeTextView) {
            Intent intent = new Intent(MemberAdd.this, MainActivity.class);
            startActivity(intent);
            return;
        }
    }
}