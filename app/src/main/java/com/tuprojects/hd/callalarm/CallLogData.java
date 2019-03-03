package com.tuprojects.hd.callalarm;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallLogData {
    private String name = "";
    private String number = "";
    private String strippedNumber = "";
    private String type = "";

    private String date = "";
    private String formattedDate = "";

    private String duration = "";
    private int durationInt;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    public CallLogData(String name, String number, String type, String date, String duration) {

        if (name == null){
            this.name = number;
        } else {
            this.name = name;
        }

        this.number = number;

        for (int i = 0; i < number.length(); i++) {
            String character = "";
            character += this.number.charAt(i);

            try {
                Integer.parseInt(character);
                strippedNumber += character;
            } catch (NumberFormatException e) {
                //do nothing
            }

        }

        if (type.equals("Outgoing") && Integer.parseInt(duration) <= 5){ //proxy for missed outgoing calls until better method is found
            this.type = type+" (Missed)";
        } else {
            this.type = type;
        }

        this.date = date;

        //Date Formatting
        formattedDate = new SimpleDateFormat("MM-dd-yy hh:mm a").format(new Date(Long.valueOf(date)));

        this.duration = duration;

        durationInt = Integer.parseInt(duration);
        seconds = durationInt%60;
        minutes = durationInt/60;
        if(minutes>=60) {
            hours = minutes/60;
            minutes = minutes%60;
        }
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getStrippedNumber() {
        return strippedNumber;
    }

    public String getType() {
        return type;
    }

    public String getDuration() { //formatted
        if (hours>0) {
            return hours + " hrs., " + minutes + " min., " + seconds + " sec.";
        } else if (minutes>0) {
            return minutes + " min., " + seconds + " sec.";
        } else {
            return seconds + " sec.";
        }
    }

    public String getDate() {
        return formattedDate;
    }
}
