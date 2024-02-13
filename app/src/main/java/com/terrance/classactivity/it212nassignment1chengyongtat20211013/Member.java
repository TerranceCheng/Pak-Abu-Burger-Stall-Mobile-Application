// This page is used to display the member list

package com.terrance.classactivity.it212nassignment1chengyongtat20211013;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Member {
    public static final int NEW_RECORD = 0;
    private String fullname, phoneNumber;
    private int id;
    private boolean isMale;

    public Member(String fullname, String phoneNumber, boolean isMale) {
        this(Member.NEW_RECORD, fullname, phoneNumber, isMale);
    }

    public Member(int id, String fullname, String phoneNumber, boolean isMale) {
        setId(id);
        setFullname(fullname);
        setPhoneNumber(phoneNumber);
        setMale(isMale);
    }

    public String getFullname() {
        return fullname;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setId(int id) {
        this.id = Math.max(0, id);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "\nFull name: %s %s\nPhone number:  %s", isMale() ? "Mr" : "Mrs", getFullname(), getPhoneNumber());
    }
}