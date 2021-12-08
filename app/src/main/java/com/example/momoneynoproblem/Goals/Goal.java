package com.example.momoneynoproblem.Goals;

public class Goal {
    private String name;
    private String monthlyLimit;
    private String date;

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    private String uID;

    public Goal() {

    }

    public Goal(String name, String monthlyLimit, String date, String uID) {
        this.name = name;
        this.monthlyLimit = monthlyLimit;
        this.date = date;
        this.uID = uID;
    }


    public Goal(String name, double monthlyLimit, String date) {}

    public String getName() {
        return name;
    }

    public String getMonthlyLimit() {
        return monthlyLimit;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return this.name + " " + monthlyLimit + " " + date;
    }

}
