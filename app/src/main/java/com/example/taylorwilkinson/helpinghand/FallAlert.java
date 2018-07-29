package com.example.taylorwilkinson.helpinghand;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FallAlert extends AppCompatActivity implements View.OnClickListener {

    Button fallHelpButton, fallCancelButton;

    public static final String DEFAULT = "not available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_alert);

        /* BUTTONS */
        /* Call For Help button */
        fallHelpButton = findViewById(R.id.fallHelpButton);
        fallHelpButton.setOnClickListener(this);
        /* False Alarm button */
        fallCancelButton = findViewById(R.id.fallCancelButton);
        fallCancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //switch utilized to determine call or cancel choice from user
        switch(v.getId()) {
            case R.id.fallHelpButton:
                /* Code to launch the user into calling or texting a contact */
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
                    emergencyText.putExtra("sms_body", "Test text message here!!!");
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

            case R.id.fallCancelButton:
                /* Code to bring a user back to the home page and notify them of cancellation*/
                Toast.makeText(this,"False Alarm! No contact has been made.", Toast.LENGTH_LONG).show();
                Intent backHome = new Intent(FallAlert.this, MainActivity.class);
                startActivity(backHome);
                break;
        }

    }
}
