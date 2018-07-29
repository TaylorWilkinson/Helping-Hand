package com.example.taylorwilkinson.helpinghand;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import static java.text.DateFormat.DEFAULT;

public class Contacts extends AppCompatActivity implements View.OnClickListener {

    TextView contactsNameTextView, contactsPhoneTextView, contactsMethodTextView;

    Button backButton, newContactButton;

    public static final String DEFAULT = "not available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsNameTextView = (TextView)findViewById(R.id.contactsNameTextView);
        contactsPhoneTextView = (TextView)findViewById(R.id.contactsPhoneTextView);
        contactsMethodTextView = (TextView)findViewById(R.id.contactsMethodTextView);


        /* BUTTONS */
        /* Back Button */
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        /* Add New Contact Button */
        newContactButton = findViewById(R.id.newContactButton);
        newContactButton.setOnClickListener(this);
    }

    public void retrieve (View v){
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String name = sharedPrefs.getString("name", DEFAULT);
        String phone = sharedPrefs.getString("phoneNumber", DEFAULT);
        String method = sharedPrefs.getString("contactMethod", DEFAULT);

        /* create a unique string for each contact method */
        if (Objects.equals(method, "callContact")) {
            method = "Phone Call";
        } else if (Objects.equals(method, "textContact")) {
            method = "Text Message";
        } else if (Objects.equals(method, "callAndTextContact")) {
            method = "Phone and Text";
        }

        if (name.equals(DEFAULT) || phone.equals(DEFAULT) || method.equals(DEFAULT)) {
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this, "Data retrieve success", Toast.LENGTH_LONG).show();
            contactsNameTextView.setText("Name: " + name);
            contactsPhoneTextView.setText("Phone Number: " + phone);
            contactsMethodTextView.setText("Contact Method: " + method);

        }
    }


    @Override
    public void onClick(View v) {
        //switch utilized to control user going to other pages
        switch(v.getId()) {
            case R.id.backButton:
                //Back button is clicked. Send user back to MainActivity
                Intent backHome = new Intent(Contacts.this, MainActivity.class);
                startActivity(backHome);
                break;

            //more cases
            case R.id.newContactButton:
                Intent NewContact = new Intent(Contacts.this, AddContact.class);
                startActivity(NewContact);
                break;
        }
    }
}
