package com.example.momoneynoproblem.Transaction;

import android.app.Application;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Model extends Application {
    public ArrayList<String> garrList = new ArrayList<>();
    public ArrayAdapter<String> garrAdp;

    public String transID;

    public Model() {
        this.transID = "t1" ;
        this.transaction_type = "t1";
        this.transaction_source_type = "t1";
        this.amount = "t1";
        this.date = "t1";
        this.storeName = "t1";
    }

    public Model(String transID, String transaction_type, String transaction_source_type, String amount, String date, String storeName) {
//        this.garrList = garrList;
//        this.garrAdp = garrAdp;
        this.transID = transID;
        this.transaction_type = transaction_type;
        this.transaction_source_type = transaction_source_type;
        this.amount = amount;
        this.date = date;
        this.storeName = storeName;
    }

    public String transaction_type;
    public String transaction_source_type;
    public String amount;
    public String date;
    public String storeName;



    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;}
    public String getTransaction_type() {return transaction_type; }


    public void setTransID(String transID) {this.transID = transID; }
    public String getTransID() {return transID; }


    public void setTransaction_source_type(String transaction_source_type) {
        this.transaction_source_type = transaction_source_type; }
    public String getTransaction_source_type() {
        return transaction_source_type;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getAmount() {
        return amount;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getStoreName() {
        return storeName;
    }

}
