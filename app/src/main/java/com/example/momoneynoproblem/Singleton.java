package com.example.momoneynoproblem;

import com.google.firebase.auth.FirebaseAuth;

public class Singleton {
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String userID = null;

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

    private Singleton() {
        if (instance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }
}
