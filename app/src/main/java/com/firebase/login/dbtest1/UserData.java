package com.firebase.login.dbtest1;

/**
 * Created by Alan Lal on 10-Nov-17.
 */

public class UserData {
    private String Email;
    private String Name;
    private String PhoneNumber;
    private String Status;

    public UserData() {
    }

    public UserData(String Email, String Name, String PhoneNumber, String Status) {
        this.Email = Email;
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
        this.Status = Status;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getStatus() {
        return Status;
    }
}
