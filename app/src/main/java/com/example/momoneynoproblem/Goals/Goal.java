package com.example.momoneynoproblem.Goals;

public class Goal {
    private String name;
    private double monthlyLimit;
    private String date;

    public Goal(String name, double monthlyLimit, String date) {
        this.name = name;
        this.monthlyLimit = monthlyLimit;
        this.date = date;
    }

    public Goal() {}

    public String getName() {
        return name;
    }

    public double getMonthlyLimit() {
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
