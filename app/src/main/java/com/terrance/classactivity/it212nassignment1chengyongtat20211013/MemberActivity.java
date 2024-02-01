package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class MemberActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TextView homeTextView;

    MemberDB memberDB = null;
    // ONE (1) is just a random UNIQUE NUMBER
    // It could be any number as long as it is UNIQUE
    private static final int REQUEST_UPDATE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        homeTextView = findViewById(R.id.homeTextView);
        homeTextView.setOnClickListener(MemberActivity.this);

        memberDB = new MemberDB(MemberActivity.this);

        refreshList();
    }

    public void refreshList() {
        ListView memberLists = findViewById(R.id.memberList);
        Member[] members = memberDB.getAllMembers();

        if(members != null) {
            ArrayAdapter<Member> ordersAdapter = new ArrayAdapter<>(
                    MemberActivity.this,
                    R.layout.my_styles,
                    members
            );
            memberLists.setOnItemClickListener(MemberActivity.this);
            memberLists.setOnItemLongClickListener(MemberActivity.this);
            memberLists.setAdapter(ordersAdapter);
        }
        else{
            memberLists.setAdapter(null);
        }

        // Remember to close database!!!
        memberDB.close();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.homeTextView) {
            Intent intent = new Intent(MemberActivity.this, MainActivity.class);
            startActivity(intent);
            return;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Member member = (Member) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(MemberActivity.this, MemberUpdate.class);
        intent.putExtra("ID", member.getId());
        startActivityForResult(intent, REQUEST_UPDATE_ACTIVITY);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_UPDATE_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK) {
                refreshList();
            }

        }
    }
}