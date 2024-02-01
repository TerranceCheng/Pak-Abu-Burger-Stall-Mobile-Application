package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CashThankYouActivity extends AppCompatActivity implements View.OnClickListener {

    TextView doneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_thank_you);

        doneTextView = findViewById(R.id.doneTextView);
        doneTextView.setOnClickListener(CashThankYouActivity.this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.doneTextView) {
            Intent intent = new Intent(CashThankYouActivity.this, MainActivity.class);
            startActivity(intent);
            return;
        }
    }
}