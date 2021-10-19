package com.example.momoneynoproblem.PDF;

public class Dataobj {
    public long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getFuelQty() {
        return fuelQty;
    }

    public void setFuelQty(double fuelQty) {
        this.fuelQty = fuelQty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    long invoiceNo;
    String Name;
    long date;
    String fuelType;
    double fuelQty;
    double amount;

    public Dataobj() {
        this.invoiceNo = 0;
        Name = "";
        this.date = 0;
        this.fuelType = "";
        this.fuelQty = 0;
        this.amount = 0;
    }
    public Dataobj(long invoiceNo, String name, long date, String fuelType, double fuelQty, double amount) {
        this.invoiceNo = invoiceNo;
        Name = name;
        this.date = date;
        this.fuelType = fuelType;
        this.fuelQty = fuelQty;
        this.amount = amount;
    }



}
