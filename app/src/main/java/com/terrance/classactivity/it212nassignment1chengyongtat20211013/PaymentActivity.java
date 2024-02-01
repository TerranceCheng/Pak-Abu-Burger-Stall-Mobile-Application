package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton,
                    sevenButton, eightButton, nineButton, zeroButton, pointButton, clearButton, calculateButton;
    private Spinner paymentMethodSpinner;
    private TextView dateTimeLabel3, homeTextView, netPriceTextView, discountTextView, totalTextView, toPayTextView2,
                        changeTextView, payTextView;
    private SimpleDateFormat dateTimeFormat;
    private final String[] method = {
            "Cash",  "e-Wallet"
    };

    private String currentDate;
    private int i = 0;
    private String toPayText = "";
    private long datetimeLabel = System.currentTimeMillis();
    private double netPrice;
    private OrderDB orderDB = null;

    public double passTotal() {
        Bundle bundle = getIntent().getExtras();
        String total = bundle.getString("total");
        double total2 = Double.parseDouble(total);

        return total2;
    }

    public String passDis() {
        Bundle bundle = getIntent().getExtras();
        String discountOn = bundle.getString("discountOn");

        return discountOn;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        dateTime();

        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(
                PaymentActivity.this,
                android.R.layout.simple_list_item_1,
                method
        );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner);
        paymentMethodSpinner.setAdapter(paymentAdapter);
        paymentMethodSpinner.setSelection(0, false);

        calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(PaymentActivity.this);

        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(PaymentActivity.this);

        pointButton = findViewById(R.id.pointButton);
        pointButton.setOnClickListener(PaymentActivity.this);

        zeroButton = findViewById(R.id.zeroButton);
        zeroButton.setOnClickListener(PaymentActivity.this);

        nineButton = findViewById(R.id.nineButton);
        nineButton.setOnClickListener(PaymentActivity.this);

        eightButton = findViewById(R.id.eightButton);
        eightButton.setOnClickListener(PaymentActivity.this);

        sevenButton = findViewById(R.id.sevenButton);
        sevenButton.setOnClickListener(PaymentActivity.this);

        sixButton = findViewById(R.id.sixButton);
        sixButton.setOnClickListener(PaymentActivity.this);

        fiveButton = findViewById(R.id.fiveButton);
        fiveButton.setOnClickListener(PaymentActivity.this);

        fourButton = findViewById(R.id.fourButton);
        fourButton.setOnClickListener(PaymentActivity.this);

        threeButton = findViewById(R.id.threeButton);
        threeButton.setOnClickListener(PaymentActivity.this);

        twoButton = findViewById(R.id.twoButton);
        twoButton.setOnClickListener(PaymentActivity.this);

        oneButton = findViewById(R.id.oneButton);
        oneButton.setOnClickListener(PaymentActivity.this);

        homeTextView = findViewById(R.id.homeTextView);
        homeTextView.setOnClickListener(PaymentActivity.this);

        payTextView = findViewById(R.id.payTextView);
        payTextView.setOnClickListener(PaymentActivity.this);

        toPayTextView2 = findViewById(R.id.toPayTextView2);
        netPriceTextView = findViewById(R.id.netPriceTextView2);
        discountTextView = findViewById(R.id.discountTextView2);
        totalTextView = findViewById(R.id.totalPriceTextView2);
        changeTextView = findViewById(R.id.changeTextView2);
        dateTimeLabel3 = findViewById(R.id.dateTimeLabel);

        orderDB = new OrderDB(PaymentActivity.this);

        double total = passTotal();
        String total2 = String.format(Locale.ENGLISH, "%.2f", total);
        String dis = passDis();

        if(dis.equals("true")) {
            netPriceWithDiscount();
            discountOn();
        }
        else if(dis.equals("false")) {
            netPriceNoDiscount();
            discountOff();
        }
        totalTextView.setText("RM " + total2);
    }

    public double netPriceWithDiscount() {
        double total = passTotal();

        netPrice = total / 95 * 100;
        String netPrice2 = String.format(Locale.ENGLISH, "%.2f", netPrice);
        netPriceTextView.setText("RM " + netPrice2);

        return netPrice;
    }

    public double netPriceNoDiscount() {
        double total = passTotal();

        netPrice = total;
        String netPrice2 = String.format(Locale.ENGLISH, "%.2f", netPrice);
        netPriceTextView.setText("RM " + netPrice2);

        return netPrice;
    }

    public double discountOn() {
        double total = passTotal();

        double discount = total / 95 * 5;
        String discount2 = String.format(Locale.ENGLISH, "%.2f", discount);
        discountTextView.setText("RM " + discount2);

        return discount;
    }

    public double discountOff() {
        double discount = 0.0;
        discountTextView.setText("-");

        return discount;
    }

    public void dateTime() {
        TextView dateTimeLabel3 = findViewById(R.id.dateTimeLabel);
        dateTimeFormat = new SimpleDateFormat("EEE, dd-MM-yyyy HH:mm:ss" );
        currentDate = dateTimeFormat.format(datetimeLabel);
        dateTimeLabel3.setText(currentDate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        double total = passTotal();

        if(view.getId() == R.id.homeTextView) {
            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
            startActivity(intent);
            return;
        }
        else if(view.getId() == R.id.payTextView) {
            Bundle bundle = getIntent().getExtras();
            String tax = bundle.getString("tax");
            String c_patty = bundle.getString("c_patty");
            String c_s_patty = bundle.getString("c_s_patty");
            String l_patty = bundle.getString("l_patty");
            String l_s_patty = bundle.getString("l_s_patty");

            int i = paymentMethodSpinner.getSelectedItemPosition();
            double toPayAmount = Double.parseDouble(toPayText);
            double d_tax = Double.parseDouble(tax);
            double change = toPayAmount - total;
            double discount, netPrice;
            int i_c_patty = Integer.parseInt(c_patty);
            int i_c_s_patty = Integer.parseInt(c_s_patty);
            int i_l_patty = Integer.parseInt(l_patty);
            int i_l_s_patty = Integer.parseInt(l_s_patty);

            Long dateTime = datetimeLabel;
            String dis = passDis();
            String payMethod = (String) paymentMethodSpinner.getSelectedItem();

            if(dis.equals("true")) {
                netPrice = netPriceWithDiscount();
                discount = discountOn();

                Order order = new Order(
                        dateTime,
                        i_c_patty,
                        i_c_s_patty,
                        i_l_patty,
                        i_l_s_patty,
                        netPrice,
                        discount,
                        total,
                        d_tax,
                        toPayAmount,
                        change,
                        payMethod
                );

                if(orderDB.addNewOrder(order)){
                    Toast.makeText(PaymentActivity.this, "Record added with discount", Toast.LENGTH_SHORT).show();
                }
            }
            else if(dis.equals("false")) {
                netPrice = netPriceNoDiscount();
                discount = discountOff();

                Order order = new Order(
                        dateTime,
                        i_c_patty,
                        i_c_s_patty,
                        i_l_patty,
                        i_l_s_patty,
                        netPrice,
                        discount,
                        total,
                        d_tax,
                        toPayAmount,
                        change,
                        payMethod
                );

                if(orderDB.addNewOrder(order)){
                    Toast.makeText(PaymentActivity.this, "Record added without discount", Toast.LENGTH_SHORT).show();
                }
            }

            if(i == 0) {
                Intent intent = new Intent(PaymentActivity.this, CashThankYouActivity.class);
                startActivity(intent);
                return;
            }
            else if(i == 1) {
                Intent intent = new Intent(PaymentActivity.this, ewalletPaymentActivity.class);
                startActivity(intent);
                return;
            }
        }
        else if(view.getId() == R.id.oneButton) {
            toPayText = toPayText + "1";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.twoButton) {
            toPayText = toPayText + "2";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.threeButton) {
            toPayText = toPayText + "3";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.fourButton) {
            toPayText = toPayText + "4";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.fiveButton) {
            toPayText = toPayText + "5";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.sixButton) {
            toPayText = toPayText + "6";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.sevenButton) {
            toPayText = toPayText + "7";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.eightButton) {
            toPayText = toPayText + "8";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.nineButton) {
            toPayText = toPayText + "9";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.zeroButton) {
            toPayText = toPayText + "0";
            calculateButton.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.pointButton) {
            if (i==0){
                toPayText = toPayText + ".";
                calculateButton.setVisibility(View.INVISIBLE);
                i++;
            }
            else{
                pointButton.setEnabled(false);
                i--;
            }
        }
        else if(view.getId() == R.id.clearButton) {
            toPayText = "";
            pointButton.setEnabled(true);
            calculateButton.setVisibility(View.INVISIBLE);
            payTextView.setVisibility(View.GONE);
            changeTextView.setText("");
        }
        else if(view.getId() == R.id.calculateButton) {
            double toPay = Double.parseDouble(toPayText);
            int i = paymentMethodSpinner.getSelectedItemPosition();

            if(i == 0) {
                if(toPay < total) {
                    Toast.makeText(PaymentActivity.this, "Please enter a bigger amount.", Toast.LENGTH_LONG).show();
                }
                else {
                    double change = toPay - total;
                    String change2 = String.format(Locale.ENGLISH, "%.2f", change);
                    changeTextView.setText("RM " + change2);
                    changeTextView.requestFocus();
                    payTextView.setVisibility(View.VISIBLE);
                }
            }
            else {
                if(toPay != total) {
                    Toast.makeText(PaymentActivity.this, "Please enter the same amount as total.", Toast.LENGTH_LONG).show();
                }
                else {
                    changeTextView.setText("-");
                    changeTextView.requestFocus();
                    payTextView.setVisibility(View.VISIBLE);
                }
            }
        }
        toPayTextView2.setText("RM " + toPayText);

    }
}