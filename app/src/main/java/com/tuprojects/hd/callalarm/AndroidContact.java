package com.tuprojects.hd.callalarm;

import java.util.ArrayList;
import java.util.List;

public class AndroidContact {

    //Fields
    private String contactName = "";
    private List<String> contactPhoneNumber = new ArrayList<>();
    private int contactID;

    //Constructor
    public AndroidContact(String contactName) {
        this.contactName = contactName;
    }

    //Getters (accessors)
    public String getName() {
        return contactName;
    }

    public String getPhoneNum(int numberType) { //0 = primary number
        try {
            return contactPhoneNumber.get(numberType);
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getStrippedPhoneNum(int numberType) { //Contact stripped number to be used as ID to match w/ Call Log stripped number

        String strippedPhoneNum = "";

        try {
            String phoneNum = contactPhoneNumber.get(numberType);

            for (int i = 0; i < phoneNum.length(); i++) {
                String character = "";
                character += phoneNum.charAt(i);

                try {
                    Integer.parseInt(character);
                    strippedPhoneNum += character;
                } catch (NumberFormatException e) {
                    //do nothing
                }

            }

            return strippedPhoneNum;
        } catch (IndexOutOfBoundsException e) {
            return strippedPhoneNum;
        }
    }

    //Setters (mutators)
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void addPhoneNumber(String phoneNumber) {
        contactPhoneNumber.add(phoneNumber);
    }

    //Consider treating like a stack
}
