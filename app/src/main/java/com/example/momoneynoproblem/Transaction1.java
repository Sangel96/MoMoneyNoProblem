package com.example.momoneynoproblem;
import java.io.Serializable;

public class Transaction1 implements Serializable {

    public String transaction_type;
    public String transaction_source_type;
    public String amount;

    public Transaction1() {

    }
    public Transaction1(String amount,String transaction_type, String transaction_source_type) {
        this.transaction_type = transaction_type;
        this.transaction_source_type = transaction_source_type;
        this.amount = amount;

    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public void setTransaction_source_type(String transaction_source_type) {
        this.transaction_source_type = transaction_source_type;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public String getTransaction_source_type() {
        return transaction_source_type;
    }
    public String getAmount() {
        return amount;
    }
}
