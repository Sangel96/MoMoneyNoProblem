package com.example.momoneynoproblem.Calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.example.momoneynoproblem.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;
import java.util.TimeZone;

public class FinancialCalendar extends AppCompatActivity{
    private Button datePicker;
    private TextView selectedDateText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        datePicker = (Button) findViewById(R.id.date_picker);
        selectedDateText = (TextView) findViewById(R.id.selectedDate);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC)"));
        calendar.clear();

        long today = MaterialDatePicker.todayInUtcMilliseconds();




        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setSelection(today);
        MaterialDatePicker materialDatePicker = builder.build();

        
        //MaterialDatePicker
        datePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        /*materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                selectedDateText.setText("Selected Date: " + materialDatePicker.getHeaderText());
            }
        });*/
    }

}
