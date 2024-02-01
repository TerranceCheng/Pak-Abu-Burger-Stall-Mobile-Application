package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
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

public class MemberUpdate extends AppCompatActivity implements View.OnClickListener {

    private Button updateButton;
    private EditText fullNameUpdateEdit, phoneUpdateEdit;
    private TextView homeTextView;
    private Spinner genderSpinner;
    int id;
    private String[] GENDER = {"Female", "Male"};

    private long datetimeLabel = System.currentTimeMillis();
    private SimpleDateFormat dateTimeFormat;
    private String currentDate;

    private static final int REQUEST_UPDATE_ACTIVITY = 1;

    // Student database object
    private MemberDB memberDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_update);

        dateTime();

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(MemberUpdate.this, android.R.layout.simple_list_item_1, GENDER);
        genderSpinner = findViewById(R.id.genderSpinner);
        genderSpinner.setSelection(1, false);
        genderSpinner.setAdapter(genderAdapter);

        homeTextView = findViewById(R.id.homeTextView);
        homeTextView.setOnClickListener(MemberUpdate.this);

        fullNameUpdateEdit = findViewById(R.id.fullNameUpdateEdit);
        phoneUpdateEdit = findViewById(R.id.phoneUpdateEdit);

        updateButton = findViewById(R.id.addButton);
        updateButton.setOnClickListener(MemberUpdate.this);

        memberDB = new MemberDB(MemberUpdate.this);

        id = getIntent().getIntExtra("ID", 0);
        Member members = memberDB.getMemberById(id);
        if (members != null) {
            fullNameUpdateEdit.setText(members.getFullname());
            phoneUpdateEdit.setText(String.valueOf(members.getPhoneNumber()));
            genderSpinner.setSelection(members.isMale() ? 1:0);
        }
        else {
            // Student by given id doesn't exist
            // Disable the save button and alert user
            updateButton.setEnabled(false);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MemberUpdate.this);
            dialog.setTitle("Error");
            dialog.setMessage("Member record doesn't exist");
            dialog.setPositiveButton(android.R.string.yes, null);
            dialog.show();
        }
    }

    public void dateTime() {
        TextView dateTimeLabel = findViewById(R.id.dateTimeLabel);
        dateTimeFormat = new SimpleDateFormat("EEE, dd-MM-yyyy HH:mm:ss" );
        currentDate = dateTimeFormat.format(datetimeLabel);
        dateTimeLabel.setText(currentDate);
    }

    @Override
    public void onClick(View view) {
        String name = fullNameUpdateEdit.getText().toString().trim();
        String phone = phoneUpdateEdit.getText().toString().trim();

        if (name.equals("") || phone.equals("")) {
            Toast.makeText(MemberUpdate.this, "All inputs are required", Toast.LENGTH_LONG).show();
            return;
        }
        Member members = new Member(
                id,
                name,
                phone,
                genderSpinner.getSelectedItemPosition()==1
        );

        AlertDialog.Builder dialog = new AlertDialog.Builder(MemberUpdate.this);
        if(memberDB.updateMember(members)) {
            dialog.setTitle("Update OK");
            dialog.setMessage("Member updated");
            // Send back value to indicate OK
            setResult(Activity.RESULT_OK);
        }
        else {
            dialog.setTitle("Update Error");
            dialog.setMessage("Failed to update member");
            // Send back value to indicate CANCEL
            setResult(Activity.RESULT_CANCELED);
        }
        dialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.show();

        if(view.getId() == R.id.homeTextView) {
            Intent intent = new Intent(MemberUpdate.this, MainActivity.class);
            startActivity(intent);
            return;
        }
    }
}