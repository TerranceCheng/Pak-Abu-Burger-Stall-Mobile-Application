package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Order {
    public static final int NEW_RECORD = 0;
    private double netPrice, discount, total, tax, toPay, change;
    private String paymentMethod;
    private Long dateTime;
    private int id, c_patty, c_s_patty, l_patty, l_s_patty;

    public Order(Long dateTime, int c_patty, int c_s_patty, int l_patty, int l_s_patty, double netPrice, double discount, double total, double tax, double toPay, double change, String paymentMethod) {
        this(Order.NEW_RECORD, dateTime, c_patty, c_s_patty, l_patty, l_s_patty, netPrice, discount, total, tax, toPay, change, paymentMethod);
    }
    public Order(int id, Long dateTime, int c_patty, int c_s_patty, int l_patty, int l_s_patty, double netPrice, double discount, double total, double tax, double toPay, double change, String paymentMethod) {
        setId(id);
        setDateTime(dateTime);
        setC_patty(c_patty);
        setC_s_patty(c_s_patty);
        setL_patty(l_patty);
        setL_s_patty(l_s_patty);
        setNetPrice(netPrice);
        setDiscount(discount);
        setTotal(total);
        setTax(tax);
        setToPay(toPay);
        setChange(change);
        setPaymentMethod(paymentMethod);
    }

    public int getId() {
        return id;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public int getC_patty() {
        return c_patty;
    }

    public int getC_s_patty() {
        return c_s_patty;
    }

    public int getL_patty() {
        return l_patty;
    }

    public int getL_s_patty() {
        return l_s_patty;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }

    public double getTax() {
        return tax;
    }

    public double getToPay() {
        return toPay;
    }

    public double getChange() {
        return change;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public void setC_patty(int c_patty) {
        this.c_patty = c_patty;
    }

    public void setC_s_patty(int c_s_patty) {
        this.c_s_patty = c_s_patty;
    }

    public void setL_patty(int l_patty) {
        this.l_patty = l_patty;
    }

    public void setL_s_patty(int l_s_patty) {
        this.l_s_patty = l_s_patty;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setToPay(double toPay) {
        this.toPay = toPay;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        return String.format(Locale.ENGLISH, "Order: %02d\nDate:  %s\nTotal:  RM %.2f (%s)", getId(), simpleDateFormat.format(getDateTime()), getTotal(), getPaymentMethod());
    }
}

