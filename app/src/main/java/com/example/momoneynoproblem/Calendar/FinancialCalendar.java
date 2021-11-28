package com.example.momoneynoproblem.Calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.example.momoneynoproblem.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class FinancialCalendar extends AppCompatActivity {
    private Button datePicker;
    private TextView selectedDateText;
    DatabaseReference databaseReference;
    ArrayList<Double> list = new ArrayList<>();
    private TextView amount;
    String date;
    SimpleDateFormat format;
    Calendar calendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        datePicker = (Button) findViewById(R.id.date_picker);
        selectedDateText = (TextView) findViewById(R.id.selectedDate);
        amount = (TextView) findViewById(R.id.amount);
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC)"));
        calendar.clear();

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);

        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        Long january = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        Long december = calendar.getTimeInMillis();

        //calendar constraints
        CalendarConstraints.Builder constraints = new CalendarConstraints.Builder();
        constraints.setStart(january);
        constraints.setEnd(december);


        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraints.build());
        MaterialDatePicker materialDatePicker = builder.build();


        //MaterialDatePicker
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                calendar.add(Calendar.DATE, 1);
                format = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                date = format.format(calendar.getTime());
                selectedDateText.setText("Selected Date: " + date);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String db_date = ds.child("date").getValue(String.class);
                    amount.setText("amount " + date);
                    if (db_date == selectedDateText.getText()) {
                        amount.setText("Amount: True");
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
