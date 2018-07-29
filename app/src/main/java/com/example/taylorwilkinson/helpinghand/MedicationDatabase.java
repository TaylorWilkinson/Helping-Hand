package com.example.taylorwilkinson.helpinghand;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by taylorwilkinson on 2018-03-22.
 */

public class MedicationDatabase {

    private SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MedicationDatabase (Context c){
        context = c;
        helper = new MyHelper(context);
    }

    public long insertData (String name, String amount, String timeOfDay) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.AMOUNT, amount);
        contentValues.put(Constants.TIME, timeOfDay);
        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.NAME, Constants.AMOUNT, Constants.TIME};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, null, null, null, null, null);

        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {
            int index = cursor.getInt(0);
            String name = cursor.getString(1);
            String amount = cursor.getString(2);
            String timeOfDay = cursor.getString(3);
            buffer.append(index + " " + name + " " + amount + " " + timeOfDay + "\n");
        }
        return buffer.toString();
    }

    public String getSelectedData(String time) {
        //select items from the database using the type "Time"
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME, Constants.AMOUNT, Constants.TIME};

        String selection = Constants.TIME + "='" +time+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.NAME);
            int index2 = cursor.getColumnIndex(Constants.AMOUNT);
            int index3 = cursor.getColumnIndex(Constants.TIME);
            String name = cursor.getString(index1);
            String amount = cursor.getString(index2);
            String timeOfDay = cursor.getString(index3);
            //Create a string that shows the name and the dose to take
            buffer.append(name + ", " + amount + " doses" + "\n");
        }
        return buffer.toString();
    }



}
