package com.ismos_salt_erp.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class GetContact {
    @SuppressLint("Range")
    public List<NumberUtil> getAllContacts(ArrayList<NumberUtil> numberUtils, FragmentActivity context) {
        /* at first add this permission in manifest  <uses-permission android:name="android.permission.READ_CONTACTS" /> */
        ContentResolver cr =context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                @SuppressLint("Range") String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        String phoneName = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                        numberUtils.add(new NumberUtil(phoneName, phoneNo));

                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return numberUtils;
    }

}
