package com.example.taylorwilkinson.helpinghand;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

import static java.net.Proxy.Type.HTTP;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    /* variables */
    RecyclerView myRecycler;
    RecyclerView.Adapter adapter;

    private SensorManager mySensorManager;
    Sensor myAccelerometer;
    float[] accel_vals;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    TextView dateTextView;

    Button emergencyHelpButton, todaysMedicationButton, addAppointmentReminderButton, locationButton, contactsButton;

    public static final String DEFAULT = "not available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set the dateTextView text as the device's current date */
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText("Today's Date: " + currentDateTimeString);

        /* HOME BUTTONS */

        //Fall Alert page
        //Link _____.xml to Java Code
        emergencyHelpButton = findViewById(R.id.emergencyHelpButton);
        emergencyHelpButton.setOnClickListener(this);

        //Today's Medication page
        //Link medication_reminderer.xml to Java Code
        todaysMedicationButton = findViewById(R.id.todaysMedicationButton);
        todaysMedicationButton.setOnClickListener(this);

        //Appointment Calendar button
        //Link _____.xml to Java Code
        addAppointmentReminderButton = findViewById(R.id.addAppointmentReminderButton);
        addAppointmentReminderButton.setOnClickListener(this);

        //Medications Settings button
        //Link _____.xml to Java Code
        locationButton = findViewById(R.id.locationButton);
        locationButton.setOnClickListener(this);

        //Contact Settings button
        //Link _____.xml to Java Code
        contactsButton = findViewById(R.id.contactsButton);
        contactsButton.setOnClickListener(this);

        /* SENSORS */
        //Create SensorManager object
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Get reference to the accelerometer
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //Calibrating accelerometer values
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Listen for an accelerometer
        mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //When paused, unregister mySensorManager in order to stop updates and save battery power
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onClick(View v) {
        //switch utilized to control user going to other pages
        switch (v.getId()) {
            case R.id.emergencyHelpButton:
                //Emergency Help button is clicked. Emergency contact should be launched!!!

                SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                String name = sharedPrefs.getString("name", DEFAULT);
                String phone = sharedPrefs.getString("phoneNumber", DEFAULT);
                String method = sharedPrefs.getString("contactMethod", DEFAULT);

                if (name.equals(DEFAULT) || phone.equals(DEFAULT) || method.equals(DEFAULT)) {
                    //Use a Toast message to show user that there is no contact data
                    Toast.makeText(this, "No contact data found", Toast.LENGTH_LONG).show();

                } else if (method.equals("callContact")) {
                    //The user is making an emergency call
                    Intent emergencyCall = new Intent(Intent.ACTION_DIAL);
                    emergencyCall.setData(Uri.parse("tel:" + phone));
                    if (emergencyCall.resolveActivity(getPackageManager()) != null) {
                        startActivity(emergencyCall);
                    }
                } else if (method.equals("textContact")) {
                    //The user is sending an emergency text
                    Intent emergencyText = new Intent(Intent.ACTION_SENDTO);
                    emergencyText.setData(Uri.parse("smsto:"+phone));  // This ensures only SMS apps respond
                    emergencyText.putExtra("sms_body", "YOUR LOVED ONE HAS SENT YOU A DISTRESS MESSAGE! CONTACT THEM IMMEDIATELY!");
                    if (emergencyText.resolveActivity(getPackageManager()) != null) {
                        Toast.makeText(this, "Text Message Sent", Toast.LENGTH_LONG).show();
                        startActivity(emergencyText);
                    }

                } else if (method.equals("callAndTextContact")) {
                    //Will need to send a text as well as launch a call...
                    //1st: send text
                    Intent emergencyText = new Intent(Intent.ACTION_SENDTO);
                    emergencyText.setData(Uri.parse("smsto:"+phone));  // This ensures only SMS apps respond
                    emergencyText.putExtra("sms_body", "YOUR LOVED ONE HAS SENT YOU A DISTRESS MESSAGE! CONTACT THEM IMMEDIATELY!");
                    if (emergencyText.resolveActivity(getPackageManager()) != null) {
                        Toast.makeText(this, "Text Message Sent", Toast.LENGTH_LONG).show();
                        startActivity(emergencyText);
                    }
                    //2nd: initiate call
                    Intent emergencyCall = new Intent(Intent.ACTION_DIAL);
                    emergencyCall.setData(Uri.parse("tel:" + phone));
                    if (emergencyCall.resolveActivity(getPackageManager()) != null) {
                        startActivity(emergencyCall);
                    }
                }
                break;

            case R.id.todaysMedicationButton:
                //Emergency Help button is clicked. Send user to that screen
                Intent Reminder = new Intent(MainActivity.this, Medications.class);
                startActivity(Reminder);
                break;

            case R.id.addAppointmentReminderButton:
                Intent AddAppointment = new Intent(MainActivity.this, AddAppointment.class);
                startActivity(AddAppointment);
                break;

            case R.id.locationButton:
                //My Location button is clicked. Send user to that screen
                Intent Map = new Intent(MainActivity.this, MapView.class);
                startActivity(Map);
                break;

            case R.id.contactsButton:
                Intent Contacts = new Intent(MainActivity.this, Contacts.class);
                startActivity(Contacts);
                break;
        }

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //Detect sensor type
        int type = event.sensor.getType();

        //Handle changes detected by accelerometer
        if(type==Sensor.TYPE_ACCELEROMETER){
            //the accelerometer is active and the Start Detection button has been pressed, so execute the following:
            accel_vals = event.values;

            //Motion detection:
            float x = accel_vals[0];
            float y = accel_vals[1];
            float z = accel_vals[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            //If Acceleration is above a certain value, the device is moving.
            //Change the statusLabel in this case.
            if(mAccel > 8.0 || mAccel < -8.0) {
                //Device in quick motion
                String accelText=Float.toString(mAccel);

                // TEST: Update dateTextView to show the accelerometer value
                //dateTextView.setText(accelText);

                //Launch FallAlert
                Intent fallAlert = new Intent(MainActivity.this, FallAlert.class);
                startActivity(fallAlert);
            }
            else {
                //Device stationary
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
