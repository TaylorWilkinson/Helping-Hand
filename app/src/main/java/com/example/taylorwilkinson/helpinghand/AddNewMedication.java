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

public class AddNewMedication extends AppCompatActivity implements View.OnClickListener {

    EditText medNameEditText, amountEditText;

    Button backToMedButton;

    String timeOfDayTaken;

    MedicationDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_med);

        /* Edit Texts */
        medNameEditText = (EditText)findViewById(R.id.medNameEditText);
        amountEditText = (EditText)findViewById(R.id.amountEditText);

        /* BUTTONS */
        /* Back Button */
        backToMedButton = findViewById(R.id.backToMedButton);
        backToMedButton.setOnClickListener(this);

        db = new MedicationDatabase(this);
    }

    public void onRadioButtonClicked(View v) {
        // Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.amRadioButton:
                if (checked)
                    // Take medication in the morning
                    timeOfDayTaken = "AM";
                break;
            case R.id.pmRadioButton:
                if (checked)
                    // Take medication in the evening
                    timeOfDayTaken = "PM";
                break;
        }
    }

    public void addMedication (View v) {
        //Add the information to the database
        String name = medNameEditText.getText().toString();
        String amount = amountEditText.getText().toString();
        Toast.makeText(this, name + ", " + amount + ", " + timeOfDayTaken, Toast.LENGTH_LONG).show();

        long id = db.insertData(name, amount, timeOfDayTaken);
        if (id < 0) {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
        medNameEditText.setText("");
        amountEditText.setText("");

        Intent BackToMedication = new Intent(AddNewMedication.this, Medications.class);
        startActivity(BackToMedication);
    }

    @Override
    public void onClick(View v) {
        Intent backToMedications = new Intent(AddNewMedication.this, Medications.class);
        startActivity(backToMedications);
    }
}
