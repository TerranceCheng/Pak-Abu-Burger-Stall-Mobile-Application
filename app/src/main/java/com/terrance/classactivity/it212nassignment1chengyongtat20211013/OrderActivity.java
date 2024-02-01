package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView pattyTextView, homeTextView, calculateTextView, totalPriceTextView, checkOutTextView;
    private Spinner chickenPattySpinner, chickenSpecialPattySpinner, lambPattySpinner, lambSpecialPattySpinner;
    private Switch memberSwitch;
    private SimpleDateFormat dateTimeFormat;

    private final Integer[] num = new Integer[101];
    private final static double chickenPatty = 4.00;
    private final static double  s_chickenPatty = 5.00;
    private final static double lambPatty = 8.80;
    private final static double s_lambPatty = 11.00;
    private long datetimeLabel = System.currentTimeMillis();
    private double total;
    private String currentDate;
    String disOn = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        pattyTextView = findViewById(R.id.pattyTextView);
        //HTML format for bold and underline
        String patty = "<b><u>Patty</u></b>";
        pattyTextView.setText(HtmlCompat.fromHtml(patty, HtmlCompat.FROM_HTML_MODE_LEGACY));

        dateTime();

        for(int i = 0; i < 101; i++){
            num[i] = new Integer(i);
        }

        chickenPattySpinner = findViewById(R.id.chickenPattySpinner);
        ArrayAdapter<Integer> chickenPattyAdapter = new ArrayAdapter<>(
                OrderActivity.this,               // ArrayAdapter created by MainActivity
                android.R.layout.simple_list_item_1,    // Simply means Text View
                num                                    // Data
        );

        chickenSpecialPattySpinner = findViewById(R.id.chickenSpecialPattySpinner);
        ArrayAdapter<Integer> chickenSpecialPattyAdapter = new ArrayAdapter<>(
                OrderActivity.this,
                android.R.layout.simple_list_item_1,
                num
        );

        lambPattySpinner = findViewById(R.id.importedLambPattySpinner);
        ArrayAdapter<Integer> lambPattyAdapter = new ArrayAdapter<>(
                OrderActivity.this,
                android.R.layout.simple_list_item_1,
                num
        );

        lambSpecialPattySpinner = findViewById(R.id.importedSpecialLambPattySpinner);
        ArrayAdapter<Integer> lambSpecialPattyAdapter = new ArrayAdapter<>(
                OrderActivity.this,
                android.R.layout.simple_list_item_1,
                num
        );

        homeTextView = findViewById(R.id.homeTextView);
        homeTextView.setOnClickListener(OrderActivity.this);

        chickenPattySpinner = findViewById(R.id.chickenPattySpinner);
        chickenPattySpinner.setAdapter(chickenPattyAdapter);

        chickenSpecialPattySpinner = findViewById(R.id.chickenSpecialPattySpinner);
        chickenSpecialPattySpinner.setAdapter(chickenSpecialPattyAdapter);

        lambPattySpinner = findViewById(R.id.importedLambPattySpinner);
        lambPattySpinner.setAdapter(lambPattyAdapter);

        lambSpecialPattySpinner = findViewById(R.id.importedSpecialLambPattySpinner);
        lambSpecialPattySpinner.setAdapter(lambSpecialPattyAdapter);

        calculateTextView = findViewById(R.id.updateNetPriceTextView);
        calculateTextView.setOnClickListener(OrderActivity.this);

        checkOutTextView = findViewById(R.id.payTextView);
        checkOutTextView.setOnClickListener(OrderActivity.this);

        totalPriceTextView = findViewById(R.id.netPriceTextView2);
        memberSwitch = findViewById(R.id.memberSwitch);
        memberSwitch.setOnCheckedChangeListener(OrderActivity.this);
    }

    public String dateTime() {
        TextView dateTimeLabel2 = findViewById(R.id.dateTimeLabel2);
        dateTimeFormat = new SimpleDateFormat("EEE, dd-MM-yyyy HH:mm:ss" );
        currentDate = dateTimeFormat.format(datetimeLabel);
        dateTimeLabel2.setText(currentDate);

        return currentDate;
    }

    public String calculateTotal() {
        int numSelected1 = (int) chickenPattySpinner.getSelectedItem();
        int numSelected2 = (int) chickenSpecialPattySpinner.getSelectedItem();
        int numSelected3 = (int) lambPattySpinner.getSelectedItem();
        int numSelected4 = (int) lambSpecialPattySpinner.getSelectedItem();

        int discountControl = 0;

        total = (numSelected1 * chickenPatty) + (numSelected2 * s_chickenPatty)
                + (numSelected3 * lambPatty) + (numSelected4 * s_lambPatty);

        if(total > 20) {
            total = total * 0.95;
            discountControl++;
        }
        else if(memberSwitch.isChecked() && discountControl == 0) {
            total = total * 0.95;
        }

        String total2 = String.format(Locale.ENGLISH, "%.2f", total);
        totalPriceTextView.setText("Total : RM " + total2);

        return total2;
    }

    public double taxes() {
        int numSelected3 = (int) lambPattySpinner.getSelectedItem();
        int numSelected4 = (int) lambSpecialPattySpinner.getSelectedItem();

        double tax = (0.8 * numSelected3) + (1 * numSelected4);

        return tax;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.homeTextView) {
            Intent intent = new Intent(OrderActivity.this, MainActivity.class);
            startActivity(intent);
            return;
        }
        else if(view.getId() == R.id.updateNetPriceTextView) {
            calculateTotal();
        }
        else if(view.getId() == R.id.payTextView) {
            String passTotal = calculateTotal();
            Double total2 = Double.parseDouble(passTotal);
            String tax = String.valueOf(taxes());

            String numSelected1 = String.valueOf(chickenPattySpinner.getSelectedItem());
            String numSelected2 = String.valueOf(chickenSpecialPattySpinner.getSelectedItem());
            String numSelected3 = String.valueOf(lambPattySpinner.getSelectedItem());
            String numSelected4 = String.valueOf(lambSpecialPattySpinner.getSelectedItem());

            Intent passIntent = new Intent(OrderActivity.this, PaymentActivity.class);

            if(memberSwitch.isChecked() || total2 > 20) {
                disOn = "true";
            }
            else {
                disOn = "false";
            }
            passIntent.putExtra("total", passTotal);
            passIntent.putExtra("discountOn", disOn);
            passIntent.putExtra("tax", tax);
            passIntent.putExtra("c_patty", numSelected1);
            passIntent.putExtra("c_s_patty", numSelected2);
            passIntent.putExtra("l_patty", numSelected3);
            passIntent.putExtra("l_s_patty", numSelected4);
            startActivity(passIntent);
            return;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        calculateTotal();
    }
}