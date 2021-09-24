package com.example.a3rdeye.subscriber;

public class Notification_ModelClass {

    String date,time,notification_Message;

    public Notification_ModelClass(String date, String time, String notification_Message) {
        this.date = date;
        this.time = time;
        this.notification_Message = notification_Message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotification_Message() {
        return notification_Message;
    }

    public void setNotification_Message(String notification_Message) {
        this.notification_Message = notification_Message;
    }
}
