package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.terrance.classactivity.it212nassignment1chengyongtat20211013.Member;

public class MemberDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "member.db";        // Likely not to change FOREVER
    private static final int DB_VERSION = 1;                   // Likely to CHANGE WHENEVER APP IS UPDATED
    private static final String MEMBER_TABLE = "members";


    public Member[] getAllMembers() {
        Member[] members = null;
        // SELECT statement doesn't involve writing to database... so request only for a READABLE database
        SQLiteDatabase db = getReadableDatabase();
        // Prepare column list to retrieve from database
        String[] columns = {"id", "fullname", "phoneNumber", "gender"};   // (0-integer, 1-string, 2-string, 3-integer)
        // Specify sorting order
        String orderBy = "`fullname` ASC";
        // Get hold of cursor to iterate through records
        Cursor cursor = db.query(MEMBER_TABLE, columns, null, null, null, null, orderBy);
        // Check if there are records returned
        if (cursor.moveToFirst()) {
            // Initialize array to fit all returned records
            members = new Member[cursor.getCount()];
            // Loop through all records and store each record into array
            int arrayIndex = 0;
            do {
                members[arrayIndex] = new Member(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3) == 1
                );
                arrayIndex++;
            } while (cursor.moveToNext());
        }

        // Close cursor
        cursor.close();
        // Close database
        db.close();

        return members;
    }

    public MemberDB(
            @Nullable Context context) {
        super(context, MemberDB.DB_NAME, null, MemberDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS `members` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `fullname` TEXT, " +
                "`gender` INTEGER DEFAULT 1, `phoneNumber` TEXT )";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    public boolean addNewMember(Member member) {
        boolean status = false;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("fullname", member.getFullname());
        cv.put("gender", member.isMale() ? 1:0);   // Insert 1 if gender is male, 0 otherwise
        cv.put("phoneNumber", member.getPhoneNumber());

        status = db.insert(MEMBER_TABLE, "fullname", cv) != -1;

        // At the end of database operations, REMEMBER TO CLOSE THE DATABASE!!!
        db.close();
        return status;
    }

    public boolean updateMember (Member member) {
        boolean status = false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullname", member.getFullname());
        cv.put("gender", member.isMale() ? 1:0);   // Insert 1 if gender is male, 0 otherwise
        cv.put("phoneNumber", member.getPhoneNumber());
        String whereClause = "id=?";
        String[] whereArguments = { String.valueOf(member.getId())};
        status = db.update(MEMBER_TABLE, cv, whereClause, whereArguments) > 0;
        db.close();
        return status;
    }

    public Member getMemberById(int id){
        Member member = null;

        // Prepare column list to retrieve from database
        String[] columns = {"id", "fullname", "phoneNumber", "gender"};
        // Specify sorting order
        String whereClause = "id = ?";
        String[] whereArguments = {String.valueOf(id)};
        // SELECT statement doesn't involve writing to database... so request only for a READABLE database
        SQLiteDatabase db = getReadableDatabase();
        // Get hold of cursor to iterate through records
        Cursor cursor = db.query(MEMBER_TABLE, columns, whereClause, whereArguments, null, null, null);
        // Check if there are records returned
        if (cursor.moveToFirst() && cursor.getCount()==1) {
            // Initialize array to fit all returned records
            member = new Member(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3) == 1
            );
        }

        // Close cursor
        cursor.close();
        // Close database
        db.close();
        return member;
    }
}
