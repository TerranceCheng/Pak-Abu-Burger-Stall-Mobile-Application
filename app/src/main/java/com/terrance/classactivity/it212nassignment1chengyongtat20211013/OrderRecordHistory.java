package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderRecordHistory extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    OrderDB orderDB = null;
    TextView homeTextView, dateRangeTextView;
    ImageView refreshImage;

    private static final int REQUEST_UPDATE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_record_history);

        orderDB = new OrderDB(OrderRecordHistory.this);
        refreshList();

        homeTextView = findViewById(R.id.homeTextView);
        homeTextView.setOnClickListener(OrderRecordHistory.this);

        refreshImage = findViewById(R.id.refreshImage);
        refreshImage.setOnClickListener(OrderRecordHistory.this);

        dateRangeTextView = findViewById(R.id.dateRangeTextView);

        CalendarConstraints.Builder calendarConstraintBuilder = new CalendarConstraints.Builder();
        calendarConstraintBuilder.setValidator(DateValidatorPointBackward.now());

        MaterialDatePicker.Builder<Pair<Long, Long>> datePicker = MaterialDatePicker.Builder.dateRangePicker();
        datePicker.setTitleText("Please select the start and end date");

        datePicker.setCalendarConstraints(calendarConstraintBuilder.build());
        final MaterialDatePicker materialDatePicker = datePicker.build();

        dateRangeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "Date_Range");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy" );
                Long startDate = selection.first;
                Long endDate = selection.second;
                String selectStart = simpleDateFormat.format(startDate);
                String selectEnd = simpleDateFormat.format(endDate);

                String output = String.format(Locale.ENGLISH, "%s - %s", selectStart, selectEnd);
                dateRangeTextView.setText(output);

                refreshOrderListByDate(startDate, endDate);
                getSummaryByRange(startDate, endDate);
        }
        });
    }

    public void refreshList() {
        ListView orderLists = findViewById(R.id.listView);
        Order[] orders = orderDB.getAllOrders();

        if(orders != null) {
            ArrayAdapter<Order> ordersAdapter = new ArrayAdapter<>(
                    OrderRecordHistory.this,
                    R.layout.my_styles,
                    orders
            );
            orderLists.setOnItemClickListener(OrderRecordHistory.this);
            orderLists.setOnItemLongClickListener(OrderRecordHistory.this);
            orderLists.setAdapter(ordersAdapter);
        }
        else{
            orderLists.setAdapter(null);
        }
        // Remember to close database!!!
        orderDB.close();
    }



    public void refreshOrderListByDate(long startDate, long endDate) {
        ListView orderLists = findViewById(R. id.listView);
        Order[] orders = orderDB.getOrderByRange(startDate, endDate);

        if(orders != null) {
            ArrayAdapter<Order> ordersAdapter = new ArrayAdapter<>(
                    OrderRecordHistory.this,
                    R.layout.my_styles,
                    orders
            );
            orderLists.setOnItemClickListener(OrderRecordHistory.this);
            orderLists.setOnItemLongClickListener(OrderRecordHistory.this);
            orderLists.setAdapter(ordersAdapter);
        }
        else{
            orderLists.setAdapter(null);
        }
        // Remember to close database!!!
        orderDB.close();
    }

    public void getSummaryByRange(long startDate, long endDate) {

        Double total = orderDB.getTotalByRange(startDate, endDate + 57599000);
        Double taxes = orderDB.getTaxByRange(startDate, endDate + 57599000);
        int orderCount = orderDB.getOrderCount(startDate, endDate + 57599000);
        Double netEarn = total - taxes;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderRecordHistory.this);
        alertDialog.setTitle("Order summary");
        String message = String.format(
                Locale.ENGLISH, "Total orders: %d\nTotal sales: RM%.2f\n" +
                        "Total taxes: RM%.2f\nNet Earnings: RM%.2f", orderCount, total, taxes, netEarn );
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(android.R.string.yes, null);
        alertDialog.setNegativeButton(android.R.string.no, null);
        alertDialog.show();

        // Remember to close database!!!
        orderDB.close();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.homeTextView) {
            Intent intent = new Intent(OrderRecordHistory.this, MainActivity.class);
            startActivity(intent);
            return;
        }
        else if(view.getId() == R.id.refreshImage) {
            dateRangeTextView.setText("Select your date range");
            refreshList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Order order = (Order) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(OrderRecordHistory.this, OrderUpdate.class);
        intent.putExtra("ID", order.getId());
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