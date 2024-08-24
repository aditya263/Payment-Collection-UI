package com.aditya.paymentcollection.Model;

public class PaymentCollection {

    String txtCompanyName,txtEmployeeName,txtAmount,phoneNum,latitude,longitude;

    public PaymentCollection(String txtCompanyName, String txtEmployeeName, String txtAmount, String phoneNum, String latitude, String longitude) {
        this.txtCompanyName = txtCompanyName;
        this.txtEmployeeName = txtEmployeeName;
        this.txtAmount = txtAmount;
        this.phoneNum = phoneNum;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTxtCompanyName() {
        return txtCompanyName;
    }

    public void setTxtCompanyName(String txtCompanyName) {
        this.txtCompanyName = txtCompanyName;
    }

    public String getTxtEmployeeName() {
        return txtEmployeeName;
    }

    public void setTxtEmployeeName(String txtEmployeeName) {
        this.txtEmployeeName = txtEmployeeName;
    }

    public String getTxtAmount() {
        return txtAmount;
    }

    public void setTxtAmount(String txtAmount) {
        this.txtAmount = txtAmount;
    }
}
