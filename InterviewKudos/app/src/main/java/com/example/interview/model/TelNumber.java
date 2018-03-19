package com.example.interview.model;

/**
 * The intent of this class is simply hold data about tel number. All logic should be implemented
 * in others classes
 *
 * Created by John on 9/11/2016.
 */
public class TelNumber {
    private String phoneNumber;
    private String phoneNumberPrice;
    private String phoneNumberOwner;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberPrice() {
        return phoneNumberPrice;
    }

    public void setPhoneNumberPrice(String phoneNumberPrice) {
        this.phoneNumberPrice = phoneNumberPrice;
    }

    public String getPhoneNumberOwner() {
        return phoneNumberOwner;
    }

    public void setPhoneNumberOwner(String phoneNumberOwner) {
        this.phoneNumberOwner = phoneNumberOwner;
    }
}
