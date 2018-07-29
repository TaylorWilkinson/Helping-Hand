package com.example.taylorwilkinson.helpinghand;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class AddAppointment extends AppCompatActivity implements View.OnClickListener {

    EditText apptTitleEditText, locationEditText, descEditText;
    DatePicker apptDatePicker;
    TimePicker startTimePicker, endTimePicker;
    String givenTitle, givenDate, givenStart, givenEnd, givenLocation, givenDesc;
    int day, month, year, startHour, startMinute, endHour, endMinute;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        /* Edit Texts */
        apptTitleEditText = (EditText) findViewById(R.id.apptTitleEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        descEditText = (EditText) findViewById(R.id.descEditText);

        /* Date Picker */
        DatePicker apptDatePicker = (DatePicker) findViewById(R.id.apptDatePicker);
        day = apptDatePicker.getDayOfMonth();
        month = apptDatePicker.getMonth() + 1;
        year = apptDatePicker.getYear();

        /* Time Pickers */
        TimePicker startTimePicker = (TimePicker) findViewById(R.id.startTimePicker);
        startHour = startTimePicker.getHour();
        startMinute = startTimePicker.getMinute();

        TimePicker endTimePicker = (TimePicker) findViewById(R.id.endTimePicker);
        endHour = endTimePicker.getHour();
        endMinute = endTimePicker.getMinute();

        /* Back Button */
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
    }

    public void addNewAppointment(View v) {
        givenTitle = apptTitleEditText.getText().toString();
        givenLocation = locationEditText.getText().toString();
        givenDesc = descEditText.getText().toString();


        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, day, startHour, startMinute);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, endHour, endMinute);
        Intent calenderIntent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .setType("vnd.android.cursor.item/event")
                .putExtra(CalendarContract.Events.TITLE, givenTitle)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.DESCRIPTION, givenDesc)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, givenLocation)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(calenderIntent);

        Toast.makeText(this, "New contact has been added to Calendar", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        Intent BackHome = new Intent(AddAppointment.this, MainActivity.class);
        startActivity(BackHome);
    }
}
