package com.example.momoneynoproblem.DescribeBudgetAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.momoneynoproblem.R;

import org.w3c.dom.Text;

public class AnalysisPage extends AppCompatActivity {
    SeekBar seekbar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_page);

        // SeekBar Chhange
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.newBudget) ;
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}