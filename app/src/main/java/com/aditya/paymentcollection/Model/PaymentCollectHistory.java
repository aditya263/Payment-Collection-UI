package com.aditya.paymentcollection.Model;

public class PaymentCollectHistory {
    String companyName,date,amount;

    public PaymentCollectHistory(String companyName, String date, String amount) {
        this.companyName = companyName;
        this.date = date;
        this.amount = amount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
