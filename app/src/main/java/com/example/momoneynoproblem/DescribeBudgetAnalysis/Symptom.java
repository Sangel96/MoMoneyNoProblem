package com.example.momoneynoproblem.DescribeBudgetAnalysis;

import android.widget.DatePicker;

import java.util.Date;

public class Symptom {

    public String radioButtonSelection;
    public String mdate;
    public String emergLevelSelection;

    public Symptom() {
    }

    public Symptom(String radioButtonSelection, String emergLevelSelection, String mdate) {
        this.radioButtonSelection = radioButtonSelection;
        this.mdate = mdate;
        this.emergLevelSelection = emergLevelSelection;
    }
}
