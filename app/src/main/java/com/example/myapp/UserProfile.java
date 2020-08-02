package com.example.myapp;

public class UserProfile {
    public String Email;
    public String MobileNo;
    public String Name;

    public UserProfile(){

    }

    public UserProfile(String userEmail, String userMobileNo, String userName) {
        this.Email=userEmail;
        this.MobileNo=userMobileNo;
        this.Name=userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
