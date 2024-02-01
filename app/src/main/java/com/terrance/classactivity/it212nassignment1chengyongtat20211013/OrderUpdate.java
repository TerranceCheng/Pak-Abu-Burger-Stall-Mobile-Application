package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderUpdate extends AppCompatActivity implements View.OnClickListener {

    private Button updateButton;
    private TextView homeTextView, idTextView, paymentMethodTextView, dateTextView, netPriceDetailTextView,
            discountDetailTextView, totalDetailTextView, paidDetailTextView, changeDetailTextView,
            chickenPattyAmount, chickenSpecialPattyAmount, lambPattyAmount, lambSpecialPattyAmount,
            taxTextView;

    private SimpleDateFormat dateTimeFormat;

    private OrderDB orderDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_update);

        dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss" );

        homeTextView = findViewById(R.id.homeTextView);
        homeTextView.setOnClickListener(OrderUpdate.this);

        chickenPattyAmount = findViewById(R.id.chickenPattyAmountTextView2);
        chickenSpecialPattyAmount = findViewById(R.id.chickenSpecialPattyAmountTextView2);
        lambPattyAmount = findViewById(R.id.lambPattyAmountTextView2);
        lambSpecialPattyAmount = findViewById(R.id.lambSpecialPattyAmountTextView2);
        idTextView = findViewById(R.id.idTextView2);
        dateTextView = findViewById(R.id.dateTextView2);
        netPriceDetailTextView = findViewById(R.id.netPriceDetailTextView);
        discountDetailTextView = findViewById(R.id.discountDetailTextView);
        taxTextView = findViewById(R.id.taxDetailTextView2);
        totalDetailTextView = findViewById(R.id.totalDetailTextView);
        paidDetailTextView = findViewById(R.id.paidDetailTextView);
        changeDetailTextView = findViewById(R.id.changeDetailTextView);
        paymentMethodTextView = findViewById(R.id.paymentMethodTextView3);

        orderDB = new OrderDB(OrderUpdate.this);

        int id = getIntent().getIntExtra("ID", 0);
        Order order = orderDB.getOrderById(id);
        if(order != null) {
            idTextView.setText(String.format(Locale.ENGLISH, "%02d", order.getId()));
            chickenPattyAmount.setText(String.format(Locale.ENGLISH, "%2d", order.getC_patty()));
            chickenSpecialPattyAmount.setText(String.format(Locale.ENGLISH, "%2d", order.getC_s_patty()));
            lambPattyAmount.setText(String.format(Locale.ENGLISH, "%2d", order.getL_patty()));
            lambSpecialPattyAmount.setText(String.format(Locale.ENGLISH, "%2d", order.getL_s_patty()));
            dateTextView.setText(String.format(Locale.ENGLISH, "%s", dateTimeFormat.format(order.getDateTime())));
            paymentMethodTextView.setText(String.format(Locale.ENGLISH, "%s", order.getPaymentMethod()));
            netPriceDetailTextView.setText(String.format(Locale.ENGLISH, "RM%.2f", order.getNetPrice()));
            discountDetailTextView.setText(String.format(Locale.ENGLISH, "RM%.2f", order.getDiscount()));
            taxTextView.setText(String.format(Locale.ENGLISH, "RM%.2f", order.getTax()));
            totalDetailTextView.setText(String.format(Locale.ENGLISH, "RM%.2f", order.getTotal()));
            paidDetailTextView.setText(String.format(Locale.ENGLISH, "RM%.2f", order.getToPay()));
            changeDetailTextView.setText(String.format(Locale.ENGLISH, "RM%.2f", order.getChange()));
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.homeTextView) {
            Intent intent = new Intent(OrderUpdate.this, MainActivity.class);
            startActivity(intent);
            return;
        }
    }
}