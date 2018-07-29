package com.example.taylorwilkinson.helpinghand;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddContact extends AppCompatActivity implements View.OnClickListener{

    EditText nameEditText,phoneEditText;

    RadioButton callContactRadioButton, textContactRadioButton, bothContactRadioButton;

    Button backButton;

    String contactMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        /* Edit Texts */
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        phoneEditText = (EditText)findViewById(R.id.phoneEditText);

        /* Back Button */
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View v) {
        // Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.callContactRadioButton:
                if (checked)
                    // Contact will be called
                    contactMethod = "callContact";
                    break;
            case R.id.textContactRadioButton:
                if (checked)
                    // Contact will be texted
                    contactMethod = "textContact";
                    break;
            case R.id.bothContactRadioButton:
                if (checked)
                    // Contact will be both called and texted
                    contactMethod = "callAndTextContact";
                    break;
        }
    }

    public void submit (View v) {
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("name", nameEditText.getText().toString());
        editor.putString("phoneNumber", phoneEditText.getText().toString());
        editor.putString("contactMethod", contactMethod.toString());
        Toast.makeText(this, "New contact has been saved to Preferences", Toast.LENGTH_LONG).show();
        editor.commit();

        Intent BackToContacts = new Intent(AddContact.this, Contacts.class);
        startActivity(BackToContacts);
    }

    @Override
    public void onClick(View v) {
        Intent backToContacts = new Intent(AddContact.this, Contacts.class);
        startActivity(backToContacts);
    }
}
