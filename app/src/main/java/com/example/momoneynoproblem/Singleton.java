package com.example.momoneynoproblem;

import com.google.firebase.auth.FirebaseAuth;

public class Singleton {
    private String userID = null;
    //Only used within AddTranscation Class and variable should be set to null after each usage to avoid bad data
    private String TransType;
    //Only used within AddTranscation Class and variable should be set to null after each usage to avoid bad data
    private String TransID;

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTransType() {
        return TransType;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }

    // Getter/setter

    private static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser().getUid() != null) {
                instance.setUserID(mAuth.getCurrentUser().getUid());
            }
        }
        return instance;
    }

    public void reset(){
        this.userID = null;
        this.TransID = null;
        this.TransType = null;
        this.instance = null;
    }

    private Singleton() {
        if (instance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }
}
