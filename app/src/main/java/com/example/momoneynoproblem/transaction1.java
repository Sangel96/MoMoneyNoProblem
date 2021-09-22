package com.example.momoneynoproblem;

import android.widget.CheckBox;
import android.widget.EditText;

public class transaction1 {

    String amount;
    String income;
    String expenses;

    public transaction1(String amount, String income, String expenses){
        this.amount = amount;
        this.income= income;
        this.expenses = expenses;
    }
    public String getAmount(){
        return amount;
    }

    public String getIncome() {
        return income;
    }

    public String getExpenses() {
        return expenses;
    }
}

