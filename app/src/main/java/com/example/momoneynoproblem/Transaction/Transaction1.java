package com.example.momoneynoproblem.Transaction;
import java.io.Serializable;

public class Transaction1 implements Serializable {

    public String transaction_type;
    public String transaction_source_type;
    public String amount;
    public String date;
    public String storeName;
    public String transID;
    public String accountId;


    public Transaction1() {

    }
    public Transaction1(String amount,String transaction_type, String transaction_source_type,
                        String transID, String date, String storeName, String accountId) {
        this.transaction_type = transaction_type;
        this.transaction_source_type = transaction_source_type;
        this.amount = amount;
        this.date = date;
        this.storeName = storeName;
        this.transID = transID;
        this.accountId = accountId;

    }


    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
    public void setTransID(String transID) {

        this.transID = transID;
    }

    public void setAccountId(String accountId) {

        this.accountId = accountId;
    }

    public void setTransaction_source_type(String transaction_source_type) {
        this.transaction_source_type = transaction_source_type;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


    public String getTransaction_type() { return transaction_type; }

    public String getTransaction_source_type() {
        return transaction_source_type;
    }
    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getStoreName() {return storeName; }
    public String getTransID() {
        return transID;
    }
    public String getAccountId() {
        return accountId;
    }

    // arrange the data inside the list view
    public String toString() {
        return "Transaction1{" +
                "transID='" + transID + '\'' +
                ", accountId='" + accountId + '\'' +  ", transaction_type='" + transaction_type + '\'' +
                ", date='" + date +  ", storeName='" + storeName +
                ", transaction_source_type='" + transaction_source_type + ", amount='" + amount +
                '}';
    }
}