package com.example.momoneynoproblem.DescribeBudgetAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.momoneynoproblem.R;

import org.w3c.dom.Text;

public class AnalysisPage extends AppCompatActivity {
    SeekBar seekbar;
    SeekBar seekbarRestaurant;
    SeekBar seekbarVacation;
    TextView textView;
    TextView textViewRestaurant;
    TextView textViewVacation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_page);
        Symptom symptom = new Symptom();
        double current_budget = 5000;
        double budget = createNewBudget(current_budget);
        // SeekBar Chhange
//        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbarRestaurant = (SeekBar) findViewById(R.id.seekBarRestaurant);
        seekbarVacation = (SeekBar) findViewById(R.id.seekBarVacation);
        textView = (TextView) findViewById(R.id.newBudget) ;
        textViewRestaurant = (TextView) findViewById(R.id.restauranBudget);
        textViewVacation = (TextView) findViewById(R.id.vacationBudget);
//        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                textView.setText(String.valueOf(progress));
//                textView.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        // Convert text to integer. Do you already use editText.setInputType(InputType.TYPE_CLASS_NUMBER), don't you?
//                        Integer enteredProgress = Integer.valueOf(s.toString());
//                        seekBar.setProgress(enteredProgress);
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {}
//                });
//
//
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
        seekbarRestaurant.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textViewRestaurant.setText(String.valueOf(progress));
                textViewRestaurant.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Convert text to integer. Do you already use editText.setInputType(InputType.TYPE_CLASS_NUMBER), don't you?
                        Integer enteredProgress = Integer.valueOf(s.toString());
                        seekbarRestaurant.setProgress(enteredProgress);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbarVacation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textViewVacation.setText(String.valueOf(progress));
                textViewVacation.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Convert text to integer. Do you already use editText.setInputType(InputType.TYPE_CLASS_NUMBER), don't you?
                        Integer enteredProgress = Integer.valueOf(s.toString());
                        seekbarVacation.setProgress(enteredProgress);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private double createNewBudget(double currentBudget) {
        return 5.0;
    }
}