package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ewalletPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    TextView doneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet_payment);

        doneTextView = findViewById(R.id.doneTextView);
        doneTextView.setOnClickListener(ewalletPaymentActivity.this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.doneTextView) {
            Intent intent = new Intent(ewalletPaymentActivity.this, CashThankYouActivity.class);
            startActivity(intent);
            return;
        }
    }
}