package com.terrance.classactivity.it212nassignment1chengyongtat20211013;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

public class OrderDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "order.db";        // Likely not to change FOREVER
    private static final int DB_VERSION = 1;                   // Likely to CHANGE WHENEVER APP IS UPDATED
    private static final String ORDER_TABLE = "orders";

    public OrderDB(@Nullable Context context) {
        super(context, OrderDB.DB_NAME, null, OrderDB.DB_VERSION);
    }

    public Order[] getAllOrders() {
        Order[] orders = null;
        // SELECT statement doesn't involve writing to database... so request only for a READABLE database
        SQLiteDatabase db = getReadableDatabase();
        // Prepare column list to retrieve from database
        String[] columns = {"id", "dateTime", "c_patty", "c_s_patty", "l_patty", "l_s_patty", "netPrice", "discount", "total", "tax", "toPay", "change", "paymentMethod"};
        // Specify sorting order
        String orderBy = "`id`, `dateTime` ASC";
        // Get hold of cursor to iterate through records
        Cursor cursor = db.query(ORDER_TABLE, columns, null, null, null, null, orderBy);
        // Check if there are records returned
        if (cursor.moveToFirst()) {
            // Initialize array to fit all returned records
            orders = new Order[cursor.getCount()];
            // Loop through all records and store each record into array
            int arrayIndex = 0;
            do {
                orders[arrayIndex] = new Order(
                        cursor.getInt(0),
                        cursor.getLong(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getDouble(6),
                        cursor.getDouble(7),
                        cursor.getDouble(8),
                        cursor.getDouble(9),
                        cursor.getDouble(10),
                        cursor.getDouble(11),
                        cursor.getString(12)
                );
                arrayIndex++;
            } while (cursor.moveToNext());
        }

        // Close cursor
        cursor.close();
        // Close database
        db.close();

        return orders;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS `orders` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `dateTime` LONG, " +
                "`c_patty` INTEGER DEFAULT 0, `c_s_patty` INTEGER DEFAULT 0, `l_patty` INTEGER DEFAULT 0, `l_s_patty` INTEGER DEFAULT 0," +
                "`netPrice` DOUBLE DEFAULT 0.0, `discount` DOUBLE DEFAULT 0.0, `total` DOUBLE DEFAULT 0.0, `tax` DOUBLE DEFAULT 0.0," +
                "`toPay` DOUBLE DEFAULT 0.0, `change` DOUBLE DEFAULT 0.0, `paymentMethod` TEXT )";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean addNewOrder(Order order) {
        // Returns status TRUE if student is successfully added, otherwise false.
        boolean status = false;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("dateTime", order.getDateTime());
        cv.put("c_patty", order.getC_patty());
        cv.put("c_s_patty", order.getC_s_patty());
        cv.put("l_patty", order.getL_patty());
        cv.put("l_s_patty", order.getL_s_patty());
        cv.put("netPrice", order.getNetPrice());
        cv.put("discount", order.getDiscount());
        cv.put("total", order.getTotal());
        cv.put("tax", order.getTax());
        cv.put("toPay", order.getToPay());
        cv.put("change", order.getChange());
        cv.put("paymentMethod", order.getPaymentMethod());

        // Insert content values into table
        // A "nullColumnHack" is required because AT LEAST one column must be inserted with value
        // inert() method returns -1 if it fails to insert
        status = db.insert(ORDER_TABLE, "id", cv) != -1;

        db.close();
        return status;
    }

    public boolean deleteOrder(Order order) {
        return true;
    }

    public Order getOrderById(int id){
        Order orders = null;

        // Prepare column list to retrieve from database
        String[] columns = {"id", "dateTime", "c_patty", "c_s_patty", "l_patty", "l_s_patty", "netPrice", "discount", "total", "tax", "toPay", "change", "paymentMethod"};
        // Specify sorting order
        String whereClause = "id = ?";
        String[] whereArguments = {String.valueOf(id)};
        // SELECT statement doesn't involve writing to database... so request only for a READABLE database
        SQLiteDatabase db = getReadableDatabase();
        // Get hold of cursor to iterate through records
        Cursor cursor = db.query(ORDER_TABLE, columns, whereClause, whereArguments, null, null, null);
        // Check if there are records returned
        if (cursor.moveToFirst()) {
                orders = new Order(
                        cursor.getInt(0),
                        cursor.getLong(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getDouble(6),
                        cursor.getDouble(7),
                        cursor.getDouble(8),
                        cursor.getDouble(9),
                        cursor.getDouble(10),
                        cursor.getDouble(11),
                        cursor.getString(12)
                );
        }

        // Close cursor
        cursor.close();
        // Close database
        db.close();
        return orders;
    }

    public Order[] getOrderByRange(long startDate, long endDate){
        Order[] orders = null;

        // Prepare column list to retrieve from database
        String[] columns = {"id", "dateTime", "c_patty", "c_s_patty", "l_patty", "l_s_patty", "netPrice", "discount", "total", "tax", "toPay", "change", "paymentMethod"};
        // Specify sorting order
        String orderBy = "`id`, `dateTime` ASC";
        String whereClause = "dateTime BETWEEN ? AND ?";
        // 57599000 milliseconds is for adding 16 hours into our timezone +8, and make it 23:59:59
        String[] whereArguments = {String.valueOf(startDate), String.valueOf(endDate + 57599000)};
        // SELECT statement doesn't involve writing to database... so request only for a READABLE database
        SQLiteDatabase db = getReadableDatabase();
        // Get hold of cursor to iterate through records
        Cursor cursor= db.query(ORDER_TABLE, columns, whereClause, whereArguments, null, null, orderBy);
        // Check if there are records returned
        if (cursor.moveToFirst()) {
            // Initialize array to fit all returned records
            orders = new Order[cursor.getCount()];
            // Loop through all records and store each record into array
            int arrayIndex = 0;
            do {
                orders[arrayIndex] = new Order(
                        cursor.getInt(0),
                        cursor.getLong(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getDouble(6),
                        cursor.getDouble(7),
                        cursor.getDouble(8),
                        cursor.getDouble(9),
                        cursor.getDouble(10),
                        cursor.getDouble(11),
                        cursor.getString(12)
                );
                arrayIndex++;
            } while (cursor.moveToNext());
        }

        // Close cursor
        cursor.close();
        // Close database
        db.close();
        return orders;
    }

    public int getOrderCount(long startDate, long endDate) {
        Integer count = null;

        String[] whereArguments = {String.valueOf(startDate), String.valueOf(endDate)};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) AS count FROM orders WHERE dateTime BETWEEN ? AND ?", whereArguments);
        // Check if there are records returned
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        // Close cursor
        cursor.close();
        // Close database
        db.close();
        return count;
    }

    public Double getTotalByRange(long startDate, long endDate){
        Double total = null;

        String[] whereArguments = {String.valueOf(startDate), String.valueOf(endDate)};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(total) AS total FROM orders WHERE dateTime BETWEEN ? AND ?", whereArguments);
        // Check if there are records returned
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }

        // Close cursor
        cursor.close();
        // Close database
        db.close();
        return total;
    }

    public Double getTaxByRange(long startDate, long endDate){
        Double taxes = null;

        String[] whereArguments = {String.valueOf(startDate), String.valueOf(endDate)};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(tax) AS taxes FROM orders WHERE dateTime BETWEEN ? AND ?", whereArguments);
        // Check if there are records returned
        if (cursor.moveToFirst()) {
            taxes = cursor.getDouble(0);
        }

        // Close cursor
        cursor.close();
        // Close database
        db.close();
        return taxes;
    }
}
