package com.example.momoneynoproblem.DescribeBudgetAnalysis;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.momoneynoproblem.Goals.AddGoals;
import com.example.momoneynoproblem.Goals.CreateGoal;
import com.example.momoneynoproblem.Goals.Goal;
import com.example.momoneynoproblem.Login.Login;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.SubAccount.CreateSubAccounts;
import com.example.momoneynoproblem.SubAccount.SubAccount;
import com.example.momoneynoproblem.UserSignUp.User;
import com.example.momoneynoproblem.UserSignUp.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AskSymptoms extends AppCompatActivity {
    String radioButtonSelection;
    DatePicker mdate;
    Date mDateOfficial;
    String mDateOfficialString;
    String emergLevelSelection;
    DatabaseReference dbReference;
    FirebaseDatabase firebaseDatabase;
    ImageView enterButton;
    FirebaseAuth mAuth;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_symptoms);
        // Button

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
                emergLevelSelection = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + emergLevelSelection,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        // Date Input
        mdate = (DatePicker) findViewById(R.id.DateInput);
        mDateOfficialString = getDateFromDatePicker(mdate).toString();
        // Radio Button Select
        // Is the button now checked?
        RadioGroup yesNoGroup;

        yesNoGroup = (RadioGroup) findViewById(R.id.yesNoGroup);
        yesNoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                radioButtonSelection = radioButton.getText().toString();
            }
        });

        enterButton = (ImageView) findViewById(R.id.enterbutton_asksymptoms);
        // inserting values into firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbReference = firebaseDatabase.getReference("Symptoms");
        enterButton.setOnClickListener(view -> {
            submitSypmtom();
            startActivity(new Intent(AskSymptoms.this, AnalysisPage.class));


        });





    }

    private void submitSypmtom() {
        //Create new Goal object
        Symptom newSymptom = new Symptom(radioButtonSelection,emergLevelSelection,mDateOfficialString);

        //insert value into database
        dbReference.push().setValue(newSymptom);
        Toast.makeText(AskSymptoms.this, "Successfully added symptom", Toast.LENGTH_SHORT).show();

    }
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


}
