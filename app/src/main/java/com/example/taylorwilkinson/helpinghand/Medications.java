package com.example.taylorwilkinson.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Medications extends AppCompatActivity implements View.OnClickListener {

    Button backButton, newMedButton;

    TextView amMedList, pmMedList;

    MedicationDatabase medDB;

    String name, amount, timeOfDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        /* EditText */
        amMedList = (TextView)findViewById(R.id.amMedList);
        pmMedList = (TextView)findViewById(R.id.pmMedList);

        medDB = new MedicationDatabase(this);

        /* BUTTONS */
        /* Back Button */
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        /* Add New Medication button */
        newMedButton = findViewById(R.id.newMedButton);
        newMedButton.setOnClickListener(this);

        //String currentMedData = medDB.getData();
        //medList.append(currentMedData);

        String morningResults = medDB.getSelectedData("AM");
        String eveningResults = medDB.getSelectedData("PM");

        amMedList.append(morningResults);
        pmMedList.append(eveningResults);
    }

    public void viewResults (View view) {
        String currentMedData = medDB.getData();
        //medList.append(currentMedData);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backButton:
                //Back button is clicked. Send user back to MainActivity
                Intent backHome = new Intent(Medications.this, MainActivity.class);
                startActivity(backHome);
                break;

            //more cases
            case R.id.newMedButton:
                Intent NewMedication = new Intent(Medications.this, AddNewMedication.class);
                startActivity(NewMedication);
                break;
        }
    }
}
