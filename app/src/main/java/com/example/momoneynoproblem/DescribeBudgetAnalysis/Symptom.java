package com.example.momoneynoproblem.DescribeBudgetAnalysis;

import android.widget.DatePicker;

public class Symptom {

    public String radioButtonSelection;
//    public DatePicker mdate;
    public String emergLevelSelection;

    public Symptom() {
    }

    public Symptom(String radioButtonSelection, String emergLevelSelection) {
        this.radioButtonSelection = radioButtonSelection;
//        this.mdate = mdate;
        this.emergLevelSelection = emergLevelSelection;
    }
}
