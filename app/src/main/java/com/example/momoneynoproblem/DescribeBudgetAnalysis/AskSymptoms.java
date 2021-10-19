package com.example.momoneynoproblem.DescribeBudgetAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.momoneynoproblem.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AskSymptoms extends AppCompatActivity {
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_symptoms);
        // Button
        Button enterSymptomButton = findViewById(R.id.EnterButton);
        enterSymptomButton.setOnClickListener(view -> {
            startActivity(new Intent(this,AnalysisPage.class));
        });
        // "How Critical" Spinner Selections
        Spinner howCriticalOptions = (Spinner) findViewById(R.id.critical_emergency_level_spinner);
        final List<String> list_options_emergency_level = new ArrayList<String>();
        list_options_emergency_level.add("Medium");
        list_options_emergency_level.add("High");
        list_options_emergency_level.add("Critical");
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_options_emergency_level);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        howCriticalOptions.setAdapter(adp1);

        howCriticalOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String emergLevelSelection = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + emergLevelSelection,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        // Date Input
        DatePicker mdate = (DatePicker) findViewById(R.id.DateInput);
        // Radio Button Select
        // Is the button now checked?
        RadioGroup yesNoGroup;

        yesNoGroup = (RadioGroup) findViewById(R.id.yesNoGroup);
        Button btnDisplay = (Button) findViewById(R.id.EnterButton);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = yesNoGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);



            }

        });



    }
//    public String onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.radio_yes:
//                if (checked)
//                    // Pirates are the best
//                    return "Yes";
//            case R.id.radio_no:
//                if (checked)
//                    return "No";
//                break;
//        }
//        return "None";
//    }

}