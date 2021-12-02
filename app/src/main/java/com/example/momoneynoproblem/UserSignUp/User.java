package com.example.momoneynoproblem.UserSignUp;

public class User {

    public String firstName, lastName, email, ufirebaseID;

    public User() {

    }

    public User(String name, String email, String uID) {
        String[] nameArray = name.split(" ");

        this.firstName = nameArray[0];
        this.lastName = nameArray[1];
        this.email = email;
        this.ufirebaseID = uID;
    }
}
